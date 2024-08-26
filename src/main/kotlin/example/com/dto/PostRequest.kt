package example.com.dto

import example.com.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val id: String,
    val user: String,
    val message: String,
    val img: String
) {
    fun toPost() = Post(
        id = id,
        user = user,
        message = message,
        img = img
    )
}