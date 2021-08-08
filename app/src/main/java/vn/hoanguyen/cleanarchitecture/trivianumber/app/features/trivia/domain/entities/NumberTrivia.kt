package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */

/**
 * {
"text": "42 is the number of gallons that one barrel of petroleum holds.",
"number": 42,
"found": true,
"type": "trivia"
}
 */
data class NumberTrivia(
    val text: String,
    val number: Int,
    val found: Boolean,
    val type: String,
)
