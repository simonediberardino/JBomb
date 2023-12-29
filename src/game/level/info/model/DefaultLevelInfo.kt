package game.level.info.model

import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.level.levels.Level
import game.powerups.PowerUp
import game.ui.panels.game.PitchPanel

abstract class DefaultLevelInfo(val level: Level) : LevelInfo() {
    override val bossMaxHealth: Int = 1000
    override val maxBombs: Int get() = BomberEntity.MAX_BOMB_CAN_HOLD
    override val levelSoundtrack: String get() = level.fileSystemHandler.getSoundForCurrentLevel("soundtrack.wav")
    override val levelBackgroundSound: String get() = level.fileSystemHandler.getSoundForCurrentLevel("background_sound.wav")
    override val stoneBlockImagePath: String get() = level.fileSystemHandler.getImageForCurrentLevel("stone.png")
    override val pitchImagePath: String get() = level.fileSystemHandler.getImageForCurrentLevel("pitch.png")
    override val destroyableBlockImagePath: String get() = level.fileSystemHandler.getImageForCurrentLevel("destroyable_block.png")
    override val allowedPerks: Array<Class<out PowerUp>> get() = PowerUp.POWER_UPS.filterNot { it in restrictedPerks }.toTypedArray()

    override val restrictedPerks: Array<Class<out PowerUp>> = arrayOf()
    override val randomPowerUpClass: Class<out PowerUp> get() = allowedPerks.random()
    override val playerSpawnCoordinates: Coordinates get() = Coordinates.generateRandomCoordinates(BomberEntity.SPAWN_OFFSET, PitchPanel.GRID_SIZE)
    override val isLastLevelOfWorld: Boolean get() = false
}