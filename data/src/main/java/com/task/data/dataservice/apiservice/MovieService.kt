package com.task.data.dataservice.apiservice

import com.task.entities.responce.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/week")
    suspend fun getMovies(): MoviesResponseDto

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): MoviesResponseDto.MovieDto
}
