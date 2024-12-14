package utils;

public class ArraysUtils {
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

    public static int[][] addPadding(int[][] tableau, int padding, int valeurPadding) {
        int hauteur = tableau.length;
        int largeur = tableau[0].length;

        int nouvelleHauteur = hauteur + 2 * padding;
        int nouvelleLargeur = largeur + 2 * padding;

        int[][] tableauAvecPadding = new int[nouvelleHauteur][nouvelleLargeur];

        // Remplir le tableau avec le padding
        for (int i = 0; i < nouvelleHauteur; i++) {
            for (int j = 0; j < nouvelleLargeur; j++) {
                if (i < padding || i >= hauteur + padding || j < padding || j >= largeur + padding) {
                    tableauAvecPadding[i][j] = valeurPadding;
                } else {
                    tableauAvecPadding[i][j] = tableau[i - padding][j - padding];
                }
            }
        }

        return tableauAvecPadding;
    }

    public static char[][] addPadding(char[][] tableau, int padding, char valeurPadding) {
        int hauteur = tableau.length;
        int largeur = tableau[0].length;

        int nouvelleHauteur = hauteur + 2 * padding;
        int nouvelleLargeur = largeur + 2 * padding;

        char[][] tableauAvecPadding = new char[nouvelleHauteur][nouvelleLargeur];

        // Remplir le tableau avec le padding
        for (int i = 0; i < nouvelleHauteur; i++) {
            for (int j = 0; j < nouvelleLargeur; j++) {
                if (i < padding || i >= hauteur + padding || j < padding || j >= largeur + padding) {
                    tableauAvecPadding[i][j] = valeurPadding;
                } else {
                    tableauAvecPadding[i][j] = tableau[i - padding][j - padding];
                }
            }
        }

        return tableauAvecPadding;
    }



}
