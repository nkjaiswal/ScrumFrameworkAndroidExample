package scrumtool;

import java.util.Date;
import java.util.List;

import scrumtool.sprint.Sprint;
import scrumtool.sprint.SprintSummary;

public interface ISprint {
	public String login(String user, String pin, Date timeStamp) throws Exception;
	public List<SprintSummary> getSprints() throws Exception;
	public Sprint getSprintDetails(String springId) throws ScrumToolException, Exception;
}
