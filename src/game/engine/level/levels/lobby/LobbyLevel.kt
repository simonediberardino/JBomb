package game.engine.level.levels.lobby

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.level.gamehandler.imp.DefaultGameHandler
import game.engine.level.gamehandler.model.GameHandler
import game.engine.level.levels.StoryLevel

abstract class LobbyLevel : StoryLevel() {
    override val gameHandler: GameHandler
        get() = object : DefaultGameHandler(this) {
            override fun generate() {
                generateLevel()
                Bomberman.getMatch().player = Player(info.playerSpawnCoordinates)
                Bomberman.getMatch().player?.spawn(false, false)
            }
        }

    abstract fun generateLevel()
}