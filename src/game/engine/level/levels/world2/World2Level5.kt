package game.engine.level.levels.world2

import game.engine.world.domain.entity.actors.impl.enemies.boss.Boss
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Eagle
import game.engine.world.domain.entity.actors.impl.enemies.npcs.FastEnemy
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.levels.lobby.WorldSelectorLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World2levelInfo

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