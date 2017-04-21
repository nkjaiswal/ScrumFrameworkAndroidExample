package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;

import info.myconnectedhome.scrumtool.ReusableTemp;
import scrumtool.ScrumTool;
import scrumtool.sprint.EffortSpent;

/**
 * Created by I322345 on 4/19/2017.
 */
public class SaveSprint  extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public SaveSprint(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        try{
            ScrumTool s = ScrumTool.getInstance();
            if(ReusableTemp.newSprint == null){
                return "ERR no sprint present";
            }
            return s.SaveSprint(ReusableTemp.newSprint);
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
