package game.domain.level.levels.level_editor

import game.domain.level.behavior.GenerateLevelFromXmlBehavior
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy

class LevelEditorGameHandler(level: Level, private val levelData: LevelGenerationData?): DefaultGameHandler(level) {
    override fun generateDestroyableBlock() {
    }

    override fun generateStone() {
        levelData?.let { GenerateLevelFromXmlBehavior(Level.currLevel, it).invoke() }
    }

    override fun spawnBoss() {}
    override fun spawnEnemies() {}
    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>, count: Int) {}
    override fun spawnMysteryBox() {}
    override fun canGameBeEnded(): Boolean = false
    override fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>) {}
    override fun spawnAnimals() {}
}