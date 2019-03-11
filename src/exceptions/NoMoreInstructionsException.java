package exceptions;

public class NoMoreInstructionsException extends Exception {

	public NoMoreInstructionsException(String errMsg){
		super(errMsg);
	}
	
	public NoMoreInstructionsException(){
		super();
	}

}
