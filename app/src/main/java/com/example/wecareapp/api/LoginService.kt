package com.example.wecareapp.api


import com.example.wecareapp.model.Patient
import retrofit2.Response
import retrofit2.http.*

interface LoginService {
/*
    @GET("posts/1")
    suspend fun getPost(): Response<Patient>

    @GET("posts/{postNumber}")
    suspend fun getPost2(
        @Path("postNumber") number: Int
    ): Response< Patient>

    @GET("posts")
    suspend fun getCustomPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<Patient>>

    @GET("posts")
    suspend fun getCustomPosts2(
        @Query("userId") userId: Int,
        @QueryMap options: Map<String, String>
    ): Response<List< Patient>>
*/
    @POST("identity/login")
    suspend fun pushPost(
        @Body post:  Patient
    ): Response<Patient>

    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPost2(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response< Patient>

}