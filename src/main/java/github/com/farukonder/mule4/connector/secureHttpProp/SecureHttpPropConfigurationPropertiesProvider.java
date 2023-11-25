package github.com.farukonder.mule4.connector.secureHttpProp;

import static java.lang.String.format;
import static java.util.Optional.of;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationProperty;
import org.mule.runtime.config.api.dsl.model.properties.DefaultConfigurationPropertiesProvider;
import org.mule.runtime.core.api.util.Base64;

import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionAlgorithm;
import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionMode;

public class SecureHttpPropConfigurationPropertiesProvider extends DefaultConfigurationPropertiesProvider {

	protected static final String PROPERTIES_EXTENSION = ".properties";
	protected static final String UNKNOWN = "unknown";

	protected final Map<String, ConfigurationProperty> configurationAttributes = new HashMap<>();
	protected String fileLocation;
	protected ResourceProvider resourceProvider;
	
	private EncryptionAlgorithm encryptionAlgorithm;
	private EncryptionMode encryptionMode;
	private String key;

	public SecureHttpPropConfigurationPropertiesProvider(ResourceProvider resourceProvider,String fileLocation,final EncryptionAlgorithm algorithm, final String key, final EncryptionMode mode) {
		super(fileLocation, resourceProvider);
		this.fileLocation = fileLocation;
		this.resourceProvider = resourceProvider;
		this.encryptionAlgorithm = algorithm;
		this.encryptionMode = mode;
        this.key = key;
	}


	@Override
	public Optional<ConfigurationProperty> getConfigurationProperty(String configurationAttributeKey) {
		return Optional.ofNullable(configurationAttributes.get(configurationAttributeKey));
	}

	@Override
	public void initialise() throws InitialisationException {
		if (!fileLocation.contains(PROPERTIES_EXTENSION)) {
			throw new RuntimeException("Configuration properties file must end with properties extension");
		}
		try (InputStream is = getResourceInputStream()) {
			if (is == null) {
				throw new RuntimeException("Couldn't find configuration properties file neither on classpath or in file system");
			}
			readAttributesFromFile(is);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public String getDescription() {
		ComponentLocation location = (ComponentLocation) getAnnotation(LOCATION_KEY);
		return format("<custom-configuration-properties file=\"%s\"> - file: %s, line number: %s", fileLocation, location.getFileName().orElse(UNKNOWN), location.getLineInFile().map(String::valueOf).orElse("unknown"));

	}

	private InputStream getResourceInputStream() throws IOException {
		if (fileLocation.contains("http")) { // this covers https as well
			return new URL(fileLocation).openStream();
		} else {
			return isAbsolutePath(fileLocation) ? new FileInputStream(fileLocation)
					: resourceProvider.getResourceAsStream(fileLocation);
		}
	}

	private boolean isAbsolutePath(String file) {
		return new File(file).isAbsolute();
	}



	protected void readAttributesFromFile(InputStream is) throws IOException {
		if (fileLocation.contains(PROPERTIES_EXTENSION)) {
			Properties properties = new Properties();
			properties.load(is);
			properties.keySet().stream().map(key -> {
				Object rawValue = properties.get(key);
				final Object rawValueConverted = createValue((String) key, (String) rawValue);
				return new ConfigurationProperty() {

                    @Override
                    public Object getSource() {
//                    	return of(this);
                    	return "decrypted value";
                    }

                    @Override
                    public Object getRawValue() {
                        return rawValueConverted;
                    }

                    @Override
                    public String getKey() {
                        return (String) key;
                    }
                };
			}).forEach(configurationAttribute -> {
				configurationAttributes.put(configurationAttribute.getKey(), configurationAttribute);
			});
		} 
	}

	protected String createKey(String parentKey, String key) {
		if (parentKey == null) {
			return key;
		}
		return parentKey + "." + key;
	}

	protected String createValue(String key, String value) {
		if (value != null && value.startsWith("![")) {
			System.out.println("decrypting value for key: " + key);
			return this.convertPropertyValue(value);
		}
		return value;
	}
	


	public EncryptionAlgorithm getEncryptionAlgorithm() {
		return this.encryptionAlgorithm;
	}

	public String convertPropertyValue(final String originalValue) {
		if (!originalValue.startsWith("![") || !originalValue.endsWith("]")) {
			return originalValue;
		}
		final String strippedValue = originalValue.substring(2, originalValue.length() - 1);
		try {
			return new String(this.decrypt(Base64.decode(strippedValue)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException((Throwable) e);
		}
	}

	public byte[] decrypt(final byte[] payload) throws Exception {
		return this.encryptionAlgorithm.getBuilder().using(this.encryptionMode).forKey(this.key).build().decrypt(payload);

	}

}
