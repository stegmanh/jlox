package tool

import java.io.PrintWriter

class GenerateAst {
    companion object {
        fun main(args: Array<String>) {
            // TODO: Make it a runnable tool
            if (args.size != 1 && false) {
                System.out.println("usage: generate_ast [output dir]")
                System.exit(1)
            }

            // TODO: Fix this to use CLI args
            //val outputDir = args[0]
            val outputDir = "/Users/holdenstegman/code/klox/dump"

            defineAST(outputDir, "Expr", listOf(
                    "Binary   ; val left: Expr, val operator: Token, val right: Expr",
                    "Grouping ; val expression: Expr",
                    "Literal  ; val value: Any",
                    "Unary    ; val operator: Token, val right: Expr"
            ));
        }

        private fun defineAST(outputDir: String, baseName: String, types: List<String>) {
            val path = "$outputDir/$baseName.kt"
            val writer = PrintWriter(path, "UTF-8")

            writer.println("package lox;")
            writer.println()
            writer.println("import java.util.List;")
            writer.println()
            writer.println("abstract class $baseName {")

            defineVisitor(writer, baseName, types)

            writer.println("  internal abstract fun <R> accept(visitor: Visitor<R>): R")
            writer.println()

            types.forEach {type ->
                val className = type.split(";")[0].trim()
                val fields = type.split(";")[1].trim()
                defineType(writer, baseName, className, fields)
            }

            writer.println("}")
            writer.close()

        }

        private fun defineType(writer: PrintWriter, baseName: String, className: String, fieldList: String) {
            writer.println("  class $className($fieldList) : $baseName() {")
            writer.println("    override fun <R> accept(visitor: Visitor<R>): R {")
            writer.println("      return visitor.visit$baseName$className(this)")
            writer.println("    }")
            writer.println("  }")
            writer.println()
        }

        private fun defineVisitor(writer: PrintWriter, baseName: String, types: List<String>) {
            writer.println("  interface Visitor<R> {")
            types.forEach {type ->
                val typeName = type.split(";")[0].trim()
                writer.println("    fun visit$baseName$typeName(${baseName.toLowerCase()}: $typeName): R")
            }

            writer.println("  }")
        }
    }
}

fun main(args: Array<String>) {
    GenerateAst.main(args)
}
