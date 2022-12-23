package ch.senegal.engine.process

import ch.senegal.engine.plugin.finder.PluginFinder
import ch.senegal.engine.plugin.finder.ServiceLoaderPluginFinder
import ch.senegal.engine.parameters.EnvironmentVariablesParameterSource
import ch.senegal.engine.parameters.ParameterSource
import ch.senegal.engine.parameters.PropertyParameterSource
import ch.senegal.engine.parameters.SystemPropertyParameterSource
import ch.senegal.engine.virtualfilesystem.PhysicalFilesVirtualFileSystem
import ch.senegal.engine.virtualfilesystem.VirtualFileSystem


fun main() {
    val virtualFileSystem: VirtualFileSystem = PhysicalFilesVirtualFileSystem()
    val pluginFinder: PluginFinder = ServiceLoaderPluginFinder
    val parameterSources: List<ParameterSource> = listOf(
        EnvironmentVariablesParameterSource,
        SystemPropertyParameterSource,
        PropertyParameterSource,
        )

    val process = SenegalProcess(
        pluginFinder = pluginFinder,
        virtualFileSystem = virtualFileSystem,
        parameterSources = parameterSources
    )

    process.runSenegalEngine()
}
