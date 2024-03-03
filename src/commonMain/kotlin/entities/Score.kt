package entities

import MIDDLE_X
import SCORE_POS_Y
import korlibs.korge.view.*
import korlibs.math.geom.*
import loader.*
import toDigits

inline fun Container.score(callback: @ViewDslMarker Score.() -> Unit = {}) =
    Score(this).apply(callback)

class Score(private val container: Container) {
    private var score = 0
    private val imageMapper = Array(10) { BitmapDB.retrieveBitmap("digits/${it}.png")}

    init {
        display()
    }

    private fun display() {
        val digits = toDigits(score)
        val digitImages = digits.map {
            Image(imageMapper[it.digitToInt()], anchor = Anchor(0.5, 0.5)).apply {
                scale(1.0, 1.0)
            }
        }
        val scoreWidth = digitImages.fold(0.0) { sum, element ->  sum + element.scaledWidth }
        var offsetX = MIDDLE_X - scoreWidth/2
        container.removeChildren()
        digitImages.forEach {
            it.apply {
                position(offsetX + it.scaledWidth/2, SCORE_POS_Y)
            }.addTo(container)
            offsetX += it.scaledWidth
        }
    }

    suspend fun increase() {
        SoundLoader.point.play()
        this.score += 1
        display()
    }

    fun reset() {
        this.score = 0
        display()
    }
}
