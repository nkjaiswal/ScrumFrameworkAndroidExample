package scrumtool;

@SuppressWarnings("serial")
public class ScrumToolException extends Exception{
	private String message;
	public ScrumToolException(String message){
		this.message = message;
	}
	@Override
	public String getMessage() {
		return "ScrumToolException: "+ message;
	}
	@Override
	public String toString() {
		return "ScrumToolException: "+ message;
	}
	
}
