package scrumtool;

import scrumtool.sprint.User;

public interface IUser {
	public String grantAdminRole(String userid) throws Exception;
	public String revokeAdminRole(String userid) throws Exception;
	public String RegisterUser(User user) throws Exception;
}
