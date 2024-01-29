package game.engine.level.behavior

import game.engine.world.entity.impl.enemies.boss.Boss

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