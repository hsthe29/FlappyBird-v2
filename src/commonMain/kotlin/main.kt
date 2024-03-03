import korlibs.image.bitmap.*
import korlibs.time.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.tween.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.animate.*
import korlibs.math.geom.*
import korlibs.math.interpolation.*
import kotlinx.coroutines.*
import loader.*
import scenes.*

suspend fun main() = Korge(
    title = "Flappy Bird",
    windowSize = Size(APP_WINDOW_WIDTH, APP_WINDOW_HEIGHT),
    backgroundColor = Colors["#2b2b2b"],
    icon = "icons/app-icon.png",
    targetFps = 60.0,
    multithreaded = true,

) {
    val backgroundBitmap = resourcesVfs["images/background.png"].readBitmap()
    awaitAll(
        async {
            BitmapDB.loadBitmaps(resourcePaths)
        },
        async {
            FontLoader.loadFonts()
        },
        async {
            SoundLoader.loadSounds()
        }
    )
    val backgroundImages = arrayOf(
        image(backgroundBitmap) {
            anchor(0.5, 0.5)
            position(450, 250)
        },
        image(backgroundBitmap) {
            anchor(0.5, 0.5)
            position(450 + 900, 250)
        }
    )
    val startPos = intArrayOf(450, 450 + 900)
    val endPos = intArrayOf(-450, 450)

    sceneContainer { changeTo { WelcomeScene() } }

    animate{
        parallel {
            sequence(looped = true) {
                tween(backgroundImages[0]::x[endPos[0]], time = 60.seconds, easing = Easing.LINEAR)
                tween(backgroundImages[0]::x[startPos[0]], time = 0.seconds, easing = Easing.LINEAR)
                }
            sequence(looped = true) {
                tween(backgroundImages[1]::x[endPos[1]], time = 60.seconds, easing = Easing.LINEAR)
                tween(backgroundImages[1]::x[startPos[1]], time = 0.seconds, easing = Easing.LINEAR)
            }
        }
    }
}
