import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.example.rules.TwoBlankLinesRule
import org.junit.jupiter.api.Test

class TwoBlankLinesRuleTest {
    private val wrappingRuleAssertThat = assertThatRule { TwoBlankLinesRule() }

    @Test
    fun `Two blank lines after imports rule`() {
        // whenever KTLINT_DEBUG env variable is set to "ast" or -DktlintDebug=ast is used
        // com.pinterest.ktlint.test.(lint|format) will print AST (along with other debug info) to the stderr.
        // this can be extremely helpful while writing and testing rules.
        // uncomment the line below to take a quick look at it
        // System.setProperty("ktlintDebug", "ast")
        val code =
            """
            import org.junit.jupiter.api.Test
            import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
            import org.example.rules.TwoBlankLinesRule
            
            class TwoBlankLinesRuleTest {}
            """.trimIndent()
        wrappingRuleAssertThat(code)
            .hasLintViolation(
                5,
                1,
                "There must be two blank lines after imports",
            )
    }
}
