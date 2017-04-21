package scrumtool;

public class URLs {
	public static String baseURL = "http://myconnectedhome.info:3003/";
	public static String getTokenURL(){
		return baseURL + "ScrumTool/Token";
	}
	public static String getAllSprintURL(){
		return baseURL + "ScrumTool/Sprint/";
	}
	public static String getSprintDetailsURL(){
		return baseURL + "ScrumTool/Sprint/";
	}
	public static String getRegisterUserUrl(){
		return baseURL + "ScrumTool/User/";
	}
	public static String getMailerServiceUrl(){
		return baseURL + "Mailer/api/v1/Mail";
	}
	public static String getActivationUrl(String userid, String token){
		return baseURL  + "ScrumTool/User/" + userid + "/Activate/" + token;
	}
	public static String getGrantAdminRoleUrl(String userid){
		return baseURL + "ScrumTool/User/"  + userid + "/GrantAdminRole";
	}
	public static String getCreateUpdateSprintUrl(){
		return baseURL + "ScrumTool/Sprint";
	}
	public static String getActivateUserUrl(String userid, String token){
		return baseURL + "ScrumTool/User/" + userid + "/Activate/" + token;
	}
	public static String getAssignedSprintUrl(){
		return baseURL + "ScrumTool/AssignedSprint/";
	}
	public static String getEfforSpentUrl(String sprintId, String backlogId){
		return baseURL + "ScrumTool/Sprint/" + sprintId + "/Backlog/" + backlogId + "/EffortSpent";
	}
	public static String getBacklogCommentUrl(String sprintId, String backlogId){
		return baseURL + "ScrumTool/Sprint/" + sprintId + "/Backlog/" + backlogId + "/Comment";
	}
	public static String getMyRoleUrl(){
		return baseURL + "ScrumTool/User/MyRole/isAdmin";
	}
}
