package com.github.kilianB.renderer;

import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.Path;

/**
 * @author Kilian
 *
 */
public class AreaRenderer<X,Y> implements Renderer<X,Y> {

	@Override
	public void layout(Series<X, Y> series, Axis<X> xAxis, Axis<Y> yAxis, boolean visible) {
		final ObservableList<Node> children = ((Group) series.getNode()).getChildren();

		Path fillPath = (Path) children.get(0);
		Path linePath = (Path) children.get(1);

		var cssPath = fillPath.getStyleClass();
		var cssLine = linePath.getStyleClass();

		if (!visible) {
			if (!cssPath.contains("hide-series-symbol")) {
				cssPath.add("hide-series-symbol");
				cssLine.add("hide-series-symbol");

				// Also add the hide artifact to the symbols
				for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
					Node symbol = it.next().getNode();
					symbol.getStyleClass().add("hide-series-symbol");
				}
			}
		} else {
			if (cssPath.contains("hide-series-symbol")) {
				cssPath.remove("hide-series-symbol");
				cssLine.add("hide-series-symbol");
				// Also add the hide artifact to the symbols
				for (Iterator<Data<X, Y>> it = getDisplayedDataIterator(series); it.hasNext();) {
					Node symbol = it.next().getNode();
					symbol.getStyleClass().remove("hide-series-symbol");
				}
			}
			makePaths(series, constructedPath, fillPath, linePath, SortingPolicy.X_AXIS, xAxis, yAxis);
		}
	}

	

}
