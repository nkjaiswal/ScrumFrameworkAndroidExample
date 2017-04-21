package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;
import java.util.List;

import info.myconnectedhome.scrumtool.ReusableTemp;
import scrumtool.Reusable;
import scrumtool.ScrumTool;
import scrumtool.sprint.SprintSummary;

/**
 * Created by I322345 on 4/16/2017.
 */
public class GetCreatedSprint extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public GetCreatedSprint(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        try{
            ScrumTool s = ScrumTool.getInstance();
            ReusableTemp.sprints = s.getSprints();
            return "Success";
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
