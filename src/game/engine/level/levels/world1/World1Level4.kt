package game.engine.level.levels.world1

import game.engine.world.domain.entity.actors.impl.enemies.npcs.Helicopter
import game.engine.world.domain.entity.actors.impl.enemies.npcs.YellowBall
import game.engine.world.domain.entity.actors.impl.enemies.npcs.Zombie
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import game.engine.level.levels.StoryLevel
import game.engine.level.info.model.LevelInfo
import game.engine.level.info.imp.World1LevelInfo

class World1Level4 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 4
            override val startEnemiesCount: Int get() = 12
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java, Helicopter::class.java, Zombie::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level5::class.java
        }
}