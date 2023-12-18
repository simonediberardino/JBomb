package game.level.functionalities

import game.Bomberman
import game.entity.Player
import game.entity.models.Coordinates
import game.multiplayer.HostBehavior
import game.ui.panels.game.PitchPanel

class GeneratePlayerUseCase: LevelUseCase {
    override fun invoke() {
        val hostBehavior = object: HostBehavior {
            override fun executeHostLogic() {
                val coords = Coordinates.generateRandomCoordinates(Player.SPAWN_OFFSET, PitchPanel.GRID_SIZE)
                Bomberman.getMatch().player = Player(coords)
                Bomberman.getMatch().player.spawn(false, false)
            }
        }

        hostBehavior.executeHostLogic()
    }
}