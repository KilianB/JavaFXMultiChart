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
         <td>8</td>
         <td style="background: #800000;" />
         <td>#800000</td>
         <td>128,0,0</td>
      </tr>
      <tr>
         <td>9</td>
         <td style="background: #8b0000;" />
         <td>#8b0000</td>
         <td>139,0,0</td>
      </tr>
      <tr>
         <td>10</td>
         <td style="background: #a52a2a;" />
         <td>#a52a2a</td>
         <td>165,42,42</td>
      </tr>
      <tr>
         <td>11</td>
         <td style="background: #b22222;" />
         <td>#b22222</td>
         <td>178,34,34</td>
      </tr>
      <tr>
         <td>12</td>
         <td style="background: #dc143c;" />
         <td>#dc143c</td>
         <td>220,20,60</td>
      </tr>
      <tr>
         <td>13</td>
         <td style="background: #ff0000;" />
         <td>#ff0000</td>
         <td>255,0,0</td>
      </tr>
      <tr>
         <td>14</td>
         <td style="background: #ff6347;" />
         <td>#ff6347</td>
         <td>255,99,71</td>
      </tr>
      <tr>
         <td>15</td>
         <td style="background: #ff7f50;" />
         <td>#ff7f50</td>
         <td>255,127,80</td>
      </tr>
      <tr>
         <td>16</td>
         <td style="background: #cd5c5c;" />
         <td>#cd5c5c</td>
         <td>205,92,92</td>
      </tr>
      <tr>
         <td>17</td>
         <td style="background: #f08080;" />
         <td>#f08080</td>
         <td>240,128,128</td>
      </tr>
      <tr>
         <td>18</td>
         <td style="background: #e9967a;" />
         <td>#e9967a</td>
         <td>233,150,122</td>
      </tr>
      <tr>
         <td>19</td>
         <td style="background: #fa8072;" />
         <td>#fa8072</td>
         <td>250,128,114</td>
      </tr>
      <tr>
         <td>20</td>
         <td style="background: #ffa07a;" />
         <td>#ffa07a</td>
         <td>255,160,122</td>
      </tr>
      <tr>
         <td>21</td>
         <td style="background: #ff4500;" />
         <td>#ff4500</td>
         <td>255,69,0</td>
      </tr>
      <tr>
         <td>22</td>
         <td style="background: #ff8c00;" />
         <td>#ff8c00</td>
         <td>255,140,0</td>
      </tr>
      <tr>
         <td>23</td>
         <td style="background: #ffa500;" />
         <td>#ffa500</td>
         <td>255,165,0</td>
      </tr>
      <tr>
         <td>24</td>
         <td style="background: #ffd700;" />
         <td>#ffd700</td>
         <td>255,215,0</td>
      </tr>
      <tr>
         <td>25</td>
         <td style="background: #b8860b;" />
         <td>#b8860b</td>
         <td>184,134,11</td>
      </tr>
      <tr>
         <td>26</td>
         <td style="background: #daa520;" />
         <td>#daa520</td>
         <td>218,165,32</td>
      </tr>
      <tr>
         <td>27</td>
         <td style="background: #eee8aa;" />
         <td>#eee8aa</td>
         <td>238,232,170</td>
      </tr>
      <tr>
         <td>28</td>
         <td style="background: #bdb76b;" />
         <td>#bdb76b</td>
         <td>189,183,107</td>
      </tr>
      <tr>
         <td>29</td>
         <td style="background: #f0e68c;" />
         <td>#f0e68c</td>
         <td>240,230,140</td>
      </tr>
      <tr>
         <td>30</td>
         <td style="background: #808000;" />
         <td>#808000</td>
         <td>128,128,0</td>
      </tr>
      <tr>
         <td>31</td>
         <td style="background: #ffff00;" />
         <td>#ffff00</td>
         <td>255,255,0</td>
      </tr>
      <tr>
         <td>32</td>
         <td style="background: #9acd32;" />
         <td>#9acd32</td>
         <td>154,205,50</td>
      </tr>
      <tr>
         <td>33</td>
         <td style="background: #556b2f;" />
         <td>#556b2f</td>
         <td>85,107,47</td>
      </tr>
      <tr>
         <td>34</td>
         <td style="background: #6b8e23;" />
         <td>#6b8e23</td>
         <td>107,142,35</td>
      </tr>
      <tr>
         <td>35</td>
         <td style="background: #7cfc00;" />
         <td>#7cfc00</td>
         <td>124,252,0</td>
      </tr>
      <tr>
         <td>36</td>
         <td style="background: #7fff00;" />
         <td>#7fff00</td>
         <td>127,255,0</td>
      </tr>
      <tr>
         <td>37</td>
         <td style="background: #adff2f;" />
         <td>#adff2f</td>
         <td>173,255,47</td>
      </tr>
      <tr>
         <td>38</td>
         <td style="background: #006400;" />
         <td>#006400</td>
         <td>0,100,0</td>
      </tr>
      <tr>
         <td>39</td>
         <td style="background: #008000;" />
         <td>#008000</td>
         <td>0,128,0</td>
      </tr>
      <tr>
         <td>40</td>
         <td style="background: #228b22;" />
         <td>#228b22</td>
         <td>34,139,34</td>
      </tr>
      <tr>
         <td>41</td>
         <td style="background: #00ff00;" />
         <td>#00ff00</td>
         <td>0,255,0</td>
      </tr>
      <tr>
         <td>42</td>
         <td style="background: #32cd32;" />
         <td>#32cd32</td>
         <td>50,205,50</td>
      </tr>
      <tr>
         <td>43</td>
         <td style="background: #90ee90;" />
         <td>#90ee90</td>
         <td>144,238,144</td>
      </tr>
      <tr>
         <td>44</td>
         <td style="background: #98fb98;" />
         <td>#98fb98</td>
         <td>152,251,152</td>
      </tr>
      <tr>
         <td>45</td>
         <td style="background: #8fbc8f;" />
         <td>#8fbc8f</td>
         <td>143,188,143</td>
      </tr>
      <tr>
         <td>46</td>
         <td style="background: #00fa9a;" />
         <td>#00fa9a</td>
         <td>0,250,154</td>
      </tr>
      <tr>
         <td>47</td>
         <td style="background: #00ff7f;" />
         <td>#00ff7f</td>
         <td>0,255,127</td>
      </tr>
      <tr>
         <td>48</td>
         <td style="background: #2e8b57;" />
         <td>#2e8b57</td>
         <td>46,139,87</td>
      </tr>
      <tr>
         <td>49</td>
         <td style="background: #66cdaa;" />
         <td>#66cdaa</td>
         <td>102,205,170</td>
      </tr>
      <tr>
         <td>50</td>
         <td style="background: #3cb371;" />
         <td>#3cb371</td>
         <td>60,179,113</td>
      </tr>
      <tr>
         <td>51</td>
         <td style="background: #20b2aa;" />
         <td>#20b2aa</td>
         <td>32,178,170</td>
      </tr>
      <tr>
         <td>52</td>
         <td style="background: #2f4f4f;" />
         <td>#2f4f4f</td>
         <td>47,79,79</td>
      </tr>
      <tr>
         <td>53</td>
         <td style="background: #008080;" />
         <td>#008080</td>
         <td>0,128,128</td>
      </tr>
      <tr>
         <td>54</td>
         <td style="background: #008b8b;" />
         <td>#008b8b</td>
         <td>0,139,139</td>
      </tr>
      <tr>
         <td>55</td>
         <td style="background: #00ffff;" />
         <td>#00ffff</td>
         <td>0,255,255</td>
      </tr>
      <tr>
         <td>56</td>
         <td style="background: #00ffff;" />
         <td>#00ffff</td>
         <td>0,255,255</td>
      </tr>
      <tr>
         <td>57</td>
         <td style="background: #e0ffff;" />
         <td>#e0ffff</td>
         <td>224,255,255</td>
      </tr>
      <tr>
         <td>58</td>
         <td style="background: #00ced1;" />
         <td>#00ced1</td>
         <td>0,206,209</td>
      </tr>
      <tr>
         <td>59</td>
         <td style="background: #40e0d0;" />
         <td>#40e0d0</td>
         <td>64,224,208</td>
      </tr>
      <tr>
         <td>60</td>
         <td style="background: #48d1cc;" />
         <td>#48d1cc</td>
         <td>72,209,204</td>
      </tr>
      <tr>
         <td>61</td>
         <td style="background: #afeeee;" />
         <td>#afeeee</td>
         <td>175,238,238</td>
      </tr>
      <tr>
         <td>62</td>
         <td style="background: #7fffd4;" />
         <td>#7fffd4</td>
         <td>127,255,212</td>
      </tr>
      <tr>
         <td>63</td>
         <td style="background: #b0e0e6;" />
         <td>#b0e0e6</td>
         <td>176,224,230</td>
      </tr>
      <tr>
         <td>64</td>
         <td style="background: #5f9ea0;" />
         <td>#5f9ea0</td>
         <td>95,158,160</td>
      </tr>
      <tr>
         <td>65</td>
         <td style="background: #4682b4;" />
         <td>#4682b4</td>
         <td>70,130,180</td>
      </tr>
      <tr>
         <td>66</td>
         <td style="background: #6495ed;" />
         <td>#6495ed</td>
         <td>100,149,237</td>
      </tr>
      <tr>
         <td>67</td>
         <td style="background: #00bfff;" />
         <td>#00bfff</td>
         <td>0,191,255</td>
      </tr>
      <tr>
         <td>68</td>
         <td style="background: #1e90ff;" />
         <td>#1e90ff</td>
         <td>30,144,255</td>
      </tr>
      <tr>
         <td>69</td>
         <td style="background: #add8e6;" />
         <td>#add8e6</td>
         <td>173,216,230</td>
      </tr>
      <tr>
         <td>70</td>
         <td style="background: #87ceeb;" />
         <td>#87ceeb</td>
         <td>135,206,235</td>
      </tr>
      <tr>
         <td>71</td>
         <td style="background: #87cefa;" />
         <td>#87cefa</td>
         <td>135,206,250</td>
      </tr>
      <tr>
         <td>72</td>
         <td style="background: #191970;" />
         <td>#191970</td>
         <td>25,25,112</td>
      </tr>
      <tr>
         <td>73</td>
         <td style="background: #000080;" />
         <td>#000080</td>
         <td>0,0,128</td>
      </tr>
      <tr>
         <td>74</td>
         <td style="background: #00008b;" />
         <td>#00008b</td>
         <td>0,0,139</td>
      </tr>
      <tr>
         <td>75</td>
         <td style="background: #0000cd;" />
         <td>#0000cd</td>
         <td>0,0,205</td>
      </tr>
      <tr>
         <td>76</td>
         <td style="background: #0000ff;" />
         <td>#0000ff</td>
         <td>0,0,255</td>
      </tr>
      <tr>
         <td>77</td>
         <td style="background: #4169e1;" />
         <td>#4169e1</td>
         <td>65,105,225</td>
      </tr>
      <tr>
         <td>78</td>
         <td style="background: #8a2be2;" />
         <td>#8a2be2</td>
         <td>138,43,226</td>
      </tr>
      <tr>
         <td>79</td>
         <td style="background: #4b0082;" />
         <td>#4b0082</td>
         <td>75,0,130</td>
      </tr>
      <tr>
         <td>80</td>
         <td style="background: #483d8b;" />
         <td>#483d8b</td>
         <td>72,61,139</td>
      </tr>
      <tr>
         <td>81</td>
         <td style="background: #6a5acd;" />
         <td>#6a5acd</td>
         <td>106,90,205</td>
      </tr>
      <tr>
         <td>82</td>
         <td style="background: #7b68ee;" />
         <td>#7b68ee</td>
         <td>123,104,238</td>
      </tr>
      <tr>
         <td>83</td>
         <td style="background: #9370db;" />
         <td>#9370db</td>
         <td>147,112,219</td>
      </tr>
      <tr>
         <td>84</td>
         <td style="background: #8b008b;" />
         <td>#8b008b</td>
         <td>139,0,139</td>
      </tr>
      <tr>
         <td>85</td>
         <td style="background: #9400d3;" />
         <td>#9400d3</td>
         <td>148,0,211</td>
      </tr>
      <tr>
         <td>86</td>
         <td style="background: #9932cc;" />
         <td>#9932cc</td>
         <td>153,50,204</td>
      </tr>
      <tr>
         <td>87</td>
         <td style="background: #ba55d3;" />
         <td>#ba55d3</td>
         <td>186,85,211</td>
      </tr>
      <tr>
         <td>88</td>
         <td style="background: #800080;" />
         <td>#800080</td>
         <td>128,0,128</td>
      </tr>
      <tr>
         <td>89</td>
         <td style="background: #d8bfd8;" />
         <td>#d8bfd8</td>
         <td>216,191,216</td>
      </tr>
      <tr>
         <td>90</td>
         <td style="background: #dda0dd;" />
         <td>#dda0dd</td>
         <td>221,160,221</td>
      </tr>
      <tr>
         <td>91</td>
         <td style="background: #ee82ee;" />
         <td>#ee82ee</td>
         <td>238,130,238</td>
      </tr>
      <tr>
         <td>92</td>
         <td style="background: #ff00ff;" />
         <td>#ff00ff</td>
         <td>255,0,255</td>
      </tr>
      <tr>
         <td>93</td>
         <td style="background: #da70d6;" />
         <td>#da70d6</td>
         <td>218,112,214</td>
      </tr>
      <tr>
         <td>94</td>
         <td style="background: #c71585;" />
         <td>#c71585</td>
         <td>199,21,133</td>
      </tr>
      <tr>
         <td>95</td>
         <td style="background: #db7093;" />
         <td>#db7093</td>
         <td>219,112,147</td>
      </tr>
      <tr>
         <td>96</td>
         <td style="background: #ff1493;" />
         <td>#ff1493</td>
         <td>255,20,147</td>
      </tr>
      <tr>
         <td>97</td>
         <td style="background: #ff69b4;" />
         <td>#ff69b4</td>
         <td>255,105,180</td>
      </tr>
      <tr>
         <td>98</td>
         <td style="background: #ffb6c1;" />
         <td>#ffb6c1</td>
         <td>255,182,193</td>
      </tr>
      <tr>
         <td>99</td>
         <td style="background: #ffc0cb;" />
         <td>#ffc0cb</td>
         <td>255,192,203</td>
      </tr>
      <tr>
         <td>100</td>
         <td style="background: #faebd7;" />
         <td>#faebd7</td>
         <td>250,235,215</td>
      </tr>
      <tr>
         <td>101</td>
         <td style="background: #f5f5dc;" />
         <td>#f5f5dc</td>
         <td>245,245,220</td>
      </tr>
      <tr>
         <td>102</td>
         <td style="background: #ffe4c4;" />
         <td>#ffe4c4</td>
         <td>255,228,196</td>
      </tr>
      <tr>
         <td>103</td>
         <td style="background: #ffebcd;" />
         <td>#ffebcd</td>
         <td>255,235,205</td>
      </tr>
      <tr>
         <td>104</td>
         <td style="background: #f5deb3;" />
         <td>#f5deb3</td>
         <td>245,222,179</td>
      </tr>
      <tr>
         <td>105</td>
         <td style="background: #fff8dc;" />
         <td>#fff8dc</td>
         <td>255,248,220</td>
      </tr>
      <tr>
         <td>106</td>
         <td style="background: #fffacd;" />
         <td>#fffacd</td>
         <td>255,250,205</td>
      </tr>
      <tr>
         <td>107</td>
         <td style="background: #fafad2;" />
         <td>#fafad2</td>
         <td>250,250,210</td>
      </tr>
      <tr>
         <td>108</td>
         <td style="background: #ffffe0;" />
         <td>#ffffe0</td>
         <td>255,255,224</td>
      </tr>
      <tr>
         <td>109</td>
         <td style="background: #8b4513;" />
         <td>#8b4513</td>
         <td>139,69,19</td>
      </tr>
      <tr>
         <td>110</td>
         <td style="background: #a0522d;" />
         <td>#a0522d</td>
         <td>160,82,45</td>
      </tr>
      <tr>
         <td>111</td>
         <td style="background: #d2691e;" />
         <td>#d2691e</td>
         <td>210,105,30</td>
      </tr>
      <tr>
         <td>112</td>
         <td style="background: #cd853f;" />
         <td>#cd853f</td>
         <td>205,133,63</td>
      </tr>
      <tr>
         <td>113</td>
         <td style="background: #f4a460;" />
         <td>#f4a460</td>
         <td>244,164,96</td>
      </tr>
      <tr>
         <td>114</td>
         <td style="background: #deb887;" />
         <td>#deb887</td>
         <td>222,184,135</td>
      </tr>
      <tr>
         <td>115</td>
         <td style="background: #d2b48c;" />
         <td>#d2b48c</td>
         <td>210,180,140</td>
      </tr>
      <tr>
         <td>116</td>
         <td style="background: #bc8f8f;" />
         <td>#bc8f8f</td>
         <td>188,143,143</td>
      </tr>
      <tr>
         <td>117</td>
         <td style="background: #ffe4b5;" />
         <td>#ffe4b5</td>
         <td>255,228,181</td>
      </tr>
      <tr>
         <td>118</td>
         <td style="background: #ffdead;" />
         <td>#ffdead</td>
         <td>255,222,173</td>
      </tr>
      <tr>
         <td>119</td>
         <td style="background: #ffdab9;" />
         <td>#ffdab9</td>
         <td>255,218,185</td>
      </tr>
      <tr>
         <td>120</td>
         <td style="background: #ffe4e1;" />
         <td>#ffe4e1</td>
         <td>255,228,225</td>
      </tr>
      <tr>
         <td>121</td>
         <td style="background: #fff0f5;" />
         <td>#fff0f5</td>
         <td>255,240,245</td>
      </tr>
      <tr>
         <td>122</td>
         <td style="background: #faf0e6;" />
         <td>#faf0e6</td>
         <td>250,240,230</td>
      </tr>
      <tr>
         <td>123</td>
         <td style="background: #fdf5e6;" />
         <td>#fdf5e6</td>
         <td>253,245,230</td>
      </tr>
      <tr>
         <td>124</td>
         <td style="background: #ffefd5;" />
         <td>#ffefd5</td>
         <td>255,239,213</td>
      </tr>
      <tr>
         <td>125</td>
         <td style="background: #fff5ee;" />
         <td>#fff5ee</td>
         <td>255,245,238</td>
      </tr>
      <tr>
         <td>126</td>
         <td style="background: #f5fffa;" />
         <td>#f5fffa</td>
         <td>245,255,250</td>
      </tr>
      <tr>
         <td>127</td>
         <td style="background: #708090;" />
         <td>#708090</td>
         <td>112,128,144</td>
      </tr>
      <tr>
         <td>128</td>
         <td style="background: #778899;" />
         <td>#778899</td>
         <td>119,136,153</td>
      </tr>
      <tr>
         <td>129</td>
         <td style="background: #b0c4de;" />
         <td>#b0c4de</td>
         <td>176,196,222</td>
      </tr>
      <tr>
         <td>130</td>
         <td style="background: #e6e6fa;" />
         <td>#e6e6fa</td>
         <td>230,230,250</td>
      </tr>
      <tr>
         <td>131</td>
         <td style="background: #fffaf0;" />
         <td>#fffaf0</td>
         <td>255,250,240</td>
      </tr>
      <tr>
         <td>132</td>
         <td style="background: #f0f8ff;" />
         <td>#f0f8ff</td>
         <td>240,248,255</td>
      </tr>
      <tr>
         <td>133</td>
         <td style="background: #f8f8ff;" />
         <td>#f8f8ff</td>
         <td>248,248,255</td>
      </tr>
      <tr>
         <td>134</td>
         <td style="background: #f0fff0;" />
         <td>#f0fff0</td>
         <td>240,255,240</td>
      </tr>
      <tr>
         <td>135</td>
         <td style="background: #fffff0;" />
         <td>#fffff0</td>
         <td>255,255,240</td>
      </tr>
      <tr>
         <td>136</td>
         <td style="background: #f0ffff;" />
         <td>#f0ffff</td>
         <td>240,255,255</td>
      </tr>
      <tr>
         <td>137</td>
         <td style="background: #fffafa;" />
         <td>#fffafa</td>
         <td>255,250,250</td>
      </tr>
      <tr>
         <td>138</td>
         <td style="background: #000000;" />
         <td>#000000</td>
         <td>0,0,0</td>
      </tr>
      <tr>
         <td>139</td>
         <td style="background: #696969;" />
         <td>#696969</td>
         <td>105,105,105</td>
      </tr>
      <tr>
         <td>140</td>
         <td style="background: #808080;" />
         <td>#808080</td>
         <td>128,128,128</td>
      </tr>
      <tr>
         <td>141</td>
         <td style="background: #a9a9a9;" />
         <td>#a9a9a9</td>
         <td>169,169,169</td>
      </tr>
      <tr>
         <td>142</td>
         <td style="background: #c0c0c0;" />
         <td>#c0c0c0</td>
         <td>192,192,192</td>
      </tr>
      <tr>
         <td>143</td>
         <td style="background: #d3d3d3;" />
         <td>#d3d3d3</td>
         <td>211,211,211</td>
      </tr>
      <tr>
         <td>144</td>
         <td style="background: #dcdcdc;" />
         <td>#dcdcdc</td>
         <td>220,220,220</td>
      </tr>
      <tr>
         <td>145</td>
         <td style="background: #f5f5f5;" />
         <td>#f5f5f5</td>
         <td>245,245,245</td>
      </tr>
      <tr>
         <td>146</td>
         <td style="background: #ffffff;" />
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
