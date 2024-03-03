package entities

import korlibs.korge.view.*

interface Viewable {
    fun addTo(container: Container): Viewable
}
