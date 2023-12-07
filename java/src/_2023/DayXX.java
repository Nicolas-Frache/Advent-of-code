package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class DayXX {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\_2023\\_XX\\input.txt"));
        String ligne = "";

        Matcher matcher = Pattern.compile("(.*?)(\\d+)(.*)").matcher(ligne);
        matcher.find(0);

        var x = Arrays.stream(reader.readLine().split(":")[1].trim().split(" ")).toList()
                .stream().filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();


        while ((ligne = reader.readLine()) != null) {

        }
    }
}