package game.domain.level.gamehandler.imp

import game.Bomberman
import game.domain.level.behavior.*
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.base.EntityState
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.models.State
import game.presentation.ui.pages.game_over.GameOverPanel
import game.utils.Utility
import java.awt.Image
import java.awt.event.ActionEvent
import javax.swing.Timer

open class DefaultGameHandler(level: Level) : GameHandler(level) {
    override val borderImages: Array<Image?>
        get() {
            val SIDES = 4
            val pitch = arrayOfNulls<Image>(SIDES)
            for (i in 0 until SIDES) {
                val path = level.fileSystemHandler.getImageForCurrentLevel("border_$i.png")
                pitch[i] = Utility.loadImage(path)
            }
            return pitch
        }

    override fun generateStone() = GenerateStoneBehavior(level.field).invoke()

    override fun generatePlayer() = GeneratePlayerBehavior(level.info.playerSpawnCoordinates).invoke()

    override fun spawnMysteryBox() = SpawnMysteryBoxBehavior(level).invoke()

    override fun generateDestroyableBlock() {
        DespawnDestroyableBlocksBehavior().invoke()
        GenerateDestroyableBlocksBehavior(level).invoke()
    }

    override fun spawnBoss() {
        SpawnBossBehavior(level.info.boss ?: return).invoke()
    }

    override fun spawnEnemies() = spawnEnemies(level.info.availableEnemies)

    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>) =
            SpawnEnemiesBehavior(level.info.startEnemiesCount, availableEnemies).invoke()

    override fun spawnAnimals() = spawnAnimals(level.info.availableAnimals)

    override fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>) =
            SpawnAnimalsBehavior(level.info.startAnimalsCount, availableAnimals).invoke()

    override fun canGameBeEnded(): Boolean = !Bomberman.match.getEntities().any { it is BomberEntity && it.state.state != State.DIED }

    override fun onPlayerDeath() = PlayerDeathBehavior().invoke()
}