package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models

import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */
internal class NumberTriviaDtoTest {
    private lateinit var gson: Gson

    @BeforeEach
    fun setUp() {
        gson = Gson()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `should return valid model when the json number is an integer`() {
        val expectedModel = NumberTriviaDto(
            text = "42 is the number of gallons that one barrel of petroleum holds.",
            number = 42.0,
            found = true,
            type = "trivia"
        )
        val jsonString = "{\n" +
                "\"text\": \"42 is the number of gallons that one barrel of petroleum holds.\",\n" +
                "\"number\": 42,\n" +
                "\"found\": true,\n" +
                "\"type\": \"trivia\"\n" +
                "}"

        val model = gson.fromJson(jsonString, NumberTriviaDto::class.java)
        assertEquals(expectedModel, model)
    }

    @Test
    fun `should return valid model when the json number is an double`() {
        val expectedModel = NumberTriviaDto(
            text = "42 is the number of gallons that one barrel of petroleum holds.",
            number = 42.2,
            found = true,
            type = "trivia"
        )
        val jsonString = "{\n" +
                "\"text\": \"42 is the number of gallons that one barrel of petroleum holds.\",\n" +
                "\"number\": 42.2,\n" +
                "\"found\": true,\n" +
                "\"type\": \"trivia\"\n" +
                "}"

        val model = gson.fromJson(jsonString, NumberTriviaDto::class.java)
        assertEquals(expectedModel, model)
    }
}