package lox

abstract class Expr {
    // Static by default
    class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr() {}
}

