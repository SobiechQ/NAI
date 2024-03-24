package Z02;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class Perceptron {
    private final static int MAX_ITERATIONS = 1_000;
    private final static double LEARNING_RATE = 0.5;
    private Vector weights;
    private BigDecimal activationLevel;

    public Perceptron(Vector initialWeights) {
        this.weights = initialWeights;
        this.activationLevel = BigDecimal.valueOf(Math.random() * 2 - 1);
    }
    public Perceptron(int domain){
        final var randomArray = DoubleStream.generate(() -> Math.random() * 2 - 1).limit(domain).toArray();
        this.weights = new Vector(randomArray);
        this.activationLevel = BigDecimal.valueOf(Math.random() * 2 - 1);
    }

    /**
     * @return true if the weghts or the activation level changed. False otherwise.
     */
    public boolean train(VectorWithResult<Boolean> vectorWithResult) {
        final var activation = this.trainActivationLevel(vectorWithResult);
        final var weights = this.trainWeights(vectorWithResult);
        final var changed = !activation.equals(this.activationLevel) || !weights.equals(this.weights);
        this.activationLevel = activation;
        this.weights = weights;
        return changed;
    }
    public int train(Iterable<VectorWithResult<Boolean>> vectors){
        int i = 0;
        for (; i < MAX_ITERATIONS; i++) {
            var changed = false;
            for (var vector : vectors) {
                changed = this.train(vector) || changed;
            }
            if (!changed) {
                break;
            }
        }
        return i;
    }
    public Boolean predict(Vector vector){
        return this.weights.dotProduct(vector).compareTo(this.activationLevel) >= 0;
    }
    private Double boolToDouble(Boolean b){
        return b ? 1.0 : 0.0;
    }
    private Vector trainWeights(VectorWithResult<Boolean> vectorWithResult){
        return this.weights.sum(vectorWithResult.multiply(LEARNING_RATE * (this.boolToDouble(vectorWithResult.getResult()) - this.boolToDouble(this.predict(vectorWithResult)))));
    }
    private BigDecimal trainActivationLevel(VectorWithResult<Boolean> vectorWithResult){
        return this.activationLevel.add(BigDecimal.valueOf(
                (this.boolToDouble(this.predict(vectorWithResult)) - (this.boolToDouble(vectorWithResult.getResult())))).multiply(BigDecimal.valueOf(LEARNING_RATE)));

    }



    @Override
    public String toString() {
        return String.format("Weights: %s, Activation Level: %s", this.weights, this.activationLevel);
    }
}
