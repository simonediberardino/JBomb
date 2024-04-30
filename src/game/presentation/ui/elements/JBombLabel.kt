package game.presentation.ui.elements

import game.values.Dimensions
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel
import javax.swing.SwingConstants

class JBombLabel(
        text: String,
        constants: Int = CENTER,
        fontSize: Int = Dimensions.FONT_SIZE_MID
) : JLabel(
        text,
        constants
) {
    constructor(text: String) : this(text, CENTER)

    init {
        isOpaque = false
        setForeground(Color.WHITE)
        setFont(Font(Font.MONOSPACED, Font.BOLD, fontSize))
        setBorder(javax.swing.BorderFactory.createEmptyBorder())
    }
}