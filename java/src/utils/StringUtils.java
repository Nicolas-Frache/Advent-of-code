package utils;

public class StringUtils {
    public static String replaceNthCharacter(String str, int n, char replacementChar) {
        if (n >= 0 && n < str.length()) {
            return str.substring(0, n) + replacementChar + str.substring(n + 1);
        } else {
            return str;
        }
    }
}
