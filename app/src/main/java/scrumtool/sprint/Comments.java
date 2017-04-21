package scrumtool.sprint;

import java.io.Serializable;
import java.util.Date;
public class Comments  implements Serializable {
	private String _userId;
	public String Description;
	private String Date;
	public Comments(){
		Date = new Date().toString();
	}
	public String getDate(){
		return Date;
	}
	public String toString(){
		return _userId + "[" + Date.toString() + "]: " + Description;
	}
}
