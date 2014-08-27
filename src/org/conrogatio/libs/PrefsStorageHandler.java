package org.conrogatio.libs;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsStorageHandler {
	// Settings and helpers
	private String location;
	private Context c;
	private SharedPreferences prefs;
	private boolean edited = false;

	/**
	 * This will initialize an instance of the SecurePreferences class
	 * 
	 * @param storageLocation
	 *            The location of where the preferences should be stored.
	 *            Standard is com.example.appname.prefs
	 * @param context
	 *            your current context.
	 */
	public PrefsStorageHandler(String storageLocation, Context context) {
		location = storageLocation;
		c = context;
		update();
	}

	/**
	 * Removing all saved data created with this object
	 */
	public void clear() {
		prefs.edit().clear().commit();
		edited = true;
	}

	// Check
	/**
	 * 
	 * @param key
	 *            The key to look for
	 * @return Returns true if the key exists
	 */
	public boolean containsKey(String key) {
		return prefs.contains(key);
	}

	// Fetch
	/**
	 * Used for getting a boolean from the storage
	 * 
	 * @param key
	 *            The key/name to look for
	 * @param defValue
	 *            The value to return if the key is not found. Must be of type
	 *            boolean.
	 * @return The boolean looked for or your defValue if not found
	 */
	public boolean fetch(String key, boolean defValue) {
		if (edited) {
			update();
		}
		return prefs.getBoolean(key, defValue);
	}

	/**
	 * Used for getting a float from the storage
	 * 
	 * @param key
	 *            The key/name to look for
	 * @param defValue
	 *            The value to return if the key is not found. Must be of type
	 *            float.
	 * @return The float looked for or your defValue if not found
	 */
	public float fetch(String key, float defValue) {
		if (edited) {
			update();
		}
		return prefs.getFloat(key, defValue);
	}

	/**
	 * Used for getting a int from the storage
	 * 
	 * @param key
	 *            The key/name to look for
	 * @param defValue
	 *            The value to return if the key is not found. Must be of type
	 *            int.
	 * @return The int looked for or your defValue if not found
	 */
	public int fetch(String key, int defValue) {
		if (edited) {
			update();
		}
		return prefs.getInt(key, defValue);
	}

	/**
	 * Used for getting a String from the storage
	 * 
	 * @param key
	 *            The key/name to look for
	 * @param defValue
	 *            The value to return if the key is not found. Must be of type
	 *            String.
	 * @return The String looked for or your defValue if not found
	 */
	public String fetch(String key, String defValue) {
		if (edited) {
			update();
		}
		return prefs.getString(key, defValue);
	}

	/**
	 * Used for getting a String Set from the storage
	 * 
	 * @param key
	 *            The key/name to look for
	 * @param defValue
	 *            The value to return if the key is not found. Must be of type
	 *            String Set.
	 * @return The String Set looked for or your defValue if not found
	 */
	public Set<String> fetch(String key, Set<String> defValue) {
		if (edited) {
			update();
		}
		return prefs.getStringSet(key, defValue);
	}

	// Put
	/**
	 * Used for storing a boolean value
	 * 
	 * @param key
	 *            What key/name to store the boolean under
	 * @param value
	 *            The boolean value to store
	 */
	public void put(String key, boolean value) {
		prefs.edit().putBoolean(key, value).commit();
		edited = true;
	}

	/**
	 * Used for storing a float value
	 * 
	 * @param key
	 *            What key/name to store the float under
	 * @param value
	 *            The float value to store
	 */
	public void put(String key, float value) {
		prefs.edit().putFloat(key, value).commit();
		edited = true;
	}

	/**
	 * Used for storing a int value
	 * 
	 * @param key
	 *            What key/name to store the int under
	 * @param value
	 *            The int value to store
	 */
	public void put(String key, int value) {
		prefs.edit().putInt(key, value).commit();
		edited = true;
	}

	/**
	 * Used for storing a String value
	 * 
	 * @param key
	 *            What key/name to store the String under
	 * @param value
	 *            The String value to store
	 */
	public void put(String key, String value) {
		prefs.edit().putString(key, value).commit();
		edited = true;
	}

	/**
	 * Used for storing a String Set
	 * 
	 * @param key
	 *            What key/name to store the String Set under
	 * @param value
	 *            The String Set to store
	 */
	public void put(String key, Set<String> value) {
		prefs.edit().putStringSet(key, value).commit();
		edited = true;
	}

	// Remove
	/**
	 * Used for removing one variable from the storage
	 * 
	 * @param key
	 *            The key/name of the variable to remove
	 */
	public void remove(String key) {
		prefs.edit().remove(key).commit();
		edited = true;
	}

	// Update local
	private void update() {
		prefs = c.getSharedPreferences(location, c.MODE_PRIVATE);
		edited = false;
	}
}