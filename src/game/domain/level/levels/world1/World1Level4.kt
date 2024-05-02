package game.domain.level.levels.world1

import game.domain.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.domain.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.levels.StoryLevel
import game.domain.level.info.model.LevelInfo
import game.domain.level.info.imp.World1LevelInfo
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy

class World1Level4 : StoryLevel() {
    override val info: LevelInfo
        get() = object : World1LevelInfo(this) {
            override val levelId: Int get() = 4
            override val startEnemiesCount: Int get() = 12
            override val availableEnemies: Array<Class<out Enemy>> get() = arrayOf(YellowBall::class.java, SkeletonEnemy::class.java, Zombie::class.java)
            override val nextLevel: Class<out Level?> get() = World1Level5::class.java
        }
}