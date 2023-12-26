package com.poisonedyouth.springhexagonaltemplate.rules

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withDataModifier
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.jupiter.api.Test

class RulesTest {

    @Test
    fun `all data classes should not use a var property`() {
        Konsist.scopeFromProduction().classes().withDataModifier().assertFalse { classDeclaration ->
            classDeclaration.properties().any { it.hasVarModifier }
        }
    }

    @Test
    fun `there are no suppressions used on class level`() {
        val classes = Konsist.scopeFromProject().classes()

        val interfaces = Konsist.scopeFromProject().interfaces()

        val objects = Konsist.scopeFromProject().objects()

        val scope = classes + interfaces + objects

        scope.assertFalse { it.hasAnnotationOf(Suppress::class) }
    }

    @Test
    fun `there are no suppressions used on method level of classes`() {
        val classes = Konsist.scopeFromProject().classes()

        val interfaces = Konsist.scopeFromProject().interfaces()

        val objects = Konsist.scopeFromProject().objects()

        val scope = classes + interfaces + objects
        scope.functions().assertFalse { it.hasAnnotationOf(Suppress::class) }
    }
}
