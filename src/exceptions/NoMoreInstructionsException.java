package exceptions;

public class NoMoreInstructionsException extends Exception {

	// thrown if player has no more instructions to execute
	
	public NoMoreInstructionsException(String errMsg){
		super(errMsg);
	}
	
	public NoMoreInstructionsException(){
		super();
	}

}
