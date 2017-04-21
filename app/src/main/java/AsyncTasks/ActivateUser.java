package AsyncTasks;

import android.os.AsyncTask;
import java.lang.reflect.Method;
import scrumtool.*;
/**
 * Created by I322345 on 4/16/2017.
 */
public class ActivateUser extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public ActivateUser(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        String userid = data[0];
        String password = data[1];
        String token = data[2];
        try{
            ScrumTool s = ScrumTool.getInstance();
            return s.activateUser(userid,token,password);
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
