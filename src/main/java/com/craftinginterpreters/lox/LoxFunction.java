package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
  private final String name;
  private final Expr.Function declaration;
  private final Environment closure;

  LoxFunction(String name, Expr.Function declaration, Environment closure) {
    this.name = name;
    this.closure = closure;
    this.declaration = declaration;
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(closure);

    for (int i = 0; i < declaration.params.size(); i++) {
      environment.define(declaration.params.get(i).lexeme, arguments.get(i));
    }

    try {
      interpreter.executeBlock(declaration.body, environment);
    } catch (Return returnValue) {
      return returnValue.value;
    }

    return null;
  }

  @Override
  public int arity() {
    return declaration.params.size();
  }
}
