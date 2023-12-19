package game.utils

import game.Bomberman

object Paths {
    const val dataFolder: String = "data"
    private const val assetsFolder: String = "assets"
    const val playerDataPath: String = "$dataFolder/data"
    const val entitiesFolder: String = "$assetsFolder/entities"
    const val enemiesFolder: String = "$entitiesFolder/enemies"
    const val worldsFolder: String = "$assetsFolder/worlds"
    val currentLevelFolder: String get() = Bomberman.getMatch().currentLevel.let { "$worldsFolder/${it.info.worldId}/level/${it.info.levelId}" }
    val currentWorldCommonFolder: String get() = String.format("%s/%d/common", worldsFolder, Bomberman.getMatch().currentLevel.info.worldId)
    const val powerUpsFolder: String = "$assetsFolder/powerups"
    const val menuImagesPath: String = "$assetsFolder/menu/images"
    const val powerUpsBorderPath: String = "$menuImagesPath/powerups_border.png"
    const val backgroundImage: String = "$menuImagesPath/background.jpg"
    const val mainMenuWallpaper: String = "$menuImagesPath/welcome.png"
    const val deathWallpaper: String = "$menuImagesPath/death.jpg"
    fun getWorldSelectorPortalPath(id: Int): String = "$assetsFolder/world_selector/world$id.png"
    const val powerupsLogoPath: String = "$powerUpsFolder/powerups_logo.png"
    const val inventoryPath: String = "$assetsFolder/inventory"
    const val soundsPath: String = "$assetsFolder/sounds"
    const val itemsPath: String = "$assetsFolder/items"
    const val defaultSoundTrack: String = "$assetsFolder/menu/sound/soundtrack.wav"
    const val uiFolder: String = "$assetsFolder/ui"
    const val cursorPath: String = "$uiFolder/cursor.png"
    const val iconPath: String = "$menuImagesPath/frame_icon.png"
    const val xmlPath: String = "$assetsFolder/xml"
    const val skinsXml: String = "$xmlPath/skins.xml"
}