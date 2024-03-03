package entities

import BIRD_ACC
import BIRD_MAX_ANGLE
import BIRD_MAX_VEL
import BIRD_MIN_ANGLE
import BIRD_POS_X
import BIRD_POS_Y
import BIRD_ROTATION_ACC
import BIRD_ROTATION_MAX_VEL
import BIRD_SCALE_X
import BIRD_SCALE_Y
import BOTTOM_MARGIN
import CRASH_ACC
import CRASH_MAX_VEL
import CRASH_ROT_ACC
import CRASH_ROT_MAX_VEL
import CRASH_VEL
import FLAP_VEL
import TOP_MARGIN
import korlibs.korge.view.*
import korlibs.math.geom.*
import loader.*

inline fun Container.bird(callback: @ViewDslMarker Bird.() -> Unit = {}): Bird {
    val bird = Bird().addTo(this).apply(callback)
    bird.initHitMask()
    return bird
}

class Bird : Entity(BitmapDB.retrieveBitmap("images/purple-bird.png")) {
    private var vel = 0.0
    private var acc = 0.0
    private var maxVel = 0.0

    private var rotVel = 0.0
    private var rotAcc = 0.0
    private var maxRotVel = 0.0

    init {
        scale(BIRD_SCALE_X, BIRD_SCALE_Y)
        resetState()
    }

    fun resetState() {
        this.x = BIRD_POS_X.toDouble()
        this.y = BIRD_POS_Y.toDouble()

        this.vel = 0.0
        this.acc = BIRD_ACC
        this.maxVel = BIRD_MAX_VEL

        this.rotVel = 0.0
        this.rotAcc = BIRD_ROTATION_ACC
        this.maxRotVel = BIRD_ROTATION_MAX_VEL

        this.rotation = 0.degrees
    }

    fun setCrash() {
        this.vel = CRASH_VEL
        this.acc = CRASH_ACC
        this.maxVel = CRASH_MAX_VEL

        this.rotVel = 0.0
        this.rotAcc = CRASH_ROT_ACC
        this.maxRotVel = CRASH_ROT_MAX_VEL
        this.rotation = BIRD_MIN_ANGLE.degrees
    }

    override fun addTo(container: Container): Bird {
        this.instance.addTo(container)
        return this
    }

    fun isDown() = this.y >= BOTTOM_MARGIN

    private fun rot() {
        if (rotVel < this.maxRotVel) {
            rotVel += this.rotAcc
        }
        this.rotation = (this.rotation + rotVel.degrees).coerceIn(BIRD_MIN_ANGLE.degrees, BIRD_MAX_ANGLE.degrees)
    }

    private fun fly(flapped: Boolean) {
        // update velocity
        if ((this.vel < this.maxVel) && !flapped) {
            this.vel += this.acc
        }
        this.y = (this.y + this.vel).coerceIn(TOP_MARGIN, BOTTOM_MARGIN)
        this.rot()
    }

    fun crash(): Boolean {
        // update velocity
        if (this.vel < this.maxVel) {
            this.vel += this.acc
        }
        this.y = (this.y + this.vel).coerceIn(TOP_MARGIN, BOTTOM_MARGIN)
        if (this.vel >= 0.0) {
            this.rot()
        }
        return this.vel >= 0.0 && this.y >= BOTTOM_MARGIN
    }

    private fun flap() {
        this.vel = FLAP_VEL
        this.rotation = BIRD_MIN_ANGLE.degrees
        this.rotVel = 0.0
    }

    fun update(flapped: Boolean) {
        if (flapped) {
            flap()
        }
        fly(flapped)
    }
}
