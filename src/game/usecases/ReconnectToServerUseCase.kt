package game.usecases

import game.properties.RuntimeProperties

class ReconnectToServerUseCase: UseCase<Unit> {
    override suspend fun invoke() {
        if (RuntimeProperties.lastConnectedIp.isEmpty())
            return

        ConnectToServerUseCase(RuntimeProperties.lastConnectedIp).invoke()
    }
}