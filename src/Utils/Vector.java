package Utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public record Vector(String result, double... x) {
    @Override
    public String toString() {
        return "Vector{" +
                "result='" + result + '\'' +
                ", x=" + Arrays.toString(x) +
                '}';
    }

    public double lengthTo(Vector vector) {
        return Math.sqrt(this.dotProduct(vector));
    }
    public double dotProduct(Vector vector) {
        if (this.x.length != vector.x.length) {
            throw new IllegalArgumentException("Vectors must have the same domain");
        }
        return IntStream.range(0, this.x.length)
                .mapToObj(i -> new double[]{this.x[i], vector.x[i]})
                .map(pair -> pair[0] - pair[1])
                .map(d -> d * d)
                .reduce(Double::sum)
                .orElseThrow(() -> new RuntimeException("Vectors cant be empty"));
    }
    public Vector multiply(double scalar) {
        return new Vector(this.result, Arrays.stream(this.x).map(d -> d * scalar).toArray());
    }
}
