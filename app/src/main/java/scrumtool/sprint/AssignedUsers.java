package scrumtool.sprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssignedUsers  implements Serializable {
	public String userId;
	public List<String> roles;
	public Integer EffortAvailable;
	
	public AssignedUsers(){
		roles = new ArrayList<String>();
	}
	public String toString(){
		return userId + " (" + EffortAvailable + " unit)";
	}
}
