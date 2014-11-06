package org.conrogatio.libs;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Base64;

/*
 Copyright (C) 2012 Sveinung Kval Bakken, sveinung.bakken@gmail.com

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */
public class SecurePrefsStorageHandler {
	public static class SecurePreferencesException extends RuntimeException {
		private static final long serialVersionUID = 3051912281127821578L;

		public SecurePreferencesException(Throwable e) {
			super(e);
		}
	}

	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
	private static final String CHARSET = "UTF-8";
	private static final String STRING_TRUE = "true";
	private static final String STRING_FALSE = "false";
	private static String deviceId;

	private static byte[] convert(Cipher cipher, byte[] bs)
			throws SecurePreferencesException {
		try {
			return cipher.doFinal(bs);
		} catch (Exception e) {
			throw new SecurePreferencesException(e);
		}
	}

	// Settings and helpers
	private String location;
	private Context c;
	private SharedPreferences prefs;
	private Cipher writer = null;
	private Cipher reader = null;
	private Cipher keyWriter = null;

	/**
	 * This will initialize an instance of the SecurePreferences class
	 * 
	 * @param storageLocation
	 *            the location of where the preferences should be stored.
	 *            Standard is com.example.appname.prefs
	 * @param salt
	 *            any String to use for encrypting the storage
	 * @param context
	 *            your current context.
	 */
	public SecurePrefsStorageHandler(String storageLocation, String salt,
			Context context) throws SecurePreferencesException {
		c = context;
		deviceId = Secure.getString(c.getContentResolver(), Secure.ANDROID_ID);
		location = storageLocation + deviceId;
		try {
			this.writer = Cipher.getInstance(TRANSFORMATION);
			this.reader = Cipher.getInstance(TRANSFORMATION);
			this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);
			initCiphers(deviceId + salt);
		} catch (GeneralSecurityException e) {
			throw new SecurePreferencesException(e);
		} catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
		update();
	}

	public void clear() {
		prefs.edit().clear().commit();
	}

	// Check
	public boolean containsKey(String key) {
		return prefs.contains(toKey(key));
	}

	// Fetch
	public boolean fetch(String key, boolean defValue)
			throws SecurePreferencesException {
		key = toKey(key);
		if (prefs.contains(key)) {
			String stringBoolean = decrypt(prefs.getString(key, ""));
			if (stringBoolean.equals(STRING_TRUE)) {
				return true;
			} else if (stringBoolean.equals(STRING_FALSE)) {
				return false;
			}
		}
		return defValue;
	}

	public float fetch(String key, float defValue)
			throws SecurePreferencesException {
		key = toKey(key);
		if (prefs.contains(key)) {
			return Float.parseFloat(decrypt(prefs.getString(key, "")));
		}
		return defValue;
	}

	public int fetch(String key, int defValue)
			throws SecurePreferencesException {
		key = toKey(key);
		if (prefs.contains(key)) {
			return Integer.parseInt(decrypt(prefs.getString(key, "")));
		}
		return defValue;
	}

	public String fetch(String key, String defValue)
			throws SecurePreferencesException {
		key = toKey(key);
		if (prefs.contains(key)) {
			return decrypt(prefs.getString(key, ""));
		}
		return defValue;
	}

	// Put
	public void put(String key, boolean value) {
		prefs.edit().putString(toKey(key), toValue(Boolean.toString(value)))
				.commit();
	}

	public void put(String key, float value) {
		prefs.edit().putString(toKey(key), toValue(Float.toString(value)))
				.commit();
	}

	public void put(String key, int value) {
		prefs.edit().putString(toKey(key), toValue(Integer.toString(value)))
				.commit();
	}

	public void put(String key, String value) {
		prefs.edit().putString(toKey(key), toValue(value)).commit();
	}

	// Remove
	public void remove(String key) {
		prefs.edit().remove(toKey(key)).commit();
	}

	// Update local
	public void update() {
		prefs = c.getSharedPreferences(location, Context.MODE_PRIVATE);
	}

	private String toKey(String key) throws SecurePreferencesException {
		return encrypt(key + deviceId, keyWriter);
	}

	private String toValue(String value) throws SecurePreferencesException {
		return encrypt(value, writer);
	}

	protected byte[] createKeyBytes(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest
				.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		md.reset();
		byte[] keyBytes = md.digest(key.getBytes(CHARSET));
		return keyBytes;
	}

	protected String decrypt(String securedEncodedValue) {
		byte[] securedValue = Base64
				.decode(securedEncodedValue, Base64.NO_WRAP);
		byte[] value = convert(reader, securedValue);
		try {
			return new String(value, CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
	}

	protected String encrypt(String value, Cipher writer)
			throws SecurePreferencesException {
		byte[] secureValue;
		try {
			secureValue = convert(writer, value.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
		String secureValueEncoded = Base64.encodeToString(secureValue,
				Base64.NO_WRAP);
		return secureValueEncoded;
	}

	protected IvParameterSpec getIv() {
		byte[] iv = new byte[writer.getBlockSize()];
		System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0,
				iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

	protected SecretKeySpec getSecretKey(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] keyBytes = createKeyBytes(key);
		return new SecretKeySpec(keyBytes, TRANSFORMATION);
	}

	// Encryption
	protected void initCiphers(String secureKey)
			throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		IvParameterSpec ivSpec = getIv();
		SecretKeySpec secretKey = getSecretKey(secureKey);
		writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
	}
}