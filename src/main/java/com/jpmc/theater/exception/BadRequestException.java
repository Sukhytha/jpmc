package com.jpmc.theater.exception;

/**
 * some sample exception classes.
 * we can create custom exceptions(checked and unchecked) if we wish to handle them differently.
 * or add extra information for the caller than what standard java has to offer.
 * There is no need to create custom exceptions if there is no value in doing so.
 */
public class BadRequestException extends RuntimeException
{
}