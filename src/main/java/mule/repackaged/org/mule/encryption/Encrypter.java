// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption;

import mule.repackaged.org.mule.encryption.exception.MuleEncryptionException;

public interface Encrypter
{
    byte[] decrypt(final byte[] p0) throws MuleEncryptionException;
    
    byte[] encrypt(final byte[] p0) throws MuleEncryptionException;
}
