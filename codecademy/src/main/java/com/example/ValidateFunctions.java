package com.example;

import static org.junit.Assert.assertEquals;

import java.time.Month;


public class ValidateFunctions {
   /**
    * @desc Validates is a given date in the form of day, month and year is valid.
    * 
    * @subcontract 31 days in month {
    *   @requires (month == 1 || month == 3 || month == 5 || month == 7 || 
    *             month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
    *   @ensures \result = true;
    * }
    * 
    * @subcontract 30 days in month {
    *   @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
    *              1 <= day <= 30;
    *   @ensures \result = true;
    * }
    * 
    * @subcontract 29 days in month {
    *   @requires month == 2 && 1 <= day <= 29 &&
    *             (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    *   @ensures \result = true;
    * }
    * 
    * @subcontract 28 days in month {
    *   @requires month == 2 && 1 <= day <= 28 &&
    *             (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
    *   @ensures \result = true;
    * }
    * 
    * @subcontract all other cases {
    *   @requires no other accepting precondition;
    *   @ensures \result = false;
    * }
    * 
    */ 
    public static boolean validateDate(int day, Month month, int year) {
        

        if ((month == Month.JANUARY || month == Month.MARCH || month == Month.MAY || month == Month.JULY ||month == Month.AUGUST || month == Month.OCTOBER || month == Month.DECEMBER) && day >= 1 && day <= 31) {
            return true;
        } 
        else if ((month == Month.APRIL || month == Month.JUNE || month == Month.SEPTEMBER || month == Month.NOVEMBER) && day >= 1 && day <= 30) {
            return true;
        } 
        else if (month == Month.FEBRUARY && day >= 1 && day <= 29 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) {
            return true;
        } 
        else if (month == Month.FEBRUARY && day >= 1 && day <= 28 && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0))) {
            return true;
        } 
        else {
            return false;
        }
    }

    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[1].length < 1;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true;
     *          }
     * 
     */
    public static boolean validateMailAddress(String mailAddress) {

        if (!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1) {
            return false;
        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.").length > 2) {
            return false;
        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false;
        } else if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.").length < 2) {
            return false;
        } else {
            return true;
        }
    }

    //test.


    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     *   @requires 0 <= percentage <= 100;
     *   @ensures \result = true;
     * }
     * 
     * @subcontract value out of range low {
     *   @requires percentage < 0;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract value out of range high {
     *   @requires percentage > 100;
     *   @signals \result = false;
     * }
     * 
     */
    public static boolean isValidPercentage(int percentage) {
        if(percentage >= 0 && percentage <=100){
            return true;
        }
        else if(percentage < 0){
            return false;
        }
        else{
            return false;
        }
    
    
            
        }    


        /**
     * @desc Formats the input postal code to a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital letters.
     * Spaces before and after the input string are trimmed.
     * 
     * @subcontract null postalCode {
     *   @requires postalCode == null;
     *   @signals (NullPointerException) postalCode == null;
     * }
     * 
     * @subcontract valid postalCode {
     *   @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *             Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *             postalCode.trim().substring(4).trim().length == 2 &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *   @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *                  postalCode.trim().substring(4).trim().toUpperCase()
     * }
     *  
     * @subcontract invalid postalCode {
     *   @requires no other valid precondition;
     *   @signals (IllegalArgumentException);
     * }
     * 
     */
    public static String formatPostalCode(/* non_null */ String postalCode) {

        if(postalCode == null){
            throw new NullPointerException();
        }

        // Trim spaces before and after the input string
        postalCode = postalCode.trim();

        int numericPart = Integer.parseInt(postalCode.substring(0, 4));
        System.out.println();
        String lettersPart = postalCode.substring(4).trim().toUpperCase();

        // Check numeric part > 999 and <= 9999
        if (numericPart <= 999 || numericPart > 9999) {
            throw new IllegalArgumentException("Invalid numeric part in postal code");
        }

        // Check letters part length == 2 and both characters are capital letters
        if (lettersPart.length() != 2) {
            throw new IllegalArgumentException("Invalid letters part in postal code");
        }

        // Format the postal code as specified
        return postalCode.substring(0, 4) + " " + lettersPart;
        
    }


    
    public static boolean validateFormatURL(String url){
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("URL must start with http:// or https://");
        
        }
        String[] urlParts = url.split("/");
        if (urlParts.length < 2) {
            throw new IllegalArgumentException("Invalid URL format"); 
    
        }
        String domain = urlParts[2];
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Invalid URL format: Missing domain");
          
        }
        String[] domainParts = domain.split("\\.");
        for (String part : domainParts) {
            if (!part.matches(".*[a-zA-Z].*")) {
                throw new IllegalArgumentException("Invalid URL format: At least one letter should be before and after every dot in the domain");
           
            }
        }
        return true;
    }


    public static boolean validateRating(Integer number){
        if (number >= 1 && number <= 10) {
            return true;
        } else {
            return false;
        }  
}
}
