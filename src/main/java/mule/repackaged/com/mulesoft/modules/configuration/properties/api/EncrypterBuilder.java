// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.com.mulesoft.modules.configuration.properties.api;

import mule.repackaged.org.mule.encryption.Encrypter;

public abstract class EncrypterBuilder
{
    protected EncryptionMode mode;
    protected String key;
    
    public EncrypterBuilder using(final EncryptionMode mode) {
        this.mode = mode;
        return this;
    }
    
    public abstract Encrypter build();
    
    public EncrypterBuilder forKey(final String key) {
        this.key = key;
        return this;
    }
}
