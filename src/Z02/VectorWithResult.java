package Z02;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class VectorWithResult<T> extends Vector implements Iterable<BigDecimal> {
    private final T result;

    public VectorWithResult(T result, double... values) {
        super(values);
        this.result = result;
    }
    public VectorWithResult(T result, BigDecimal... values) {
        super(values);
        this.result = result;
    }
    public VectorWithResult(T result, Vector vector) {
        super(vector);
        this.result = result;
    }


    @Override
    public String toString() {
        return String.format("Vector: [(%s) -> %s]",  StreamSupport.stream(super.spliterator(), false).map(Object::toString).collect(Collectors.joining(", ")), this.result);
    }


    public T getResult() {
        return result;
    }


}
