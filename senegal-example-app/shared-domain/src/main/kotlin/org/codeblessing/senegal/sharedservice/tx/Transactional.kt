package org.codeblessing.senegal.sharedservice.tx

import java.lang.annotation.Inherited
import org.springframework.transaction.annotation.Transactional as SpringTransactional

/**
 * By default, the Spring Framework's transaction handling only marks a transaction
 * for rollback if it encounters an unchecked exception (i.e. RuntimeException and
 * Subclasses thereof). In case of a checked exception (e.g. IOException), the
 * transaction will **not** be rolled back. To enforce rollbacks for all exceptions,
 * the attribute `rollbackFor` must be set.
 *
 * Since Kotlin doesn't distinguish between checked and unchecked exceptions the
 * default behaviour is not sensible for most of our use cases. Hence this custom
 * annotation to trigger a rollback for all exceptions.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Inherited
@SpringTransactional(
    rollbackFor = [Throwable::class]
)
annotation class Transactional
