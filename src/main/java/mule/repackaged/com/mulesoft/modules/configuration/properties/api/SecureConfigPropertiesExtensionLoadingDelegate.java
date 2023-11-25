// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.api;

import java.lang.reflect.Type;

import org.mule.metadata.api.ClassTypeLoader;
import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.MetadataFormat;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.OptionalParameterDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterGroupDeclarer;
import org.mule.runtime.api.meta.model.display.DisplayModel;
import org.mule.runtime.api.meta.model.display.PathModel;
import org.mule.runtime.extension.api.declaration.type.ExtensionsTypeLoaderFactory;
import org.mule.runtime.extension.api.loader.ExtensionLoadingContext;
import org.mule.runtime.extension.api.loader.ExtensionLoadingDelegate;

public class SecureConfigPropertiesExtensionLoadingDelegate implements ExtensionLoadingDelegate
{
    public static final String EXTENSION_NAME = "Secure Properties";
    public static final String VERSION = "1.2.0";
    
    public void accept(final ExtensionDeclarer extensionDeclarer, final ExtensionLoadingContext context) {
        final ConfigurationDeclarer configurationDeclarer = extensionDeclarer.named("Secure Properties").describedAs("Crafted Config Properties Extension").withCategory(Category.SELECT).onVersion("1.2.0").fromVendor("Mulesoft").withConfig("config");
        final ParameterGroupDeclarer defaultParameterGroup = configurationDeclarer.onDefaultParameterGroup();
        defaultParameterGroup.withRequiredParameter("file").ofType((MetadataType)BaseTypeBuilder.create(MetadataFormat.JAVA).stringType().build()).withExpressionSupport(ExpressionSupport.NOT_SUPPORTED).withDisplayModel(DisplayModel.builder().path(new PathModel(PathModel.Type.FILE, false, PathModel.Location.EMBEDDED, new String[] { "yaml", "properties" })).build()).describedAs(" The location of the file with the secure configuration properties to use. It may be a location in the classpath or an absolute location. \nThe file location value may also contains references to properties that will only be resolved based on system properties or properties set at deployment time.");
        defaultParameterGroup.withRequiredParameter("key").ofType((MetadataType)BaseTypeBuilder.create(MetadataFormat.JAVA).stringType().build());
        ((OptionalParameterDeclarer)defaultParameterGroup.withOptionalParameter("fileLevelEncryption").ofType((MetadataType)BaseTypeBuilder.create(MetadataFormat.JAVA).booleanType().build())).defaultingTo((Object)Boolean.FALSE);
        defaultParameterGroup.withOptionalParameter("encoding").ofType((MetadataType)BaseTypeBuilder.create(MetadataFormat.JAVA).booleanType().build());
        final ParameterGroupDeclarer parameterGroupDeclarer = configurationDeclarer.onParameterGroup("encrypt").withDslInlineRepresentation(true);
        final ClassTypeLoader typeLoader = ExtensionsTypeLoaderFactory.getDefault().createTypeLoader();
        ((OptionalParameterDeclarer)parameterGroupDeclarer.withOptionalParameter("algorithm").ofType(typeLoader.load((Type)EncryptionAlgorithm.class))).defaultingTo((Object)EncryptionAlgorithm.AES);
        ((OptionalParameterDeclarer)parameterGroupDeclarer.withOptionalParameter("mode").ofType(typeLoader.load((Type)EncryptionMode.class))).defaultingTo((Object)EncryptionMode.CBC);
    }
}
