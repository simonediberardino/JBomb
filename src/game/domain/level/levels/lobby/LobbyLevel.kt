package game.domain.level.levels.lobby

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.StoryLevel

abstract class LobbyLevel : StoryLevel() {
    override val gameHandler: GameHandler
        get() = object : DefaultGameHandler(this) {
            override fun generate() {
                generateLevel()
                Bomberman.getMatch().player = Player(info.playerSpawnCoordinates)
                Bomberman.getMatch().player!!.logic.spawn(forceSpawn = false, forceCentering = false)
            }
        }

    abstract fun generateLevel()
}