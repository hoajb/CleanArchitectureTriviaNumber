package vn.hoanguyen.cleanarchitecture.trivianumber.app.features.trivia.domain.entities

import vn.hoanguyen.cleanarchitecture.trivianumber.core.extension.empty

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 */

data class NumberTrivia(
    val text: String,
    val number: Double,
    val found: Boolean,
    val type: String,
)

fun NumberTrivia.empty(): NumberTrivia = NumberTrivia(String.empty(), 0.0, false, String.empty())
fun NumberTrivia.isEmpty(): Boolean =
    this.text == String.empty() && this.number == 0.0 && !this.found && this.type == String.empty()