package Z01;

import java.util.Arrays;
import java.util.function.BinaryOperator;
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
        if (this.x.length != vector.x.length) {
            throw new IllegalArgumentException("Vectors must have the same domain");
        }
        return Math.sqrt(IntStream.range(0, this.x.length)
                .mapToObj(i -> new double[]{this.x[i], vector.x[i]})
                .map(pair -> pair[0] - pair[1])
                .map(d -> d * d)
                .reduce(Double::sum)
                .orElseThrow(() -> new RuntimeException("Vectors cant be empty"))
        );


    }
}
