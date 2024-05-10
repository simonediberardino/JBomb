package game.domain.level.behavior

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.presentation.ui.pages.game_over.GameOverPanel
import java.awt.event.ActionEvent
import javax.swing.Timer

class PlayerDeathBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        if (Bomberman.match.currentLevel.gameHandler.canGameBeEnded()) {
            val t = Timer(EntityInteractable.SHOW_DEATH_PAGE_DELAY_MS.toInt()) { _: ActionEvent? ->
                Bomberman.destroyLevel(true)
                Bomberman.showActivity(GameOverPanel::class.java)
            }
            t.isRepeats = false
            t.start()
        }
    }

    override fun clientBehavior(): () -> Unit = {}
}