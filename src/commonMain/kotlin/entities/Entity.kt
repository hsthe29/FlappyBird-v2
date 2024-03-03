package entities

import korlibs.image.bitmap.*
import korlibs.korge.view.*
import korlibs.math.geom.*

abstract class Entity(private var bitmap: Bitmap): Collidable, Viewable {
    val instance = Image(bitmap, anchor = Anchor(0.5, 0.5))
    private lateinit var hitMask: Array<BooleanArray>

    var x: Double
        get() = instance.x
        set(value) { instance.x = value }
    var y: Double
        get() = instance.y
        set(value) { instance.y = value }
    var scale: Double
        get() = instance.scale
        set(value) { instance.scale = value }
    var scaleX: Double
        get() = instance.scaleX
        set(value) { instance.scaleX = value }
    var scaleY: Double
        get() = instance.scaleY
        set(value) { instance.scaleX = value }
    var anchor: Anchor
        get() = instance.anchor
        set(value) { instance.anchor = value }
    var rotation: Angle
        get() = instance.rotation
        set(value) { instance.rotation = value }

    fun xy(x: Number, y: Number) = instance.xy(x, y)

    fun position(x: Number, y: Number) = instance.position(x, y)

    fun anchor(anchorX: Number, anchorY: Number = anchorX) = instance.anchor(anchorX, anchorY)

    fun scale(sx: Number, sy: Number = sx) = instance.scale(sx, sy)

//    fun rotation(angle: Angle) = instance.rotation(angle)


    fun initHitMask() {
        val width = instance.scaledWidth.toInt()
        val height = instance.scaledHeight.toInt()
        val resizedBitmap = bitmap.resized(width,
            height,
            scale = ScaleMode.FIT,
            Anchor(0.5, 0.5)).mipmaps()

        hitMask = Array(height) { y -> BooleanArray(width) { x -> resizedBitmap.getRgba(x, y).a != 0 } }
    }

    override fun hitMask(): Array<BooleanArray> {
        return hitMask
    }

    override fun rectBound(): Rectangle {
        return Rectangle(x = (this.x - instance.scaledWidth/2).toInt(),
            y = (this.y - instance.scaledHeight/2).toInt(),
            width = instance.scaledWidth.toInt(),
            height = instance.scaledHeight.toInt())
    }

    fun removeFromParent() {
        instance.removeFromParent()
    }

}
