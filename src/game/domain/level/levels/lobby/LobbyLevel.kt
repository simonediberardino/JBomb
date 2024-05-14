package game.domain.level.levels.lobby

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.StoryLevel

abstract class LobbyLevel : StoryLevel() {
    override val gameHandler: GameHandler
        get() = object : DefaultGameHandler(this) {
            override fun generate() {
                generateLevel()
                JBomb.match.player = Player(info.playerSpawnCoordinates)
                JBomb.match.player!!.logic.spawn(forceSpawn = false, forceCentering = false)
                spawnAnimals()
            }
        }

    abstract fun generateLevel()
}