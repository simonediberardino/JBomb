package game.level.world2

import game.entity.player.Player
import game.entity.enemies.boss.Boss
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.npcs.Eagle
import game.entity.enemies.npcs.FastEnemy
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.entity.player.BomberEntity
import game.level.Level
import game.level.StoryLevel
import game.level.WorldSelectorLevel
import game.level.info.model.LevelInfo
import game.level.info.imp.World2levelInfo

class World2Level5 : StoryLevel() {
    override val info: LevelInfo
        get() = object: World2levelInfo(this) {
            override val levelId: Int get() = 5
            override val boss: Boss get() = Clown()
            override val startEnemiesCount: Int get() = 7
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(FastEnemy::class.java, Eagle::class.java)
            override val isLastLevelOfWorld: Boolean get() = true
            override val nextLevel: Class<out Level?> get() = WorldSelectorLevel::class.java
            override val playerSpawnCoordinates: Coordinates get() = Coordinates.roundCoordinates(Coordinates(0, 0), BomberEntity.SPAWN_OFFSET)
        }
}