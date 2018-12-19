package com.github.kilianB;

/**
 * Symbol types for legend and chart
 * @author Kilian
 *
 */
public enum SymbolType {
	
	solidSquare("Square","solid"),
	solidDiamond("Diamond","solid"),
	solidCross("Cross","solid"),
	solidTriangle("Triangle","solid"),
	solidCicrle("Circle","solid"),
	
	//Hollow not yet implemented
	hollowSquare("Square","hollow"),
	hollowDiamond("Diamond","hollow"),
	hollowCross("Cross","hollow"),
	hollowTriangle("Triangle","hollow"),
	hollowCircle("Circle","hollow");
	
	
	private SymbolType(String type, String fillType) {
		this.type = type;
		this.fillType = fillType;
	}
	
	String type;
	String fillType;
	
	public String getType() {
		return type;
	}
	
	public String getFill() {
		return fillType;
	}
}
