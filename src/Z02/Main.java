package Z02;

import Utils.Utils;
import Z01.KNN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        var irisSetosa = new LinkedList<VectorWithResult<Boolean>>();
//        var irisVersicolor = new LinkedList<VectorWithResult<Boolean>>();
//        var irisVirginica = new LinkedList<VectorWithResult<Boolean>>();
//        try (var reader = new BufferedReader(new FileReader("src/Z01/IRIS.csv"))) {
//            var vectors = reader.lines().skip(1).map(s -> s.split(",")).map(s -> new VectorWithResult<>(s[4], Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]))).toList();
//            vectors.forEach(v -> {
//                irisSetosa.add(new VectorWithResult<>(v.getResult().equals("Iris-setosa"), v));
//                irisVersicolor.add(new VectorWithResult<>(v.getResult().equals("Iris-versicolor"), v));
//                irisVirginica.add(new VectorWithResult<>(v.getResult().equals("Iris-virginica"), v));
//            });
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(irisSetosa);
//        System.out.println(irisVersicolor);
//        System.out.println(irisVirginica);
//        var perceptronSetosa = new Perceptron(new Vector(0,0,0,0));
//        System.out.println(perceptronSetosa.train(irisSetosa));
//        System.out.println(perceptronSetosa);
//        var perceptronVersicolor = new Perceptron(new Vector(0,0,0,0));
//        System.out.println(perceptronVersicolor.train(irisVersicolor));
//        System.out.println(perceptronVersicolor);
//        var perceptronVirginica = new Perceptron(new Vector(0,0,0,0));
//        System.out.println(perceptronVirginica.train(irisVirginica));
//        System.out.println(perceptronVirginica);
//        for (VectorWithResult<Boolean> vec : irisVirginica) {
//            System.out.println(vec+" -> " + perceptronVirginica.predict(vec));
//        }
        List<VectorWithResult<String>> vectors;
        try (var reader = new BufferedReader(new FileReader("src/Z01/IRIS.csv"))) {
            vectors = reader.lines().skip(1).map(s -> s.split(",")).map(s -> new VectorWithResult<>(s[4], Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]))).toList();
            vectors = new ArrayList<>(vectors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.shuffle(vectors);
        var trainingSet = vectors.subList(0, (int) (vectors.size() * 0.8));
        var testSet = vectors.subList((int) (vectors.size() * 0.8), vectors.size());

        var network = new PerceptronNetwork<String>();

        network.train(trainingSet);


        for (VectorWithResult<String> vec : testSet) {
            final var map = network.predict(vec);
            System.out.printf("Expected: [%s], Predicted: [%s]\n", vec.getResult(), map);
        }
        System.out.println(network.test(testSet));


    }
}
