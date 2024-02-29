package game.engine.level.behavior

import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss

class SpawnBossBehavior(val boss: Boss): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            boss.spawn(true, false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}