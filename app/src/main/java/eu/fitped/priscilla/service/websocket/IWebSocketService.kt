package eu.fitped.priscilla.service.websocket

interface IWebSocketService {
    fun connect(url: String)
    fun send(message: String): Boolean
    fun close(code: Int = 1000, reason: String = "Closing connection")
    fun addListener(listener: IWebSocketListener)
    fun removeListener(listener: IWebSocketListener)
}