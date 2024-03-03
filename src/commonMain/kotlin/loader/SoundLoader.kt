package loader

import korlibs.audio.format.*
import korlibs.audio.sound.*
import korlibs.io.file.std.*

object SoundLoader {
    lateinit var die: Sound
        private set
    lateinit var hit: Sound
    lateinit var point: Sound
    lateinit var swoosh: Sound
    lateinit var wing: Sound

    suspend fun loadSounds() {
        this.die = resourcesVfs["sounds/die.wav"].readMusic()
        this.hit = resourcesVfs["sounds/hit.wav"].readMusic()
        this.point = resourcesVfs["sounds/point.wav"].readMusic()
        this.swoosh = resourcesVfs["sounds/swoosh.wav"].readMusic()
        this.wing = resourcesVfs["sounds/wing.wav"].readMusic()
    }
}
