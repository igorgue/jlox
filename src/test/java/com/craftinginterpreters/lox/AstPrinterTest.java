package com.craftinginterpreters.lox;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AstPrinterTest {

  @Test
  public void printBinaryExpr() {
    Expr expr = new Expr.Binary(
        new Expr.Unary(
            new Token(TokenType.MINUS, "-", null, 1),
            new Expr.Literal(123)),
        new Token(TokenType.STAR, "*", null, 1),
        new Expr.Grouping(
            new Expr.Literal(45.67)));
    assertThat(new AstPrinter().print(expr)).isEqualTo("(* (- 123) (group 45.67))");
  }
}
