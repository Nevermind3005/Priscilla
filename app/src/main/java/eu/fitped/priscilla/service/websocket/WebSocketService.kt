package eu.fitped.priscilla.service.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import javax.inject.Inject

class WebSocketService @Inject constructor(
    private val _okHttpClient: OkHttpClient
) : IWebSocketService {

    private var _webSocket: WebSocket? = null
    private val listeners = mutableListOf<IWebSocketListener>()

    override fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        _webSocket = _okHttpClient.newWebSocket(request, object : okhttp3.WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                listeners.forEach { it.onOpen(webSocket, response) }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                listeners.forEach { it.onMessage(webSocket, text) }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                listeners.forEach { it.onClosing(webSocket, code, reason) }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                listeners.forEach { it.onClosed(webSocket, code, reason) }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                listeners.forEach { it.onFailure(webSocket, t, response) }
            }
        })
    }

    override fun send(message: String): Boolean {
        return _webSocket?.send(message) ?: false
    }

    override fun close(code: Int, reason: String) {
        _webSocket?.close(code, reason)
        _webSocket = null
    }

    override fun addListener(listener: IWebSocketListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    override fun removeListener(listener: IWebSocketListener) {
        listeners.remove(listener)
    }
}