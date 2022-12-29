package com.craftinginterpreters.lox;

import com.craftinginterpreters.lox.Expr.Binary;
import com.craftinginterpreters.lox.Expr.Grouping;
import com.craftinginterpreters.lox.Expr.Literal;
import com.craftinginterpreters.lox.Expr.Unary;

/**
 * Interpreter
 */
public class Interpreter implements Expr.Visitor<Object> {

    private Object evaluate(final Expr expr) { return expr.accept(this); }

    @Override
    public Object visitBinaryExpr(Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
        case BANG_EQUAL:
            return !isEqual(left, right);
        case EQUAL_EQUAL:
            return isEqual(left, right);
        case GREATER:
            checkNumberOperands(expr.operator, left, right);
            return (double)left > (double)right;
        case GREATER_EQUAL:
            checkNumberOperands(expr.operator, left, right);
            return (double)left >= (double)right;
        case LESS:
            checkNumberOperands(expr.operator, left, right);
            return (double)left < (double)right;
        case LESS_EQUAL:
            checkNumberOperands(expr.operator, left, right);
            return (double)left <= (double)right;
        case MINUS:
            checkNumberOperands(expr.operator, left, right);
            return (double)left - (double)right;
        case PLUS:
            if (left instanceof Double && right instanceof Double) {
                return (double)left + (double)right;
            }

            if (left instanceof String && right instanceof String) {
                return (String)left + (String)right;
            }
        case SLASH:
            checkNumberOperands(expr.operator, left, right);
            return (double)left / (double)right;
        case STAR:
            checkNumberOperands(expr.operator, left, right);
            return (double)left * (double)right;
        default:
            break;
        }

        return null;
    }

    @Override
    public Object visitGroupingExpr(Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
        case BANG:
            return !isTruthy(right);
        case MINUS:
            return -(double)right;
        default:
            return null;
        }
    }

    private boolean isTruthy(final Object object) {
        if (object == null)
            return false;
        if (object instanceof Boolean)
            return (boolean)object;
        return true;
    }

    private boolean isEqual(final Object a, final Object b) {
        if (a == null && b == null)
            return true;
        if (a == null)
            return false;

        return a.equals(b);
    }

    private void checkNumberOperands(final Token operator, final Object left,
                                     final Object right) {
        if (left instanceof Double && right instanceof Double)
            return;

        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    void interpret(Expr expression) {
        try {
            Object value = evaluate(expression);
            System.out.println(stringify(value));
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }

    private String stringify(final Object object) {
        if (object == null)
            return "nil";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0"))
                text = text.substring(0, text.length() - 2);
            return text;
        }

        return object.toString();
    }
}
