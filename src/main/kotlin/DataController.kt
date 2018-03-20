import javafx.geometry.Point2D
import tornadofx.*

class DataController : Controller() {
	val xSize = 50
	val ySize = 50

	val xMin = -1.0
	private val xMax = 1.0

	val yMin = -1.0
	private val yMax = 1.0

	val dx = (xMax - xMin) / (xSize - 1)
	val dy = (yMax - yMin) / (ySize - 1)

	private val data = Array(xSize) { i ->
		val x = xMin + i * dx
		DoubleArray(ySize) { j ->
			val y = yMin + j * dy
			Math.sin(2.5 * Math.PI * x) * Math.sin(2.5 * Math.PI * y)
		}
	}


	fun getInterpolatedData(x: Double, y: Double): Double {
		var ix1 = ((x - xMin) / dx).toInt()
		ix1 = Math.min(Math.max(ix1, 0), xSize - 1)
		val ix2 = Math.min(ix1 + 1, xSize - 1)
		var iy1 = ((y - yMin) / dy).toInt()
		iy1 = Math.min(Math.max(iy1, 0), ySize - 1)
		val iy2 = Math.min(iy1 + 1, ySize - 1)

		val x1 = xMin + ix1 * dx
		val x2 = xMin + ix2 * dx
		val y1 = yMin + iy1 * dy
		val y2 = yMin + iy2 * dy

		val r = (x - x1) / (x2 - x1)
		val s = (y - y1) / (y2 - y1)

		return (1 - r) * (1 - s) * data[ix1][iy1] +
				r * (1 - s) * data[ix2][iy1] +
				r * s * data[ix2][iy2] +
				(1 - r) * s * data[ix1][iy2]
	}
}