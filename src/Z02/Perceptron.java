package Z02;

import java.math.BigDecimal;
import java.util.function.Function;

public class Perceptron {
    private final static double LEARNING_RATE = 0.1;
    private Vector weights;
    private BigDecimal activationLevel = BigDecimal.valueOf(0.5);

    public Perceptron(Vector initialWeights) {
        this.weights = initialWeights;
    }

    public void train(VectorWithResult<Boolean> vectorWithResult) {
        var activation = this.trainActivationLevel(vectorWithResult);
        var weights = this.trainWeights(vectorWithResult);
        this.activationLevel = activation;
        this.weights = weights;
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
