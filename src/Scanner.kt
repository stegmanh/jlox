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
        val c = advance()
        when(c) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)
            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS else LESS_EQUAL)
            '>' -> addToken(if (match('=')) GREATER else GREATER_EQUAL)
            else -> Lox.error(line, "unexpected token $c.") // TODO: Turn a string of errors into a single error
        }
    }

    private fun advance(): Char {
        this.current++
        return source[current - 1]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Object?) {
        val text = this.source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }
}