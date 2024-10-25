package game.presentation.ui.pages.multiplayer

import game.JBomb.showActivity
import game.JBomb.startLevel
import game.domain.level.levels.multiplayer.MultiplayerLevel
import game.domain.level.levels.multiplayer.MultiplayerLevelMap1
import game.domain.match.JBombMatch.Companion.port
import game.localization.Localization
import game.localization.Localization.MP_CASTLE_UI_NAME
import game.network.gamehandler.ServerGameHandler
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.panels.models.BoxMenuPanel
import game.presentation.ui.viewelements.bombermanbutton.GrayButton
import game.presentation.ui.viewelements.bombermanbutton.RedButton
import game.presentation.ui.viewelements.bombermanbutton.YellowButton
import game.repository.RepositoryMaps
import game.utils.Utility
import game.utils.file_system.Paths
import game.values.Dimensions
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane


class MultiplayerSelectMapPanel(
    cardLayout: CardLayout,
    parent: JPanel,
    frame: JBombFrame
) : BoxMenuPanel(
    cardLayout,
    parent,
    frame,
    Localization.get(Localization.MP_CHOOSE_MAP),
    Paths.mainMenuWallpaper,
    false
) {
    override fun getBoxPanelWidth(): Int = Utility.px(800)

    override fun addCustomElements() {
        boxComponentsPanel.addComponent(createMapList())
        boxComponentsPanel.addComponent(createBackButton())
    }

    private fun createMapList(): JScrollPane {
        val mapPanel = JPanel(GridBagLayout()).apply {
            alignmentY = TOP_ALIGNMENT
            alignmentX = CENTER_ALIGNMENT
            isOpaque = false       // Make the panel transparent
        }

        val gbc = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            anchor = GridBagConstraints.NORTH
            gridx = 0
            weightx = 1.0
            weighty = 0.0
            insets = java.awt.Insets(5, 5, 5, 5)  // Padding between buttons
        }

        RepositoryMaps.maps.forEach { (t, u) ->
            GrayButton(t).apply {
                isOpaque = false       // Make the button transparent
                background = null
                addActionListener {
                    startLevel(
                        u.newInstance(),
                        ServerGameHandler(port)
                    )
                }
            }.also { button ->
                mapPanel.add(button, gbc)
            }
        }

        val filler = JPanel().apply {
            isOpaque = false      // Make the filler panel transparent
        }
        gbc.weighty = 1.0
        mapPanel.add(filler, gbc)

        return JScrollPane(mapPanel).apply {
            border = null
            isOpaque = false
            background = null
            viewport.isOpaque = false          // Make the viewport transparent
            preferredSize = Dimension(
                boxPanelWidth - Dimensions.DEFAULT_X_PADDING * 2,
                Utility.px(400)
            )
            verticalScrollBar.also {
                it.preferredSize = Dimension(0, 0)
                it.unitIncrement = 16
            }
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            alignmentX = CENTER_ALIGNMENT
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        }
    }

    private fun createBackButton(): JButton {
        return RedButton(Localization.get(Localization.BACK)).apply {
            addActionListener { showActivity(MultiplayerPanel::class.java) }
        }
    }
}
