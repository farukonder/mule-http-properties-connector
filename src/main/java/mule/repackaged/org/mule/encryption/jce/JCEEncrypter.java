// 
// Decompiled by Procyon v0.5.36
// 

package mule.repackaged.org.mule.encryption.jce;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import mule.repackaged.org.mule.encryption.Encrypter;
import mule.repackaged.org.mule.encryption.exception.MuleEncryptionException;
import mule.repackaged.org.mule.encryption.exception.MuleInvalidAlgorithmConfigurationException;
import mule.repackaged.org.mule.encryption.exception.MuleInvalidKeyException;
import mule.repackaged.org.mule.encryption.key.EncryptionKeyFactory;


public class JCEEncrypter implements Encrypter
{
    private static final String INSTALL_JCE_MESSAGE = " You need to install the Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files";
    private static final String ECB = "ECB";
    private final String provider;
    private final String transformation;
    private final EncryptionKeyFactory keyFactory;
    
    public JCEEncrypter(final String transformation, final EncryptionKeyFactory keyFactory) {
        this(transformation, null, keyFactory);
    }
    
    public JCEEncrypter(final String transformation, final String provider, final EncryptionKeyFactory keyFactory) {
        this.transformation = transformation;
        this.provider = provider;
        this.keyFactory = keyFactory;
    }
    
    @Override
    public byte[] decrypt(final byte[] content) throws MuleEncryptionException {
        return this.runCipher(content, this.keyFactory.decryptionKey(), 2);
    }
    
    @Override
    public byte[] encrypt(final byte[] content) throws MuleEncryptionException {
        return this.runCipher(content, this.keyFactory.encryptionKey(), 1);
    }
    
    protected void initCipher(final Cipher cipher, final Key cipherKey, final int mode) throws InvalidKeyException, InvalidAlgorithmParameterException {
        final String[] cipherParts = this.transformation.split("/");
        if (cipherParts.length >= 2 && "ECB".equals(cipherParts[1])) {
            cipher.init(mode, cipherKey);
        }
        else {
            final IvParameterSpec ips = new IvParameterSpec(Arrays.copyOfRange(cipherKey.getEncoded(), 0, cipher.getBlockSize()));
            cipher.init(mode, cipherKey, ips, new SecureRandom());
        }
    }
    
    private byte[] runCipher(final byte[] content, final Key key, final int mode) throws MuleEncryptionException {
        try {
            final Cipher cipher = this.getCipher();
            this.initCipher(cipher, key, mode);
            return cipher.doFinal(content);
        }
        catch (InvalidAlgorithmParameterException e) {
            throw this.invalidAlgorithmConfigurationException(String.format("Wrong configuration for algorithm '%s'", this.transformation), e);
        }
        catch (NoSuchAlgorithmException e2) {
            throw this.invalidAlgorithmConfigurationException(String.format("Cipher '%s' not found", this.transformation), e2);
        }
        catch (NoSuchPaddingException e3) {
            throw this.invalidAlgorithmConfigurationException(String.format("Invalid padding selected for cipher '%s'", this.transformation), e3);
        }
        catch (NoSuchProviderException e4) {
            throw this.invalidAlgorithmConfigurationException(String.format("Provider '%s' not found", this.provider), e4);
        }
        catch (InvalidKeyException e5) {
            throw this.handleInvalidKeyException(e5, new String(key.getEncoded()));
        }
        catch (Exception e6) {
            throw new MuleEncryptionException("Could not encrypt or decrypt the data.", e6);
        }
    }
    
    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return (this.provider == null) ? Cipher.getInstance(this.transformation) : Cipher.getInstance(this.transformation, this.provider);
    }
    
    private MuleEncryptionException invalidAlgorithmConfigurationException(String message, final Exception e) {
        if (!JCE.isJCEInstalled()) {
            message += " You need to install the Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files";
        }
        return new MuleInvalidAlgorithmConfigurationException(message, e);
    }
    
    private MuleEncryptionException handleInvalidKeyException(final InvalidKeyException e, final String key) {
        final String message = String.format("The key is invalid, please make sure it's of a supported size (actual is %s)", key.length());
        return new MuleInvalidKeyException(message, e);
    }
}
