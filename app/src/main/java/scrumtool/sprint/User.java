package scrumtool.sprint;

import java.io.Serializable;

public class User  implements Serializable {
	@SuppressWarnings("unused")
	private String _id;
	public String userid, email;
	public boolean isAdmin;
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return this.password;
	}
	public String toString(){
		return userid + "|" + email;
	}
}
