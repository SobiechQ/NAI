package Z01;

import Utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Input {
    private final KNN knn;
    public Input() {
        try (var reader = new BufferedReader(new FileReader("src/Z01/IRIS.csv"))) {
            var vectors = reader.lines()
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(s -> new Vector(s[4], Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3])))
                    .collect(Collectors.toList());
            this.knn = new KNN(vectors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getUserInput() throws IOException{
        var reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("""
                Choose command:
                1 - Load test data from file
                2 - Load test data from user input
                """);
        var command = Integer.parseInt(reader.readLine());
        switch (command) {
            case 1 -> this.loadTestDataFromFile();
            case 2 -> {
                System.out.println("Enter comma seperated 4 value vector");

                var read = reader.readLine();
                var vector = new Vector("User-Entered-Data", Arrays.stream(read.split(",")).mapToDouble(Double::parseDouble).toArray());
                System.out.println("Enter k");
                var k = Integer.parseInt(reader.readLine());
                this.loadTestDataFromUserInput(vector, k);

            }
            default -> throw new IllegalArgumentException("Invalid command");
        }
    }
    public void loadTestDataFromFile() {
        IntStream.rangeClosed(1, 20).forEach(this.knn::testVecotrs);
    }
    public void loadTestDataFromUserInput(Vector vector, int k) {
        System.out.printf("Provided vector: [%s]\nis closest to: ", vector);
        System.out.println(this.knn.getClosestTo(vector, k));
    }

}
