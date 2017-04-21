package scrumtool;

public class HttpResponse {
	private int httpCode;
	private String httpResponseMessage;
	public HttpResponse(int HttpCode, String HttpResponseMessage){
		this.httpCode = HttpCode;
		this.httpResponseMessage = HttpResponseMessage;
	}
	public int getHttpCode() {
		return httpCode;
	}
	public String getHttpResponseMessage() {
		return httpResponseMessage;
	}
}
