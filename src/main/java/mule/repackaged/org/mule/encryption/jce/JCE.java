// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption.jce;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

public class JCE
{
    public static boolean isJCEInstalled() {
        try {
            final int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
            return maxKeyLen > 256;
        }
        catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
