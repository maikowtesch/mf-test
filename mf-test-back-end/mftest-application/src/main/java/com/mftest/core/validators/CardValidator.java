package com.mftest.core.validators;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CardValidator {
	
	/**
	 * Validate the card number.<br>
	 * Must be a numeric string. Length is in between 13 to 19 characters.<br><br>
	 * (there's an extra validation with the "Luhn algorithm", but I decided to not implement for the sake of simplicity)
	 * @param number
	 * @return true if valid, false otherwise.
	 */
	public static boolean validateCardNumber(String number) {
		// only numbers, length between 13 and 19.
		if ( !number.matches("[0-9]+") || number.length() < 13 || number.length() > 19 ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Validate the expiry date.<br>
	 * Format YY/MM. Year must not be a past year. Month must be in between 1 and 12.
	 * @param date
	 * @return true if valid, false otherwise.
	 */
	@SuppressWarnings("static-access")
	public static boolean validateExpiryDate(String date) {
		String[] split = date.split("\\/");
		
		int currentYear = new GregorianCalendar().getInstance().get(Calendar.YEAR) - 2000;
		
		if (split.length != 2 ||
			split[0].length() != 2 || split[1].length() != 2 ||
			!split[0].matches("[0-9]+") || !split[1].matches("[0-9]+") ||
			Integer.parseInt(split[0]) < currentYear ||
			Integer.parseInt(split[1]) < 1 || Integer.parseInt(split[1]) > 12) {
			return false;
		}
		
		return true;
	}

}
