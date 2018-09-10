package com.github.kilianB;

import com.github.kilianB.MultiTypeChart.SeriesType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class TypedSeries<X, Y> {

	// JavaFx's Series is a final class with no interface, abstract class to extend
	// from.
	// We have to jump through some hoops to get it working correctly

	private final Series<X, Y> series;

	/**
	 * Support multiple y axis. This is the y axis index 
	 * to determine which axis this should be drawn in.
	 * 
	 * An index of 0 defaults to the primary axis
	 */
	private int yAxisIndex = 0;
	
	private int xAxisIndex = 0;
	
	private Side yAxisSide = Side.RIGHT;
	
	private Side xAxisSide = Side.TOP;
	
	private ReadOnlyObjectWrapper<SeriesType> seriesType;

	// Line specific
	// We can't set the bean initially. Just after it has been attached to the

	/*
	 *	We can't create stylable properties outside of the bean object.
	 *	For now discard css support, or do we set the listeners once a series was added
	 *	to the chart? Or can we? TODO
	 *Also useful for area. only not for scatter
	 */
	private BooleanProperty showSymbols = new SimpleBooleanProperty(true);

	/**
	 * Used only for line series. Should the points be sorted by x value or kept in
	 * insertion order?
	 */
	private SortingPolicy sortingPolicy = SortingPolicy.X_AXIS;

	private TypedSeries(Series<X, Y> series, ReadOnlyObjectWrapper<SeriesType> seriesType, int yAxisIndex,
			int xAxisIndex, SortingPolicy xAxisSortingPolicy, Side xAxisSide, Side yAxisSide) {
		this.series = series;
		this.seriesType = seriesType;
		this.sortingPolicy = xAxisSortingPolicy;
		this.yAxisIndex = yAxisIndex;
		this.xAxisIndex = xAxisIndex;
		this.xAxisSide = xAxisSide;
		this.yAxisSide = yAxisSide;
	}

	public SeriesType getSeriesType() {
		return seriesType.get();
	}

	public ReadOnlyObjectProperty<SeriesType> seriesProperty() {
		return seriesType.getReadOnlyProperty();
	}

	public void setSeriesType(SeriesType seriesType) {
		this.seriesType.set(seriesType);
	}

	public Series<X, Y> getSeries() {
		return series;
	}

	public SortingPolicy getSortingPolicy() {
		return sortingPolicy;
	}

	public void setSortingPolicy(SortingPolicy sortingPolicy) {
		this.sortingPolicy = sortingPolicy;
	}

	public BooleanProperty showSymbolsProperty() {
		return showSymbols;
	}

	public int getYAxisIndex() {
		return yAxisIndex;
	}

	public int getXAxisIndex() {
		return xAxisIndex;
	}

	public Side getyAxisSide() {
		return yAxisSide;
	}

	public Side getxAxisSide() {
		return xAxisSide;
	}
	
	public void addData(X x, Y y){
		series.getData().add(new Data<X, Y>(x,y));
	}
	
	public static <X1,Y1> Builder<X1,Y1> builder(String seriesName) {
		return new Builder<X1,Y1>(seriesName);
	}
	
	public static <X1,Y1> Builder<X1,Y1> builder(Series<X1,Y1> series) {
		return new Builder<X1,Y1>(series);
	}
	
	
	public interface ITypeStage<X1,Y1>{
		IAxisStage<X1,Y1> line();
		IAxisStage<X1,Y1> scatter();
		IAxisStage<X1,Y1> area();
	}
	
	public interface ILineAxisStage<X1,Y1>{
		//Only line chart options
		ILineAxisStage<X1,Y1> withXAxisSortingPolicy(SortingPolicy sortingPolicy);
		
		IAxisStage<X1,Y1> withXAxisIndex(int xAxisIndex);
		IAxisStage<X1,Y1> withXAxisSide(Side side);
		IAxisStage<X1,Y1> withYAxisIndex(int yAxisIndex);
		IAxisStage<X1,Y1> withYAxisSide(Side side);
		TypedSeries<X1, Y1> build();
	}
	
	public interface IAxisStage<X1,Y1>{
		
		IAxisStage<X1,Y1> withXAxisIndex(int xAxisIndex);
		IAxisStage<X1,Y1> withXAxisSide(Side side);
		IAxisStage<X1,Y1> withYAxisIndex(int yAxisIndex);
		IAxisStage<X1,Y1> withYAxisSide(Side side);
		
		TypedSeries<X1,Y1> build();
	}
	
	public static final class Builder<X,Y> implements ITypeStage<X,Y>,ILineAxisStage<X,Y>,IAxisStage<X,Y>{
		
		private Series<X,Y> series;
		private ReadOnlyObjectWrapper<SeriesType> seriesType;
		private SortingPolicy xAxisSortingPolicy = SortingPolicy.NONE;
		private int xAxisIndex = 0;
		private int yAxisIndex = 0;
		private Side xAxisSide = Side.BOTTOM;
		private Side yAxisSide = Side.RIGHT;
		
		
		private Builder(String seriesName) {
			series = new Series<X,Y>();
			series.setName(seriesName);
		};
		
		private Builder(Series<X,Y> series) {
			this.series = series;
		}
		
		@Override
		public IAxisStage<X,Y> line() {
			seriesType = new ReadOnlyObjectWrapper<SeriesType>(SeriesType.LINE);
			return this;
		}

		@Override
		public IAxisStage<X,Y> scatter() {
			seriesType = new ReadOnlyObjectWrapper<SeriesType>(SeriesType.SCATTER);
			return this;
		}

		@Override
		public IAxisStage<X,Y> area() {
			seriesType = new ReadOnlyObjectWrapper<SeriesType>(SeriesType.AREA);
			return this;
		}

		@Override
		public ILineAxisStage<X, Y> withXAxisSortingPolicy(SortingPolicy sortingPolicy) {
			xAxisSortingPolicy = sortingPolicy;
			return this;
		}

		@Override
		public IAxisStage<X, Y> withXAxisIndex(int xAxisIndex) {
			this.xAxisIndex = xAxisIndex;
			return this;
		}

		@Override
		public IAxisStage<X, Y> withXAxisSide(Side side) {
			if(side.equals(Side.TOP) || side.equals(Side.BOTTOM)) {
				xAxisSide = side;
			}else {
				throw new IllegalArgumentException("X Axis may only be placed at the Top or Bottom of the chart");
			}
			return this;
		}

		@Override
		public IAxisStage<X, Y> withYAxisIndex(int yAxisIndex) {
			this.yAxisIndex = yAxisIndex;
			return this;
		}

		@Override
		public IAxisStage<X, Y> withYAxisSide(Side side) {
			if(side.equals(Side.LEFT) || side.equals(Side.RIGHT)) {
				xAxisSide = side;
			}else {
				throw new IllegalArgumentException("Y Axis may only be placed at the Left or Right of the chart");
			}
			return this;
		}

		@Override
		public TypedSeries<X, Y> build() {
			return new TypedSeries(series,seriesType,yAxisIndex,xAxisIndex,xAxisSortingPolicy,
					xAxisSide,yAxisSide);
		}
		

		
	}
	
}