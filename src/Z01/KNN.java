package Z01;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class KNN {
    private final Collection<Vector> vectorsTest;
    private final Collection<Vector> vectorsBase;

    public KNN(Collection<Vector> vectorsTest, Collection<Vector> vectorsBase) {
        this.vectorsTest = vectorsTest;
        this.vectorsBase = vectorsBase;
    }
    public record KnnResult(String result, double probability) {
        @Override
        public String toString() {
            return String.format("Result: [%s], probability: [%s]", this.result, this.probability);
        }
    }
    public KnnResult getClosestTo(Vector vector, int k){
        var map = vectorsBase.stream()
                .map(v -> new Object[]{v, vector.lengthTo(v)})
                .sorted(Comparator.comparingDouble(value -> (Double) value[1]))
                .map(pair -> (Vector) pair[0])
                .limit(k)
                .collect((Supplier<Map<String, Integer>>) HashMap::new,
                        (stringIntegerMap, vector1) -> stringIntegerMap.put(vector1.result(), stringIntegerMap.getOrDefault(vector1.result(), 0) + 1),
                        (stringIntegerMap, stringIntegerMap2) -> {});
        var entry = map.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElse(new Map.Entry<>() {
                    @Override
                    public String getKey() {
                        return "Not Found";
                    }

                    @Override
                    public Integer getValue() {
                        return -1;
                    }

                    @Override
                    public Integer setValue(Integer value) {
                        throw new UnsupportedOperationException();
                    }
                });

        return new KnnResult(entry.getKey(), (double) entry.getValue() / k);


    }

    public void testVecotrs(int k){
        this.vectorsTest
                .forEach(v -> {
                    System.out.printf("Test: [%s]\n", v);
                    System.out.printf("Result: [%s]\n\n", getClosestTo(v, k));
                });
    }

    public Collection<Vector> getVectorsTest() {
        return vectorsTest;
    }

    public Collection<Vector> getVectorsBase() {
        return vectorsBase;
    }
}
