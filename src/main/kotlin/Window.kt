import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import tornadofx.*
import java.util.concurrent.atomic.AtomicInteger

class AppStarter : App(MainView::class)

class MainView : View("Math Function") {
	private val dataController by inject<DataController>()

	override val root = pane {
		canvas(800.0, 800.0) {
			widthProperty().bind(this@pane.widthProperty())
			heightProperty().bind(this@pane.heightProperty())

			sizeProperty().onChange {
				if (width > 0 && height > 0) {
					//create temporary image
					val image = WritableImage(width.toInt(), height.toInt())

					val dxImage = dataController.dx * dataController.xSize / width
					val dyImage = dataController.dy * dataController.ySize / height

					//render to the image in parallel
					parallelFor(0 until width.toInt()) { i ->
						val x = dataController.xMin + i * dxImage
						for (j in 0 until height.toInt()) {
							val y = dataController.yMin + j * dyImage
							val color = 0.5 * (dataController.getInterpolatedData(x, y) + 1.0)
							image.pixelWriter.setColor(i, j, Color(color, color, color, 1.0))
						}
					}

					//draw the image
					graphicsContext2D.drawImage(image, 0.0, 0.0)
				}
			}
		}
	}
}

