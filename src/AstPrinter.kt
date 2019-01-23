package lox

class AstPrinter: Expr.Visitor<String> {
    fun print(expr: Expr): String {
        return expr.accept(this)
    }

    override fun visitExprBinary(expr: Expr.Binary): String {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right)
    }

    override fun visitExprGrouping(expr: Expr.Grouping): String {
        return parenthesize("group", expr.expression)
    }

    override fun visitExprLiteral(expr: Expr.Literal): String {
        if (expr.value == null) {
            return "nil"
        }
        return expr.value.toString()
    }

    override fun visitExprUnary(expr: Expr.Unary): String {
        return parenthesize(expr.operator.lexeme, expr.right)
    }

    private fun parenthesize(name: String, vararg exprs: Expr): String {
        val builder = StringBuilder()

        builder.append("(").append(name)
        for (expr in exprs) {
            builder.append(" ")
            builder.append(expr.accept(this))
        }
        builder.append(")")

        return builder.toString()
    }
}

