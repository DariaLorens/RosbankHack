package ru.lorens.rosbankhack.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.lorens.rosbankhack.Consts

object RestClient {

    val getClient: ApiInterface by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("")
        suspend fun getUsers(): Respons

        @POST("")
        suspend fun setUser(@Body user: User): Respons

        @POST("")
        suspend fun setUsers(@Body user: List<User>): Respons

        @POST("get-cards")
        suspend fun getCards(@Body customer: Customer): List<Card>

        @POST("reaction")
        suspend fun reaction(@Body reaction: Reaction)
    }

    data class Customer(val customer_id: Int)
    data class Reaction(val user_id: Int, val article_id: Int, val reaction: String)
}