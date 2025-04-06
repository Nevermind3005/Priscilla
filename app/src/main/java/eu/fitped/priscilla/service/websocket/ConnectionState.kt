package eu.fitped.priscilla.service.websocket

enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    CLOSING,
    ERROR
}