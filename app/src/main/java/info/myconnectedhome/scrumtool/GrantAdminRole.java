package info.myconnectedhome.scrumtool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Method;

import AsyncTasks.GrantAdminRoleTask;

public class GrantAdminRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_admin_role);
    }

    public void GrantAdminRole(View v){
        String userid = ((EditText)findViewById(R.id.grant_admin_userid)).getText().toString();
        if(userid.length()<=0){
            ReusableTemp.showToast(getApplicationContext(),"Please Give an User ID");
            return;
        }
        new GrantAdminRoleTask(getMethod("GrantAdminRoleResponse"),this).execute(userid);
    }
    public void GrantAdminRoleResponse(String s){
        Log.v("GRANT",s);
        if(s.startsWith("ERR")){
            ReusableTemp.showToast(getApplicationContext(),"Unable to grant admin role");
        }else{
            ReusableTemp.showToast(getApplicationContext(),"Successfully Granted the Admin Role to user");
            finish();
        }
    }
    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return GrantAdminRole.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
}

