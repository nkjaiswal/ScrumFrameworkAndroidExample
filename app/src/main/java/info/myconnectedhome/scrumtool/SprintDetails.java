package info.myconnectedhome.scrumtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

import AsyncTasks.GetSprintDetails;
import scrumtool.Reusable;
import scrumtool.sprint.AssignedUsers;
import scrumtool.sprint.Backlogs;
import scrumtool.sprint.Sprint;
import scrumtool.sprint.SprintSummary;

public class SprintDetails extends AppCompatActivity {


    Object that;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_details);


        that = this;
        Intent i = getIntent();
        final Sprint sprint = (Sprint)i.getSerializableExtra("sprint");

        ((TextView)findViewById(R.id.spd_sprint_name)).setText(sprint.SprintName);
        ((TextView)findViewById(R.id.spd_sprint_version)).setText("v"+sprint.SprintVersion);
        ((TextView)findViewById(R.id.spd_sprint_status)).setText("Status:" + sprint.Status);
        ((TextView)findViewById(R.id.spd_sprint_start)).setText("Start: " + sprint.StartDate);
        ((TextView)findViewById(R.id.spd_sprint_ends)).setText("End: " + sprint.EndDate);

        List<Backlogs> backlogs  = sprint.Backlogs;
        ArrayAdapter<Backlogs> backlogAdapter = new ArrayAdapter<Backlogs>(this,android.R.layout.simple_list_item_1,backlogs);
        final ListView backlogView = (ListView) findViewById(R.id.spd_backlog_list);
        backlogView.setAdapter(backlogAdapter);
        backlogView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> backlogs, View view, int position, long id) {
                Backlogs backlogDetails = (Backlogs)((ListView) backlogs).getAdapter().getItem(position);
                Intent myIntent = new Intent(SprintDetails.this, BacklogDetails.class);
                myIntent.putExtra("sprint",sprint);
                myIntent.putExtra("backlog",backlogDetails);
                SprintDetails.this.startActivity(myIntent);
            }
        });

        List<AssignedUsers> assignedUser  = sprint.AssignedUsers;
        ArrayAdapter<AssignedUsers> userAdapter = new ArrayAdapter<AssignedUsers>(this,android.R.layout.simple_list_item_1,assignedUser);
        final ListView userView = (ListView) findViewById(R.id.spd_assigned_user_list);
        userView.setAdapter(userAdapter);
        userView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> users, View view, int position, long id) {
                AssignedUsers user = (AssignedUsers) ((ListView) users).getAdapter().getItem(position);

            }
        });
    }

    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return SprintDetails.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
}
