package game.domain.level.info.model

import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.impl.animals.FoxAnimal
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.values.Dimensions
import java.awt.Dimension

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
    override val startAnimalsCount: Int
        get() = 0
    override val availableAnimals: Array<Class<out AnimalEntity>>
        get() = arrayOf(FoxAnimal::class.java)
    override var mapDimension: Dimension? = Dimension(Utility.px(930), Utility.px(800))
    override val cameraMoveEnabled: Boolean = true
    override val networkName: String = "World $worldId Level $levelId"
    override val isTimeEnabled: Boolean = true
    override val timeLimitMinutes: Int = 0
}