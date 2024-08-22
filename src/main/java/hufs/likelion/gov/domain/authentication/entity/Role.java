package hufs.likelion.gov.domain.authentication.entity;

import hufs.likelion.gov.domain.authentication.exception.MemberException;

public enum Role {
	NationalQualification, PartTime, Manager, Protector;

	public static void fromString(String roleStr) {
		try {
			Role.valueOf(roleStr);
		} catch (IllegalArgumentException e) {
			throw new MemberException("Invalid role: " + roleStr);
		}
	}
}
