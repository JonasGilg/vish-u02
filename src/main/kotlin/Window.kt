import javafx.application.Platform
import javafx.beans.Observable
import javafx.geometry.Point2D
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import tornadofx.*

class AppStarter : App(MainView::class)

class MainView : View("Bilinear Interpolation") {
	private val dataController by inject<DataController>()

	private var canvasSize: Point2D? = null

	override val root = pane {
		prefWidth = 800.0
		prefHeight = 800.0

		canvas {
			widthProperty().bind(this@pane.widthProperty())
			heightProperty().bind(this@pane.heightProperty())

			val listener = { _: Observable ->
				if (canvasSize == null) {
					Platform.runLater {
						val w = width.toInt()
						val h = height.toInt()

						val image = WritableImage(w, h)

						parallelFor(0 until w) { x ->
							for (y in 0 until h) {
								val color = 0.5 * (dataController.getInterpolatedData(x / width, y / height) + 1.0)
								image.pixelWriter.setColor(x, y, Color(color, color, color, 1.0))
							}
						}

						graphicsContext2D.drawImage(image, 0.0, 0.0)

						canvasSize = null
					}
				}
				canvasSize = Point2D(width, height)
			}

			widthProperty().addListener(listener)
			heightProperty().addListener(listener)
		}
	}
}

