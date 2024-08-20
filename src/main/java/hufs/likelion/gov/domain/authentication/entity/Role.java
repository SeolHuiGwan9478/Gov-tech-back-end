package hufs.likelion.gov.domain.authentication.entity;

import hufs.likelion.gov.domain.authentication.exception.MemberException;

public enum Role {
	NationalQualification, PartTime, Manager, Protector;

	public static Role fromString(String roleStr) {
		try {
			return Role.valueOf(roleStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MemberException("Invalid role: " + roleStr);
		}
	}
}
