package eu.vmpay.jsonplaceholder.repository

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @Embedded
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String,
    @SerializedName("website") val website: String,
    @SerializedName("company") val company: Company
)

data class Geo(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)

data class Company(
    @SerializedName("name") val name: String,
    @SerializedName("catchPhrase") val catchPhrase: String,
    @SerializedName("bs") val bs: String
)

data class Address(
    @SerializedName("street") val street: String,
    @SerializedName("suite") val suite: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipcode") val zipCode: String,
    @Embedded
    @SerializedName("geo") val geo: Geo
)

@Entity(tableName = "comments")
data class Comment(
    @SerializedName("postId") val postId: Int,
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)

@Entity(tableName = "posts")
data class Post(
    @SerializedName("userId") val userId: Int,
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)