package example.com.database.dao

import example.com.model.Post
import example.com.model.Posts
import example.com.model.Posts.message
import example.com.model.Posts.user
import example.com.model.User
import example.com.model.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PostDao {
    suspend fun findAll(): List<Post> = dbQuery {
        Posts.selectAll().map {
            Post(
                id = it[Posts.id],
                user = it[Posts.user],
                message = it[Posts.message],
                img = it[Posts.img]
            )
        }
    }

    suspend fun getPostsUser(user: String): List<Post> {
        return dbQuery {
            Posts.selectAll().where { Posts.user eq user }
                .map {
                    Post(
                        id = it[Posts.id],
                        user = it[Posts.user],
                        message = it[Posts.message],
                        img = it[Posts.img]
                    )
                }
        }
    }

    suspend fun save(post: Post) =
        dbQuery {
            val insertStatement = Posts.insert {
                it[id] = post.id
                it[user] = post.user
                it[message] = post.message
                it[img] = post.img
            }
            insertStatement.resultedValues?.singleOrNull()?.let {
                Post(
                    id = it[Posts.id],
                    user = it[Posts.user],
                    message = it[Posts.message],
                    img = it[Posts.img]
                )
            }
        }
    suspend fun update(post: Post): Boolean {
        return dbQuery {
            Users.update({ Users.id eq post.id }) {
                it[Posts.id] = post.id
                it[user] = post.user
                it[message] = post.message
                it[Posts.img] = post.img
            } > 0
        }
    }

    suspend fun findByEmailAndPassword(email: String, password: String): User {
        return dbQuery {
            Users.selectAll().where { Users.email.eq(email) and Users.password.eq(password) }
                .map {
                    User(
                        id = it[Users.id],
                        name = it[Users.name],
                        email = it[Users.email],
                        password = it[Users.password],
                        img = it[Users.img]
                    )
                }.first()
        }
    }

    suspend fun findById(id: String): Post? = dbQuery {
        Posts.selectAll().where { Posts.id eq id }
            .map {
                Post(
                    id = it[Posts.id],
                    user = it[Posts.user],
                    message = it[Posts.message],
                    img = it[Posts.img]
                )
            }.firstOrNull()
    }

    suspend fun saveImage(id: String, img: String): Boolean {
        return dbQuery {
            Users.update({ Users.id eq id }) {
                it[Users.img] = img
            } > 0
        }
    }

    suspend fun delete(id: String): Boolean {
        return dbQuery {
            Posts.deleteWhere { Posts.id eq id } > 0
        }
    }

    suspend fun deleteAll(): Boolean {
        return dbQuery {
            Posts.deleteAll() > 0
        }
    }

    suspend fun saveAll(posts: List<Post>) = dbQuery {
        Users.batchInsert(posts) { post ->
            this[Posts.id] = post.id
            this[Posts.user] = post.user
            this[Posts.message] = post.message
            this[Posts.img] = post.img
        }
    }
}