package game.utils.dev

import java.io.File
import javax.swing.JFileChooser


object FileUtils {
    fun selectFile(title: String): String? {
        val fileChooser = JFileChooser()
        fileChooser.setDialogTitle(title)

        val result = fileChooser.showOpenDialog(null)

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            return selectedFile.absolutePath
        }

        return null
    }

    fun chooseDestinationFile(title: String): String? {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = title
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY

        val userSelection = fileChooser.showSaveDialog(null)

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            val selectedFile: File = fileChooser.selectedFile
            return selectedFile.absolutePath
        }

        return null // If the user cancels or closes the dialog
    }
}