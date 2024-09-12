package org.example.rules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement

class TwoBlankLinesRule : Rule(
    RuleId("two-blank-lines-after-import-rule"),
    about = About()
) {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // Process CLASS nodes
        if (node.elementType == ElementType.CLASS) {
            val prevSibling = node.treePrev

            // Check if the previous sibling is the IMPORT_LIST (import block)
            if (prevSibling?.elementType == ElementType.IMPORT_LIST) {
                val importsText = prevSibling.text

                // Count the number of blank lines between imports and class declaration
                val blankLines = importsText.split("\n").count { it.isBlank() }

                if (blankLines < 2) {
                    // Emit an error if there are fewer than 2 blank lines
                    emit(node.startOffset, "There should be two blank lines between the import block and the class declaration", true)

                    if (autoCorrect && prevSibling is LeafPsiElement) {
                        // Add the necessary blank lines before the class declaration
                        prevSibling.rawReplaceWithText(importsText + "\n\n")
                    }
                }
            }
        }
    }
}
