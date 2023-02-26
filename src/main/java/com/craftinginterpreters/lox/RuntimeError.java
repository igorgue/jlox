package com.craftinginterpreters.lox;

/**
 * RuntimeError
 */
public class RuntimeError extends RuntimeException {

  final Token token;

  RuntimeError(final Token token, final String message) {
    super(message);
    this.token = token;
  }
}
