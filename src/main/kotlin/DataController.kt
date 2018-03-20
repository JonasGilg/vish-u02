import javafx.geometry.Point2D
import tornadofx.*

class DataController : Controller() {
	val xSize = 50
	val ySize = 50

	val xMin = -1.0
	private val xMax = 1.0

	val yMin = -1.0
	private val yMax = 1.0

	val dx = (xMax - xMin) / (xSize)
	val dy = (yMax - yMin) / (ySize)

	private val data: Array<DoubleArray> by lazy {
		val data = Array(xSize) { DoubleArray(ySize) }
		for (i in 0 until xSize) {
			val x = xMin + i * dx
			for (j in 0 until ySize) {
				val y = yMin + j * dy
				data[i][j] = Math.sin(2.5 * Math.PI * x) * Math.sin(2.5 * Math.PI * y)
			}
		}
		data
	}

	fun getInterpolatedData(x: Double, y: Double): Double {
		var i0 = ((x - xMin) / dx).toInt()
		i0 = Math.min(Math.max(i0, 0), xSize - 1)
		val i1 = Math.min(i0 + 1, xSize - 1)
		var j0 = ((y - yMin) / dy).toInt()
		j0 = Math.min(Math.max(j0, 0), ySize - 1)
		val j1 = Math.min(j0 + 1, ySize - 1)

		val x0 = xMin + i0 * dx
		val x1 = xMin + i1 * dx
		val y0 = yMin + j0 * dy
		val y1 = yMin + j1 * dy

		val p = Point2D(x, y)
		val p1 = Point2D(x0, y0)
		val p2 = Point2D(x0, y1)
		val p3 = Point2D(x1, y1)
		val p4 = Point2D(x1, y0)

		val r = (p - p1) dot (p2 - p1) / p2.distance(p1)
		val s = (p - p1) dot (p4 - p1) / p4.distance(p1)

		//return (1 - r) * (1 - s)

		return data[i0][j0]
	}
}