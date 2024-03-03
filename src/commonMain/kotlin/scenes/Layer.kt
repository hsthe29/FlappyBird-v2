package scenes

import APP_WINDOW_HEIGHT
import APP_WINDOW_WIDTH
import korlibs.korge.view.*
import korlibs.logger.*

inline fun Container.layer(layerName: String, callback: @ViewDslMarker Layer.() -> Unit = {}) =
    Layer(layerName).addTo(this, callback)

class Layer(val layerName: String): Container() {
    init {
        width = APP_WINDOW_WIDTH.toDouble()
        height = APP_WINDOW_HEIGHT.toDouble()
    }
}
