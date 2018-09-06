package com.github.kilianB;

import com.github.kilianB.MultiTypeChart.SeriesType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.XYChart.Series;

public class TypedSeries<X, Y> {

	// JavaFx's Series is a final class with no interface, abstract class to extend
	// from.
	// We have to jump through some hoops to get it working correctly

	private final Series<X, Y> series;

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

	public TypedSeries(Series<X, Y> series, SeriesType seriesType, SortingPolicy sortingPolicy) {
		this.series = series;
		this.seriesType = new ReadOnlyObjectWrapper<>(seriesType);
		this.sortingPolicy = sortingPolicy;
	}

	public TypedSeries(Series<X, Y> series, SeriesType seriesType) {
		this(series, seriesType, SortingPolicy.X_AXIS);
	}

	public TypedSeries(Series<X, Y> series) {
		this(series, SeriesType.SCATTER);
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

}