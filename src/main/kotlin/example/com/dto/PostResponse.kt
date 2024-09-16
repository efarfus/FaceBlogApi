package example.com.dto

import example.com.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: String,
    val user: String,
    val message: String,
    val img: String
)

fun Post.toPostResponse() = PostResponse(
    id = id,
    user = user,
    message = message,
    img = img
)