package game.domain.level.gamehandler.imp

import game.JBomb
import game.domain.level.behavior.*
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.abstracts.models.State

open class DefaultStoryLevelHandler(level: Level) : DefaultGameHandler(level) {
    override fun generateStone() = GenerateStoneBehavior(level.field).invoke()

    override fun spawnMysteryBox() = SpawnMysteryBoxBehavior(level).invoke()

    override fun spawnBoss() {
        SpawnBossBehavior(level.info.boss ?: return).invoke()
    }

    override fun spawnEnemies() = spawnEnemies(level.info.availableEnemies, level.info.startEnemiesCount)

    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>, count: Int) =
            SpawnEnemiesBehavior(count, availableEnemies).invoke()

    override fun spawnAnimals() = spawnAnimals(level.info.availableAnimals)

    override fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>) =
            SpawnAnimalsBehavior(level.info.startAnimalsCount, availableAnimals).invoke()

    override fun canGameBeEnded(): Boolean = !JBomb.match.getEntities().any { it is BomberEntity && it.state.state != State.DIED }
}