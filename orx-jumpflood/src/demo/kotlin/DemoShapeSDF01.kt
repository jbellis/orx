import org.openrndr.application
import org.openrndr.draw.ColorFormat
import org.openrndr.draw.ColorType
import org.openrndr.draw.colorBuffer
import org.openrndr.extensions.SingleScreenshot
import org.openrndr.extra.jumpfill.ShapeSDF
import org.openrndr.svg.loadSVG

fun main() {
    application {
        configure {
            width = 1280
            height = 720
        }
        program {
            val sdf = ShapeSDF()
            val df = colorBuffer(width, height, format = ColorFormat.RGBa, type = ColorType.FLOAT32)

            val shapes = loadSVG("orx-jumpflood/src/demo/resources/name.svg").findShapes().map { it.shape }
            sdf.setShapes(shapes)
            sdf.apply(emptyArray(), df)

            if (System.getProperty("takeScreenshot") == "true") {
                extend(SingleScreenshot()) {
                    this.outputFile = System.getProperty("screenshotPath")
                }
            }
            extend {
                if(mouse.pressedButtons.isEmpty())
                    drawer.image(df)
                else
                    drawer.shapes(shapes)
            }
        }
    }
}