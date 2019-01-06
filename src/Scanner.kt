package lox

class Scanner(val source: String) {
    val tokens: List<Token> = ArrayList()

    fun scanTokens(): List<Token> {
        return tokens
    }
}