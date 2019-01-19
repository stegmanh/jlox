package lox;

abstract class Expr {
    interface Visitor<R> {
        fun visitExprBinary(expr: Binary): R
        fun visitExprGrouping(expr: Grouping): R
        fun visitExprLiteral(expr: Literal): R
        fun visitExprUnary(expr: Unary): R
    }
    internal abstract fun <R> accept(visitor: Visitor<R>): R

    class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitExprBinary(this)
        }
    }

    class Grouping(val expression: Expr) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitExprGrouping(this)
        }
    }

    class Literal(val value: Any) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitExprLiteral(this)
        }
    }

    class Unary(val operator: Token, val right: Expr) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitExprUnary(this)
        }
    }

}
