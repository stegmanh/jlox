package tool

class GenerateAst {
    companion object {
        fun main(args: Array<String>) {
            if (args.size != 1) {
                System.out.println("usage: generate_ast [output dir]")
                System.exit(1)
            }
            val outputDir = args[0]
        }
    }
}

fun main(args: Array<String>) {
    GenerateAst.main(args)
}
