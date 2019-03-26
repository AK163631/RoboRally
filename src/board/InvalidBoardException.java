package board;

public class InvalidBoardException extends Exception {

	// thrown is board file is in an invalid format or erroneous
	public InvalidBoardException(String errMsg) {
		super(errMsg);

	}
}
