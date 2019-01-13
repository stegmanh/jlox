package lox

import lox.TokenType.*

class Scanner(val source: String) {
    val tokens: ArrayList<Token> = ArrayList()
    private var start = 0
    private var current = 0
    private var line = 0

    companion object {
        private val keyWords = hashMapOf(
                "and" to AND,
                "class" to CLASS,
                "else" to   ELSE,
                "false" to FALSE,
                "for" to FOR,
                "fun" to FUN,
                "if" to IF,
                "nil" to NIL,
                "or" to OR,
                "print" to PRINT,
                "return" to RETURN,
                "super" to SUPER,
                "this" to THIS,
                "true" to TRUE,
                "var" to VAR,
                "while" to WHILE
        )
    }

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

    // TODO: Add support for /**/ style comments
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
            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance()
                } else {
                    addToken(SLASH)
                }
            }
            ' ', '\r', '\t' -> {}
            '\n' -> {
                line++
            }
            '"' -> string()
            in '0'..'9' -> number()
            else -> {
                if (isAlpha(c)) {
                    identifier()
                } else {
                    Lox.error(line, "unexpected token $c.") // TODO: Turn a string of errors into a single error
                }
            }
        }
    }

    // advances the scanner and returns the previous character
    private fun advance(): Char {
        this.current++
        return source[current - 1]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = this.source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) { return false }
        if (source[current] != expected) return false

        current++
        return true
    }

    private fun peek(): Char {
        if (isAtEnd()) { return 0.toChar() } // null character
        return source[current]
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) { return 0.toChar() }
        return source[current + 1]
    }

    private fun string() {
        while(peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++
            }
            advance()
        }

        if (isAtEnd()) {
            Lox.error(line, "unterminated string")
        }

        // Grab the closing "
        advance()

        // Grab the inner values
        val value = source.substring(start + 1, current - 1)
        addToken(STRING, value)
    }

    private fun number() {
        while (isDigit(peek())) {
            advance()
        }

        if (peek() == '.' && isDigit(peekNext())) {
            advance()

            while (isDigit(peek())) {
                advance()
            }
        }

        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()

        val text = source.substring(start, current)
        var tokenType = keyWords.get(text)
        if (tokenType == null) { tokenType = IDENTIFIER }

        addToken(tokenType)
    }

    private fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun isAlpha(c: Char): Boolean {
        return c in 'a'..'z' || c in 'A'..'Z' || c == '_'
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return isAlpha(c) || isDigit(c)
    }
}