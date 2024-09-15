package org.example.rules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.psi.KtPsiFactory

fun PsiElement.setLineBreaks(lineBreaks: Int = 3) {
    val factory = KtPsiFactory(this.project)
    val breaks = factory.createNewLine(lineBreaks)
    this.node.replaceChild(this.node.firstChildNode, breaks.node)
}

class TwoBlankLinesRule :
    Rule(
        RuleId("codestyle:two-blank-lines-after-import"),
        about = About(),
    ) {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node.elementType == ElementType.IMPORT_LIST) {
            val nextSibling = node.treeNext
            if (nextSibling.psi is PsiWhiteSpace && nextSibling.text.count { it == '\n' } != 3) {
                val nextNode = nextSibling.treeNext
                println(nextNode)
                emit(nextNode.startOffset, "There must be two blank lines after imports", true)
                if (autoCorrect) {
                    // Auto-correction logic
                    nextSibling.psi.setLineBreaks(2)
                }
            }
        }
    }
}
