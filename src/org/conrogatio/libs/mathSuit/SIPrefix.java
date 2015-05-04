package org.conrogatio.libs.mathSuit;

public enum SIPrefix {
	YOTTA(24, "yotta", "Y", "septillion"),
	ZETA(21, "zetta", "Z", "sextillion"),
	EXA(18, "exa", "E", "quintillion"),
	PETA(15, "peta", "P", "quadrillion"),
	TERA(12, "tera", "T", "trillion"),
	GIGA(9, "giga", "G", "billion"),
	MEGA(6, "mega", "M", "million"),
	KILO(3, "kilo", "K", "thousand"),
	HECTO(2, "hecto", "H", "hundred"),
	DECA(1, "deca", "D", "ten"),
	IDENT(0, "", "", ""),
	DECI(-1, "deci", "d", "tenth"),
	CENTI(-2, "centi", "c", "hundredth"),
	MILLI(-3, "milli", "m", "thounsandth"),
	MICRO(-6, "micro", "μ", "millionth"),
	NANO(-9, "nano", "n", "billionth"),
	PICO(-12, "pico", "p", "trillionth"),
	FEMTO(-15, "femto", "f", "quadrillionth"),
	ATTO(-18, "atto", "a", "quintillionth"),
	ZEPTO(-21, "zepto", "z", "sextillionth"),
	YOCTO(-24, "yocto", "y", "septiollionth");
	final private int exponent;
	final private String prefix;
	final private String symbol;
	final private String word;
	
	private SIPrefix(final int exponent, final String prefix, final String symbol, final String word) {
		this.exponent = exponent;
		this.prefix = prefix;
		this.symbol = symbol;
		this.word = word;
	}
	
	int getExponent() {
		return this.exponent;
	}
	
	String getPrefix() {
		return this.prefix;
	}
	
	String getSymbol() {
		return this.symbol;
	}
	
	String getWord() {
		return this.word;
	}
	
	SIPrefix prefixForValue(int value) {
		int exponents[] = { 24, 21, 18, 15, 12, 9, 6, 3, 2, 1, 0, -1, -2, -3, -6, -9, -12, -15, -18, -21, -24 };
		for (int exponent : exponents)
			if (Math.pow(10, exponent) < value)
				break;
		return prefixForExponent(exponent);
	}
	
	SIPrefix prefixForExponent(int exponent) {
		if (exponent > 24)
			return YOTTA;
		else if (exponent > 21)
			return ZETA;
		else if (exponent > 18)
			return EXA;
		else if (exponent > 15)
			return PETA;
		else if (exponent > 12)
			return TERA;
		else if (exponent > 9)
			return GIGA;
		else if (exponent > 6)
			return MEGA;
		else if (exponent > 3)
			return KILO;
		else if (exponent > 2)
			return HECTO;
		else if (exponent > 1)
			return DECA;
		else if (exponent > 0)
			return IDENT;
		else if (exponent > -1)
			return DECI;
		else if (exponent > -2)
			return CENTI;
		else if (exponent > -3)
			return MILLI;
		else if (exponent > -6)
			return MICRO;
		else if (exponent > -9)
			return NANO;
		else if (exponent > -12)
			return PICO;
		else if (exponent > -15)
			return FEMTO;
		else if (exponent > -18)
			return ATTO;
		else if (exponent > -21)
			return ZEPTO;
		else
			return YOCTO;
	}
}