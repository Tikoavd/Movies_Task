package com.task.domain.model.errorhandler

sealed class ApiException(
    message: String?
) : Throwable(message)

class MobApiException(
    val code: Int,
    message: String?
) : ApiException(message)

class RestApiException(
    val code: String,
    message: String?
) : ApiException(message)

class PasswordExpiredException(
    val requestId: String,
    val confirmationId: String,
    val passwordResetToken: String,
    message: String?
) : ApiException(message)
