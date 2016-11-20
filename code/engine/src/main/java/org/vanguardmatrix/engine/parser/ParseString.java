package org.vanguardmatrix.engine.parser;

import java.util.Locale;

public class ParseString {

    public ParseString() {
        // TODO Auto-generated constructor stub
    }

    public static String getOnlyDigits(String string) {
        String str = string;
        str = str.replaceAll("[^\\d]", "");

        str = str.replace(")", "");
        str = str.replace("(", "");
        return str;
    }

    public static String[] getDashSeparatedString(String string) {
        String str[] = string.split("-");
        return str;
    }

    public static String getOnlyDigits(String string, boolean ignoreFirstdigit) {

        String firstChar = string.substring(0, 1);
        String str = "";
        if (firstChar.equals("+")) {
            str = string.substring(1);
            str = str.replaceAll("[^\\d]", "");
            str = str.replace(")", "");
            str = str.replace("(", "");
            return firstChar + str;
        } else {
            str = string;
            str = str.replaceAll("[^\\d]", "");
            str = str.replace(")", "");
            str = str.replace("(", "");
            return str;
        }
    }

    public static String getAlphaAndDigitsOnly(String input) {
        return input.replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
    }

    public static String getInitCap(String input) {
        String s = "";
        if (input.contains(" ")) {

            String[] x = input.split(" ");

            if (x.length > 0) {

                for (String y : x) {
                    try {
                        s = s
                                + (y.substring(0, 1).toUpperCase(
                                Locale.getDefault()) + y.substring(1,
                                y.length()));
                        s = s + " ";
                    } catch (Exception e) {

                    }

                }
            } else {
                try {
                    s = input.substring(0, 1).toUpperCase(Locale.getDefault())
                            + input.substring(1, input.length());
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        } else {

            try {
                s = input.substring(0, 1).toUpperCase(Locale.getDefault())
                        + input.substring(1, input.length());
            } catch (Exception e) {
                s = input;
            }

        }

        return s;
    }

    /*
     * Replace all instances of a String in a String.
     *
     * @param s String to alter.
     *
     * @param f String to look for.
     *
     * @param r String to replace it with, or null to just remove it.
     */
    public static String alterSlashes(String dataString, String toLookFor,
                                      String toReplaceWith) {
        if (dataString == null)
            return dataString;
        if (toLookFor == null)
            return dataString;
        if (toReplaceWith == null)
            toReplaceWith = "";

        int index01 = dataString.indexOf(toLookFor);
        while (index01 != -1) {
            dataString = dataString.substring(0, index01) + toReplaceWith
                    + dataString.substring(index01 + toLookFor.length());
            index01 += toReplaceWith.length();
            index01 = dataString.indexOf(toLookFor, index01);
        }
        return dataString;
    }

    public static String removeSlash(String s) {
        String f = "\'", r = "'";
        if (s == null)
            return s;

        int index01 = s.indexOf(f);
        while (index01 != -1) {
            s = s.substring(0, index01) + r + s.substring(index01 + f.length());
            index01 += r.length();
            index01 = s.indexOf(f, index01);
        }
        return s;
    }

    public static String addSlash(String s) {

        String f = "'", r = "\'";
        if (s == null)
            return s;

        int index01 = s.indexOf(f);
        while (index01 != -1) {
            s = s.substring(0, index01) + r + s.substring(index01 + f.length());
            index01 += r.length();
            index01 = s.indexOf(f, index01);
        }
        return s;
    }

    public static String filterTag(String s) {
        return s.replaceAll("[-+.^:,\\W]", "").toLowerCase(Locale.getDefault());
    }

}
