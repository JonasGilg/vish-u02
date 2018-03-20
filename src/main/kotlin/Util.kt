import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Region
import tornadofx.*
import java.util.stream.IntStream

fun parallelFor(range: IntRange, op: (Int) -> Unit) = IntStream.range(range.first, range.last).parallel().forEach(op)

fun Region.sizeProperty(): ReadOnlyObjectProperty<Size> {
	val property = ReadOnlyObjectWrapper(Size(width, height))

	widthProperty().onChange { property.value = Size(it, height) }
	heightProperty().onChange { property.value = Size(width, it) }

	return property.readOnlyProperty
}

val Region.size get() = Size(width, height)

fun Canvas.sizeProperty() : ReadOnlyObjectProperty<Size> {
	val property = ReadOnlyObjectWrapper(Size(width, height))

	widthProperty().onChange { property.value = Size(it, height) }
	heightProperty().onChange { property.value = Size(width, it) }

	return property.readOnlyProperty
}

val Canvas.size get() = Size(width, height)

data class Size(val width: Double, val height: Double)

operator fun Point2D.plus(other: Point2D): Point2D = this.add(other)
operator fun Point2D.minus(other: Point2D): Point2D = this.subtract(other)

operator fun Point2D.times(factor: Double): Point2D = this.multiply(factor)
operator fun Point2D.div(dividend: Double): Point2D = this.multiply(1.0 / dividend)

infix fun Point2D.dot(other: Point2D): Double = this.dotProduct(other)