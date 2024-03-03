package scenes

import APP_WINDOW_WIDTH
import GROUND_POS_Y
import MIDDLE_X
import MIDDLE_Y
import PIPE_VEC
import collidesWith
import entities.*
import isPassed
import korlibs.event.*
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.io.lang.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import kotlinx.coroutines.*
import loader.*

class PlayScene : Scene() {
    private lateinit var player: Bird
    private var flapped = false
    private lateinit var obstacles: Obstacles
    private lateinit var grounds: Array<Image>
    private lateinit var score: Score

    private lateinit var obstacleLayer: Layer
    private lateinit var scoreLayer: Layer

    private lateinit var frameUpdater: CloseableCancellable

    private val endPos = MIDDLE_X - APP_WINDOW_WIDTH

    override suspend fun SContainer.sceneInit() {
        obstacleLayer = layer("obstacle") {
            obstacles = obstacles()
        }
        player = bird()
        val groundBitmap = BitmapDB.retrieveBitmap("images/ground.png")
        grounds = arrayOf(
            image(groundBitmap, anchor = Anchor(0.5, 0.5)) {
                position(MIDDLE_X, GROUND_POS_Y)
            },
            image(groundBitmap,  anchor = Anchor(0.5, 0.5)) {
                position(MIDDLE_X + APP_WINDOW_WIDTH, GROUND_POS_Y)
            }
        )
        scoreLayer = layer("score") {
            score = score()
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
        run()

//        animate{
//            parallel {
//                sequence(looped = true) {
//                    tween(grounds[0]::x[endPos[0]], time = 30.seconds, easing = Easing.LINEAR)
//                    tween(grounds[0]::x[startPos[0]], time = 0.seconds, easing = Easing.LINEAR)
//                }
//                sequence(looped = true) {
//                    tween(grounds[1]::x[endPos[1]], time = 30.seconds, easing = Easing.LINEAR)
//                    tween(grounds[1]::x[startPos[1]], time = 0.seconds, easing = Easing.LINEAR)
//                }
//            }
//        }
    }

    private suspend fun makePlayerDeath() {
        SoundLoader.hit.play()
        player.setCrash()
        SoundLoader.die.play()
        var end: Boolean
        frameUpdater = sceneContainer.addUpdater {
            end = player.crash()
            if (end) {
                frameUpdater.close()
            }
        }
    }

    private suspend fun showGameOver() {
        val gameOver = sceneContainer.image(resourcesVfs["images/gameover.png"].readBitmap().mipmaps(), anchor = Anchor(0.5, 0.5)) {
            position(MIDDLE_X, MIDDLE_Y + 50)
        }
        sceneContainer.image(resourcesVfs["buttons/restart-btn.png"].readBitmap().mipmaps(), anchor = Anchor(0.5, 0.5)) {
            scale(0.5, 0.5)
            position(MIDDLE_X, MIDDLE_Y -100)
            onClick {
                gameOver.removeFromParent()
                this.removeFromParent()
                resetState()
                run()
            }
            onOver {
                scale(0.48, 0.49)
            }
            onOut {
                scale(0.5, 0.5)
            }
        }
    }

    private fun resetState() {
        player.resetState()
        obstacles.resetState()
        score.reset()
    }

    private suspend fun run() {
        frameUpdater = sceneContainer.addUpdater {
            if (input.keys.justPressed(Key.SPACE) || input.keys.justPressed(Key.UP)) {
                launch { SoundLoader.wing.play() }
                flapped = true
            }
            player.update(flapped)
            obstacles.update()
            if (isPassed(player, obstacles)) {
                launch { score.increase() }
            }
            flapped = false

            grounds.forEach {
                it.x -= PIPE_VEC
                if (it.x < endPos) {
                    it.x += 2*APP_WINDOW_WIDTH
                }
            }
            if (collidesWith(player, obstacles.obstacles) or player.isDown()) {
                frameUpdater.close()
                launch {
                    makePlayerDeath()
                    showGameOver()
                }
            }
        }
    }
}
