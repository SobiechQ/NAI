package Z02;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron(new Vector(1,1));
        var list = List.of(
                new VectorWithResult<>(true, 0, 0),
                new VectorWithResult<>(false, 1, 0),
                new VectorWithResult<>(false, 0, 1),
                new VectorWithResult<>(false, 1, 1)
        );
//        System.out.println(perceptron);
//        System.out.println(perceptron.predict(list.get(1)));
//
//        for (int i = 0; i < 40; i++) {
//            perceptron.train(list.get(1));
//            System.out.println(perceptron);
//            System.out.println(perceptron.predict(list.get(1)));
//
//        }


        for (int i = 0; i < 100; i++) {
            System.out.println(perceptron);
            list.forEach(v-> System.out.printf("Vector: [%s] -> [%s]\n", v, perceptron.predict(v)));
            list.forEach(perceptron::train);
            System.out.println("====================================");
        }
        list.forEach(v-> System.out.printf("Vector: [%s] -> [%s]\n", v, perceptron.predict(v)));



    }
}
