package Z02;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector implements Iterable<BigDecimal> {
    private final List<BigDecimal> values;

    public Vector(double... values) {
        this.values = new LinkedList<>(Arrays.stream(values).mapToObj(BigDecimal::valueOf).collect(Collectors.toList()));
    }
    public Vector(BigDecimal... values) {
        this.values = List.of(values);
    }

    @Override
    public String toString() {
        return String.format("Vector: [%s]", values.stream().map(BigDecimal::doubleValue).map(d->((Object)d).toString()).collect(Collectors.joining(", ")));
    }

    public BigDecimal lengthTo(Vector vector) {
        return this.dotProduct(vector).sqrt(MathContext.DECIMAL128);
    }

    public BigDecimal dotProduct(Vector vector) {
        if (this.getDomain() != vector.getDomain()) {
            throw new IllegalArgumentException("Vectors must have the same domain");
        }
        return this.tupleStream(vector)
                .map(pair -> pair[0].multiply(pair[1]))
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Vectors cant be empty"));
    }

    public Vector multiply(double scalar) {
        return new Vector(this.values.stream().map(d -> d.multiply(BigDecimal.valueOf(scalar))).toArray(BigDecimal[]::new));
    }
    public Vector multiply(BigDecimal scalar) {
        return new Vector(this.values.stream().map(d -> d.multiply(scalar)).toArray(BigDecimal[]::new));
    }
    public BigDecimal get(int index) {
        if (index < 0 || index >= values.size())
            throw new IllegalArgumentException("Index out of bounds");
        return this.values.get(index);
    }

    public void set(int index, BigDecimal value) {
        if (index < 0 || index >= values.size() || value == null)
            throw new IllegalArgumentException("Index out of bounds");
        this.values.set(index, value);
    }

    public Vector sum(Vector vector) {
        if (this.values.size() != vector.values.size())
            throw new IllegalArgumentException("Vectors must have the same domain");
        return new Vector(this.tupleStream(vector)
                .map(pair -> pair[0].add(pair[1]))
                .toArray(BigDecimal[]::new));
    }
    private Stream<BigDecimal[]> tupleStream(Vector vector){
        if (this.values.size() != vector.values.size())
            throw new IllegalArgumentException("Vectors must have the same domain");
        return IntStream.range(0, this.getDomain())
                .mapToObj(i -> new BigDecimal[]{this.get(i), vector.get(i)});
    }

    @Override
    public Iterator<BigDecimal> iterator() {
        return this.values.iterator();
    }
    public int getDomain(){
        return this.values.size();
    }


}
