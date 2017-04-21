package scrumtool;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import scrumtool.Token;
import scrumtool.sprint.Comments;
import scrumtool.sprint.EffortSpent;
import scrumtool.sprint.MailMessage;
import scrumtool.sprint.Sprint;
import scrumtool.sprint.SprintSummary;
import scrumtool.sprint.User;

public class ScrumTool implements ISprint, IUser {
	private static ScrumTool scrumTool;
	private IRestHandler restHandler;
	private Gson gson;

	private ScrumTool(IRestHandler restHandler) {
		this.restHandler = restHandler;
		gson = new GsonBuilder().create();
		;
	}

	public static ScrumTool getInstance(IRestHandler restHandler) {
		if (scrumTool == null) {
			scrumTool = new ScrumTool(restHandler);
		}
		return scrumTool;
	}

	public static ScrumTool getInstance() throws Exception {
		if (scrumTool == null) {
			throw new RestHandlerNotInitializedException();
		}
		return scrumTool;
	}

	//==============USER LOGIN and TOKEN PART==============================
	private Token token;
	private String user;
	private String pin;

	private String getAuthorizationHeader() throws ScrumToolException {
		if (token == null || token.token == "") {
			throw new ScrumToolException("Please login first to get authorization token header");
		}
		return "Token " + user + ":" + token.token;
	}

	@Override
	public String login(String user, String pin, Date timeStamp) throws Exception {
		String preAuth = Reusable.getSHA(user + pin);
		String xAuth = Reusable.toBASE64(user + ":" + preAuth);
		this.user = user;
		this.pin = pin;
		String auth = "RSA " + xAuth;
		String getTokenURL = URLs.getTokenURL();
		String sResponse = restHandler.GET(getTokenURL, auth);
		token = gson.fromJson(sResponse, Token.class);
		return sResponse;
	}

	//==============SPRINT HANDLING PART====================================
	@Override
	public List<SprintSummary> getSprints() throws Exception {
		String sResponse = restHandler.GET(URLs.getAllSprintURL(), this.getAuthorizationHeader());
		Type listType = new TypeToken<List<SprintSummary>>() {
		}.getType();
		return gson.fromJson(sResponse, listType);
	}

	@Override
	public Sprint getSprintDetails(String springId) throws Exception {
		String sResponse = restHandler.GET(URLs.getSprintDetailsURL() + springId, this.getAuthorizationHeader());
		Type listType = new TypeToken<List<Sprint>>() {
		}.getType();
		List<Sprint> sprint = gson.fromJson(sResponse, listType);
		return sprint.get(0);
	}
	
	public String SaveSprint(Sprint s) throws ScrumToolException, Exception{
		String payload = gson.toJson(s);
		String sResponse = restHandler.POST(URLs.getCreateUpdateSprintUrl(), this.getAuthorizationHeader(), payload);
		return sResponse;
	}
	
	public List<SprintSummary> getAssignedSprint() throws Exception{
		String sResponse = restHandler.GET(URLs.getAssignedSprintUrl(), this.getAuthorizationHeader());
		Type listType = new TypeToken<List<SprintSummary>>() {
		}.getType();
		return gson.fromJson(sResponse, listType);
	}
	
	public String setEffortSpentOnBacklog(String sprintId, String backlogId, EffortSpent ef) throws ScrumToolException, Exception{
//		List<EffortSpent> efsp = new ArrayList<EffortSpent>();
//		efsp.add(ef);
		String payload = gson.toJson(ef);
		String sResponse = restHandler.POST(URLs.getEfforSpentUrl(sprintId, backlogId), this.getAuthorizationHeader(), payload);
		return sResponse;
	}
	
	public String commentBacklog(String sprintId, String backlogId, Comments c) throws ScrumToolException, Exception{
		String payload = gson.toJson(c);
		String sResponse = restHandler.POST(URLs.getBacklogCommentUrl(sprintId, backlogId), this.getAuthorizationHeader(), payload);
		return sResponse;
	}
	//============USER REGISTRATION PART====================================
	@Override
	public String RegisterUser(User user) throws ProtocolException, MalformedURLException, IOException, Exception {
		user.setPassword(Reusable.getSHA(user.email  + "MyPrivateKey" + Math.random()));
		String payload = gson.toJson(user);
		String sResponse = restHandler.POST(URLs.getRegisterUserUrl(),this.getAuthorizationHeader(),payload);
		MailMessage mail = new MailMessage();
		mail.to = user.email;
		mail.subject = "You got registerd in Scrum Tool by " + this.user;
		mail.message = user.userid + ", your Activation token is " + user.getToken();
		Reusable.sendMail(gson.toJson(mail),restHandler,this.getAuthorizationHeader());
		return sResponse;
	}

	@Override
	public String grantAdminRole(String userid) throws Exception {
		return restHandler.GET(URLs.getGrantAdminRoleUrl(userid), this.getAuthorizationHeader());
	}

	@Override
	public String revokeAdminRole(String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String activateUser(String userid, String token, String newPassword) throws Exception{
		String payload = "{\"password\":\"" + newPassword + "\"}";
		return restHandler.POST(URLs.getActivateUserUrl(userid, token), "", payload);
	}

	public String isAdmin() throws Exception{
		return restHandler.GET(URLs.getMyRoleUrl(),getAuthorizationHeader());
	}
}
