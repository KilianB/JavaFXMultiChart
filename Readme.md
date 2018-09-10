# JavaFXMultiChart 

A <b>work in progress</b> fix for the default javafx chart class to allow combining multiple chart types and axis in one chart object.
Currently JavaFX does not support to create a composite chart using different series types. Java 10 required!**

## Features: 
- Fixed value markers
- Multiple X and Y Axis
- Hide/Show Series (click on legend)
- Different chart types in one chart

![bildschirmfoto 2018-09-10 um 17 39 44](https://user-images.githubusercontent.com/9025925/45307979-b4b2d580-b520-11e8-8643-8c41098c41df.png)


## Usage:

````JAVA
//Create a chart
MultiTypeChart<Number, Number> multiTypeChart= new MultiTypeChart<>(new NumberAxis(), new NumberAxis());

//Builder pattern
TypedSeries lineSeries = TypedSeries.builder("Line").line().build();
TypedSeries areaSeries = TypedSeries.builder("Area").area().build();
TypedSeries scatterSeries = TypedSeries.builder("Scatter").scatter().build();

//Advanced control with full generics
TypedSeries<Number,Number> lineSeries1 = TypedSeries.<Number,Number>
    builder("Line Series 1").line()
    .withYAxisIndex(1)
    .withYAxisSide(Side.RIGHT)
    .build();

//Add/remove value markers
boolean showLabel = true;
multiTypeChart.addValueMarker(new ValueMarker<Number>(5,true,Color.BLUE,showLabel));
multiTypeChart.addValueMarker(new ValueMarker<Number>(12,false,Color.BLACK,showLabel));
multiTypeChart.addValueMarker(new ValueMarker<Number>(20,false,Color.GREEN,showLabel));

//Add data
for(...){
    lineSeries.addData(x,y);
}
````

### Changelog 

#### 10.09
- Add builder pattern to typed series
- Add support for secondary/multiple x and y axis
- change legend to be a flow pane instead of tile pane for better looking layout
- add support to show hide series on mouse click

## Work in progress

Due to the API containing a lot of private and final methods the result is rather lackluster and just works good enough if somone does not look at the code. (A lot of copy and pasted code, switch statements and 
almost no indication of OOP).
Java 9/10 discourage "illegal" reflection access usage and byte code manipulation in conjunction with a custom class loader to circumvent the final restrictions seem a bit overkill for such a feature.
This should only be considered a hack until the api caught up until the jdk feature request <a href="https://bugs.openjdk.java.net/browse/JDK-8090594">JDK-8090594</a> is resolved.


- Support all basic chart types
- potential ObservableValue resource leak if series get added and removed
- implement animation code
- implement remove series action
- make full use of beans.observable.values
- test secondary/3rd ... nth x Axis and left axis.


**I think there is one usage of `var` and no streams or anything else, so if you want to backport it to Java <8 it should take 2 minutes.
