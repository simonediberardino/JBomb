package game.domain.level.behavior

import game.Bomberman
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.MysteryBoxPerk
import game.domain.world.domain.entity.geo.Coordinates
import game.presentation.ui.panels.game.PitchPanel

class SpawnMysteryBoxBehavior(val level: Level) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            spawnMysteryBox()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }

    private fun spawnMysteryBox() {
        val player = Bomberman.match.player ?: return
        val c = Coordinates.generateCoordinatesAwayFrom(player.info.position, PitchPanel.GRID_SIZE * 2)
        val mysteryBox: Entity = MysteryBoxPerk({ level }, { player })
        mysteryBox.info.position = c
        mysteryBox.logic.spawn()
    }
}