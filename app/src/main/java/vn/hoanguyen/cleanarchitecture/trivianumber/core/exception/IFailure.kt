package vn.hoanguyen.cleanarchitecture.trivianumber.core.exception

/**
 * Created by Hoa Nguyen on Aug 06 2021.
 * Copied from https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/main/app/src/main/kotlin/com/fernandocejas/sample/core/exception/Failure.kt
 */

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class IFailure {
    object NetworkConnection : IFailure()
    object ServerError : IFailure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : IFailure()
}