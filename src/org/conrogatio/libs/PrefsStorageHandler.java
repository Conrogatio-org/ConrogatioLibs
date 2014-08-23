package org.conrogatio.libs;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsStorageHandler {
	// Settings and helpers
	private String location;
	private Context c;
	private SharedPreferences prefs;

	/**
	 * This will initialize an instance of the SecurePreferences class
	 * 
	 * @param storageLocation
	 *            the location of where the preferences should be stored.
	 *            Standard is com.example.appname.prefs
	 * @param context
	 *            your current context.
	 */
	public PrefsStorageHandler(String storageLocation, Context context) {
		location = storageLocation;
		c = context;
		update();
	}

	public void clear() {
		prefs.edit().clear().commit();
	}

	// Check
	public boolean containsKey(String key) {
		return prefs.contains(key);
	}

	// Fetch
	public boolean fetch(String key, boolean defValue) {
		return prefs.getBoolean(key, defValue);
	}

	public float fetch(String key, float defValue) {
		return prefs.getFloat(key, defValue);
	}

	public int fetch(String key, int defValue) {
		return prefs.getInt(key, defValue);
	}

	public String fetch(String key, String defValue) {
		return prefs.getString(key, defValue);
	}

	public Set<String> fetch(String key, Set<String> defValue) {
		return prefs.getStringSet(key, defValue);
	}

	// Put
	public void put(String key, boolean value) {
		prefs.edit().putBoolean(key, value).commit();
	}

	public void put(String key, float value) {
		prefs.edit().putFloat(key, value).commit();
	}

	public void put(String key, int value) {
		prefs.edit().putInt(key, value).commit();
	}

	public void put(String key, String value) {
		prefs.edit().putString(key, value).commit();
	}

	public void put(String key, Set<String> value) {
		prefs.edit().putStringSet(key, value).commit();
	}

	// Remove
	public void remove(String key) {
		prefs.edit().remove(key).commit();
	}

	// Update local
	public void update() {
		prefs = c.getSharedPreferences(location, c.MODE_PRIVATE);
	}
}