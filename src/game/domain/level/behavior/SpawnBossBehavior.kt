package game.domain.level.behavior

import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss

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