package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;

import info.myconnectedhome.scrumtool.ReusableTemp;
import scrumtool.ScrumTool;

/**
 * Created by I322345 on 4/18/2017.
 */
public class GetAssignedSprint  extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public GetAssignedSprint(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        try{
            ScrumTool s = ScrumTool.getInstance();
            ReusableTemp.sprints = s.getAssignedSprint();
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