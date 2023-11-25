// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.internal.keyfactories;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.mule.runtime.core.api.util.Base64;

import mule.repackaged.org.mule.encryption.key.EncryptionKeyFactory;

public class AsymmetricEncryptionKeyFactory implements EncryptionKeyFactory
{
    private String algorithm;
    private String key;
    
    public AsymmetricEncryptionKeyFactory(final String algorithm, final String key) {
        this.algorithm = algorithm;
        this.key = key;
        this.validateKey();
    }
    
    public Key encryptionKey() {
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(this.key));
        try {
            final KeyFactory kf = KeyFactory.getInstance(this.algorithm);
            return kf.generatePrivate(spec);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not build the Encryption key", e);
        }
    }
    
    public Key decryptionKey() {
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(this.key));
        try {
            final KeyFactory kf = KeyFactory.getInstance(this.algorithm);
            return kf.generatePublic(spec);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not build the descryption key", e);
        }
    }
    
    private void validateKey() {
        if (this.key == null) {
            throw new IllegalArgumentException("If keystore is not defined then the key is considered to be an encryption key in Base64 encoding");
        }
    }
}
