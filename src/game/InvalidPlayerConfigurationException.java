package game;

public class InvalidPlayerConfigurationException extends Exception {

	// thrown is program file is in an invalid format or erroneous
	public InvalidPlayerConfigurationException(String errMsg) {
		super(errMsg);

	}

}
