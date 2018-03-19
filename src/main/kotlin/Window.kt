import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import tornadofx.*

class AppStarter : App(MainView::class)

class MainView : View("Math Function") {
	private val dataController by inject<DataController>()

	override val root = pane {
		canvas(800.0, 800.0) {
			sizeProperty().onChange {
				if (it == null || it.width <= 0 || it.height <= 0) return@onChange

				width = it.width
				height = it.height

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
