package eu.fitped.priscilla.service.websocket

import okhttp3.Response
import okhttp3.WebSocket

interface IWebSocketListener {

    fun onOpen(webSocket: WebSocket, response: Response)

    fun onMessage(webSocket: WebSocket, text: String)

    fun onClosing(webSocket: WebSocket, code: Int, reason: String)

    fun onClosed(webSocket: WebSocket, code: Int, reason: String)

    fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?)
}

