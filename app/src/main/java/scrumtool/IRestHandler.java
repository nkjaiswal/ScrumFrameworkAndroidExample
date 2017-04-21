package scrumtool;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface IRestHandler {
	public String GET(String url, String header) throws ProtocolException, MalformedURLException, IOException, Exception;
	public String POST(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception;
	public String PUT(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception;
	public void DELETE(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception;
}
