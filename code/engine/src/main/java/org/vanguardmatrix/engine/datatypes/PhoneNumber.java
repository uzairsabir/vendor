package org.vanguardmatrix.engine.datatypes;

import org.vanguardmatrix.engine.parser.ParseString;

public class PhoneNumber {

    protected String phoneNumber = "";

    public PhoneNumber(String phone_Number) {

        this.phoneNumber = getAbsoluteNumber(phone_Number);
    }

    public static String getFormatedNumber(String phoneNumber) {

        // Log.e("Received Number 2 format", " : " + phoneNumber);

        phoneNumber = PhoneNumber.getAbsoluteNumber(phoneNumber);

        try {
            return "+"
                    + phoneNumber.substring(2, phoneNumber.length() - 10)
                    + " "
                    + phoneNumber.substring(phoneNumber.length() - 10,
                    phoneNumber.length() - 7)
                    + " "
                    + phoneNumber.substring(phoneNumber.length() - 7,
                    phoneNumber.length() - 4)
                    + " "
                    + phoneNumber.substring(phoneNumber.length() - 4,
                    phoneNumber.length());

        } catch (Exception e) {
            // e.printStackTrace();
            return PhoneNumber.getAbsoluteNumber(phoneNumber);
        }

    }

    public static String getOnlyDigits(String number) {
        return ParseString.getOnlyDigits(number);
    }

    public static int getOnlyDigits(String number, boolean returnInt) {
        return Integer.parseInt(ParseString.getOnlyDigits(number));
    }

    public static String getFormattedNumber(String userName) {
        String x = "";
        if (userName.contains("@")) {
            x = userName.substring(0, userName.indexOf("@"));
        } else
            x = userName;

        return x;
    }

    public static String getAbsoluteNumber(String phoneNumber) {
        try {
            return PhoneNumber.getAbsoluteNumber(phoneNumber, 0);
        } catch (Exception e) {

        }
        return null;
    }

    public static String getAbsoluteNumber(String phoneNumber, int countryCode) {

        // Log.e("Received Num", phoneNumber);

        if (phoneNumber.charAt(0) == '+') {
            try {
                phoneNumber = "00" + getOnlyDigits(phoneNumber.trim());
            } catch (Exception e) {

            }

            // Log.e("Delivered Num2", "-----  " + phoneNumber);
            return phoneNumber;
        }

        phoneNumber = getOnlyDigits(phoneNumber.trim());

        if (phoneNumber.length() == 10)
            phoneNumber = countryCode + phoneNumber;

        if (phoneNumber.startsWith("00") && phoneNumber.length() > 12) {
            // Log.e("Delivered Num1", "-----  " + phoneNumber);
            return phoneNumber;
        }

        if (phoneNumber.length() < 11) {
            // Log.e("Delivered Num3", "-----  " + phoneNumber);
            return phoneNumber;
        }

        if (phoneNumber.length() >= 12 && !phoneNumber.startsWith("00")) {

            // Log.e("Delivered Num 4", "-----  " + "00" + phoneNumber);
            return "00" + phoneNumber;
        }

        phoneNumber = "00" + countryCode
                + phoneNumber.substring(1, phoneNumber.length());

        // Log.e("Delivered Num 5", "-----  " + phoneNumber);
        return phoneNumber;

    }

    public static int getCountryCode(String phoneNumber) {
        if (phoneNumber.length() <= 11)
            return 0;

        if (phoneNumber.charAt(1) == '0' || phoneNumber.charAt(1) == '+') {
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 10);
        }

        phoneNumber.replace("+", "");
        phoneNumber.replace("00", "");
        phoneNumber.replace(" ", "");

        return Integer.parseInt(phoneNumber);
    }

    public String getAbsoluteNumber() {
        return PhoneNumber.getAbsoluteNumber(this.phoneNumber);

        // return this.phoneNumber;
    }

    public String getNumber() {
        return this.phoneNumber;
    }

    public String getFormatedNumber() {

        return PhoneNumber.getFormatedNumber(this.phoneNumber);

    }

    public String getFormatedNumber(boolean is4AllContactList) {

        return this.phoneNumber;

    }
}
