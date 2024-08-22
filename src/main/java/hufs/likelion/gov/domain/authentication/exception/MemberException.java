package hufs.likelion.gov.domain.authentication.exception;

public class MemberException extends RuntimeException {

	public MemberException(String message) {
		super(message);
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
	}
}
