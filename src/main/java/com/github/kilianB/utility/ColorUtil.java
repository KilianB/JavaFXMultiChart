package com.github.kilianB.utility;

import javafx.scene.paint.Color;

public class ColorUtil {

	/**
	 * Return either white or black depending on the supplied color to guarantee
	 * readability if the contrast color is used as text overlay ontop of the input
	 * color.
	 * 
	 * @param input
	 * @return
	 */
	public static Color getContrastColor(Color input) {
		// Luminascense
		double y = 0.299 * input.getRed() + 0.587 * input.getGreen() + 0.114 * input.getBlue();
		if (y > 0.55) {
			return Color.BLACK;
		} else {
			return Color.WHITE;
		}
	}

	/**
	 * Create color palettes
	 * 
	 * @author Kilian
	 *
	 */
	public static class ColorPalette {

		public static Color[] getPallette(int numColors) {
			return getPallette(numColors, Color.web("#003f5c"), Color.web("#ffa600"));
		}

		public static Color[] getPallette(int numColors, Color startColor, Color endColor) {

			Color[] cols = new Color[numColors];
			for (int i = 0; i < numColors; i++) {
				double factor = i / (double) numColors;
				cols[i] = startColor.interpolate(endColor, factor);
			}
			return cols;
		}

		public static Color[] getPalletteHue(int numColors) {
			return getPalletteHue(numColors, Color.web("#003f5c"), Color.web("#ffa600"));
		}

		public static Color[] getPalletteHue(int numColors, Color startColor, Color endColor) {

			double hDelta = (endColor.getHue() - startColor.getHue()) / numColors;
			double sDelta = (endColor.getSaturation() - startColor.getSaturation()) / numColors;
			double bDelta = (endColor.getBrightness() - startColor.getBrightness()) / numColors;

			Color[] cols = new Color[numColors];
			for (int i = 0; i < numColors; i++) {

				double newSat = startColor.getSaturation() + sDelta * i;
				double newBrightness = startColor.getBrightness() + bDelta * i;

				// Wrap around
				if (newSat > 1) {
					newSat = MathUtil.getFractionalPart(newSat);
				} else if (newSat < 0) {
					newSat = 1 - newSat;
				}

				if (newBrightness > 1) {
					newBrightness = MathUtil.getFractionalPart(newBrightness);
				} else if (newBrightness < 0) {
					newBrightness = 1 - newBrightness;
				}

				cols[i] = Color.hsb(startColor.getHue() + hDelta * i, newSat, newBrightness);
			}
			return cols;
		}
	}

	public static java.awt.Color fxToAwtColor(Color fxColor) {
		return new java.awt.Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue(),
				(float) fxColor.getOpacity());
	}

	public static Color awtToFxColor(java.awt.Color awtColor) {
		return new Color(awtColor.getRed() / 255d, awtColor.getGreen() / 255d, awtColor.getBlue() / 255d,
				awtColor.getAlpha() / 255d);
	}

	/**
	 * Convert an argb value to it's individual components in range of 0 - 255
	 * 
	 * @param argb
	 *            values as int
	 * @return [0] Alpha, [1] Red, [2] Green, [3] Blue
	 * 
	 */
	public static int[] argbToComponents(int argb) {
		return new int[] { argb >> 24 & 0xFF, argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF };
	}

	/**
	 * Converts the components to a single int argb representation. The individual
	 * values are not range checked
	 * 
	 * @param alpha
	 *            in range of 0 - 255
	 * @param red
	 *            in range of 0 - 255
	 * @param green
	 *            in range of 0 - 255
	 * @param blue
	 *            in range of 0 - 255
	 * @return a single int representing the argb value
	 */
	public static int componentsToARGB(int alpha, int red, int green, int blue) {
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	public static javafx.scene.paint.Color argbToFXColor(int argb) {

		int[] components = argbToComponents(argb);

		return new javafx.scene.paint.Color(components[1] / 255d, components[2] / 255d, components[3] / 255d,
				components[0] / 255d);
	}

	public static String fxToHex(Color color) {
		return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}

	// https://stackoverflow.com/a/2103608/3244464
	// https://www.compuphase.com/cmetric.htm
	public static double distance(Color c1, Color c2) {
		double rmean = (c1.getRed() * 255 + c2.getRed() * 255) / 2;
		int r = (int) (c1.getRed() * 255 - c2.getRed() * 255);
		int g = (int) (c1.getGreen() * 255 - c2.getGreen() * 255);
		int b = (int) (c1.getBlue() * 255 - c2.getBlue() * 255);
		double weightR = 2 + rmean / 256;
		double weightG = 4.0;
		double weightB = 2 + (255 - rmean) / 256;
		return Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b);
	}

	// https://stackoverflow.com/a/2103608/3244464
	// https://www.compuphase.com/cmetric.htm
	public static double distance(java.awt.Color c1, java.awt.Color c2) {
		double rmean = (c1.getRed() + c2.getRed()) / 2;
		int r = (int) (c1.getRed() - c2.getRed());
		int g = (int) (c1.getGreen() - c2.getGreen());
		int b = (int) (c1.getBlue() - c2.getBlue());
		double weightR = 2 + rmean / 256;
		double weightG = 4.0;
		double weightB = 2 + (255 - rmean) / 256;
		return Math.sqrt(weightR * r * r + weightG * g * g + weightB * b * b);
	}

}
