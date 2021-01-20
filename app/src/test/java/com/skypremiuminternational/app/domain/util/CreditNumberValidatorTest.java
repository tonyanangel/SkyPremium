package com.skypremiuminternational.app.domain.util;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreditNumberValidatorTest {

  private CreditNumberValidator validator;

  private List<String> validNumbers;
  private List<String> invalidNumbers;

  @Before
  public void setUp() throws Exception {
    validator = new CreditNumberValidator();
    invalidNumbers = new ArrayList<>();
    invalidNumbers.add("12312");
    invalidNumbers.add("5555555555554443");

    validNumbers = new ArrayList<>();
    validNumbers.add("4012888888881881");
    validNumbers.add("5555555555554444");
  }

  @Test
  public void cannot_contain_space() {
    assertFalse(validator.validate("555555555555 444"));
  }

  @Test
  public void must_have_16_digit() {
    assertTrue(validator.validate("5555555555554444"));
    assertFalse(validator.validate("555555555555444"));
  }

  @Test
  public void cannot_be_null_or_empty() {
    assertFalse(validator.validate(null));
    assertFalse(validator.validate(""));
  }

  @Test
  public void validate() {
    for (String validNumber : validNumbers) {
      assertTrue(validator.validate(validNumber));
    }

    for (String invalidNumber : invalidNumbers) {
      assertFalse(validator.validate(invalidNumber));
    }
  }
}