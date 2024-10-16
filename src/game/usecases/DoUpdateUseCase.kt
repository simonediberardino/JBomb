package game.usecases

import game.localization.Localization
import game.utils.ui.ToastUtils
import game.utils.dev.Log
import game.utils.file_system.Paths
import java.io.File
import java.io.InputStream
import kotlin.system.exitProcess

class DoUpdateUseCase : UseCase<Any> {
    override suspend fun invoke(): Any {
        ToastUtils.show(Localization.get(Localization.UPDATE_TUTORIAL))
        return 0
    }
}