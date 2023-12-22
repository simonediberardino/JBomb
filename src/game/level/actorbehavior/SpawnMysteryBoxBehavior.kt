package game.level.actorbehavior

import game.Bomberman
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.level.Level
import game.ui.panels.game.PitchPanel

class SpawnMysteryBoxBehavior(val level: Level): GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            spawnMysteryBox()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }

    private fun spawnMysteryBox() {
        val player = Bomberman.getMatch().player ?: return
        val c = Coordinates.generateCoordinatesAwayFrom(player.coords, PitchPanel.GRID_SIZE * 2)
        val mysteryBox: Entity = MysteryBoxPerk(level, player)
        mysteryBox.coords = c
        mysteryBox.spawn()
    }
}