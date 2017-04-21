package info.myconnectedhome.scrumtool;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

import AsyncTasks.RegisterUser;
import scrumtool.ScrumTool;
import scrumtool.sprint.User;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
    }

    public void RegisterUser(View v){
        String userid = ((EditText)findViewById(R.id.register_userid)).getText().toString();
        String email = ((EditText)findViewById(R.id.register_email)).getText().toString();
        if(userid.length() <=0 || email.length() <= 0){
            showToast(getApplicationContext(),"Please enter User ID and Email ID");
            return;
        }
        String isAdmin = ((CheckBox)findViewById(R.id.register_admin)).isChecked()?"Y":"N";

        new RegisterUser(getMethod("RegisterUserResponse"),this).execute(email,userid,isAdmin);


    }
    public void RegisterUserResponse(String s){
        if(s.startsWith("ERR")){
            showToast(getApplicationContext(),"Error while registering user. User may already present");
        }else{
            showToast(getApplicationContext(),"Successfully Registered.");
            finish();
        }

    }
    public void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return RegisterUserActivity.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
}
