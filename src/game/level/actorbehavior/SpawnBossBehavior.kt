package game.level.actorbehavior

import game.entity.enemies.boss.Boss

class SpawnBossBehavior(val boss: Boss): GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            boss.spawn(true, false)
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}