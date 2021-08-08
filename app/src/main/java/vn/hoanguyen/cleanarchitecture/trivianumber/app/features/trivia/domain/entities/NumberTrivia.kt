package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */

data class NumberTrivia(
    val text: String,
    val number: Double,
    val found: Boolean,
    val type: String,
)
