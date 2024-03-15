package Z01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        KNN knn;
        try (var reader = new BufferedReader(new FileReader("src/Z01/IRIS.csv"))) {
            var vectors = new LinkedList<Vector>();
            var toShuffle = reader.lines()
                    .skip(1)
                    .map(s -> s.split(","))
                    .map(s -> new Vector(s[4], Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3])))
                    .collect(Collectors.toList());
            while (!toShuffle.isEmpty()) {
                final int index = (int) (Math.random() * toShuffle.size());
                vectors.addLast(toShuffle.get(index));
                toShuffle.remove(index);
            }
            final int testSize = (int) (vectors.size() * 0.3);
            knn = new KNN(vectors.stream().skip(testSize).toList(), vectors.stream().limit(testSize).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Vector testVector = knn.getVectorsTest().stream().findFirst().get();
//        System.out.println(testVector);
//        System.out.println(knn.getClosestTo(testVector, 0));

        knn.testVecotrs(10);
    }
}
