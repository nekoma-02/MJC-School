/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package by.mjc.util;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {

	public boolean isPositiveNumber(String str) {

		return NumberUtils.isDigits(str) && NumberUtils.createInteger(str) >= 0 ? true : false;

	}
}
