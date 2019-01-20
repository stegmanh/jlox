package lox

class AstPrinter: Expr.Visitor<String> {
    fun print(expr: Expr): String {
        return expr.accept(this)
    }

    override fun visitExprGrouping(expr: Expr.Grouping): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitExprLiteral(expr: Expr.Literal): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitExprUnary(expr: Expr.Unary): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visitExprBinary(expr: Expr.Binary): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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