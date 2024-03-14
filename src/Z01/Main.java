package Z01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

//sepal_length,sepal_width,petal_length,petal_width,species
record Flower(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String species) {
    //tutaj przyjmij tablice jako jeden parametr
    //rzuć error jak size inny
}

public class Main {
    public static void main(String[] args) {
        //dane testowe losowe 30%
        var daneBazowe = new LinkedList<Flower>();
        try (var reader = new BufferedReader(new FileReader("src/Z01/IRIS.csv"))) {
            var toShuffle = reader.lines()
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(s -> new Flower(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), s[4]))
                    .collect(Collectors.toList());
            while (!toShuffle.isEmpty()) {
                int index = (int) (Math.random() * toShuffle.size());
                daneBazowe.addLast(toShuffle.get(index));
                toShuffle.remove(index);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var daneTestowe = daneBazowe.stream()
                .limit((long) (daneBazowe.size()*0.3))
                .collect(Collectors.toList());
        System.out.println(daneBazowe.size());
        daneBazowe.removeAll(daneTestowe);

        System.out.println(daneTestowe.size());
        System.out.println(daneBazowe.size());

    }
}
