package hufs.likelion.gov.domain.authentication.entity;

import hufs.likelion.gov.domain.authentication.exception.MemberException;

public enum Role {
	CARETAKER, MANAGER, PROTECTOR, SUPERADMIN;

	public static void fromString(String roleStr) {
		try {
			Role.valueOf(roleStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MemberException("Invalid role: " + roleStr);
		}
	}
}
