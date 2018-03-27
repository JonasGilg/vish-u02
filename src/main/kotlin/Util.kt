import java.util.stream.IntStream

fun parallelFor(range: IntRange, op: (Int) -> Unit) = IntStream.range(range.first, range.last).parallel().forEach(op)

