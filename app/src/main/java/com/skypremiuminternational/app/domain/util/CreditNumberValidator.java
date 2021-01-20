package com.skypremiuminternational.app.domain.util;

import javax.inject.Inject;

/**
 * Luhn Class is an implementation of the Luhn algorithm that checks validity of a credit card number.
 *
 * @param creditNumber the credit card number to validate.
 * @author <a href="http://www.chriswareham.demon.co.uk/software/Luhn.java">Chris Wareham</a>
 * @version Checks whether a string of digits is a valid credit card number according to the Luhn algorithm. 1. Starting with the second to last digit and
 * moving left, double the value of all the alternating digits. For any digits that thus become 10 or more, add their digits together. For example,
 * 1111 becomes 2121, while 8763 becomes 7733 (from (1+6)7(1+2)3). 2. Add all these digits together. For example, 1111 becomes 2121, then 2+1+2+1 is
 * 6; while 8763 becomes 7733, then 7+7+3+3 is 20. 3. If the total ends in 0 (put another way, if the total modulus 10 is 0), then the number is valid
 * according to the Luhn formula, else it is not valid. So, 1111 is not valid (as shown above, it comes out to 6), while 8763 is valid (as shown
 * above, it comes out to 20).
 * @return <b>true</b> if the number is valid, <b>false</b> otherwise.
 */
public class CreditNumberValidator {

  @Inject
  public CreditNumberValidator() {

  }

  public boolean validate(String creditNumber) {
    if (creditNumber == null) return false;
    if (creditNumber.trim().equalsIgnoreCase("")) return false;
    if (creditNumber.length() != 16) return false;
    if (creditNumber.contains(" ")) return false;

    boolean alternate = false;
    int sum = 0;
    for (int i = creditNumber.length() - 1; i >= 0; i--) {
      int n = Integer.parseInt(creditNumber.substring(i, i + 1));
      if (alternate) {
        n *= 2;
        if (n > 9) {
          n = (n % 10) + 1;
        }
      }
      sum += n;
      alternate = !alternate;
    }
    return (sum % 10 == 0);
  }
}
