package com.github.kilianB.utility;

public class MathUtil {

	
	
	/**
	 * Clamp a number between its lower and upper bound. 
	 * If x > upper bound return upper bound.
	 * If x < lower bound return lower bound
	 * 
	 * @param value
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	public static <T extends Number & Comparable<T>> T clampNumber(T value,T lowerBound,T upperBound) {
		if(value.compareTo(lowerBound) <= 0) {
			return lowerBound;
		}else if(value.compareTo(upperBound) >= 0) {
			return upperBound;
		} 
		return value;
	}

	/**
	 * Find the nearest integer which will divide the given number.
	 *  number%x == 0
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static long findClosestDivisibleInteger(long dividend, long divisor) {
		long lowerBound = dividend - (dividend % divisor);
		long upperBound = (dividend + divisor) - (dividend % divisor);
		if (dividend - lowerBound > upperBound - dividend) {
			return upperBound;
		} else {
			return lowerBound;
		}
	}
	
	/**
	 * Return the fractional part of the number
	 * @param d a double 
	 * @return the fractional part of the number
	 */
	public static double getFractionalPart(double d) {
		return d - (int)d;
	}
	
	public static boolean isDoubleEquals(double needle, double target, double epsilon) {
		 return Math.abs(needle - target) < epsilon;
		 
		 //We could use mashine precision e.g.  Math.ulp(d)
		 
	}
	
	/**
	 * 
	 * @param gaussian A gaussian with std 1 and mean 0
	 * @param newStd	new standard deviation
	 * @param newMean 	new mean 
	 * @return
	 */
	public static int fitGaussian(int gaussian, int newStd, int newMean) {
		return gaussian * newStd + newMean;
	}
	
	public static double fitGaussian(double gaussian, double newStd, double newMean) {
		return gaussian * newStd + newMean;
	}
	
	/**
	 * Checks if the supplied argument is a positive non null numeric value and 
	 * throws a IllegalArgumentException if it isn't
	 * @param value to be checked
	 * @return The supplied value
	 */
	public static <T extends Number> T requirePositiveValue(T value) {
		return requirePositiveValue(value,"");
	}
	
	/**
	 * Checks if the supplied argument is a positive non null numeric value and 
	 * throws a IllegalArgumentException if it isn't
	 * @param value to be checked
	 * @param message to be thrown in case of error
	 * @return The supplied value
	 */
	public static <T extends Number> T requirePositiveValue(T value,String message) {
		if(value.doubleValue() <= 0) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}
 	
	/**
	 * Checks if the supplied argument is lays within the given bounds
	 * throws a IllegalArgumentException if it doesn't
	 * @param value to be checked
	 * @param lowerBound inclusively
	 * @param higherBound inclusively
	 * @param message to be thrown in case of error
	 * @return The supplied value
	 */
	public static <T extends Number> T requireInRange(T value, T lowerBound, T higherBound, String message) {
		if(value.doubleValue() < lowerBound.doubleValue() && value.doubleValue() > higherBound.doubleValue()) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}
	
	/**
	 * Calculate the number of characters needed to present the integer part of a number
	 * <pre>
	 * 1234     -> 4
	 * 12345    -> 5
	 * 12345.12 -> 5
	 * </pre>
	 * @param n A number
	 * @return the character count of the integer part of the number
	 */
	public static int charsNeeded(Number n) {
		
		double numberAsDouble = n.doubleValue();
		int negative = (numberAsDouble < 0 ? 1 : 0);
		if(isDoubleEquals(numberAsDouble,0d,1e-5)) {
			return 1 + negative;
		}
		return (int) Math.floor(Math.log10(Math.abs(numberAsDouble))+1) + negative;
	}
}