package example.com.model

import org.jetbrains.exposed.sql.Table

data class Post(
    val id: String,
    val user: String,
    val message: String,
    val img: String
)

object Posts : Table() {
    val id = text("id")
    val user = text("user")
    val message = text("message")
    val img = text("img")
    override val primaryKey = PrimaryKey(id)
}