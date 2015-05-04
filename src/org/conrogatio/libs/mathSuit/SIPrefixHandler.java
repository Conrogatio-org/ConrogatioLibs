package org.conrogatio.libs.mathSuit;

import java.util.HashMap;
import java.util.Map;

public class SIPrefixHandler {
	private Map<Integer, String> prefixes;
	
	private void initializePrefixes() {
		prefixes = new HashMap<Integer, String>();
		prefixes.put(-24, "Yokto");
		prefixes.put(-21, "Zepto");
		prefixes.put(-18, "Att");
		prefixes.put(-15, "Femto");
		prefixes.put(-12, "Piko");
		prefixes.put(-9, "Nano");
		prefixes.put(-6, "Mikro");
		prefixes.put(-3, "Milli");
		prefixes.put(0, "");
		prefixes.put(3, "Kilo");
		prefixes.put(6, "Mega");
		prefixes.put(9, "Giga");
		prefixes.put(12, "Tera");
		prefixes.put(15, "Peta");
		prefixes.put(18, "Exa");
		prefixes.put(21, "Zetta");
		prefixes.put(24, "Yotta");
	}
	
	public SIPrefixHandler() {
		initializePrefixes();
	}
	
	public String getPrefix(int exponent) {
		return prefixes.get(exponent);
	}
}
