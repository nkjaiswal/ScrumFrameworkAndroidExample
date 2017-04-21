package AsyncTasks;

import android.os.AsyncTask;

import java.lang.reflect.Method;

import info.myconnectedhome.scrumtool.ReusableTemp;
import scrumtool.ScrumTool;
import scrumtool.sprint.Comments;

/**
 * Created by I322345 on 4/19/2017.
 */
public class BacklogComment extends AsyncTask<String, String, String> {
    private Method m;
    private Object a;
    public BacklogComment(Method m, Object a){
        this.m=m;
        this.a = a;
    }
    @Override
    protected String doInBackground(String... data){
        String sprintId = data[0];
        String backlogId = data[1];
        String comment  = data[2];
        Comments c = new Comments();
        c.Description = comment;
        try{
            ScrumTool s = ScrumTool.getInstance();
            return s.commentBacklog(sprintId,backlogId,c);
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
