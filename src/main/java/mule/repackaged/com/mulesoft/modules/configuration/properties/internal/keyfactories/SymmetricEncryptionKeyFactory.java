// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.internal.keyfactories;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import mule.repackaged.org.mule.encryption.key.SymmetricKeyFactory;


public class SymmetricEncryptionKeyFactory implements SymmetricKeyFactory
{
    private String algorithm;
    private String key;
    
    public SymmetricEncryptionKeyFactory(final String algorithm, final String key) {
        this.algorithm = algorithm;
        this.key = key;
        this.validateKey();
    }
    
    public Key encryptionKey() {
        final byte[] bytes = this.key.getBytes();
        return new SecretKeySpec(bytes, this.algorithm);
    }
    
    private void validateKey() {
        if (this.key == null) {
            throw new IllegalArgumentException("If keystore is not defined then the key is considered to be an encryption key in Base64 encoding");
        }
    }
}
