import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
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