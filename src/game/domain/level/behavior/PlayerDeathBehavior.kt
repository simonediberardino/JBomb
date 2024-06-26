package game.domain.level.behavior

import game.JBomb
import game.domain.events.game.DefeatGameEvent
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.presentation.ui.pages.game_over.GameOverPanel
import java.awt.event.ActionEvent
import javax.swing.Timer

class PlayerDeathBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        if (JBomb.match.currentLevel.gameHandler.canGameBeEnded() && !JBomb.isGameEnded) {
            val t = Timer(EntityInteractable.SHOW_DEATH_PAGE_DELAY_MS.toInt()) { _: ActionEvent? ->
                if (!JBomb.isGameEnded) {
                    DefeatGameEvent().invoke(null)
                    JBomb.destroyLevel(true)
                    JBomb.showActivity(GameOverPanel::class.java)
                }
            }
            t.isRepeats = false
            t.start()
        }
    }

    override fun clientBehavior(): () -> Unit = {}
}