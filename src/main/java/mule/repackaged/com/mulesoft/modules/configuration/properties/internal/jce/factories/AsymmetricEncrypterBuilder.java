// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.internal.jce.factories;



import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncrypterBuilder;
import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionAlgorithm;
import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionMode;
import mule.repackaged.com.mulesoft.modules.configuration.properties.internal.jce.EncryptionPadding;
import mule.repackaged.com.mulesoft.modules.configuration.properties.internal.keyfactories.AsymmetricEncryptionKeyFactory;
import mule.repackaged.org.mule.encryption.Encrypter;
import mule.repackaged.org.mule.encryption.jce.JCEEncrypter;
import mule.repackaged.org.mule.encryption.key.EncryptionKeyFactory;

public class AsymmetricEncrypterBuilder extends EncrypterBuilder
{
    @Override
    public Encrypter build() {
        return (Encrypter)new JCEEncrypter(EncryptionAlgorithm.RSA.name() + "/" + EncryptionMode.ECB.name() + "/" + EncryptionPadding.PKCS1PADDING.name(), (String)null, (EncryptionKeyFactory)new AsymmetricEncryptionKeyFactory(EncryptionAlgorithm.RSA.name(), this.key));
    }
}
