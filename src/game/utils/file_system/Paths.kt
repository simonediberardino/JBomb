package game.utils.file_system

import game.JBomb
import java.io.File
import java.util.*


object Paths {
    @JvmStatic
    val playerDataPath: String = let {
        val os = System.getProperty("os.name").lowercase(Locale.getDefault())
        val userHome = System.getProperty("user.home")

        val appname = "JBomb"

        if (os.contains("win")) {
            // Windows: Use AppData\Local
            "${System.getenv("LOCALAPPDATA")}${File.separator}$appname${File.separator}PlayerData"
        } else if (os.contains("mac")) {
            // macOS: Use Library/Application Support
            "$userHome${File.separator}Library${File.separator}Application Support${File.separator}$appname${File.separator}PlayerData"
        } else {
            // Linux/Unix: Use .config
            "$userHome${File.separator}.config${File.separator}$appname${File.separator}PlayerData"
        }
    }

    @JvmStatic
    val dataFile: String get() = "data"
    private val assetsFolder: String get() = "assets"

    @JvmStatic
    val entitiesFolder: String get() = "$assetsFolder/entities"

    @JvmStatic
    val enemiesFolder: String get() = String.format("%s/enemies", entitiesFolder)

    @JvmStatic
    val blocksFolder: String get() = String.format("%s/blocks", entitiesFolder)

    @JvmStatic
    val animalsFolder: String get() = String.format("%s/animals", entitiesFolder)

    @JvmStatic
    val worldsFolder: String get() = "$assetsFolder/worlds"

    @JvmStatic
    val currentLevelFolder: String
        get() {
            val level = JBomb.match.currentLevel!!
            return "$worldsFolder/${level.info.worldId}/level/${level.info.levelId}"
        }

    @JvmStatic
    val currentWorldCommonFolder: String get() = "$worldsFolder/${JBomb.match.currentLevel!!.info.worldId}/common"

    @JvmStatic
    val powerUpsFolder: String get() = "$assetsFolder/powerups"

    @JvmStatic
    val powerUpsBorderPath: String get() = "$menuImagesPath/powerups_border.png"
    private val menuImagesPath: String get() = String.format("%s/menu/images", assetsFolder)

    @JvmStatic
    val backgroundImage: String get() = "$menuImagesPath/background.jpg"

    @JvmStatic
    val mainMenuWallpaper: String get() = "$menuImagesPath/welcome.jpg"

    @JvmStatic
    val deathWallpaper: String get() = "$menuImagesPath/death.jpg"

    @JvmStatic
    fun getWorldSelectorPortalPath(id: Int): String {
        return "$assetsFolder/world_selector/world$id/world$id%format%.png"
    }

    @JvmStatic
    val powerupsLogoPath: String get() = "$powerUpsFolder/powerups_logo.png"

    @JvmStatic
    val inventoryPath: String get() = "$assetsFolder/inventory"

    @JvmStatic
    val soundsPath: String get() = "$assetsFolder/sounds"

    @JvmStatic
    val itemsPath: String get() = "$assetsFolder/items"

    @JvmStatic
    val defaultSoundTrack: String get() = "$assetsFolder/menu/sound/soundtrack.wav"
    private val uiFolder: String get() = "$assetsFolder/ui"

    @JvmStatic
    val cursorPath: String get() = "$uiFolder/cursor.png"

    @JvmStatic
    val iconPath: String get() = "$menuImagesPath/frame_icon.png"
    private val xmlPath: String get() = "xml"

    @JvmStatic
    val skinsXml: String get() = "$xmlPath/skins.xml"

    @JvmStatic
    val configXml: String get() = "$xmlPath/config.xml"

    @JvmStatic
    val versionXml: String get() = "$xmlPath/version.xml"

    @JvmStatic
    val mapsXml: String get() = "$xmlPath/levels"
}
