import { environment } from '../../environments/environment';
import { Configuration, ConfigurationParameters } from '../../generated-openapi';

export function OpenApiRestConfigurationFactory(): Configuration {
  const configurationParameters: ConfigurationParameters = {
    basePath: environment.serverBasePath,
  };
  return new Configuration(configurationParameters);
}
