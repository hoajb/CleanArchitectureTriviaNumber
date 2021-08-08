package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.data.models

import com.google.gson.annotations.SerializedName
import vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities.NumberTrivia
import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.empty

/**
 * Created by Hoa Nguyen on Aug 08 2021.
 */

/**
 * {
"text": "42 is the number of gallons that one barrel of petroleum holds.",
"number": 42,
"found": true,
"type": "trivia"
}
 */
data class NumberTriviaDto(
    @SerializedName("text")
    val text: String?,
    @SerializedName("number")
    val number: Double?,
    @SerializedName("found")
    val found: Boolean?,
    @SerializedName("type")
    val type: String?,
) {
    fun toNumberTrivia(): NumberTrivia = NumberTrivia(
        text.orEmpty(),
        number ?: 0.0,
        found ?: false,
        type.orEmpty(),
    )

    companion object {
        val empty = NumberTriviaDto(
            String.empty(),
            0.0,
            false,
            String.empty()
        )

    }
}