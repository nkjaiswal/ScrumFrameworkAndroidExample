package AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Date;

import scrumtool.ScrumTool;

/**
 * Created by I322345 on 4/16/2017.
 */
public class Login extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public Login(Method m, Object a){
        this.m = m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        Log.v("Login:doInBackground","I am here");
        String userid = data[0];
        String password = data[1];
        try{
                ScrumTool s = ScrumTool.getInstance();
            return s.login(userid,password,new Date());
            //ProtocolException, MalformedURLException, IOException, Exception
        }catch(ProtocolException e){
            return "ERR " + e.toString();
        }catch(MalformedURLException e){
            return "ERR " + e.toString();
        }catch(IOException e){
            return "ERR " + e.toString();
        }catch(Exception e){
            return "ERR " + e.toString();
        }
    }
    protected void onPostExecute(String result) {
        Log.v("Login:postExe",result);
        super.onPostExecute(result);
        Object[] parameters = new Object[1];
        parameters[0] = result;
        try{
            m.invoke(a, parameters);
        }catch(Exception e){}
    }
}
