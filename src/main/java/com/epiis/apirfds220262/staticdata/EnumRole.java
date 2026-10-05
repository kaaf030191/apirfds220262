package com.epiis.apirfds220262.staticdata;

public enum EnumRole {
	ENCARGADO("Encargado", "ENCARGADO"),
	ESTUDIANTE("Estudiantes", "ESTUDIANTE");

	private String value;
	private String code;

	EnumRole(String value, String code) {
		this.value = value;
		this.code = code;
	}

	public String getValue() {
		return this.value;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public static EnumRole fromValue(String value) {
		for(EnumRole role: EnumRole.values()) {
			if(role.value.equalsIgnoreCase(value) || role.code.equalsIgnoreCase(value)) {
				return role;
			}
		}

		return null;
	}
}
