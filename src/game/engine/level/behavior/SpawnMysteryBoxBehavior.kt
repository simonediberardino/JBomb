package game.engine.level.behavior

import game.Bomberman
import game.engine.world.entity.impl.bonus.mystery_box.MysteryBoxPerk
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity
import game.engine.level.levels.Level
import game.engine.ui.panels.game.PitchPanel

class SpawnMysteryBoxBehavior(val level: Level): GameBehavior() {
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
        val c = Coordinates.generateCoordinatesAwayFrom(player.info.position, PitchPanel.GRID_SIZE * 2)
        val mysteryBox: Entity = MysteryBoxPerk(level, player)
        mysteryBox.info.position = c
        mysteryBox.spawn()
    }
}