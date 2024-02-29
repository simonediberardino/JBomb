package game.engine.level.info.model

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import game.engine.world.domain.entity.pickups.powerups.PowerUp

/**
 * Abstract base class that is used to represent all the info of a Level instance;
 */
abstract class LevelInfo {
    // Default max health of a Boss
    abstract val bossMaxHealth: Int
    // Default limit of bombs that can picked up
    abstract val maxBombs: Int
    // Loop soundtrack of the level
    abstract val levelSoundtrack: String
    // Loop background sound of the level: e.g. noise in the background etc.
    abstract val levelBackgroundSound: String?

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