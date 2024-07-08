package com.example.messenger.models

data class Message(
    val id: String = "",
    val from: String = "",
    val body: String = "",
) {
    override fun equals(other: Any?): Boolean {

        return (other as Message).id == id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + body.hashCode()
        return result
    }
}