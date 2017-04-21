package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;

import scrumtool.ScrumTool;

/**
 * Created by I322345 on 4/16/2017.
 */
public class GrantAdminRoleTask  extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public GrantAdminRoleTask(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        String userid = data[0];
        try{
            ScrumTool s = ScrumTool.getInstance();
            return s.grantAdminRole(userid);
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
