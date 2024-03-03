package entities

import LEFT_MARGIN
import MAX_OBSTACLE_Y
import MAX_PIPE_OFFSET
import MIN_OBSTACLE_Y
import MIN_PIPE_OFFSET
import OBSTACLE_DISTANCE
import RIGHT_MARGIN
import collideWith
import korlibs.korge.view.*
import loader.*
import kotlin.random.*

fun Container.obstacle(callback: @ViewDslMarker Obstacle.() -> Unit = {}): Obstacle {
    return Obstacle().addTo(this).apply(callback)
}

fun Container.obstacles(callback: @ViewDslMarker Obstacles.() -> Unit = {}): Obstacles {
    return Obstacles(this).apply(callback)
}

class Obstacle: Viewable {
    private val up: Pipe
    private val down: Pipe

    val x: Double
        get() = up.x
    val y: Double

    var passed = false

    init {
        val upBitmap = BitmapDB.retrieveBitmap("images/pipe-up.png")
        val downBitmap = BitmapDB.retrieveBitmap("images/pipe-down.png")

        val verticalOffset = Random.nextDouble(MIN_PIPE_OFFSET, MAX_PIPE_OFFSET)
        val verticalPos = Random.nextDouble(MIN_OBSTACLE_Y, MAX_OBSTACLE_Y)
        y = verticalPos
        up = Pipe(upBitmap).apply {
            position(RIGHT_MARGIN, verticalPos - verticalOffset)
        }
        down = Pipe(downBitmap).apply {
            position(RIGHT_MARGIN, verticalPos + verticalOffset)
        }

        up.initHitMask()
        down.initHitMask()
    }

    override fun addTo(container: Container): Obstacle {
        this.up.addTo(container)
        this.down.addTo(container)
        return this
    }

    fun isOut(): Boolean {
        return this.up.x < LEFT_MARGIN
    }

    fun isOver(): Boolean {
        return this.up.x < (RIGHT_MARGIN - OBSTACLE_DISTANCE)
    }

    fun update() {
        this.up.update()
        this.down.update()
    }

    fun removeFromParent() {
        this.up.removeFromParent()
        this.down.removeFromParent()
    }

    fun collidedWith(that: Collidable): Boolean {
        return collideWith(that, this.up) || collideWith(that, this.down)
    }
}

class Obstacles(private val container: Container) {
    val obstacles = mutableListOf<Obstacle>()

    init {
        placeNew()
    }

    fun resetState() {
        while(obstacles.isNotEmpty()) {
            val removedObstacle = obstacles.removeFirst()
            removedObstacle.removeFromParent()
        }
        placeNew()
    }

    private fun placeNew() {
        obstacles.add(Obstacle().apply { addTo(container) })
    }

    fun update() {
        for (obstacle in obstacles) {
            obstacle.update()
        }

        if (obstacles.first().isOut()) {
            val removedObstacle = obstacles.removeFirst()
            removedObstacle.removeFromParent()
        }

        if (obstacles.last().isOver()) {
            placeNew()
        }
    }
}
