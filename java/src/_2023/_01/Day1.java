package _2023._01;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Day1 {

    public static void main(String[] args) throws IOException {
        String inputFile = "src\\_2023\\_01\\input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String ligne;

        ArrayList<Integer> currentGroup;
        int sum = 0;


        while ((ligne = reader.readLine()) != null) {
            currentGroup = new ArrayList<>();
            // on itère sur chaque caractère de la ligne
            while(ligne.length() > 0){
                String currentChar = ligne.substring(0, 1);
                try {
                    int tmp = parseInt(currentChar);
                    currentGroup.add(tmp);
                }catch (NumberFormatException ignored) {

                }

                String newLigne = parseLeftDigit(ligne);
                if(!newLigne.equals(ligne)){
                    ligne = newLigne;
                } else {
                    ligne = ligne.substring(1);
                }
            }
            sum += parseInt(""+ currentGroup.get(0) + currentGroup.get(currentGroup.size() - 1));
        }
        System.out.println(sum);

        reader.close();
    }

    static String parseLeftDigit(String str) {
        int digit = 1;
        while(digit <= 9){
            String numberStr = getNumberStr(digit);
            if(str.indexOf(numberStr) == 0){
                // eighthreezzz -> 8threezzz, ainsi on pourra lire le 3
                return str.replaceFirst(numberStr, digit+ numberStr.substring(1));
            }
            digit++;
        }
        return str;
    }

    static String getNumberStr(int param) {
        String number = "";
        switch (param) {
            case 1 -> number = "one";
            case 2 -> number = "two";
            case 3 -> number = "three";
            case 4 -> number = "four";
            case 5 -> number = "five";
            case 6 -> number = "six";
            case 7 -> number = "seven";
            case 8 -> number = "eight";
            case 9 -> number = "nine";
        }
        return number;
    }
}
