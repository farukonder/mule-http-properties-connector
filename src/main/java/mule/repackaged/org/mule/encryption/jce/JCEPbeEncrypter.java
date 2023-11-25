// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption.jce;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

import mule.repackaged.org.mule.encryption.key.EncryptionKeyFactory;



public class JCEPbeEncrypter extends JCEEncrypter
{
    public JCEPbeEncrypter(final String transformation, final EncryptionKeyFactory keyFactory) {
        this(transformation, null, keyFactory);
    }
    
    public JCEPbeEncrypter(final String transformation, final String provider, final EncryptionKeyFactory keyFactory) {
        super(transformation, provider, keyFactory);
    }
    
    @Override
    protected void initCipher(final Cipher cipher, final Key cipherKey, final int mode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        final IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(cipherKey.getEncoded(), 0, cipher.getBlockSize()));
        cipher.init(mode, cipherKey, new PBEParameterSpec("12345678".getBytes(), 20, ivParam));
    }
}
