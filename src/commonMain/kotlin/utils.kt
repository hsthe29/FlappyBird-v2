import entities.*
import korlibs.math.geom.*
import kotlin.math.*

fun toDigits(number: Int) = number.toString().toCharArray()

fun isPassed(bird: Bird, obstacles: Obstacles): Boolean {
    for (obstacle in obstacles.obstacles) {
        if (!obstacle.passed) {
            if (obstacle.x < bird.x) {
                obstacle.passed = true
                return true
            } else {
                return false
            }
        }
    }
    return false
}

fun intersection(a: Rectangle, b: Rectangle): Rectangle = if (a intersects b) {
        val newLeft = max(a.left, b.left)
        val newTop = max(a.top, b.top)
        val newWidth = min(a.right, b.right) - newLeft
        val newHeight = min(a.bottom, b.bottom) - newTop
        Rectangle(newLeft, newTop, newWidth, newHeight) }
    else Rectangle.ZERO

fun collideWith(a: Collidable, b: Collidable): Boolean {
    val rectA = a.rectBound()
    val rectB = b.rectBound()
    val hitmaskA = a.hitMask()
    val hitmaskB = b.hitMask()
    val rect = intersection(rectA, rectB)

    if (rect.width == 0.0 || rect.height == 0.0) {
        return false
    }
    val xA = (rect.x - rectA.x).toInt()
    val yA = (rect.y - rectA.y).toInt()
    val xB = (rect.x - rectB.x).toInt()
    val yB = (rect.y - rectB.y).toInt()

    for(x in 0..<rect.width.toInt()) {
        for(y in 0..<rect.height.toInt()) {
            if (hitmaskA[yA + y][xA + x] && hitmaskB[yB + y][xB + x]) {
                return true
            }
        }
    }
    return false
}

fun collidesWith(a: Collidable, b: List<Obstacle>): Boolean {
    for (obstacle in b) {
        if (obstacle.collidedWith(a)) {
            return true
        }
    }
    return false
}
