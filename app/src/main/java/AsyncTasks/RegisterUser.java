package AsyncTasks;

import android.os.AsyncTask;
import android.widget.CheckBox;

import java.lang.reflect.Method;

import info.myconnectedhome.scrumtool.R;
import scrumtool.ScrumTool;
import scrumtool.sprint.User;

/**
 * Created by I322345 on 4/16/2017.
 */
public class RegisterUser extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public RegisterUser(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        String email = data[0];
        String userid = data[1];
        boolean isAdmin = (data[2].equalsIgnoreCase("Y"))?true:false;
        try{
            ScrumTool s = ScrumTool.getInstance();
            User u = new User();
            u.email = email;
            u.userid = userid;
            u.isAdmin = isAdmin;
            return s.RegisterUser(u);
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
