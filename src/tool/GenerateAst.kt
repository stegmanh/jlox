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
            val outputDir = args[0]
            //val outputDir = "/Users/holdenstegman/code/klox/dump"
            defineAST(outputDir, "Expr", listOf(
                    "Binary   ; left: Expr, operator: Token, right: Expr",
                    "Grouping ; expression: Expr",
                    "Literal  ; value: Any",
                    "Unary    ; operator: Token, right: Expr"
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

            types.forEach {type ->
                val className = type.split(";")[0].trim()
                val fields = type.split(";")[1].trim()
                defineType(writer, baseName, className, fields)
                print(fields)
            }

            writer.println("}")
            writer.close()

        }

        private fun defineType(writer: PrintWriter, baseName: String, className: String, fieldList: String) {
            writer.println("  class $className($fieldList) : $baseName() {}")
        }
    }
}

fun main(args: Array<String>) {
    GenerateAst.main(args)
}
