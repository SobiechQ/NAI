package Z02;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class PerceptronNetwork<T> implements Iterable<Map.Entry<T, Perceptron>> {
    private final Map<T, Perceptron> perceptrons;

    public PerceptronNetwork(Map<T, Perceptron> perceptrons) {
        this.perceptrons = perceptrons;
    }

    public PerceptronNetwork() {
        this(new HashMap<>());
    }

    public void train(Iterable<VectorWithResult<T>> vectors) {
        StreamSupport.stream(vectors.spliterator(), false).forEach(v -> {
            if (!this.perceptrons.containsKey(v.getResult())) {
                this.perceptrons.put(v.getResult(), new Perceptron(v.getDomain()));
            }
        });

        for (T t : perceptrons.keySet()) {
            final var perceptron = perceptrons.get(t);
            final var list =
                    StreamSupport.stream(vectors.spliterator(), false)
                            .map(v -> new VectorWithResult<>(v.getResult().equals(t), v))
                            .toList();
            System.out.println(perceptron.train(list));
        }
    }
    public Map<T, Boolean> predict(Vector vector){
        final var map = new HashMap<T, Boolean>();
        this.perceptrons.forEach((result, perceptron) -> map.put(result, perceptron.predict(vector)));
        return map;
    }
    public double test(Iterable<VectorWithResult<T>> vectors){
        final var size = StreamSupport.stream(vectors.spliterator(), false).count();
        final var correct = StreamSupport.stream(vectors.spliterator(), false)
                .map(v -> {
                    final var resultMap = this.predict(v);
                    resultMap.entrySet().removeIf(e -> !e.getValue());
                    return resultMap.size() == 1 && resultMap.containsKey(v.getResult());
                })
                .filter(b -> b)
                .count();
        return (double) correct / size;
    }

    @Override
    public Iterator<Map.Entry<T, Perceptron>> iterator() {
        return this.perceptrons.entrySet().iterator();
    }
}
