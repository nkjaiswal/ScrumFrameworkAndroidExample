package Rest;
import scrumtool.IRestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestHandler implements IRestHandler {

    @Override
    public String GET(String url, String header) throws ProtocolException, MalformedURLException, IOException, Exception{
        URL getUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (header != "") {
            conn.setRequestProperty("Authorization", header);
        }
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "", temp;
        while ((temp = br.readLine()) != null) {
            output = output + temp;
        }
        conn.disconnect();
        return output;
    }

    @Override
    public String POST(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception{
        URL getUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("content-type", "application/json");
        if (header != "") {
            conn.setRequestProperty("Authorization", header);
        }
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(payload.getBytes());
        os.flush();
        os.close();
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "", temp;
        while ((temp = br.readLine()) != null) {
            output = output + temp;
        }
        conn.disconnect();
        return output;
    }

    @Override
    public String PUT(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception{
        URL getUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("content-type", "application/json");
        if (header != "") {
            conn.setRequestProperty("Authorization", header);
        }
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(payload.getBytes());
        os.flush();
        os.close();
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "", temp;
        while ((temp = br.readLine()) != null) {
            output = output + temp;
        }
        conn.disconnect();
        return output;
    }

    @Override
    public void DELETE(String url, String header, String payload) throws ProtocolException, MalformedURLException, IOException, Exception{
        URL getUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Accept", "application/json");
        if (header != "") {
            conn.setRequestProperty("Authorization", header);
        }
        conn.setDoOutput(true);
        conn.connect();
    }

}
