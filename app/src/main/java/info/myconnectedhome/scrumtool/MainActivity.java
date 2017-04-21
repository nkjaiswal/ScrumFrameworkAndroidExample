package info.myconnectedhome.scrumtool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void registerUser(View v){
        Intent myIntent = new Intent(MainActivity.this, RegisterUserActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void openMyCreatedSprintActivity(View v){
        Intent myIntent = new Intent(MainActivity.this, CreatedSprintList.class);
        myIntent.putExtra("myCreater",true);
        MainActivity.this.startActivity(myIntent);
    }

    public void openMyassignedSprintActivity(View v){
        Intent myIntent = new Intent(MainActivity.this, CreatedSprintList.class);
        myIntent.putExtra("myCreater",false);
        MainActivity.this.startActivity(myIntent);
    }

    public void openGrantAdminRole(View v){
        Intent myIntent = new Intent(MainActivity.this, GrantAdminRole.class);
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String value = intent.getStringExtra("isAdmin");

        if(value.equalsIgnoreCase("Y")){
            findViewById(R.id.addSprint).setVisibility(View.VISIBLE);
            findViewById(R.id.created_sprint).setVisibility(View.VISIBLE);
            findViewById(R.id.register_user).setVisibility(View.VISIBLE);
            findViewById(R.id.main_grant_role).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.addSprint).setVisibility(View.INVISIBLE);
            findViewById(R.id.main_grant_role).setVisibility(View.INVISIBLE);
            findViewById(R.id.created_sprint).setVisibility(View.INVISIBLE);
            findViewById(R.id.register_user).setVisibility(View.INVISIBLE);
        }
    }
    public void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void AddSprintClicked(View v){
        Intent myIntent = new Intent(MainActivity.this, CreateNewSprint.class);
        MainActivity.this.startActivity(myIntent);
    }
}
