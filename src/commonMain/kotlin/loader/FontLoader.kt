package loader

import korlibs.image.font.*
import korlibs.io.file.std.*

object FontLoader {
    lateinit var aller: TtfFont
        private set
    lateinit var blomberg: TtfFont
        private set
    lateinit var plaguard: TtfFont
        private set

    suspend fun loadFonts() {
        aller = resourcesVfs["fonts/Aller_Rg.ttf"].readTtfFont()
        blomberg = resourcesVfs["fonts/Blomberg.ttf"].readTtfFont()
        plaguard = resourcesVfs["fonts/Plaguard.ttf"].readTtfFont()
    }
}
