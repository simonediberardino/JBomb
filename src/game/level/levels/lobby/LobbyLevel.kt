package game.level.levels.lobby

import game.Bomberman
import game.entity.player.Player
import game.level.gamehandler.imp.DefaultGameHandler
import game.level.gamehandler.model.GameHandler
import game.level.levels.StoryLevel

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