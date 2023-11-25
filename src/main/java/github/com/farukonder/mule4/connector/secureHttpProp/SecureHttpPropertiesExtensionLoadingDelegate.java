package github.com.farukonder.mule4.connector.secureHttpProp;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.meta.Category.SELECT;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.runtime.api.meta.ExpressionSupport.SUPPORTED;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterGroupDeclarer;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.api.loader.ExtensionLoadingDelegate;

public class SecureHttpPropertiesExtensionLoadingDelegate implements ExtensionLoadingDelegate{

	public static final String EXTENSION_NAME = "Secure Http Properties";
    public static final String CONFIG_ELEMENT = "config";
    
	@Override
	public void accept(ExtensionDeclarer extensionDeclarer, ExtensionLoadingContext context) {
		 ConfigurationDeclarer configurationDeclarer = extensionDeclarer.named(EXTENSION_NAME)
			        .describedAs(String.format("%s Extension", EXTENSION_NAME))
			        .withCategory(SELECT)
			        .onVersion("1.0.0")
			        // TODO replace with you company name
			        .fromVendor("farukonder.github.io")
			        // This defines a global element in the extension with name config
			        .withConfig(CONFIG_ELEMENT);
		 
		  ParameterGroupDeclarer defaultParameterGroup = configurationDeclarer.onDefaultParameterGroup();
		    
		  // TODO you can add/remove configuration parameter using the code below.
		    defaultParameterGroup
	        .withRequiredParameter("httpPath").ofType(BaseTypeBuilder.create(JAVA).stringType().build())
	        .withExpressionSupport(SUPPORTED)
	        .describedAs("Used for reading properties file from the url");
		    
		    defaultParameterGroup
	        .withOptionalParameter("algorithm").ofType(BaseTypeBuilder.create(JAVA).stringType().build())
	        .withExpressionSupport(NOT_SUPPORTED)
	        .describedAs("Used for decrypt algorithm");
		    
		    defaultParameterGroup
	        .withOptionalParameter("key").ofType(BaseTypeBuilder.create(JAVA).stringType().build())
	        .withExpressionSupport(NOT_SUPPORTED)
	        .describedAs("Used for decrypt algorithm key");
	}

}
