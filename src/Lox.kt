package lox

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths

class Lox {
    companion object {
        var hadError = false

        fun run(args: Array<String>) {
            if (args.size > 1) {
                System.out.println("Usage: klox [script]")
                System.exit(64)
            } else if (args.size == 1) {
                throw Exception("unimplemented")
            } else {
                runPrompt()
            }
        }

        private fun runFile(path: String) {
            val bytes = Files.readAllBytes(Paths.get(path))
            run(String(bytes))
            if (hadError) {
                System.exit(65)
            }
        }

        private fun runPrompt() {
            val input = InputStreamReader(System.`in`)
            val reader = BufferedReader(input)

            while(true) {
               System.out.print("> ")
               run(reader.readLine())
               hadError = false
            }
        }

        private fun run(source: String) {
            println("running: $source")
            /*val scanner = Scanner(source)
            List<Token> tokens = scanner.scanTokens()

            for (Token t : tokens) {
                System.out.println(t)
            }*/
        }

        fun error(line: Int, message: String) {
            report(line, "", message)
        }

        fun report(line: Int, where: String, message: String) {
            System.out.println("[line $line] Error$where: $message")
            hadError = true
        }
    }
}