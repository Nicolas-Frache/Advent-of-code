package _2023._03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3Part1 {
    static ArrayList<ArrayList<Character>> engine;

    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_03\\input.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;

        engine = new ArrayList<>();
        int i = 0;
        int sum = 0;

        // Création d'un tableau à 2 dimensions
        while ((ligne = reader.readLine()) != null) {
            engine.add(new ArrayList<>());
            for (int j = 0; j < ligne.length(); j++) {
                engine.get(i).add(ligne.charAt(j));
            }
            i++;
        }
        reader.close();

        i = 0;
        for (var line : engine) {
            boolean inNumber = false;
            boolean isNumberValid = false;
            String currentNumber = "";

            int j = 0;
            for (char c : line) {
                // DEBUT NOMBRE
                if (!inNumber && Character.isDigit(c)) {
                    inNumber = true;
                    isNumberValid = false;
                    currentNumber = "";

                    if (j != 0) { // VERIF GAUCHE
                        isNumberValid = isSymbol(i, j - 1);
                        if (!isNumberValid && i != 0) { // AU DESSUS
                            isNumberValid = isSymbol(i - 1, j - 1);
                        }
                        if (!isNumberValid && i != engine.size() - 1) { // EN DESSOUS
                            isNumberValid = isSymbol(i + 1, j - 1);
                        }
                    }
                }

                // PARTOUT DANS LE NOMBRE
                if (inNumber && Character.isDigit(c)) {
                    currentNumber += c;
                    // AU DESSUS
                    if (!isNumberValid && i != 0) {
                        isNumberValid = isSymbol(i - 1, j);
                    }
                    // EN DESSOUS
                    if (!isNumberValid && i != engine.size() - 1) {
                        isNumberValid = isSymbol(i + 1, j);
                    }
                }

                // FIN NOMBRE
                if (inNumber && !Character.isDigit(c) || inNumber && j == line.size() - 1) {
                    if (!isNumberValid && j != line.size() - 1) { // VERIF DROITE
                        isNumberValid = isSymbol(i, j);
                        if (!isNumberValid && i != 0) { // AU DESSUS
                            isNumberValid = isSymbol(i - 1, j);
                        }
                        if (!isNumberValid && i != engine.size() - 1) { // EN DESSOUS
                            isNumberValid = isSymbol(i + 1, j);
                        }
                    }

                    if (isNumberValid) {
                        sum += Integer.parseInt(currentNumber);
                    }
                    inNumber = false;
                }
                j++;
            }
            i++;
        }
        System.out.println(sum);
    }

    static boolean isSymbol(int i, int j) {
        char c = engine.get(i).get(j);
        return !Character.isDigit(c) && c != '.';
    }
}