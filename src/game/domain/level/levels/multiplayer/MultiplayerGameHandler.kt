package game.domain.level.levels.multiplayer

import game.domain.level.behavior.DespawnDestroyableBlocksBehavior
import game.domain.level.behavior.GenerateDestroyableBlocksBehavior
import game.domain.level.behavior.GeneratePlayerBehavior
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.utils.Utility
import java.awt.Image

class MultiplayerGameHandler(level: MultiplayerLevel) : DefaultGameHandler(level) {
    override fun generateStone() {}
    override fun spawnBoss() {}
    override fun spawnEnemies() {}
    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>, count: Int) {}
    override fun spawnMysteryBox() {}
    override fun canGameBeEnded(): Boolean = false
    override fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>) {}
    override fun spawnAnimals() {}
}