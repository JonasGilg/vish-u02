import javafx.scene.paint.Color
import tornadofx.*

class AppStarter : App(MainView::class)

class MainView : View("Math Function") {
	private val size = 800

	private val dc by inject<DataController>()

	override val root = pane {
		canvas(size.toDouble(), size.toDouble()) {
			val g2d = graphicsContext2D

			val dxImage = dc.dx * dc.xSize / width
			val dyImage = dc.dy * dc.ySize / height

			for (i in 0 until size) {
				val x = dc.xMin + i * dxImage
				for (j in 0 until size) {
					val y = dc.yMin + j * dyImage
					val color = 0.5 * (dc.getInterpolatedData(x, y) + 1.0)
					g2d.fill = Color(color, color, color, 1.0)
					g2d.fillRect(i.toDouble(), j.toDouble(), 1.0, 1.0)
				}
			}
		}
	}
}