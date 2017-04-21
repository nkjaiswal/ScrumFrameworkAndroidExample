package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;

import scrumtool.ScrumTool;
import scrumtool.sprint.Comments;
import scrumtool.sprint.EffortSpent;

/**
 * Created by I322345 on 4/19/2017.
 */
public class EffortUpdate extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public EffortUpdate(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        String sprintId = data[0];
        String backlogId = data[1];
        Integer effort  = Integer.parseInt(data[2]);
        EffortSpent ef = new EffortSpent();
        ef.EffortConsumed = effort;
        try{
            ScrumTool s = ScrumTool.getInstance();
            return s.setEffortSpentOnBacklog(sprintId,backlogId,ef);
        }catch(Exception e){
            return "ERR " + e.toString();
        }
    }
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Object[] parameters = new Object[1];
        parameters[0] = result;
        try{
            m.invoke(a, parameters);
        }catch(Exception e){}
    }
}
