package entities

import korlibs.math.geom.*

interface Collidable {
    fun rectBound(): Rectangle
    fun hitMask(): Array<BooleanArray>
}
