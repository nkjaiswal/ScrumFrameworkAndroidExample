package scrumtool.sprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeveloperAssigned  implements Serializable {
	public String userId;
	public Integer EffortAssigned;
	public List<EffortSpent> EffortSpent;
	
	public DeveloperAssigned(){
		EffortSpent = new ArrayList<EffortSpent>();
	}
	public String toString(){
		return userId + "(" + EffortAssigned + " unit)";
	}
}
