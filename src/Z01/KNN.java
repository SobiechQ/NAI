package Z01;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Utils.Utils;

public class KNN {
    private final Collection<Vector> vectorsTest;
    private final Collection<Vector> vectorsBase;

    public KNN(Collection<Vector> vectorsTest, Collection<Vector> vectorsBase) {
        this.vectorsTest = vectorsTest;
        this.vectorsBase = vectorsBase;
    }
    public KNN(Collection<Vector> vectors){
        var list = new LinkedList<>(vectors);
        Utils.shuffle(list);
        final int testSize = (int) (list.size() * 0.3);
        this.vectorsTest = new LinkedList<>(list).subList(0, testSize);
        this.vectorsBase = new LinkedList<>(list).subList(testSize, list.size());
    }
    public record KnnResult(String result, double probability) {
        @Override
        public String toString() {
            return String.format("Result: [%s], probability: [%s]", this.result, this.probability);
        }
    }
    public KnnResult getClosestTo(Vector vector, int k){
        var list = new LinkedList<>(this.vectorsBase);

        Utils.sort(list, Comparator.comparingDouble(vector::lengthTo));

        var map = list.stream()
                .limit(k)
                .collect((Supplier<Map<String, Integer>>) HashMap::new,
                        (stringIntegerMap, vector1) -> stringIntegerMap.put(vector1.result(), stringIntegerMap.getOrDefault(vector1.result(), 0) + 1),
                        (stringIntegerMap, stringIntegerMap2) -> {});

        var entry = map.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow(()-> new RuntimeException("No result"));

        return new KnnResult(entry.getKey(), (double) entry.getValue() / k);
    }

    public void testVecotrs(int k){
        var mediumPropability = this.vectorsTest.stream()
                .map(v->this.getClosestTo(v, k))
                .mapToDouble(KnnResult::probability)
                .average()
                .orElseThrow(()-> new RuntimeException("No result"));
        System.out.printf("For k: [%d] medium propability: [%f]\n", k, mediumPropability);
    }
}
