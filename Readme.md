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

//add series 
multiTypeChart.addSeries(scatterSeries);
multiTypeChart.addSeries(areaSeries);
multiTypeChart.addSeries(lineSeries);

//customize color if desired
multiTypeChart.setSeriesColor(1, 90);
multiTypeChart.setSeriesColor(2, 50);
    
//customize symbol (currently not well supported with colors)
//multiTypeChart.setSeriesSymbol(0,SymbolType.solidTriangle);
//multiTypeChart.setSeriesSymbol(1,SymbolType.solidTriangle);
   
//Add data
for(...){
    lineSeries.addData(x,y);
}
````


### Color Table
<table>
   <thead>
      <tr>
         <th>ColorId</th>
         <th>Color</th>
         <th>Hex</th>
         <th>Decimal Code</th>
      </tr>
   </thead>
   <tbody>
      <tr>
         <td>7</td>
         <td>
            <img src="https://via.placeholder.com/80x40/800000/800000" />
         </td>
         <td>#800000</td>
         <td>128,0,0</td>
      </tr>
      <tr>
         <td>8</td>
         <td>
            <img src="https://via.placeholder.com/80x40/8b0000/8b0000" />
         </td>
         <td>#8b0000</td>
         <td>139,0,0</td>
      </tr>
      <tr>
         <td>9</td>
         <td>
            <img src="https://via.placeholder.com/80x40/a52a2a/a52a2a" />
         </td>
         <td>#a52a2a</td>
         <td>165,42,42</td>
      </tr>
      <tr>
         <td>10</td>
         <td>
            <img src="https://via.placeholder.com/80x40/b22222/b22222" />
         </td>
         <td>#b22222</td>
         <td>178,34,34</td>
      </tr>
      <tr>
         <td>11</td>
         <td>
            <img src="https://via.placeholder.com/80x40/dc143c/dc143c" />
         </td>
         <td>#dc143c</td>
         <td>220,20,60</td>
      </tr>
      <tr>
         <td>12</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff0000/ff0000" />
         </td>
         <td>#ff0000</td>
         <td>255,0,0</td>
      </tr>
      <tr>
         <td>13</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff6347/ff6347" />
         </td>
         <td>#ff6347</td>
         <td>255,99,71</td>
      </tr>
      <tr>
         <td>14</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff7f50/ff7f50" />
         </td>
         <td>#ff7f50</td>
         <td>255,127,80</td>
      </tr>
      <tr>
         <td>15</td>
         <td>
            <img src="https://via.placeholder.com/80x40/cd5c5c/cd5c5c" />
         </td>
         <td>#cd5c5c</td>
         <td>205,92,92</td>
      </tr>
      <tr>
         <td>16</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f08080/f08080" />
         </td>
         <td>#f08080</td>
         <td>240,128,128</td>
      </tr>
      <tr>
         <td>17</td>
         <td>
            <img src="https://via.placeholder.com/80x40/e9967a/e9967a" />
         </td>
         <td>#e9967a</td>
         <td>233,150,122</td>
      </tr>
      <tr>
         <td>18</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fa8072/fa8072" />
         </td>
         <td>#fa8072</td>
         <td>250,128,114</td>
      </tr>
      <tr>
         <td>19</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffa07a/ffa07a" />
         </td>
         <td>#ffa07a</td>
         <td>255,160,122</td>
      </tr>
      <tr>
         <td>20</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff4500/ff4500" />
         </td>
         <td>#ff4500</td>
         <td>255,69,0</td>
      </tr>
      <tr>
         <td>21</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff8c00/ff8c00" />
         </td>
         <td>#ff8c00</td>
         <td>255,140,0</td>
      </tr>
      <tr>
         <td>22</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffa500/ffa500" />
         </td>
         <td>#ffa500</td>
         <td>255,165,0</td>
      </tr>
      <tr>
         <td>23</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffd700/ffd700" />
         </td>
         <td>#ffd700</td>
         <td>255,215,0</td>
      </tr>
      <tr>
         <td>24</td>
         <td>
            <img src="https://via.placeholder.com/80x40/b8860b/b8860b" />
         </td>
         <td>#b8860b</td>
         <td>184,134,11</td>
      </tr>
      <tr>
         <td>25</td>
         <td>
            <img src="https://via.placeholder.com/80x40/daa520/daa520" />
         </td>
         <td>#daa520</td>
         <td>218,165,32</td>
      </tr>
      <tr>
         <td>26</td>
         <td>
            <img src="https://via.placeholder.com/80x40/eee8aa/eee8aa" />
         </td>
         <td>#eee8aa</td>
         <td>238,232,170</td>
      </tr>
      <tr>
         <td>27</td>
         <td>
            <img src="https://via.placeholder.com/80x40/bdb76b/bdb76b" />
         </td>
         <td>#bdb76b</td>
         <td>189,183,107</td>
      </tr>
      <tr>
         <td>28</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f0e68c/f0e68c" />
         </td>
         <td>#f0e68c</td>
         <td>240,230,140</td>
      </tr>
      <tr>
         <td>29</td>
         <td>
            <img src="https://via.placeholder.com/80x40/808000/808000" />
         </td>
         <td>#808000</td>
         <td>128,128,0</td>
      </tr>
      <tr>
         <td>30</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffff00/ffff00" />
         </td>
         <td>#ffff00</td>
         <td>255,255,0</td>
      </tr>
      <tr>
         <td>31</td>
         <td>
            <img src="https://via.placeholder.com/80x40/9acd32/9acd32" />
         </td>
         <td>#9acd32</td>
         <td>154,205,50</td>
      </tr>
      <tr>
         <td>32</td>
         <td>
            <img src="https://via.placeholder.com/80x40/556b2f/556b2f" />
         </td>
         <td>#556b2f</td>
         <td>85,107,47</td>
      </tr>
      <tr>
         <td>33</td>
         <td>
            <img src="https://via.placeholder.com/80x40/6b8e23/6b8e23" />
         </td>
         <td>#6b8e23</td>
         <td>107,142,35</td>
      </tr>
      <tr>
         <td>34</td>
         <td>
            <img src="https://via.placeholder.com/80x40/7cfc00/7cfc00" />
         </td>
         <td>#7cfc00</td>
         <td>124,252,0</td>
      </tr>
      <tr>
         <td>35</td>
         <td>
            <img src="https://via.placeholder.com/80x40/7fff00/7fff00" />
         </td>
         <td>#7fff00</td>
         <td>127,255,0</td>
      </tr>
      <tr>
         <td>36</td>
         <td>
            <img src="https://via.placeholder.com/80x40/adff2f/adff2f" />
         </td>
         <td>#adff2f</td>
         <td>173,255,47</td>
      </tr>
      <tr>
         <td>37</td>
         <td>
            <img src="https://via.placeholder.com/80x40/006400/006400" />
         </td>
         <td>#006400</td>
         <td>0,100,0</td>
      </tr>
      <tr>
         <td>38</td>
         <td>
            <img src="https://via.placeholder.com/80x40/008000/008000" />
         </td>
         <td>#008000</td>
         <td>0,128,0</td>
      </tr>
      <tr>
         <td>39</td>
         <td>
            <img src="https://via.placeholder.com/80x40/228b22/228b22" />
         </td>
         <td>#228b22</td>
         <td>34,139,34</td>
      </tr>
      <tr>
         <td>40</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00ff00/00ff00" />
         </td>
         <td>#00ff00</td>
         <td>0,255,0</td>
      </tr>
      <tr>
         <td>41</td>
         <td>
            <img src="https://via.placeholder.com/80x40/32cd32/32cd32" />
         </td>
         <td>#32cd32</td>
         <td>50,205,50</td>
      </tr>
      <tr>
         <td>42</td>
         <td>
            <img src="https://via.placeholder.com/80x40/90ee90/90ee90" />
         </td>
         <td>#90ee90</td>
         <td>144,238,144</td>
      </tr>
      <tr>
         <td>43</td>
         <td>
            <img src="https://via.placeholder.com/80x40/98fb98/98fb98" />
         </td>
         <td>#98fb98</td>
         <td>152,251,152</td>
      </tr>
      <tr>
         <td>44</td>
         <td>
            <img src="https://via.placeholder.com/80x40/8fbc8f/8fbc8f" />
         </td>
         <td>#8fbc8f</td>
         <td>143,188,143</td>
      </tr>
      <tr>
         <td>45</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00fa9a/00fa9a" />
         </td>
         <td>#00fa9a</td>
         <td>0,250,154</td>
      </tr>
      <tr>
         <td>46</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00ff7f/00ff7f" />
         </td>
         <td>#00ff7f</td>
         <td>0,255,127</td>
      </tr>
      <tr>
         <td>47</td>
         <td>
            <img src="https://via.placeholder.com/80x40/2e8b57/2e8b57" />
         </td>
         <td>#2e8b57</td>
         <td>46,139,87</td>
      </tr>
      <tr>
         <td>48</td>
         <td>
            <img src="https://via.placeholder.com/80x40/66cdaa/66cdaa" />
         </td>
         <td>#66cdaa</td>
         <td>102,205,170</td>
      </tr>
      <tr>
         <td>49</td>
         <td>
            <img src="https://via.placeholder.com/80x40/3cb371/3cb371" />
         </td>
         <td>#3cb371</td>
         <td>60,179,113</td>
      </tr>
      <tr>
         <td>50</td>
         <td>
            <img src="https://via.placeholder.com/80x40/20b2aa/20b2aa" />
         </td>
         <td>#20b2aa</td>
         <td>32,178,170</td>
      </tr>
      <tr>
         <td>51</td>
         <td>
            <img src="https://via.placeholder.com/80x40/2f4f4f/2f4f4f" />
         </td>
         <td>#2f4f4f</td>
         <td>47,79,79</td>
      </tr>
      <tr>
         <td>52</td>
         <td>
            <img src="https://via.placeholder.com/80x40/008080/008080" />
         </td>
         <td>#008080</td>
         <td>0,128,128</td>
      </tr>
      <tr>
         <td>53</td>
         <td>
            <img src="https://via.placeholder.com/80x40/008b8b/008b8b" />
         </td>
         <td>#008b8b</td>
         <td>0,139,139</td>
      </tr>
      <tr>
         <td>54</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00ffff/00ffff" />
         </td>
         <td>#00ffff</td>
         <td>0,255,255</td>
      </tr>
      <tr>
         <td>55</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00ffff/00ffff" />
         </td>
         <td>#00ffff</td>
         <td>0,255,255</td>
      </tr>
      <tr>
         <td>56</td>
         <td>
            <img src="https://via.placeholder.com/80x40/e0ffff/e0ffff" />
         </td>
         <td>#e0ffff</td>
         <td>224,255,255</td>
      </tr>
      <tr>
         <td>57</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00ced1/00ced1" />
         </td>
         <td>#00ced1</td>
         <td>0,206,209</td>
      </tr>
      <tr>
         <td>58</td>
         <td>
            <img src="https://via.placeholder.com/80x40/40e0d0/40e0d0" />
         </td>
         <td>#40e0d0</td>
         <td>64,224,208</td>
      </tr>
      <tr>
         <td>59</td>
         <td>
            <img src="https://via.placeholder.com/80x40/48d1cc/48d1cc" />
         </td>
         <td>#48d1cc</td>
         <td>72,209,204</td>
      </tr>
      <tr>
         <td>60</td>
         <td>
            <img src="https://via.placeholder.com/80x40/afeeee/afeeee" />
         </td>
         <td>#afeeee</td>
         <td>175,238,238</td>
      </tr>
      <tr>
         <td>61</td>
         <td>
            <img src="https://via.placeholder.com/80x40/7fffd4/7fffd4" />
         </td>
         <td>#7fffd4</td>
         <td>127,255,212</td>
      </tr>
      <tr>
         <td>62</td>
         <td>
            <img src="https://via.placeholder.com/80x40/b0e0e6/b0e0e6" />
         </td>
         <td>#b0e0e6</td>
         <td>176,224,230</td>
      </tr>
      <tr>
         <td>63</td>
         <td>
            <img src="https://via.placeholder.com/80x40/5f9ea0/5f9ea0" />
         </td>
         <td>#5f9ea0</td>
         <td>95,158,160</td>
      </tr>
      <tr>
         <td>64</td>
         <td>
            <img src="https://via.placeholder.com/80x40/4682b4/4682b4" />
         </td>
         <td>#4682b4</td>
         <td>70,130,180</td>
      </tr>
      <tr>
         <td>65</td>
         <td>
            <img src="https://via.placeholder.com/80x40/6495ed/6495ed" />
         </td>
         <td>#6495ed</td>
         <td>100,149,237</td>
      </tr>
      <tr>
         <td>66</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00bfff/00bfff" />
         </td>
         <td>#00bfff</td>
         <td>0,191,255</td>
      </tr>
      <tr>
         <td>67</td>
         <td>
            <img src="https://via.placeholder.com/80x40/1e90ff/1e90ff" />
         </td>
         <td>#1e90ff</td>
         <td>30,144,255</td>
      </tr>
      <tr>
         <td>68</td>
         <td>
            <img src="https://via.placeholder.com/80x40/add8e6/add8e6" />
         </td>
         <td>#add8e6</td>
         <td>173,216,230</td>
      </tr>
      <tr>
         <td>69</td>
         <td>
            <img src="https://via.placeholder.com/80x40/87ceeb/87ceeb" />
         </td>
         <td>#87ceeb</td>
         <td>135,206,235</td>
      </tr>
      <tr>
         <td>70</td>
         <td>
            <img src="https://via.placeholder.com/80x40/87cefa/87cefa" />
         </td>
         <td>#87cefa</td>
         <td>135,206,250</td>
      </tr>
      <tr>
         <td>71</td>
         <td>
            <img src="https://via.placeholder.com/80x40/191970/191970" />
         </td>
         <td>#191970</td>
         <td>25,25,112</td>
      </tr>
      <tr>
         <td>72</td>
         <td>
            <img src="https://via.placeholder.com/80x40/000080/000080" />
         </td>
         <td>#000080</td>
         <td>0,0,128</td>
      </tr>
      <tr>
         <td>73</td>
         <td>
            <img src="https://via.placeholder.com/80x40/00008b/00008b" />
         </td>
         <td>#00008b</td>
         <td>0,0,139</td>
      </tr>
      <tr>
         <td>74</td>
         <td>
            <img src="https://via.placeholder.com/80x40/0000cd/0000cd" />
         </td>
         <td>#0000cd</td>
         <td>0,0,205</td>
      </tr>
      <tr>
         <td>75</td>
         <td>
            <img src="https://via.placeholder.com/80x40/0000ff/0000ff" />
         </td>
         <td>#0000ff</td>
         <td>0,0,255</td>
      </tr>
      <tr>
         <td>76</td>
         <td>
            <img src="https://via.placeholder.com/80x40/4169e1/4169e1" />
         </td>
         <td>#4169e1</td>
         <td>65,105,225</td>
      </tr>
      <tr>
         <td>77</td>
         <td>
            <img src="https://via.placeholder.com/80x40/8a2be2/8a2be2" />
         </td>
         <td>#8a2be2</td>
         <td>138,43,226</td>
      </tr>
      <tr>
         <td>78</td>
         <td>
            <img src="https://via.placeholder.com/80x40/4b0082/4b0082" />
         </td>
         <td>#4b0082</td>
         <td>75,0,130</td>
      </tr>
      <tr>
         <td>79</td>
         <td>
            <img src="https://via.placeholder.com/80x40/483d8b/483d8b" />
         </td>
         <td>#483d8b</td>
         <td>72,61,139</td>
      </tr>
      <tr>
         <td>80</td>
         <td>
            <img src="https://via.placeholder.com/80x40/6a5acd/6a5acd" />
         </td>
         <td>#6a5acd</td>
         <td>106,90,205</td>
      </tr>
      <tr>
         <td>81</td>
         <td>
            <img src="https://via.placeholder.com/80x40/7b68ee/7b68ee" />
         </td>
         <td>#7b68ee</td>
         <td>123,104,238</td>
      </tr>
      <tr>
         <td>82</td>
         <td>
            <img src="https://via.placeholder.com/80x40/9370db/9370db" />
         </td>
         <td>#9370db</td>
         <td>147,112,219</td>
      </tr>
      <tr>
         <td>83</td>
         <td>
            <img src="https://via.placeholder.com/80x40/8b008b/8b008b" />
         </td>
         <td>#8b008b</td>
         <td>139,0,139</td>
      </tr>
      <tr>
         <td>84</td>
         <td>
            <img src="https://via.placeholder.com/80x40/9400d3/9400d3" />
         </td>
         <td>#9400d3</td>
         <td>148,0,211</td>
      </tr>
      <tr>
         <td>85</td>
         <td>
            <img src="https://via.placeholder.com/80x40/9932cc/9932cc" />
         </td>
         <td>#9932cc</td>
         <td>153,50,204</td>
      </tr>
      <tr>
         <td>86</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ba55d3/ba55d3" />
         </td>
         <td>#ba55d3</td>
         <td>186,85,211</td>
      </tr>
      <tr>
         <td>87</td>
         <td>
            <img src="https://via.placeholder.com/80x40/800080/800080" />
         </td>
         <td>#800080</td>
         <td>128,0,128</td>
      </tr>
      <tr>
         <td>88</td>
         <td>
            <img src="https://via.placeholder.com/80x40/d8bfd8/d8bfd8" />
         </td>
         <td>#d8bfd8</td>
         <td>216,191,216</td>
      </tr>
      <tr>
         <td>89</td>
         <td>
            <img src="https://via.placeholder.com/80x40/dda0dd/dda0dd" />
         </td>
         <td>#dda0dd</td>
         <td>221,160,221</td>
      </tr>
      <tr>
         <td>90</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ee82ee/ee82ee" />
         </td>
         <td>#ee82ee</td>
         <td>238,130,238</td>
      </tr>
      <tr>
         <td>91</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff00ff/ff00ff" />
         </td>
         <td>#ff00ff</td>
         <td>255,0,255</td>
      </tr>
      <tr>
         <td>92</td>
         <td>
            <img src="https://via.placeholder.com/80x40/da70d6/da70d6" />
         </td>
         <td>#da70d6</td>
         <td>218,112,214</td>
      </tr>
      <tr>
         <td>93</td>
         <td>
            <img src="https://via.placeholder.com/80x40/c71585/c71585" />
         </td>
         <td>#c71585</td>
         <td>199,21,133</td>
      </tr>
      <tr>
         <td>94</td>
         <td>
            <img src="https://via.placeholder.com/80x40/db7093/db7093" />
         </td>
         <td>#db7093</td>
         <td>219,112,147</td>
      </tr>
      <tr>
         <td>95</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff1493/ff1493" />
         </td>
         <td>#ff1493</td>
         <td>255,20,147</td>
      </tr>
      <tr>
         <td>96</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ff69b4/ff69b4" />
         </td>
         <td>#ff69b4</td>
         <td>255,105,180</td>
      </tr>
      <tr>
         <td>97</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffb6c1/ffb6c1" />
         </td>
         <td>#ffb6c1</td>
         <td>255,182,193</td>
      </tr>
      <tr>
         <td>98</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffc0cb/ffc0cb" />
         </td>
         <td>#ffc0cb</td>
         <td>255,192,203</td>
      </tr>
      <tr>
         <td>99</td>
         <td>
            <img src="https://via.placeholder.com/80x40/faebd7/faebd7" />
         </td>
         <td>#faebd7</td>
         <td>250,235,215</td>
      </tr>
      <tr>
         <td>100</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f5f5dc/f5f5dc" />
         </td>
         <td>#f5f5dc</td>
         <td>245,245,220</td>
      </tr>
      <tr>
         <td>101</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffe4c4/ffe4c4" />
         </td>
         <td>#ffe4c4</td>
         <td>255,228,196</td>
      </tr>
      <tr>
         <td>102</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffebcd/ffebcd" />
         </td>
         <td>#ffebcd</td>
         <td>255,235,205</td>
      </tr>
      <tr>
         <td>103</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f5deb3/f5deb3" />
         </td>
         <td>#f5deb3</td>
         <td>245,222,179</td>
      </tr>
      <tr>
         <td>104</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fff8dc/fff8dc" />
         </td>
         <td>#fff8dc</td>
         <td>255,248,220</td>
      </tr>
      <tr>
         <td>105</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fffacd/fffacd" />
         </td>
         <td>#fffacd</td>
         <td>255,250,205</td>
      </tr>
      <tr>
         <td>106</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fafad2/fafad2" />
         </td>
         <td>#fafad2</td>
         <td>250,250,210</td>
      </tr>
      <tr>
         <td>107</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffffe0/ffffe0" />
         </td>
         <td>#ffffe0</td>
         <td>255,255,224</td>
      </tr>
      <tr>
         <td>108</td>
         <td>
            <img src="https://via.placeholder.com/80x40/8b4513/8b4513" />
         </td>
         <td>#8b4513</td>
         <td>139,69,19</td>
      </tr>
      <tr>
         <td>109</td>
         <td>
            <img src="https://via.placeholder.com/80x40/a0522d/a0522d" />
         </td>
         <td>#a0522d</td>
         <td>160,82,45</td>
      </tr>
      <tr>
         <td>110</td>
         <td>
            <img src="https://via.placeholder.com/80x40/d2691e/d2691e" />
         </td>
         <td>#d2691e</td>
         <td>210,105,30</td>
      </tr>
      <tr>
         <td>111</td>
         <td>
            <img src="https://via.placeholder.com/80x40/cd853f/cd853f" />
         </td>
         <td>#cd853f</td>
         <td>205,133,63</td>
      </tr>
      <tr>
         <td>112</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f4a460/f4a460" />
         </td>
         <td>#f4a460</td>
         <td>244,164,96</td>
      </tr>
      <tr>
         <td>113</td>
         <td>
            <img src="https://via.placeholder.com/80x40/deb887/deb887" />
         </td>
         <td>#deb887</td>
         <td>222,184,135</td>
      </tr>
      <tr>
         <td>114</td>
         <td>
            <img src="https://via.placeholder.com/80x40/d2b48c/d2b48c" />
         </td>
         <td>#d2b48c</td>
         <td>210,180,140</td>
      </tr>
      <tr>
         <td>115</td>
         <td>
            <img src="https://via.placeholder.com/80x40/bc8f8f/bc8f8f" />
         </td>
         <td>#bc8f8f</td>
         <td>188,143,143</td>
      </tr>
      <tr>
         <td>116</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffe4b5/ffe4b5" />
         </td>
         <td>#ffe4b5</td>
         <td>255,228,181</td>
      </tr>
      <tr>
         <td>117</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffdead/ffdead" />
         </td>
         <td>#ffdead</td>
         <td>255,222,173</td>
      </tr>
      <tr>
         <td>118</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffdab9/ffdab9" />
         </td>
         <td>#ffdab9</td>
         <td>255,218,185</td>
      </tr>
      <tr>
         <td>119</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffe4e1/ffe4e1" />
         </td>
         <td>#ffe4e1</td>
         <td>255,228,225</td>
      </tr>
      <tr>
         <td>120</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fff0f5/fff0f5" />
         </td>
         <td>#fff0f5</td>
         <td>255,240,245</td>
      </tr>
      <tr>
         <td>121</td>
         <td>
            <img src="https://via.placeholder.com/80x40/faf0e6/faf0e6" />
         </td>
         <td>#faf0e6</td>
         <td>250,240,230</td>
      </tr>
      <tr>
         <td>122</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fdf5e6/fdf5e6" />
         </td>
         <td>#fdf5e6</td>
         <td>253,245,230</td>
      </tr>
      <tr>
         <td>123</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffefd5/ffefd5" />
         </td>
         <td>#ffefd5</td>
         <td>255,239,213</td>
      </tr>
      <tr>
         <td>124</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fff5ee/fff5ee" />
         </td>
         <td>#fff5ee</td>
         <td>255,245,238</td>
      </tr>
      <tr>
         <td>125</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f5fffa/f5fffa" />
         </td>
         <td>#f5fffa</td>
         <td>245,255,250</td>
      </tr>
      <tr>
         <td>126</td>
         <td>
            <img src="https://via.placeholder.com/80x40/708090/708090" />
         </td>
         <td>#708090</td>
         <td>112,128,144</td>
      </tr>
      <tr>
         <td>127</td>
         <td>
            <img src="https://via.placeholder.com/80x40/778899/778899" />
         </td>
         <td>#778899</td>
         <td>119,136,153</td>
      </tr>
      <tr>
         <td>128</td>
         <td>
            <img src="https://via.placeholder.com/80x40/b0c4de/b0c4de" />
         </td>
         <td>#b0c4de</td>
         <td>176,196,222</td>
      </tr>
      <tr>
         <td>129</td>
         <td>
            <img src="https://via.placeholder.com/80x40/e6e6fa/e6e6fa" />
         </td>
         <td>#e6e6fa</td>
         <td>230,230,250</td>
      </tr>
      <tr>
         <td>130</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fffaf0/fffaf0" />
         </td>
         <td>#fffaf0</td>
         <td>255,250,240</td>
      </tr>
      <tr>
         <td>131</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f0f8ff/f0f8ff" />
         </td>
         <td>#f0f8ff</td>
         <td>240,248,255</td>
      </tr>
      <tr>
         <td>132</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f8f8ff/f8f8ff" />
         </td>
         <td>#f8f8ff</td>
         <td>248,248,255</td>
      </tr>
      <tr>
         <td>133</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f0fff0/f0fff0" />
         </td>
         <td>#f0fff0</td>
         <td>240,255,240</td>
      </tr>
      <tr>
         <td>134</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fffff0/fffff0" />
         </td>
         <td>#fffff0</td>
         <td>255,255,240</td>
      </tr>
      <tr>
         <td>135</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f0ffff/f0ffff" />
         </td>
         <td>#f0ffff</td>
         <td>240,255,255</td>
      </tr>
      <tr>
         <td>136</td>
         <td>
            <img src="https://via.placeholder.com/80x40/fffafa/fffafa" />
         </td>
         <td>#fffafa</td>
         <td>255,250,250</td>
      </tr>
      <tr>
         <td>137</td>
         <td>
            <img src="https://via.placeholder.com/80x40/000000/000000" />
         </td>
         <td>#000000</td>
         <td>0,0,0</td>
      </tr>
      <tr>
         <td>138</td>
         <td>
            <img src="https://via.placeholder.com/80x40/696969/696969" />
         </td>
         <td>#696969</td>
         <td>105,105,105</td>
      </tr>
      <tr>
         <td>139</td>
         <td>
            <img src="https://via.placeholder.com/80x40/808080/808080" />
         </td>
         <td>#808080</td>
         <td>128,128,128</td>
      </tr>
      <tr>
         <td>140</td>
         <td>
            <img src="https://via.placeholder.com/80x40/a9a9a9/a9a9a9" />
         </td>
         <td>#a9a9a9</td>
         <td>169,169,169</td>
      </tr>
      <tr>
         <td>141</td>
         <td>
            <img src="https://via.placeholder.com/80x40/c0c0c0/c0c0c0" />
         </td>
         <td>#c0c0c0</td>
         <td>192,192,192</td>
      </tr>
      <tr>
         <td>142</td>
         <td>
            <img src="https://via.placeholder.com/80x40/d3d3d3/d3d3d3" />
         </td>
         <td>#d3d3d3</td>
         <td>211,211,211</td>
      </tr>
      <tr>
         <td>143</td>
         <td>
            <img src="https://via.placeholder.com/80x40/dcdcdc/dcdcdc" />
         </td>
         <td>#dcdcdc</td>
         <td>220,220,220</td>
      </tr>
      <tr>
         <td>144</td>
         <td>
            <img src="https://via.placeholder.com/80x40/f5f5f5/f5f5f5" />
         </td>
         <td>#f5f5f5</td>
         <td>245,245,245</td>
      </tr>
      <tr>
         <td>145</td>
         <td>
            <img src="https://via.placeholder.com/80x40/ffffff/ffffff" />
         </td>
         <td>#ffffff</td>
         <td>255,255,255</td>
      </tr>
   </tbody>
</table>


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
- don't rely on generics (they don't really apply when using multiple axis) and move from extending javafx x,y chart to just chart. 
- allow value markers to be added to an arbitrary axis.

**I think there is one usage of `var` and no streams or anything else, so if you want to backport it to Java <8 it should take 2 minutes.
