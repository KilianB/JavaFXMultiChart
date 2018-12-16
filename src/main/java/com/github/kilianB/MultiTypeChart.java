package com.github.kilianB;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.github.kilianB.Legend.LegendItem;
import com.github.kilianB.utility.ColorUtil;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.AccessibleRole;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

/**
 * 
 * 
 * 
 * This is by far the most hacky redundant and ugly code ever written. ...
 * 
 * @author Kilian
 *
 * @param <X>
 * @param <Y>
 */
public class MultiTypeChart<X, Y> extends XYChart<X, Y> {

	private static final Logger LOG = Logger.getLogger(MultiTypeChart.class.getName());

	public enum SeriesType {
		SCATTER, LINE, AREA, STACKED_AREA, STACKED_BAR
	}

	// Global chart properties
	private static final String COLOR_CSS_CLASS = "default-color";
	private static final String LINE_CHART_LINE_CSS_CLASS = "chart-series-line";
	private static final String LINE_CHART_SYMBOL_CSS_CLASS = "chart-line-symbol";
	private static final String SCATTER_CHART_SYMBOL_CSS_CLASS = "chart-symbol";

	// Area
	private static final String AREA_CHART_SYMBOL_CSS_CLASS = "chart-area-symbol";
	private static final String AREA_CHART_SERIES_LINE_CSS_CLASS = "chart-series-area-line";
	private static final String AREA_CHART_FILL_CSS_CLASS = "chart-series-area-fill";
	// Markers
	ReentrantLock markerLock = new ReentrantLock();
	private HashMap<ValueMarker<?>, Path> valueMarkers = new HashMap<>();

	// Keep track of used listeners to avoid memory leaks
	private HashMap<ValueMarker<?>, ChangeListener<Boolean>> valueMarkerLabelMap = new HashMap<>();

	protected HashMap<Series<X, Y>, TypedSeries<X, Y>> typedSeries = new LinkedHashMap<>();
	protected HashMap<Series<X, Y>, Boolean> seriesVisibility = new HashMap<>();
	protected HashMap<Series<X, Y>, SymbolType> seriesSymbolType = new HashMap<>();

	/** A package private hashmap in the chart object */
	protected Map<Series<X, Y>, Integer> seriesColorMap = new HashMap<>();
	protected BitSet availableColors = new BitSet(8);

	// TODO
//	private static final Color[] availableColorsExtended = new Color[164];
//	static {
//		
//	}

	// Area chart
	/** Remember the current animation state of area charts for layout */
	private Map<Series<X, Y>, DoubleProperty> seriesYMultiplierMap = new HashMap<>();

	protected Legend legend = new Legend();

	private Group plotArea;

	// Implement multi axis support
	private final int AXIS_PADDING = 5;
	private HashMap<Integer, Axis<X>> additionalXAxis = new HashMap<>();
	private HashMap<Integer, Axis<Y>> additionalYAxis = new HashMap<>();

	/**
	 * Legend items
	 */
	private HashMap<Series<X, Y>, LegendItem> legendMap;

	/**
	 * 
	 * @param index
	 * @param xAxis true xAxis false yAxis
	 */
	@SuppressWarnings("unchecked")
	protected void addAdditionalAxis(int index, boolean isX, Side side) {

		// todo only number?
		Axis<?> axis;

		if (isX) {
			// XAxis
			axis = new NumberAxis();
			additionalXAxis.put(index, (Axis<X>) axis);
		} else {
			// YAxis
			axis = new NumberAxis();
			additionalYAxis.put(index, (Axis<Y>) axis);
		}

		axis.setSide(side);

		// package private. But as far as I can see this is not necessary when the side
		// is set?
		// axis.setEffectiveOrientation(Orientation.VERTICAL);

		axis.autoRangingProperty().addListener((ov, t, t1) -> {
			updateAxisRange();
		});

		getChartChildren().add(axis);

		// Make space for the axis. We are using the padding since we don't have enough
		// access to other components

		// Construct the binding
		ReadOnlyDoubleProperty[] axisWidthAndHeightProperty = new ReadOnlyDoubleProperty[additionalYAxis.size()
				+ additionalXAxis.size()];

		int i = 0;
		for (var entry : additionalYAxis.entrySet()) {
			axisWidthAndHeightProperty[i++] = entry.getValue().widthProperty();
		}

		for (var entry : additionalXAxis.entrySet()) {
			axisWidthAndHeightProperty[i++] = entry.getValue().heightProperty();
		}

		ObjectBinding<Insets> paddingBinding = new ObjectBinding<>() {

			{
				super.bind(axisWidthAndHeightProperty);
			}

			@Override
			protected Insets computeValue() {

				double top = 0, right = 0, bottom = 0, left = 0;

				for (var entry : additionalYAxis.entrySet()) {

					double width = entry.getValue().getWidth();

					switch (entry.getValue().getSide()) {
					case LEFT:
						left += (width + AXIS_PADDING);
						break;
					case RIGHT:
						right += (width + AXIS_PADDING);
						break;
					default:
						throw new IllegalStateException("Y Axis may only be positioned right or left");
					}
				}

				for (var entry : additionalXAxis.entrySet()) {
					double height = entry.getValue().getHeight();

					switch (entry.getValue().getSide()) {
					case TOP:
						top += (height + AXIS_PADDING);
						break;
					case BOTTOM:
						bottom += (height + AXIS_PADDING);
						break;
					default:
						throw new IllegalStateException("X Axis may only be positioned top or bottom");
					}
				}
				return new Insets(top, right, bottom, left);
			}
		};

		this.paddingProperty().bind(paddingBinding);

		this.requestChartLayout();
	}

	public MultiTypeChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
		super(xAxis, yAxis);
		
		//Add css
		String css = getClass().getClassLoader().getResource("chart.css").toExternalForm();
		System.out.println("CSS: " + css);
		this.getStylesheets().add(css);
		
		setData(FXCollections.<Series<X, Y>>observableArrayList());

		plotArea = (Group) this.lookup(".chart-horizontal-grid-lines").getParent();

		// Fetch references of package private fields

		// Starting with jdk 9 "illegal" reflection output a warning. Since we don't
		// want every user to run vm-args simply handle colors ourselves.
		// try {
		// Field f = this.getClass().getSuperclass().getDeclaredField("seriesColorMap");
		// f.setAccessible(true);
		// seriesColorMap = (Map<Series<X, Y>, Integer>) f.get(this);
		// f.setAccessible(false);
		// } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
		// | IllegalAccessException e) {
		// e.printStackTrace();
		// }
		seriesColorMap = new HashMap<>();

	}

	public boolean addSeries(TypedSeries<X, Y> series) throws IllegalArgumentException {
		if (!typedSeries.containsKey(series.getSeries())) {

			// JavaFX uses weak listeners? No need to dispose afterwards

			series.seriesProperty().addListener((change) -> {
				// TODO REALLY FIX IS NEEDED HERE!
				// if we add and remove it the listener will be attached twice?
				this.getData().remove(series.getSeries());
				this.addSeries(series);
				// TODO we need to change multiplier maps etc .... we can't just change
				// Series. maybe remove and re add?
			});

			// Update ticks depending on the show symbol property
			series.showSymbolsProperty().addListener((obs, oldValue, newValue) -> {

				// Not used for scatter charts
				if (series.getSeriesType().equals(SeriesType.SCATTER)) {
					return;
				}

				// we need to change the series
				Series<X, Y> ser = series.getSeries();
				SymbolType t = seriesSymbolType.get(series);
				String symTypeCss = (t != null) ? t.name() : "";

				for (int itemIndex = 0; itemIndex < ser.getData().size(); itemIndex++) {
					Data<X, Y> item = ser.getData().get(itemIndex);
					Node symbol = item.getNode();
					if (newValue && symbol == null) { // create any symbols

						String identifier = "";
						switch (series.getSeriesType()) {
						case LINE:
							identifier = LINE_CHART_SYMBOL_CSS_CLASS;
							break;
						case AREA:
							identifier = AREA_CHART_SYMBOL_CSS_CLASS;
							break;
						}
						this.createSymbols(ser, itemIndex, item, identifier, symTypeCss);

						symbol = item.getNode();
						getPlotChildren().add(symbol);
					} else if (!newValue && symbol != null) { // remove symbols
						getPlotChildren().remove(symbol);
						symbol = null;
						item.setNode(null);
					}
				}
				requestChartLayout();
			});

			typedSeries.put(series.getSeries(), series);
			seriesVisibility.put(series.getSeries(), Boolean.TRUE);

			// Check if we need to add an additional axis
			int yAxisIndex = series.getYAxisIndex();
			int xAxisIndex = series.getXAxisIndex();

			if (yAxisIndex != 0 && !additionalYAxis.containsKey(yAxisIndex)) {
				addAdditionalAxis(yAxisIndex, false, series.getyAxisSide());
			}

			if (xAxisIndex != 0 && !additionalXAxis.containsKey(xAxisIndex)) {
				addAdditionalAxis(xAxisIndex, true, series.getxAxisSide());
			}

			getData().add(series.getSeries());

			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
		switch (typedSeries.get(series).getSeriesType()) {
		case AREA:
			addAreaItem(series, itemIndex, item);
			break;
		case LINE:
			addLineItem(series, itemIndex, item);
			break;
		case SCATTER:
			addScatterItem(series, itemIndex, item);
			break;
		case STACKED_AREA:
			break;
		case STACKED_BAR:
			break;
		default:
			break;
		}
	}

	@Override
	protected void dataItemRemoved(Data<X, Y> item, Series<X, Y> series) {
		// TODO Auto-generated method stub

	}

	// Never overwritten. some other requestChartLayout probably catches it
	@Override
	protected void dataItemChanged(Data<X, Y> item) {
	}

	@Override
	protected void seriesAdded(Series<X, Y> series, int seriesIndex) {

		int freeIndex = availableColors.nextClearBit(0) % availableColors.size();
		availableColors.set(freeIndex);

		seriesColorMap.put(series, freeIndex);

		// Chart specific setup
		TypedSeries<X, Y> ser = typedSeries.get(series);

		switch (ser.getSeriesType()) {
		case AREA:
			// create new paths for series
			Path seriesLine = new Path();
			Path fillPath = new Path();
			seriesLine.setStrokeLineJoin(StrokeLineJoin.BEVEL);
			Group areaGroup = new Group(fillPath, seriesLine);
			series.setNode(areaGroup);
			// create series Y multiplier
			DoubleProperty seriesYAnimMultiplier = new SimpleDoubleProperty(this, "seriesYMultiplier");
			seriesYMultiplierMap.put(series, seriesYAnimMultiplier);
			seriesYAnimMultiplier.setValue(1d);
			getPlotChildren().add(areaGroup);
			break;
		case LINE:
			// Line and area charts require a path node
			seriesLine = new Path();
			seriesLine.setStrokeLineJoin(StrokeLineJoin.BEVEL);
			series.setNode(seriesLine);
			// create series Y multiplier
			seriesYAnimMultiplier = new SimpleDoubleProperty(this, "seriesYMultiplier");
			seriesYMultiplierMap.put(series, seriesYAnimMultiplier);
			seriesYAnimMultiplier.setValue(1d);
			getPlotChildren().add(seriesLine);
			break;
		case SCATTER:
			// No need to set up
			break;
		case STACKED_AREA:
		case STACKED_BAR:
			throw new UnsupportedOperationException("Not implemented yet");
		default:
			break;

		}

		// Finally add the individual data points
		for (int j = 0; j < series.getData().size(); j++) {
			dataItemAdded(series, j, series.getData().get(j));
		}
	}

	@Override
	protected void seriesRemoved(Series<X, Y> series) {

		SeriesType removedSeriesType = typedSeries.remove(series).getSeriesType();

		if (shouldAnimate()) {

			int animationDuration = 0;
			switch (removedSeriesType) {
			case LINE:
				animationDuration = 900;
				break;
			case SCATTER:
				animationDuration = 400;
				break;
			default:
				animationDuration = 500;
			}

			// Can't we use this for all chart types? Why do we need an additional one for
			// catter?

			// What happened to the exceptions?
			// TODO do we want to use reflection or simply copy and paste the method?
			try {
				Method method = XYChart.class.getMethod("createSeriesRemoveTimeLine", Series.class, Integer.class);
				method.setAccessible(true);
				KeyFrame[] keyframes = (KeyFrame[]) method.invoke(this, series, animationDuration);
				method.setAccessible(false);
				Timeline tl = new Timeline(keyframes);
				tl.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			// Not all types of charts use series nodes. Check before removing
			if (series.getNode() != null) {
				getPlotChildren().remove(series.getNode());
			}

			// Everything else is the same
			for (final Data<X, Y> d : series.getData()) {
				final Node symbol = d.getNode();
				getPlotChildren().remove(symbol);
			}
			removeSeriesFromDisplay(series);
		}

		// if (shouldAnimate()) {
		// ParallelTransition pt = new ParallelTransition();
		// pt.setOnFinished(event -> {
		// removeSeriesFromDisplay(series);
		// });
		// for (final Data<X, Y> d : series.getData()) {
		// final Node symbol = d.getNode();
		// // fade out old symbol
		// FadeTransition ft = new FadeTransition(Duration.millis(500), symbol);
		// ft.setToValue(0);
		// ft.setOnFinished(actionEvent -> {
		// getPlotChildren().remove(symbol);
		// symbol.setOpacity(1.0);
		// });
		// pt.getChildren().add(ft);
		// }
		// pt.play();
		// }

	}

	@Override
	protected void seriesChanged(ListChangeListener.Change<? extends Series> c) {

		for (int i = 0; i < getData().size(); i++) {
			final Series<X, Y> s = getData().get(i);

			TypedSeries<X, Y> type = typedSeries.get(s);

			String seriesColor = COLOR_CSS_CLASS + seriesColorMap.get(s);

			SymbolType symType = seriesSymbolType.get(s);
			String symTypeCss = (symType != null) ? symType.name() : "";

			switch (type.getSeriesType()) {
			case AREA:
				Path seriesLine = (Path) ((Group) s.getNode()).getChildren().get(1);
				Path fillPath = (Path) ((Group) s.getNode()).getChildren().get(0);
				seriesLine.getStyleClass().setAll(AREA_CHART_SERIES_LINE_CSS_CLASS, "series" + i, seriesColor);
				fillPath.getStyleClass().setAll(AREA_CHART_FILL_CSS_CLASS, "series" + i, seriesColor);

				for (int j = 0; j < s.getData().size(); j++) {
					final Data<X, Y> item = s.getData().get(j);
					final Node node = item.getNode();
					if (node != null)
						node.getStyleClass().setAll(AREA_CHART_SYMBOL_CSS_CLASS, "series" + i, "data" + j, seriesColor,
								symTypeCss);
				}
				break;
			case LINE:
				Node seriesNode = s.getNode();
				if (seriesNode != null) {
					seriesNode.getStyleClass().setAll(LINE_CHART_LINE_CSS_CLASS, "series" + i, seriesColor);
				}
					
				for (int j = 0; j < s.getData().size(); j++) {
					final Node symbol = s.getData().get(j).getNode();
					if (symbol != null) {
						symbol.getStyleClass().setAll(LINE_CHART_SYMBOL_CSS_CLASS, "series" + i, "data" + j,
								seriesColor, symTypeCss);
					}	
				}
				break;

			case SCATTER:

				for (int j = 0; j < s.getData().size(); j++) {
					final Node symbol = s.getData().get(j).getNode();
					if (symbol != null) {
						symbol.getStyleClass().setAll(SCATTER_CHART_SYMBOL_CSS_CLASS, "series" + i, "data" + j,
								seriesColor, symTypeCss);
					}

				}
				break;
			case STACKED_AREA:
			case STACKED_BAR:
			default:
				throw new UnsupportedOperationException("Not implemented yet");
			}

		}
	}

	// Layout

	@Override
	protected void layoutPlotChildren() {

		List<LineTo> constructedPath = new ArrayList<>(getDataSizeMultiType());

		series: for (int seriesIndex = 0; seriesIndex < getDataSizeMultiType(); seriesIndex++) {
			Series<X, Y> series = getData().get(seriesIndex);

			// If it's not visible don't layout

			var typed = typedSeries.get(series);

			SeriesType type = typed.getSeriesType();
			int xAxisIndex = typed.getXAxisIndex();
			int yAxisIndex = typed.getYAxisIndex();

			final Axis<X> xAxis;
			final Axis<Y> yAxis;

			if (xAxisIndex == 0) {
				xAxis = getXAxis();
			} else {
				xAxis = additionalXAxis.get(xAxisIndex);
			}

			if (yAxisIndex == 0) {
				yAxis = getYAxis();
			} else {
				yAxis = additionalYAxis.get(yAxisIndex);
			}

			// Not really object oriented but hey. Work with what we got here ...
			switch (type) {
			case AREA:
				layoutAreaChart(series, constructedPath, xAxis, yAxis);
				break;
			case LINE:
				layoutLineChart(series, constructedPath, xAxis, yAxis);
				break;
			case SCATTER:
				layoutScatterSeries(series, xAxis, yAxis);
				break;
			default:
				LOG.warning("No layout method found for: " + type.name() + ". Skip series: " + series);
				continue series;
			}
		}

		// Take care of markers

		final Axis<X> xAxis = getXAxis();
		final Axis<Y> yAxis = getYAxis();
		// double maxY = xAxis.getWidth();
		// double maxX = yAxis.getHeight();

		// 10 px padding
		double yMin = yAxis.getBoundsInLocal().getMaxY() + 5;
		double yMax = yMin - plotArea.getBoundsInParent().getHeight();

		markerLock.lock();
		Iterator<Entry<ValueMarker<?>, Path>> iter = valueMarkers.entrySet().iterator();

		while (iter.hasNext()) {

			var entry = iter.next();
			ValueMarker<?> marker = entry.getKey();
			Path p = entry.getValue();
			p.getElements().clear();
			try {
				p.setStroke(marker.getColor());

				Label label = null;
				if (marker.enableLabelProperty().get()) {
					label = (Label) marker.getLabel();
					label.setTextFill(ColorUtil.getContrastColor(marker.getColor()));
					label.setBackground(
							new Background(new BackgroundFill(marker.getColor(), new CornerRadii(3), null)));
					// relocate label

				}

				if (marker.isVertical()) {

					double x = xAxis.getDisplayPosition((X) marker.getValue()) + xAxis.getLayoutX();
					// Check if it is currently displayed
					if (!Double.valueOf(x).isNaN() && (x != xAxis.getZeroPosition() || !isVerticalZeroLineVisible())
							&& x > 0 && x <= xAxis.getWidth()) {
						// 0.5 offest to counter anti aliasing
						p.getElements().addAll(new MoveTo(x + 0.5, yMin), new LineTo(x + 0.5, yMax));

						if (label != null) {
							final double w = label.prefWidth(-1);
							final double h = label.prefHeight(-1);
							label.resizeRelocate(x - w / 2, yMin, w, h);
						}

					}
				} else {
					// Horizontal
					double y = yAxis.getDisplayPosition((Y) marker.getValue()) + yAxis.getLayoutY();
					if (!Double.valueOf(y).isNaN() && (y != yAxis.getZeroPosition() || !isHorizontalZeroLineVisible())
							&& y > 0 && y <= yAxis.getHeight()) {
						p.getElements().addAll(new MoveTo(yAxis.getLayoutX() + yAxis.getWidth(), y + 0.5),
								new LineTo(yAxis.getLayoutX() + yAxis.getWidth() + xAxis.getWidth(), y + 0.5));

						if (label != null) {
							final double w = label.prefWidth(-1);
							final double h = label.prefHeight(-1);
							label.resizeRelocate(xAxis.getLayoutX() - w, y - h / 2, w, h);
						}

					}
				}
			} catch (ClassCastException ex) {
				LOG.severe("Could not cast value markers value to axis." + marker + " Detatch marker from chart");
				iter.remove();
				// Remove the path as well
				getChartChildren().remove(p);
				ChangeListener<Boolean> l = valueMarkerLabelMap.remove(marker);
				if (l != null) {
					marker.enableLabelProperty().removeListener(l);
				}
			}

		}
		markerLock.unlock();

		// layout additional axis should be done in requestAxisLayout but it's once
		// again final ....

		double offsetRight = 0;
		double offsetLeft = -(yAxis.getWidth() * 2 + AXIS_PADDING);
		double offsetTop = 0;
		double offsetBottom = xAxis.getHeight() + AXIS_PADDING;

		for (var axisEntry : additionalYAxis.entrySet()) {
			// Shall we create an array after each new axis addition / deletion for quicker
			// traversal?
			// Hashmaps are more expensive for sure...
			Axis<Y> additional = axisEntry.getValue();
			// Original Y Axis is already layed out. We still need to compute the width as
			// label sizes might
			// differ depending on values
			double prefWidth = snapSizeY(additional.prefWidth(getYAxis().getHeight()));

			if (additional.getSide().equals(Side.RIGHT)) {
				additional.resizeRelocate(plotArea.getBoundsInParent().getMaxX() + offsetRight, getYAxis().getLayoutY(),
						prefWidth, getYAxis().getHeight());
				offsetRight += prefWidth + AXIS_PADDING;
			} else if (additional.getSide().equals(Side.LEFT)) {
				additional.resizeRelocate(plotArea.getBoundsInParent().getMinX() + offsetLeft, getYAxis().getLayoutY(),
						prefWidth, getYAxis().getHeight());
				offsetLeft -= (prefWidth + AXIS_PADDING);
			}
		}

		for (var axisEntry : additionalXAxis.entrySet()) {
			// Shall we create an array after each new axis addition / deletion for quicker
			// traversal?
			// Hashmaps are more expensive for sure...
			Axis<X> additional = axisEntry.getValue();
			// Original Y Axis is already layed out. We still need to compute the width as
			// label sizes might
			// differ depending on values
			double prefHeight = snapSizeY(additional.prefHeight(getXAxis().getWidth()));

			if (additional.getSide().equals(Side.TOP)) {
				additional.resizeRelocate(plotArea.getBoundsInParent().getMinX(),
						plotArea.getBoundsInParent().getMinY() + offsetTop - prefHeight, xAxis.getWidth(), prefHeight);

				offsetTop -= (prefHeight + AXIS_PADDING);
			} else if (additional.getSide().equals(Side.BOTTOM)) {
//				additional.resizeRelocate(
//						plotArea.getBoundsInParent().getMinX(),
//						xAxis.getLayoutBounds().getMaxY() + offsetBottom,
//						xAxis.getWidth(), 
//						prefHeight);
//				offsetLeft -= (prefHeight + AXIS_PADDING);
			}

		}
//		

	}

	protected void layoutLineChart(Series<X, Y> series, List<LineTo> constructedPath, Axis<X> xAxis, Axis<Y> yAxis) {
		final Node seriesNode = series.getNode();
		if (seriesNode instanceof Path) {

			boolean visible = seriesVisibility.get(series);

			if (visible) {
				makePaths(series, constructedPath, null, (Path) seriesNode, typedSeries.get(series).getSortingPolicy(),
						xAxis, yAxis);
			}

			seriesNode.setVisible(visible);

			for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
				it.next().getNode().setVisible(visible);
			}

//			if(!visible) {
//				if(!cssPath.contains("hide-series-symbol")) {
//					cssPath.add("hide-series-symbol");
//
//					//Also add the hide artifact to the symbols
//					for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
//						Node symbol = it.next().getNode();
//						symbol.getStyleClass().add("hide-series-symbol");
//					}
//				}	
//			}else {
//				if(cssPath.contains("hide-series-symbol")) {
//					cssPath.remove("hide-series-symbol");
//					//Also add the hide artifact to the symbols
//					for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
//						Node symbol = it.next().getNode();
//						symbol.getStyleClass().remove("hide-series-symbol");
//					}
//				}
//				
//			}
		}
	}

	protected void layoutAreaChart(Series<X, Y> series, List<LineTo> constructedPath, Axis<X> xAxis, Axis<Y> yAxis) {
		final ObservableList<Node> children = ((Group) series.getNode()).getChildren();

		boolean visible = seriesVisibility.get(series);

		Path fillPath = (Path) children.get(0);
		Path linePath = (Path) children.get(1);

		if (visible) {
			makePaths(series, constructedPath, fillPath, linePath, SortingPolicy.X_AXIS, xAxis, yAxis);
		}

		fillPath.setVisible(visible);
		linePath.setVisible(visible);

		for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
			it.next().getNode().setVisible(visible);
		}

	}

	protected void layoutScatterSeries(Series<X, Y> series, Axis<X> xAxis, Axis<Y> yAxis) {

		boolean visible = seriesVisibility.get(series);

		// TODO they are still displayed since they
		for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
			Data<X, Y> item = it.next();
			// We don't have access to the item's current value circumvent using chart
			// methods
			double x = xAxis.getDisplayPosition(this.getCurrentDisplayedXValue(item));
			double y = yAxis.getDisplayPosition(this.getCurrentDisplayedYValue(item));
			if (Double.isNaN(x) || Double.isNaN(y)) {
				return;
			}
			Node symbol = item.getNode();

			if (symbol != null) {
				if (visible) {
					symbol.setVisible(true);
					final double w = symbol.prefWidth(-1);
					final double h = symbol.prefHeight(-1);
					symbol.resizeRelocate(x - (w / 2), y - (h / 2), w, h);
				} else {
					symbol.setVisible(false);
				}
			}

//			var css = symbol.getStyleClass();
//			if(!visible) {
//				if(!css.contains("hide-series-symbol")) {
//					css.add("hide-series-symbol");
//				}
//			}else {
//				
//				while(css.contains("hide-series-symbol")) {
//					css.remove("hide-series-symbol");
//				}
//
//				if (symbol != null) {
//					final double w = symbol.prefWidth(-1);
//					final double h = symbol.prefHeight(-1);
//					symbol.resizeRelocate(x - (w / 2), y - (h / 2), w, h);
//				}
//			}
			symbol.applyCss();
		}
	}

	// Add data item
	protected void addAreaItem(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
		// TODO add animation code

		SymbolType t = seriesSymbolType.get(series);
		String symTypeCss = (t != null) ? t.name() : "";

		createSymbols(series, itemIndex, item, AREA_CHART_SYMBOL_CSS_CLASS, symTypeCss);
		getPlotChildren().add(item.getNode());
	}

	protected void addScatterItem(Series<X, Y> series, int itemIndex, Data<X, Y> item) {

		SymbolType t = seriesSymbolType.get(series);
		String symTypeCss = (t != null) ? t.name() : "";

		createSymbols(series, itemIndex, item, SCATTER_CHART_SYMBOL_CSS_CLASS, symTypeCss);
		Node symbol = item.getNode();

		// add and fade in new symbol if animated
		if (shouldAnimate()) {
			symbol.setOpacity(0);
			getPlotChildren().add(symbol);
			FadeTransition ft = new FadeTransition(Duration.millis(500), symbol);
			ft.setToValue(1);
			ft.play();
		} else {
			getPlotChildren().add(symbol);
		}
	}

	protected void addLineItem(Series<X, Y> series, int itemIndex, Data<X, Y> item) {

		SymbolType t = seriesSymbolType.get(series);
		String symTypeCss = (t != null) ? t.name() : "";

		// Create the node
		if (typedSeries.get(series).showSymbolsProperty().get()) {
			createSymbols(series, itemIndex, item, LINE_CHART_SYMBOL_CSS_CLASS, symTypeCss);
		}
		
		Node symbol = item.getNode();

		if (symbol != null) {
			getPlotChildren().add(symbol);
		}

		// TODO add animation code
	}

	private void createSymbols(Series<X, Y> series, int itemIndex, Data<X, Y> item, String symbolCSSIdentifier,
			String additionalCss) {
		Node symbol = item.getNode();
		// TODO should we also check here is e.g. scatter chart ticks should be drawn?
		if (symbol == null) {
			symbol = new StackPane();
			symbol.setAccessibleRole(AccessibleRole.TEXT);
			symbol.setAccessibleRoleDescription("Point");
			symbol.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
			item.setNode(symbol);
		}

		if (symbol != null) {
			symbol.getStyleClass().addAll(symbolCSSIdentifier, "series" + getData().indexOf(series), "data" + itemIndex,
					COLOR_CSS_CLASS + seriesColorMap.get(series), additionalCss);
		}
	}

	// Go with the more specific implementation for line and area charts.
	// TODO we could auto range to not stick to the 0 line

	@Override
	protected void updateAxisRange() {
		// default axis
		final Axis<X> xa = getXAxis();
		final Axis<Y> ya = getYAxis();

		// Feetch axis related data
		HashMap<Integer, List<X>> xData = new HashMap<>();
		HashMap<Integer, List<Y>> yData = new HashMap<>();

		/*
		 * Check if we have at least one axis which requires automatic layouting
		 */
		boolean oneAutoRanging = false;

		// Default axis
		if (xa.isAutoRanging()) {
			xData.put(0, new ArrayList<X>());
			oneAutoRanging = true;
		}

		if (ya.isAutoRanging()) {
			yData.put(0, new ArrayList<Y>());
			oneAutoRanging = true;
		}

		for (var entry : additionalYAxis.entrySet()) {

			if (entry.getValue().isAutoRanging()) {
				yData.put(entry.getKey(), new ArrayList<Y>());
				oneAutoRanging = true;
			}
		}

		for (var entry : additionalXAxis.entrySet()) {
			if (entry.getValue().isAutoRanging()) {
				xData.put(entry.getKey(), new ArrayList<X>());
				oneAutoRanging = true;
			}
		}

		if (oneAutoRanging) {

			// Collect data for each axis
			for (Series<X, Y> series : getData()) {

				// If the series is not visible don't include it in the axis range
				if (!seriesVisibility.get(series)) {
					continue;
				}

				// To which axis does it belong to?
				var typed = typedSeries.get(series);
				int yAxisIndex = typed.getYAxisIndex();
				int xAxisIndex = typed.getXAxisIndex();

				var xDataAxis = xData.get(xAxisIndex);
				var yDataAxis = yData.get(yAxisIndex);

				if (xDataAxis == null && yDataAxis == null) {
					continue;
				}
				for (Data<X, Y> data : series.getData()) {
					if (xDataAxis != null)
						xDataAxis.add(data.getXValue());
					if (yDataAxis != null)
						yDataAxis.add(data.getYValue());
				}
			}

			// Push new data to each axis
			for (var entry : xData.entrySet()) {

				int axisIndex = entry.getKey();
				List<X> xDataPoints = entry.getValue();
				Axis<X> axis;

				if (axisIndex == 0) {
					axis = xa;
				} else {
					axis = additionalXAxis.get(axisIndex);
				}

				if (xDataPoints != null && !xDataPoints.isEmpty()
						&& !(xDataPoints.size() == 1 && axis.toNumericValue(xDataPoints.get(0)) == 0)) {
					axis.invalidateRange(xDataPoints);
				}
			}

			for (var entry : yData.entrySet()) {

				int axisIndex = entry.getKey();
				List<Y> yDataPoints = entry.getValue();
				Axis<Y> axis;

				if (axisIndex == 0) {
					axis = ya;
				} else {
					axis = additionalYAxis.get(axisIndex);
				}

				if (yDataPoints != null && !yDataPoints.isEmpty()
						&& !(yDataPoints.size() == 1 && axis.toNumericValue(yDataPoints.get(0)) == 0)) {
					axis.invalidateRange(yDataPoints);
				}
			}
		}
	}

	// utility
	protected int getDataSizeMultiType() {
		final ObservableList<Series<X, Y>> data = getData();
		return (data != null) ? data.size() : 0;
	}

	protected void makePaths(Series<X, Y> series, List<LineTo> constructedPath, Path fillPath, Path linePath,
			SortingPolicy sortAxis, Axis<X> xAxis, Axis<Y> yAxis) {

		double yAnimMultiplier = seriesYMultiplierMap.get(series).get();
		final double hlw = linePath.getStrokeWidth() / 2.0;
		final boolean sortX = (sortAxis == SortingPolicy.X_AXIS);
		final boolean sortY = (sortAxis == SortingPolicy.Y_AXIS);
		final double dataXMin = sortX ? -hlw : Double.NEGATIVE_INFINITY;
		final double dataXMax = sortX ? xAxis.getWidth() + hlw : Double.POSITIVE_INFINITY;
		final double dataYMin = sortY ? -hlw : Double.NEGATIVE_INFINITY;
		final double dataYMax = sortY ? yAxis.getHeight() + hlw : Double.POSITIVE_INFINITY;
		LineTo prevDataPoint = null;
		LineTo nextDataPoint = null;
		constructedPath.clear();
		for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
			Data<X, Y> item = it.next();
			double x = xAxis.getDisplayPosition(getCurrentDisplayedXValue(item));
			double y = yAxis.getDisplayPosition(
					yAxis.toRealValue(yAxis.toNumericValue(getCurrentDisplayedYValue(item)) * yAnimMultiplier));
			boolean skip = (Double.isNaN(x) || Double.isNaN(y));
			Node symbol = item.getNode();
			if (symbol != null) {
				final double w = symbol.prefWidth(-1);
				final double h = symbol.prefHeight(-1);
				if (skip) {
					symbol.resizeRelocate(-w * 2, -h * 2, w, h);
				} else {
					symbol.resizeRelocate(x - (w / 2), y - (h / 2), w, h);
				}
			}
			if (skip)
				continue;
			if (x < dataXMin || y < dataYMin) {
				if (prevDataPoint == null) {
					prevDataPoint = new LineTo(x, y);
				} else if ((sortX && prevDataPoint.getX() <= x) || (sortY && prevDataPoint.getY() <= y)) {
					prevDataPoint.setX(x);
					prevDataPoint.setY(y);
				}
			} else if (x <= dataXMax && y <= dataYMax) {
				constructedPath.add(new LineTo(x, y));
			} else {
				if (nextDataPoint == null) {
					nextDataPoint = new LineTo(x, y);
				} else if ((sortX && x <= nextDataPoint.getX()) || (sortY && y <= nextDataPoint.getY())) {
					nextDataPoint.setX(x);
					nextDataPoint.setY(y);
				}
			}
		}

		if (!constructedPath.isEmpty() || prevDataPoint != null || nextDataPoint != null) {
			if (sortX) {
				Collections.sort(constructedPath, (e1, e2) -> Double.compare(e1.getX(), e2.getX()));
			} else if (sortY) {
				Collections.sort(constructedPath, (e1, e2) -> Double.compare(e1.getY(), e2.getY()));
			} else {
				// assert prevDataPoint == null && nextDataPoint == null
			}
			if (prevDataPoint != null) {
				constructedPath.add(0, prevDataPoint);
			}
			if (nextDataPoint != null) {
				constructedPath.add(nextDataPoint);
			}

			// assert !constructedPath.isEmpty()
			LineTo first = constructedPath.get(0);
			LineTo last = constructedPath.get(constructedPath.size() - 1);

			final double displayYPos = first.getY();

			ObservableList<PathElement> lineElements = linePath.getElements();
			lineElements.clear();
			lineElements.add(new MoveTo(first.getX(), displayYPos));
			lineElements.addAll(constructedPath);

			if (fillPath != null) {
				ObservableList<PathElement> fillElements = fillPath.getElements();
				fillElements.clear();
				double yOrigin = yAxis.getDisplayPosition(yAxis.toRealValue(0.0));

				fillElements.add(new MoveTo(first.getX(), yOrigin));
				fillElements.addAll(constructedPath);
				fillElements.add(new LineTo(last.getX(), yOrigin));
				fillElements.add(new ClosePath());
			}
		}
	}

	public void setSeriesVisibility(TypedSeries series, boolean b) {

		LegendItem lItem = legendMap.get(series.getSeries());

		// Get the legends and update it accordingly
		Node symbol = lItem.getSymbol();
		ObservableList<String> cssClass = symbol.getStyleClass();
		// TODO duplicated code.
		if (cssClass.contains("hide-series")) {
			symbol.setEffect(null);
			symbol.getStyleClass().remove("hide-series");
			toggleSeriesVisability(series.getSeries(), true);
		} else {
			symbol.getStyleClass().add("hide-series");
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setSaturation(-0.5);
			symbol.setEffect(colorAdjust);
			toggleSeriesVisability(series.getSeries(), false);
		}

	}

	protected void toggleSeriesVisability(Series<X, Y> series, boolean b) {
		seriesVisibility.put(series, b);
		this.requestChartLayout();
	}

	/**
	 * 
	 * @param series
	 * @return true if the series is displayed false if it is hidden
	 */
	public boolean isSeriesVisible(TypedSeries series) {
		return seriesVisibility.get(series.getSeries());
	}

	@Override
	protected void updateLegend() {
		legendMap = new LinkedHashMap<>();
		if (getData() != null) {
			for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
				Series<X, Y> series = getData().get(seriesIndex);

				String name = series.getName();
				if (name == null) {
					name = "Series " + seriesIndex;
				}

				SymbolType t = seriesSymbolType.get(series);
				String symTypeCss = (t != null) ? t.name() : "";

				Node symbol = new StackPane();
				symbol.getStyleClass().addAll("chart-legend-symbol", "default-color" + seriesColorMap.get(series),
						"chart-symbol", symTypeCss); // This css class contains background-color of the chart. Just go
														// ahead and use
				// it
				LegendItem lItem = new LegendItem(name, symbol);

				lItem.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {

						ObservableList<String> cssClass = symbol.getStyleClass();

						if (cssClass.contains("hide-series")) {
							symbol.setEffect(null);
							symbol.getStyleClass().remove("hide-series");
							toggleSeriesVisability(series, true);
						} else {
							symbol.getStyleClass().add("hide-series");
							ColorAdjust colorAdjust = new ColorAdjust();
							colorAdjust.setSaturation(-0.5);
							symbol.setEffect(colorAdjust);
							toggleSeriesVisability(series, false);
						}
					}
				});
				legendMap.put(series, lItem);
			}
		}

		legend.getItems().setAll(legendMap.values());
		setLegend(legend);
		if (legendMap.size() > 0) {
			if (getLegend() == null) {
				setLegend(legend);
			}
		} else {
			setLegend(null);
		}
	}
	/*
	 * These methods are useful for so many use cases. They should be publicly
	 * accessible by default!!
	 * 
	 */

	/**
	 * Get the JavaFX X coordinate of the item
	 * 
	 * @param item
	 * @return
	 */
	public X getCoordinate(Data<X, Y> item) {
		return this.getCurrentDisplayedXValue(item);
	}

	/**
	 * Get the JavaFX Y coordinate of the item
	 * 
	 * @param item
	 * @return
	 */
	public Y getYCoordinate(Data<X, Y> item) {
		return this.getCurrentDisplayedYValue(item);
	}

	/*
	 * Value markers
	 * 
	 * 3 ways we can implement value markers. 1. Either we create a separate series
	 * 2. Draw a line on top using stackpanes 3. draw on the plot area emulating
	 * grid line approach
	 * 
	 * 1. Is a bit more tricky if we want seperate colors and custom labels
	 * therefore le
	 */

	public void addValueMarker(ValueMarker<?> markerToAdd) {
		markerLock.lock();
		Path p = new Path();
		valueMarkers.put(markerToAdd, p);

		ChangeListener<Boolean> cl = (obs, oldValue, newValue) -> {
			if (newValue) {
				getChartChildren().add(((ValueMarker<?>) obs).getLabel());
			} else {
				getChartChildren().remove(((ValueMarker<?>) obs).getLabel());
			}
			requestChartLayout();
		};

		valueMarkerLabelMap.put(markerToAdd, cl);
		markerToAdd.enableLabelProperty().addListener(cl);

		// Add it to the chart
		getChartChildren().add(p);

		if (markerToAdd.enableLabelProperty().get()) {
			getChartChildren().add(markerToAdd.getLabel());
		}

		markerLock.unlock();
	}

	public void removeValueMarker(ValueMarker<?> markerToRemove) {
		markerLock.lock();
		Path p = valueMarkers.remove(markerToRemove);
		if (p != null) {
			getChartChildren().remove(p);
		}

		// We also have to remove the listener or else we leake memory
		ChangeListener<Boolean> l = valueMarkerLabelMap.remove(markerToRemove);
		if (l != null) {
			markerToRemove.enableLabelProperty().removeListener(l);
		}

		markerLock.unlock();
	}

	/*
	 * Series can be used in multiple chart. How styling is performed is bound to a
	 * chart object, therefore use these functions to set the display type
	 */

	/**
	 * Set the series to the specified color. The colors can be found in the
	 * chart.css file.
	 * 
	 * @param seriesIndex the index of the series
	 * @param colorIndex  in range of [0-168]
	 */
	public void setSeriesColor(int seriesIndex, int colorIndex) {
		// Gettings the index in a linked hashset is expensive. This won't be performed
		// many times!
		int index = 0;
		for (var entry : typedSeries.entrySet()) {
			if (index++ == seriesIndex) {
				setSeriesColor(entry.getKey(), colorIndex);
				break;
			}
		}
	}

	/**
	 * Set the series to the specified color. The colors can be found in the
	 * chart.css file.
	 * 
	 * @param series     the series to set the color for
	 * @param colorIndex in range of [0-146]
	 */
	public void setSeriesColor(Series series, int colorIndex) {
		if (series != null) {
			seriesColorMap.put(series, colorIndex);
			legendMap.get(series);
			updateLegend();
			this.seriesChanged(null);
		}
	}

	public void setSeriesSymbol(int seriesIndex, SymbolType type) {
		// Gettings the index in a linked hashset is expensive. This won't be performed
		// many times!
		int index = 0;
		for (var entry : typedSeries.entrySet()) {
			if (index++ == seriesIndex) {
				setSeriesSymbol(entry.getKey(), type);
				break;
			}
		}
	}

	public void setSeriesSymbol(Series series, SymbolType type) {
		if (series != null) {
			seriesSymbolType.put(series, type);
			legendMap.get(series);
			updateLegend();
			this.seriesChanged(null);
		}
	}

}
