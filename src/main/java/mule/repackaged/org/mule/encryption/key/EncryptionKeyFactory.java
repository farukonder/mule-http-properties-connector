// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption.key;

import java.security.Key;

public interface EncryptionKeyFactory
{
    Key encryptionKey();
    
    Key decryptionKey();
}
