package game.level.info.model

import game.data.DataInputOutput
import game.entity.enemies.boss.Boss
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.level.Level
import game.powerups.PowerUp

abstract class LevelInfo {
    abstract val bossMaxHealth: Int
    abstract val maxBombs: Int
    abstract val levelSoundtrack: String
    abstract val levelBackgroundSound: String?
    abstract val explosionLength: Int
    abstract val stoneBlockImagePath: String
    abstract val pitchImagePath: String
    abstract val destroyableBlockImagePath: String
    abstract val boss: Boss?
    abstract val startEnemiesCount: Int
    abstract val maxDestroyableBlocks: Int
    abstract val isArenaLevel: Boolean
    abstract val diedMessage: String?
    abstract val nextLevel: Class<out Level?>?
    abstract val availableEnemies: Array<Class<out Enemy>>
    abstract val worldId: Int
    abstract val levelId: Int
    abstract val restrictedPerks: Array<Class<out PowerUp>>
    abstract val allowedPerks: Array<Class<out PowerUp>>
    abstract val randomPowerUpClass: Class<out PowerUp>
    abstract val playerSpawnCoordinates: Coordinates
    abstract val isLastLevelOfWorld: Boolean
}