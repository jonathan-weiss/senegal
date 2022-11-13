package ch.senegal.example.openapi

import ch.senegal.example.frontendapi.FrontendApi
import ch.senegal.example.frontendapi.facade.FrontendApiFacade
import org.mockito.Mockito
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication(scanBasePackageClasses = [FrontendApi::class])
class OpenApiGeneratorApplication

val logger: Logger = LoggerFactory.getLogger(OpenApiGeneratorApplication::class.java)

fun main(args: Array<String>) {
    val context = runApplication<OpenApiGeneratorApplication>(*args)
    val registry = context.autowireCapableBeanFactory as BeanDefinitionRegistry
    mockFacades(context, registry)
}

private fun mockFacades(
    context: ConfigurableApplicationContext,
    registry: BeanDefinitionRegistry
) {

    for (name in context.beanFactory.beanDefinitionNames) {
        val beanDefinition = context.beanFactory.getBeanDefinition(name)
        beanDefinition.beanClassName?.let { className ->
            if (requiresMocking(className)) {
                mock(className, registry, name, context.beanFactory)
            }
        }
    }
}

private fun requiresMocking(className: String): Boolean {
    val facadePackageName = FrontendApiFacade::class.java.`package`.name
    return className.startsWith(facadePackageName)
}

private fun mock(
    className: String,
    registry: BeanDefinitionRegistry,
    name: String,
    beanFactory: ConfigurableListableBeanFactory
) {
    logger.info("Mocking: $className")
    val clazz = Class.forName(className)
    registry.removeBeanDefinition(name)
    beanFactory.registerSingleton(name, Mockito.mock(clazz))
}
