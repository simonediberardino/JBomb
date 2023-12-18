package game.multiplayer

class PlayerRoleContext(private val hostBehavior: HostBehavior, private val clientBehavior: ClientBehavior) {
    fun executePlayerLogic() {
        if (isHostPlayer()) {
            hostBehavior.executeHostLogic()
        } else {
            clientBehavior.executeClientLogic()
        }
    }
}