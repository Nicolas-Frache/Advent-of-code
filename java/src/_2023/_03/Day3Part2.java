package _2023._03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3Part2 {
    static int numbersForCurrentGear = 0;
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
                if (c == '*') {
                    numbersForCurrentGear = 0;
                    int valueForThisGear = 1;

                    // ON CHERCHE LES NOMBRES AUTOUR
                    // AU-DESSUS (soit un seul nombre, soit deux dans les coins)
                    if (!Character.isDigit(engine.get(i - 1).get(j))) {
                        valueForThisGear *= getValueIfIsNumber(i - 1, j - 1); // Coin haut Gauche
                        valueForThisGear *= getValueIfIsNumber(i - 1, j + 1); // Coin haut Droit
                    } else {
                        valueForThisGear *= getValueIfIsNumber(i - 1, j); // Centre haut
                    }
                    // EN DESSOUS (soit un seul nombre, soit deux dans les coins)
                    if (!Character.isDigit(engine.get(i + 1).get(j))) {
                        valueForThisGear *= getValueIfIsNumber(i + 1, j - 1); // Coin bas Gauche
                        valueForThisGear *= getValueIfIsNumber(i + 1, j + 1); // Coin bas Droit
                    } else {
                        valueForThisGear *= getValueIfIsNumber(i + 1, j); // Centre bas
                    }
                    valueForThisGear *= getValueIfIsNumber(i, j - 1); // GAUCHE
                    valueForThisGear *= getValueIfIsNumber(i, j + 1); // DROITE
                    if (numbersForCurrentGear >= 2) {
                        sum += valueForThisGear;
                    }
                }
                j++;
            }
            i++;
        }
        System.out.println(sum);
    }

    // Retourne le nombre correspondant à la position donnée, ou 1 si ce n'est pas un nombre ou si on sort du tableau
    static int getValueIfIsNumber(int i, int j) {
        if (j >= 0 && j < engine.get(0).size()
                && i >= 0 && i < engine.size()
                && Character.isDigit(engine.get(i).get(j))) {
            numbersForCurrentGear++;
            return getFullNumber(i, j);
        }
        return 1;
    }

    // Renvoie le nombre complet à partir de la position d'un de ses chiffres
    static int getFullNumber(int i, int j) {
        String numberStr = "" + engine.get(i).get(j);
        // On se déplace vers la gauche
        int j2 = j - 1;
        while (j2 >= 0 && Character.isDigit(engine.get(i).get(j2))) {
            numberStr = engine.get(i).get(j2) + numberStr;
            j2--;
        }
        // On se déplace vers la droite
        j2 = j + 1;
        while (j2 <= engine.get(i).size() - 1 && Character.isDigit(engine.get(i).get(j2))) {
            numberStr += engine.get(i).get(j2);
            j2++;
        }
        return Integer.parseInt(numberStr);
    }
}