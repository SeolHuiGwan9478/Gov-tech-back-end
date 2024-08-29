package hufs.likelion.gov.global.constant;

import hufs.likelion.gov.domain.authentication.entity.Role;

public class RoleValidator {

	public static void validateSuperUserRole(Role role) {
		if(role != Role.SUPERADMIN) {
			throw new IllegalArgumentException(GlobalConstant.AUTHENTICATION_SUPER_ADMIN_ERR_MSG);
		}
	}

	public static void validateManagerRole(Role role) {
		if(role != Role.MANAGER) {
			throw new IllegalArgumentException(GlobalConstant.AUTHENTICATION_MANAGER_ERR_MSG);
		}
	}

	public static void validateProtectorRole(Role role) {
		if(role != Role.PROTECTOR) {
			throw new IllegalArgumentException(GlobalConstant.AUTHENTICATION_PROTECTOR_ERR_MSG);
		}
	}

	public static void validateCaretakerRole(Role role) {
		if(role != Role.CARETAKER) {
			throw new IllegalArgumentException(GlobalConstant.AUTHENTICATION_CARETAKER_ERR_MSG);
		}
	}

	public static void validateSuperOrManagerRole(Role role) {
		if(role != Role.SUPERADMIN && role != Role.MANAGER) {
			throw new IllegalArgumentException(GlobalConstant.REGISTER_AUTH_ERR_MSG);
		}
	}

}
