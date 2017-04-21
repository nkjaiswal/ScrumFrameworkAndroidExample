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
public class MyRole extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public MyRole(Method m, Object a){
        this.m = m;
        this.a = a;
    }
    @Override
    protected java.lang.String doInBackground(java.lang.String... data){
        Log.v("isAdmin:doInBackground","I am here");

        try{
            ScrumTool s = ScrumTool.getInstance();
            return s.isAdmin();
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
    protected void onPostExecute(java.lang.String result) {
        Log.v("isAdmin:postExe",result);
        super.onPostExecute(result);
        Object[] parameters = new Object[1];
        parameters[0] = result;
        try{
            m.invoke(a, parameters);
        }catch(Exception e){}
    }
}
