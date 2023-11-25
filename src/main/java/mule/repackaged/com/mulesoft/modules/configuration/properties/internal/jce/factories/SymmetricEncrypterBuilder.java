// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.internal.jce.factories;

import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncrypterBuilder;
import mule.repackaged.com.mulesoft.modules.configuration.properties.api.EncryptionAlgorithm;
import mule.repackaged.com.mulesoft.modules.configuration.properties.internal.jce.EncryptionPadding;
import mule.repackaged.com.mulesoft.modules.configuration.properties.internal.keyfactories.SymmetricEncryptionKeyFactory;
import mule.repackaged.org.mule.encryption.Encrypter;
import mule.repackaged.org.mule.encryption.jce.JCEEncrypter;
import mule.repackaged.org.mule.encryption.key.EncryptionKeyFactory;

public class SymmetricEncrypterBuilder extends EncrypterBuilder
{
    private EncryptionAlgorithm encryptionAlgorithm;
    
    public SymmetricEncrypterBuilder(final EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
    
    @Override
    public Encrypter build() {
        return (Encrypter)new JCEEncrypter(this.encryptionAlgorithm.name() + "/" + this.mode.name() + "/" + EncryptionPadding.PKCS5Padding.name(), (String)null, (EncryptionKeyFactory)new SymmetricEncryptionKeyFactory(this.encryptionAlgorithm.name(), this.key));
    }
}
