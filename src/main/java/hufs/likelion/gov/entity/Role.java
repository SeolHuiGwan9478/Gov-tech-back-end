package hufs.likelion.gov.entity;

public enum Role {
	NationalQualification, PartTime, Manager, Protector;

	public static Role fromString(String roleStr) {
		try {
			return Role.valueOf(roleStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid role: " + roleStr);
		}
	}
}
