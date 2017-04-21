package scrumtool;

@SuppressWarnings("serial")
public class RestHandlerNotInitializedException extends Exception {

	@Override
	public String getMessage() {
		return "RestHandlerNotInitializedException: You must first initialize the ScrumTool class by calling getInstance(IRestHandler) method";
	}

	@Override
	public String toString() {
		return "RestHandlerNotInitializedException: You must first initialize the ScrumTool class by calling getInstance(IRestHandler) method";
	}

}
