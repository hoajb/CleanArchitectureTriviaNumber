package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import retrofit2.http.GET
import retrofit2.http.Path
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto

/**
 * Created by Hoa Nguyen on Aug 11 2021.
 */
interface NumberTriviaRestAPI {

    @GET ("{number}?json")
    suspend fun getConcreteNumberTrivia(@Path("number") number: Int): NumberTriviaDto

    @GET ("random/trivia?json")
    suspend fun getRandomNumberTrivia(): NumberTriviaDto
}