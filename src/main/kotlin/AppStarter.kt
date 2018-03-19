import javafx.scene.paint.Color
import tornadofx.*

class AppStarter : App(MainView::class)

class MainView : View("Math Function") {
	private val xSize: Int = 50
	private val ySize: Int = 50
	private val xMin: Double = -1.0
	private val yMin: Double = 1.0
	private val dx: Double = -1.0
	private val dy: Double = 1.0
	private val data: Array<DoubleArray> = Array(xSize) { DoubleArray(ySize) }

	init { createMathFuncData() }

	private fun createMathFuncData() {
		for (i in 0 until xSize) {
			val x = xMin + i * dx
			for (j in 0 until ySize) {
				val y = yMin + j * dy
				data[i][j] = Math.sin(2.5 * Math.PI * x) * Math.sin(2.5 * Math.PI * y)
			}
		}
	}

	private fun getInterpolatedData(x: Double, y: Double): Double {
		// (x,y) coordinates of value to be interpolated
		// indices of closest sample points in x direction, x0 <= x <= x1
		var i0 = ((x - xMin) / dx).toInt()
		i0 = Math.min(Math.max(i0, 0), xSize - 1)    // ensure 0 <= i0 < xSize
		val i1 = Math.min(i0 + 1, xSize - 1)
		// indices of closest sample points in y direction, y0 <= y <= y1
		var j0 = ((y - yMin) / dy).toInt()
		j0 = Math.min(Math.max(j0, 0), ySize - 1)    // ensure 0 <= j0 < ySize
		val j1 = Math.min(j0 + 1, ySize - 1)
		// to do: bilinear interpolation of f(x,y)
		// ...
		// dummy: return non-interpolated value f(x0,y0)
		return data[i0][j0]
	}

	override val root = borderpane {
		center = canvas(800.0, 800.0) {
			val dxImage = dx * xSize / width
			val dyImage = dy * ySize / height

			for (i in 0 until 800) {
				val x = xMin + i * dxImage
				for (j in 0 until 800) {
					val y = yMin + j * dyImage
					val color = 0.5 * (getInterpolatedData(x, y) + 1)
					graphicsContext2D.fill = Color(color, color, color, 1.0)
					graphicsContext2D.fillRect(x, y, 1.0, 1.0)
				}
			}
		}
	}
}