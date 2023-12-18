package game.level.functionalities

import game.entity.enemies.boss.Boss
import game.multiplayer.HostBehavior

class SpawnBossUseCase(val boss: Boss): LevelUseCase {
    override fun invoke() {
        val hostBehavior = object: HostBehavior{
            override fun executeHostLogic() {
                boss.spawn(true, false)
            }
        }

        hostBehavior.executeHostLogic()
    }
}