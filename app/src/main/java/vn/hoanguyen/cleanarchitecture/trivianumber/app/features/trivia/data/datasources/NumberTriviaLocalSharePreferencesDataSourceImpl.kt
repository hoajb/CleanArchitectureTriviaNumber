package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.datasources

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models.NumberTriviaDto
import vn.hoanguyen.cleanarchitecture.trivianumber.core.exception.CachedException
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.empty
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on Aug 09 2021.
 */
class NumberTriviaLocalSharePreferencesDataSourceImpl @Inject constructor(
    private val sharePreferences: SharedPreferences,
    private val gson: Gson
) : NumberTriviaLocalDataSource {
    override suspend fun getConcreteNumberTrivia(number: Double): NumberTriviaDto {
        val jsonString =
            sharePreferences.getString(NUMBER_TRIVIA_LOCAL_PREF_KEY, String.empty()).orEmpty()

        if (jsonString.isNotEmpty())
            try {
                return gson.fromJson(jsonString, NumberTriviaDto::class.java)
            } catch (e: JsonSyntaxException) {
                throw CachedException("Not found with [JsonSyntaxException]")
            }
        throw CachedException("not found")
    }

    override suspend fun cacheNumberTrivia(itemToCache: NumberTriviaDto) {
        //Fixme
    }

    companion object {
        const val NUMBER_TRIVIA_LOCAL_PREF_KEY = "NUMBER_TRIVIA_LOCAL_PREF_KEY"
    }
}