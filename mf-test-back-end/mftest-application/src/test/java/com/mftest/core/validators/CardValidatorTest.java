package com.mftest.core.validators;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CardValidatorTest {

	// Scenarios to test "validateCardNumber"
	
	@Test
	public void testCardNumberIsValid() {
		assertTrue(CardValidator.validateCardNumber("1111222233334444"));
	}
	
	@Test
	public void testCardNumberIsInvalidNotNumeric() {
		assertFalse(CardValidator.validateCardNumber("1111A22x333.B444"));
	}
	
	@Test
	public void testCardNumberIsInvalidLessThan13() {
		assertFalse(CardValidator.validateCardNumber("111122223333"));
	}
	
	@Test
	public void testCardNumberIsInvalidGreaterThan19() {
		assertFalse(CardValidator.validateCardNumber("11112222333344445555"));
	}
	
	
	// Scenarios to test "validateExpiryDate"
	
	@Test
	public void testExpiryDateIsValid() {
		assertTrue(CardValidator.validateExpiryDate("22/12"));
	}
	
	@Test
	public void testExpiryDateIsInvalidBadFormat() {
		assertFalse(CardValidator.validateExpiryDate("22/12/32"));
	}
	
	@Test
	public void testExpiryDateIsInvalidBadFormat2() {
		assertFalse(CardValidator.validateExpiryDate("2/10"));
	}
	
	@Test
	public void testExpiryDateIsInvalidBadFormat3() {
		assertFalse(CardValidator.validateExpiryDate("22/1"));
	}
	
	@Test
	public void testExpiryDateIsInvalidNotNumber() {
		assertFalse(CardValidator.validateExpiryDate("2x/10"));
	}
	
	@Test
	public void testExpiryDateIsInvalidNotNumber2() {
		assertFalse(CardValidator.validateExpiryDate("22/A1"));
	}
	
	@Test
	public void testExpiryDateIsInvalidLessThanCurrentYear() {
		assertFalse(CardValidator.validateExpiryDate("02/01"));
	}
	
	@Test
	public void testExpiryDateIsInvalidMonthLessThan01() {
		assertFalse(CardValidator.validateExpiryDate("22/00"));
	}
	
	@Test
	public void testExpiryDateIsInvalidMonthGreaterThan12() {
		assertFalse(CardValidator.validateExpiryDate("22/13"));
	}
	
}
