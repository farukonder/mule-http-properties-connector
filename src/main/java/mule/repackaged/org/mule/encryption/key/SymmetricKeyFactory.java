// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption.key;

import java.security.Key;



public interface SymmetricKeyFactory extends EncryptionKeyFactory
{
    default Key decryptionKey() {
        return this.encryptionKey();
    }
}
