package game.presentation.ui.elements

import game.utils.dev.Log
import game.utils.ui.Utils2D
import game.values.Dimensions
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JPanel


class JBombLabelMultiLine(
        text: String,
        fontSize: Int,
        maxWidth: Int
) : JPanel() {
    constructor(title: String, maxWidth: Int) : this(title, Dimensions.FONT_SIZE_MID, maxWidth)

    init {
        val font = Font(Font.MONOSPACED, Font.BOLD, fontSize)

        isOpaque = false
        layout = GridLayout(0, 1)

        val textWidth = Utils2D.stringWidth(font, text)
        val characterWidth = textWidth / text.length
        val maxCharactersInLine = maxWidth / characterWidth

        val lines = Utils2D.splitStringIntoChunks(text, maxCharactersInLine)

        lines.forEach {
            add(JBombLabel(
                    text = it.trim(),
                    fontSize = fontSize
            ))
        }

        repaint()
    }
}