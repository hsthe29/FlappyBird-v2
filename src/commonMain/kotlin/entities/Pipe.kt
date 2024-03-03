package entities

import PIPE_SCALE_X
import PIPE_SCALE_Y
import PIPE_VEC
import korlibs.image.bitmap.*
import korlibs.korge.view.*


inline fun Container.pipe(bitmap: Bitmap, callback: @ViewDslMarker Pipe.() -> Unit = {}) =
    Pipe(bitmap).addTo(this).apply(callback)

class Pipe(bitmap: Bitmap): Entity(bitmap) {

    init {
        scale(PIPE_SCALE_X, PIPE_SCALE_Y)

    }

    override fun addTo(container: Container): Pipe {
        this.instance.addTo(container)
        return this
    }

    fun update() {
        this.x -= PIPE_VEC
    }
}
