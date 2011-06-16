package com.mcbouncer.util;

public class MCBValidators {

	public static boolean UserAndReasonValidator(String[] args) {

		return MCBValidators.isAtLeastArgs(args, 1);
	}

	public static boolean UserValidator(String[] args) {

		return (MCBValidators.isAtLeastArgs(args, 1) && !MCBValidators.isAtLeastArgs(args, 2));
	}

	/**
	 * Returns true if the string is an integer
	 */
	protected static boolean isInteger(String arg) {
		try {
			Integer.parseInt(arg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns true if the string is a number
	 */
	protected static boolean isNumeric(String arg) {
		try {
			Double.parseDouble(arg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns true if a string is at least a length
	 */
	protected static boolean isAtLeastLength(String str, int length) {
		return str.length() >= length;
	}

	/**
	 * Returns true if there are at least a number of arguments
	 */
	protected static boolean isAtLeastArgs(String[] args, int length) {
		return args.length >= length;
	}

	/**
	 * Returns true if a value exists inside a String[]
	 */
	protected static boolean inArray(String substring, String[] strings) {

		for (String string : strings) {
			if (string.equals(substring)) {
				return true;
			}
		}

		return false;
	}
}
