package scrumtool.sprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EffortSpent  implements Serializable {
	private String Date;
	public Integer EffortConsumed;
	public List<Comments> Comments;
	
	public EffortSpent(){
		Comments = new ArrayList<Comments>();
		Date = new Date().toString();
	}
	public String getDate(){
		return Date;
	}
	public  String toString(){
		if(Comments.size()>=1){
			return "Effort Spent: " + EffortConsumed + " unit, on " + Date + ". Comment:" + Comments.get(0).Description;
		}else{
			return "Effort Spent: " + EffortConsumed + " unit, on " + Date + ".";
		}
	}
}
