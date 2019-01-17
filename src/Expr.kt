/*package lox

import java.util.List;

abstract class Expr {
    class Binary(left: Expr, operator: Token, right: Expr) : Expr()
    class Grouping(expression: Expr) : Expr()
    class Literal(value: Any) : Expr()
    class Unary(operator: Token, right: Expr) : Expr()
}*/

package lox;

import java.util.List;

abstract class Expr {
    interface Visitor<R> {
        fun visitExprBinary(expr: Binary): R
        fun visitExprGrouping(expr: Grouping): R
        fun visitExprLiteral(expr: Literal): R
        fun visitExprUnary(expr: Unary): R
    }

    class Binary(left: Expr, operator: Token, right: Expr) : Expr()

    /*<R> R accept(visitor: Visitor<R>) {
        return visitor.visitBinaryExpr(this)
    }
    class Grouping(expression: Expr) : Expr() {}

    <R> R accept(visitor: Visitor<R>) {
        return visitor.visitGroupingExpr(this)
    }
    class Literal(value: Any) : Expr() {}

    <R> R accept(visitor: Visitor<R>) {
        return visitor.visitLiteralExpr(this)
    }
    class Unary(operator: Token, right: Expr) : Expr() {}

    <R> R accept(visitor: Visitor<R>) {
        return visitor.visitUnaryExpr(this)
    }*/

    abstract <R> R accept(visitor: Visitor<R>)
}
