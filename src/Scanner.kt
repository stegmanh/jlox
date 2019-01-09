package lox

import lox.TokenType.*

class Scanner(val source: String) {
    val tokens: ArrayList<Token> = ArrayList()
    private var start = 0
    private var current = 0
    private var line = 0

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }

        tokens.add(Token(EOF, "", null, line))
        return tokens
    }

    fun isAtEnd(): Boolean {
        return current >= source.length
    }

    fun scanToken() {
        
    }
}