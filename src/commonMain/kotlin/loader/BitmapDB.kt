package loader

import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*

object BitmapDB {
    private val bitmaps = HashMap<String, Bitmap>()

    suspend fun loadBitmap(resourcePath: String) {
        bitmaps[resourcePath] = resourcesVfs[resourcePath].readBitmap().mipmaps()
    }

    suspend fun loadBitmaps(resourcePaths: Array<String>) {
        for(path in resourcePaths) loadBitmap(path)
    }

    fun retrieveBitmap(resourcePath: String): Bitmap = bitmaps[resourcePath] ?: TODO("Please make sure that load \"$resourcePath\" first")

    fun reset() {
        this.bitmaps.clear()
    }
}
