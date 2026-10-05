package com.epiis.apirfds220262.staticdata;

import java.text.Normalizer;

public enum EnumProcess {
	PENDING("Pendiente de revisión"),
	SEEN("Visto"),
	REFUSED("Rechazado"),
	COORDINATION("En coordinación"),
	CLOSE("Cerrado");
	
	private String value;
	
	EnumProcess(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}

	public static EnumProcess fromValue(String value) {
		if(value == null || value.isBlank()) {
			return null;
		}

		String normalized = normalize(value);

		for(EnumProcess process: EnumProcess.values()) {
			if(normalize(process.value).equals(normalized) || normalize(process.name()).equals(normalized)) {
				return process;
			}
		}

		return null;
	}

	private static String normalize(String value) {
		return Normalizer.normalize(value, Normalizer.Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			.trim()
			.toLowerCase();
	}
}