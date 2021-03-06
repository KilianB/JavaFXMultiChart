package com.github.kilianB.demo;

import java.util.Random;

import com.github.kilianB.MultiTypeChart;
import com.github.kilianB.SymbolType;
import com.github.kilianB.TypedSeries;
import com.github.kilianB.ValueMarker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Controller {
	@FXML
	private BorderPane borderPane;

	@FXML
	public void initialize() {

		System.out.println("Init controller");
		
		// Create a chart
		MultiTypeChart<Number, Number> multiTypeChart = new MultiTypeChart<>(new NumberAxis(), new NumberAxis());

		TypedSeries<Number, Number> lineSeries = TypedSeries.<Number, Number>builder("Line").line()
				.build();

		TypedSeries<Number, Number> scatterSeries = TypedSeries.<Number, Number>builder("Scatter Series").scatter()
				.withXAxisIndex(1).build();

		TypedSeries<Number, Number> areaSeries = TypedSeries.<Number, Number>builder("Area").area().build();

		TypedSeries<Number, Number> lineSeries1 = TypedSeries.<Number, Number>builder("Line Series 1").line()
				.withYAxisIndex(1).withYAxisSide(Side.LEFT).build();

		TypedSeries<Number, Number> lineSeries2 = TypedSeries.<Number, Number>builder("Line Series 2").line()
				.withYAxisIndex(2).withXAxisIndex(2).withYAxisSide(Side.RIGHT).build();

		multiTypeChart.addSeries(scatterSeries);
		multiTypeChart.addSeries(areaSeries);
		multiTypeChart.addSeries(lineSeries);
		multiTypeChart.addSeries(lineSeries1);
		multiTypeChart.addSeries(lineSeries2);

		// Add/remove value markers
		boolean showLabel = true;
		multiTypeChart.addValueMarker(new ValueMarker<Number>(5, true, Color.BLUE, showLabel));
		multiTypeChart.addValueMarker(new ValueMarker<Number>(12, false, Color.BLACK, showLabel));
		multiTypeChart.addValueMarker(new ValueMarker<Number>(20, false, Color.GREEN, showLabel));

		multiTypeChart.setSeriesColor(0, 8);
		multiTypeChart.setSeriesColor(1, 9);
		multiTypeChart.setSeriesColor(2, 20);
		multiTypeChart.setSeriesColor(3, 31);
		multiTypeChart.setSeriesColor(4, 42);
		multiTypeChart.setSeriesColor(5, 53);
		multiTypeChart.setSeriesColor(6, 64);

		
		multiTypeChart.setSeriesSymbol(0,SymbolType.hollowSquare);
		multiTypeChart.setSeriesSymbol(1,SymbolType.solidCicrle);
		multiTypeChart.setSeriesSymbol(2,SymbolType.solidCicrle);
		
		borderPane.setCenter(multiTypeChart);

		new Thread(() -> {
			Random r = new Random();

			for (int i = 0; i < 100; i++) {

				int j = i;
				Platform.runLater(() -> {

					lineSeries.addData(j * 10d, r.nextInt(35));
					lineSeries1.addData(j * 10d, r.nextInt(20));
					lineSeries2.addData(j * 10d, r.nextInt(25));
					areaSeries.addData(j * 10d, r.nextInt(35));

					for (int m = 0; m < r.nextInt(30); m++) {
						scatterSeries.addData(j * 10d, r.nextInt(10));
					}

				});

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
		;

	}

}
