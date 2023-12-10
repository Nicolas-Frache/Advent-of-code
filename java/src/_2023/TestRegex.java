package _2023;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_08\\input.txt"));

        String ligne = "VTM = (VPB, NKT)";
        Matcher matcher = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3}).*").matcher(ligne);

        if (matcher.find()) {
            System.out.println(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
        } else {
            System.out.println("No match found");
        }
    }
}
