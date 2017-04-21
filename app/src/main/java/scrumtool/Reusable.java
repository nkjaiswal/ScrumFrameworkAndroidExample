package scrumtool;

import java.security.MessageDigest;
import android.util.Base64;
import java.util.Date;
import java.util.Formatter;


public class Reusable {
	public static String getMongoDate(Date d){
		return (d.getYear()+1900) + "-" + (d.getMonth()+1) + "-" + d.getDate() + "T" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + ".000Z";//2017-04-01T10:45:30.468Z
	}
	public static void sendMail(String payload, IRestHandler restHandler, String header) throws Exception{
		restHandler.POST(URLs.getMailerServiceUrl(), header, payload);
	}
	public static String toBASE64(String s){
		return Base64.encodeToString(s.getBytes(),Base64.DEFAULT);
	}
	public static String getSHA(String s){
		return encryptPassword(s);
	}
	private static String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
}
