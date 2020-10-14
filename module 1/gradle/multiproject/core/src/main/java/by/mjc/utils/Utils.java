package by.mjc.utils;

import by.mjc.util.StringUtils;

public class Utils {
	
	public boolean isAllPositiveNumbers(String... str) {
		StringUtils su = new StringUtils();
		
		for (String item : str) {
			if (!su.isPositiveNumber(item)) {
				return false;
			}
		}
		
		
		return true;
	}
}
