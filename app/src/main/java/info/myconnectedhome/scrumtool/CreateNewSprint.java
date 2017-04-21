package info.myconnectedhome.scrumtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


import java.lang.reflect.Method;

import AsyncTasks.SaveSprint;
import scrumtool.sprint.AssignedUsers;
import scrumtool.sprint.Backlogs;
import scrumtool.sprint.DeveloperAssigned;
import scrumtool.sprint.Sprint;

public class CreateNewSprint extends AppCompatActivity {

    public void SaveSprint(View v){
        Sprint s = new Sprint();
        s.EffortUnit = "DAY";
        s.HoursInOneDay = 6;
        s.SprintName = ((TextView)findViewById(R.id.new_psrint_name)).getText().toString();
        s.SprintVersion = ((TextView)findViewById(R.id.new_sprint_version)).getText().toString();
        s.Status = "OPEN";
        DatePicker startDate = ((DatePicker)findViewById(R.id.new_start));
        s.StartDate = getFormattedDate(startDate);
        s.EndDate = getFormattedDate((DatePicker)findViewById(R.id.new_end));

        DeveloperAssigned d = new DeveloperAssigned();
        d.EffortAssigned = 10;
        d.userId = "admin";

        Backlogs b = new Backlogs();
        b.EffortEstimated = 15;
        b.Description = "Demo Backlog";
        b.BacklogId = 1;
        b.DeveloperAssigned.add(d);
        b.Status = "OPEN";
        s.Backlogs.add(b);

        AssignedUsers user  = new AssignedUsers();
        user.userId = "admin";
        user.EffortAvailable = 12;
        user.roles.add("DEVELOPER");
        user.roles.add("TESTER");

        s.AssignedUsers.add(user);
        ReusableTemp.newSprint = s;
        new SaveSprint(getMethod("onSaveSprintResponse"),this).execute();
    }
    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return CreateNewSprint.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
    public void onSaveSprintResponse(String s){
        if(s.startsWith("ERR")){
            ReusableTemp.showToast(getApplicationContext(),"Can't save the Sprint");
            Log.v("ErrorSave",s);
        }else{
            ReusableTemp.showToast(getApplicationContext(),"Sprint Saved");
            ReusableTemp.newSprint = null;
            finish();
        }
    }
    public void onAddBacklogClick(View v){
        Intent myIntent = new Intent(CreateNewSprint.this, AddNewBacklog.class);
        CreateNewSprint.this.startActivity(myIntent);
    }
    public void onAddDeveloper(View v){
        Intent myIntent = new Intent(CreateNewSprint.this, AddDeveloper.class);
        CreateNewSprint.this.startActivity(myIntent);
    }
    public String getFormattedDate(DatePicker d){
        return d.getYear() + "-" + ((d.getMonth() < 10)?"0"+d.getMonth():d.getMonth()) + "-" + (d.getDayOfMonth()<10?"0"+d.getDayOfMonth():d.getDayOfMonth()) + "T00:00:00.000Z";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_sprint);
    }
}
