package Z01;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

public class KNN {
    private final Collection<Vector> vectorsTest;
    private final Collection<Vector> vectorsBase;

    public KNN(Collection<Vector> vectorsTest, Collection<Vector> vectorsBase) {
        this.vectorsTest = vectorsTest;
        this.vectorsBase = vectorsBase;
    }
    public List<Vector> getClosestTo(Vector vector, int k){
        return vectorsBase.stream()
                .map(v -> new Object[]{v, vector.lengthTo(v)})
                .sorted(Comparator.comparingDouble(value -> (Double) value[1]))
                .map(pair -> (Vector) pair[0])
                .limit(k)
                .toList();
    }

    public void testVecotrs(int k){
        this.vectorsTest
                .forEach(v -> {
                    System.out.println("Test:");
                    System.out.println(v);
                    System.out.println("\nClosest:");
                    this.getClosestTo(v, k)
                            .forEach(System.out::println);
                    System.out.println();
                });
    }

    public Collection<Vector> getVectorsTest() {
        return vectorsTest;
    }

    public Collection<Vector> getVectorsBase() {
        return vectorsBase;
    }
}
