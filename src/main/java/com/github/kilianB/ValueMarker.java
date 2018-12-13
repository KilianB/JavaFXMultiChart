package com.github.kilianB;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A value marker represents a horizontal or vertical line overlaid over the chart.
 * @author Kilian
 *
 * @param <X> type of the axis
 */
public class ValueMarker<X>{
	
//	public enum Direction{
//		HORIZONTAL,
//		VERTICAL
//	}
	
	private X value;
	private Color color;
	private BooleanProperty enableLabel = new SimpleBooleanProperty(true);
	private boolean vertical;
	
	private Node label;
	
	public ValueMarker(X value, boolean vertical, Color color, boolean enableLabel) {
		this.value = value;
		this.color = color;
		this.enableLabel.set(enableLabel);
		this.vertical = vertical;
		if(enableLabel) {
			label = new Label(value.toString());
			label.getStyleClass().add("chart-value-marker-label");
		}
	}

	public X getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public BooleanProperty enableLabelProperty() {
		return enableLabel;
	}

	public boolean isVertical() {
		return vertical;
	}

	public Node getLabel() {
		return label;
	}
	
	
	
	
	
	
	
}
