package github.com.farukonder.mule4.connector.secureHttpProp;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.config.api.dsl.model.ConfigurationParameters;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProviderFactory;

import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionAlgorithm;
import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionMode;

public class SecureHttpPropertiesProviderFactory implements ConfigurationPropertiesProviderFactory {

	public static final String EXTENSION_NAMESPACE = "secure-http-properties";
	public static final String SECURE_CONFIGURATION_PROPERTIES_ELEMENT = "config";
	public static final ComponentIdentifier SECURE_CONFIGURATION_PROPERTIES = ComponentIdentifier.builder()
			.namespace(EXTENSION_NAMESPACE).name(SECURE_CONFIGURATION_PROPERTIES_ELEMENT).build();

	@Override
	public SecureHttpPropConfigurationPropertiesProvider createProvider(ConfigurationParameters parameters,
			ResourceProvider externalResourceProvider) {
		final String file = parameters.getStringParameter("httpPath");
		System.out.print("Secure Http Path for properties " + file);
		final String key = parameters.getStringParameter("key");
		final EncryptionAlgorithm algorithm = this.getAlgorithm(parameters);
		final EncryptionMode mode = this.getMode(parameters);

		return new SecureHttpPropConfigurationPropertiesProvider(externalResourceProvider, file, algorithm, key, mode);
	}

	public ComponentIdentifier getSupportedComponentIdentifier() {
		return SECURE_CONFIGURATION_PROPERTIES;
	}

	private EncryptionAlgorithm getAlgorithm(ConfigurationParameters parameters) {
		return EncryptionAlgorithm.valueOf(this.getProperty(parameters, "algorithm", EncryptionAlgorithm.Blowfish.name()));
	}

	private EncryptionMode getMode(final ConfigurationParameters parameters) {
		return EncryptionMode.valueOf(this.getProperty(parameters, "mode", EncryptionMode.CBC.name()));
	}

	private String getProperty(ConfigurationParameters parameters, final String property, final String defaultValue) {
		final String propertyValue = parameters.getStringParameter(property);
		return (propertyValue != null) ? propertyValue : defaultValue;
	}

}
