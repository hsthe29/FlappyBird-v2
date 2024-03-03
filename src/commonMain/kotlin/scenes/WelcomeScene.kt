package scenes

import APP_WINDOW_WIDTH
import MIDDLE_X
import MIDDLE_Y
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.animate.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.tween.*
import korlibs.korge.view.*
import korlibs.math.interpolation.*
import loader.*
import kotlin.time.Duration.Companion.seconds

class WelcomeScene : Scene() {
    private lateinit var startButton: Image
    private lateinit var logo: Image
    private lateinit var instruction: Text

    override suspend fun SContainer.sceneInit() {
        val startImageBitmap = resourcesVfs["buttons/start-btn.png"].readBitmap().mipmaps()
        val logoBitmap = resourcesVfs["logos/flappybird.png"].readBitmap().mipmaps()
        startButton = image(startImageBitmap) {
            anchor(0.5, 0.5)
            position(450, 300)
            scale(0.2, 0.2)
            onClick {
                sceneContainer.changeTo { PlayScene() }
            }
            onOver {
                scale(0.22, 0.22)
                alpha = 1.0
            }
            onOut {
                scale(0.2, 0.2)
                alpha = 0.8
            }
            alpha = 0.8
        }
        logo = image(logoBitmap) {
            anchor(0.5, 0.5)
            position(450, 110)
            scale(0.95, 0.95)
        }
        instruction = text("Click to Start, press SPACE key or UP key to play",
            font = FontLoader.plaguard,
            color = Colors.BLACK,
            textSize = 25.0) {

            position(MIDDLE_X - scaledWidth/2, MIDDLE_Y + 150)
        }
        text("Copyright © 2024 - hsthe29 - All Rights Reserved",
            font = FontLoader.aller,
            color = Colors.BLACK, textSize = 10) {
            position(0, MIDDLE_Y + 250 - scaledHeight)
        }
        text("A gift for _judiez87_", font = FontLoader.blomberg, color = Colors.BLACK, textSize = 10) {
            position(APP_WINDOW_WIDTH - scaledWidth, MIDDLE_Y + 250 - scaledHeight)
        }
    }

    override suspend fun SContainer.sceneMain() {
        animate {
            sequence(looped = true) {
                tween(logo::scale[0.95, 1.05], time = 3.seconds, easing = Easing.LINEAR)
                tween(logo::scale[1.05, 0.95], time = 3.seconds, easing = Easing.LINEAR)
            }
        }
    }
}
