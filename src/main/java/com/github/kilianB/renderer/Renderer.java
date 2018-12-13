package com.github.kilianB.renderer;

import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart.Series;

/**
 * @author Kilian
 *
 */
public interface Renderer<X,Y> {

	void layout(Series<X, Y> series, Axis<X> xAxis, Axis<Y> yAxis, boolean visible);
	
}
