package utils;

public class Arrays {
    public static char[][] extendArray(char[][] input, int factor) {
        int oldLen = input.length;
        int newLen = oldLen * factor;

        char[][] output = new char[newLen][newLen];

        for (int i = 0; i < newLen; i += oldLen) {
            for (int j = 0; j < newLen; j += oldLen) {
                for (int x = 0; x < oldLen; x++) {
                    System.arraycopy(input[x], 0, output[i + x], j, oldLen);
                }
            }
        }
        return output;
    }
}
