package game.presentation.ui.elements

import java.awt.BorderLayout
import java.awt.BorderLayout.*
import java.awt.Color
import java.awt.Dialog
import java.awt.Graphics
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JPanel

fun createDialog(parentFrame: JFrame): JDialog {
    return JDialog(parentFrame).apply {
        isUndecorated = true
        size = parentFrame.size
        location = parentFrame.location  // Align location with the parent frame
        background = Color(0, 0, 0, 0)
        modalityType = Dialog.ModalityType.MODELESS
        isResizable = false
        defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
    }
}

fun createBlurBackground(panel: JPanel): JPanel {
    return object : JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.color = Color(0, 0, 0, 195)
            g.fillRect(0, 0, width, height)
        }
    }.apply {
        isOpaque = false
        layout = BorderLayout()
        add(panel, CENTER)
    }
}