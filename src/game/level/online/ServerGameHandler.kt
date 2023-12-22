package game.level.online

import game.http.callbacks.TCPServerCallback
import game.http.dao.EntityDao
import game.http.messages.AssignIdHttpMessage
import game.http.messages.SpawnedEntityHttpMessage
import game.http.repo.HttpRepository
import game.http.serializing.HttpParserSerializer
import game.http.sockets.TCPServer

// add level as parameter, online handler
class ServerGameHandler : TCPServerCallback {
    private lateinit var server: TCPServer
    var running: Boolean = false
        private set

    fun create() {
        server = TCPServer(482).also { it.start() }
        server.register(this)
    }

    fun onEntitySpawn(entityDao: EntityDao) {
        val message = SpawnedEntityHttpMessage(entityDao)
        HttpRepository.instance.send(message)
    }

    override fun onCloseServer() {
        running = false
    }

    override fun onStartServer() {
        running = true
    }

    override fun onClientConnected(indexedClient: TCPServer.IndexedClient) {
        server.sendData(indexedClient.id, HttpParserSerializer.instance.serialize(AssignIdHttpMessage(indexedClient.id)))
    }
}