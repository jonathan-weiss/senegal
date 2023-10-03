package org.codeblessing.senegal.architecturetests

import org.reflections.Reflections
import kotlin.reflect.KClass

/**
 * Helper class to access code of the private debt platform and perform structural checks on it.
 * Add new convenience methods on `reflections: Reflections` as needed.
 */
object SenegalCodebase {

    private val reflections: Reflections by lazy {
        Reflections("org.codeblessing.senegal")
    }

    fun <T : Annotation> getTypesAnnotatedWith(klass: KClass<T>): Set<Class<out Any>> {
        return reflections.getTypesAnnotatedWith(klass.java)
    }
}
