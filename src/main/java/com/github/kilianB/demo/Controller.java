package com.github.kilianB.demo;


import java.util.Random;

import com.github.kilianB.MultiTypeChart;
import com.github.kilianB.TypedSeries;
import com.github.kilianB.ValueMarker;
import com.github.kilianB.MultiTypeChart.SeriesType;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Controller {
	@FXML
	private BorderPane borderPane;

	@FXML
	public void initialize() {
		
		//Create a chart
		MultiTypeChart<Number, Number> multiTypeChart= new MultiTypeChart<>(new NumberAxis(), new NumberAxis());
		
		//Create series as usual
		Series<Number,Number> line = new Series<>();
		line.setName("Line");
		
		Series<Number,Number> scatter = new Series<>();
		scatter.setName("Scatter");
		
		Series<Number,Number> area = new Series<>();
		area.setName("Area");
		
		//Wrap series in a typed series object
		multiTypeChart.addSeries(new TypedSeries<>(scatter,SeriesType.SCATTER));
		multiTypeChart.addSeries(new TypedSeries<>(line,SeriesType.LINE));
		multiTypeChart.addSeries(new TypedSeries<>(area,SeriesType.AREA));
		
		//Add/remove value markers
		boolean showLabel = true;
		multiTypeChart.addValueMarker(new ValueMarker<Number>(5,true,Color.BLUE,showLabel));
		multiTypeChart.addValueMarker(new ValueMarker<Number>(12,false,Color.BLACK,showLabel));
		multiTypeChart.addValueMarker(new ValueMarker<Number>(20,false,Color.GREEN,showLabel));
		
		
		
		borderPane.setCenter(multiTypeChart);
				
		new Thread(()->{	
			Random r = new Random();
			
			for(int i = 0; i < 100; i++) {
				
				int j = i;
				Platform.runLater(()->{
					
					line.getData().add(new Data<Number, Number>(j*10d,r.nextInt(20)));
					area.getData().add(new Data<Number, Number>(j*10d,r.nextInt(20)));
					
					for(int m = 0; m < r.nextInt(30); m++) {
						scatter.getData().add(new Data<Number, Number>(j*10d,r.nextInt(10)));
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
		
		
	}

}
