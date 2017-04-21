package info.myconnectedhome.scrumtool;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import AsyncTasks.GetAssignedSprint;
import AsyncTasks.GetCreatedSprint;
import AsyncTasks.GetSprintDetails;
import scrumtool.Reusable;
import scrumtool.sprint.Sprint;
import scrumtool.sprint.SprintSummary;

public class CreatedSprintList extends ListActivity {

    Method mSprintClickedResponse;
    Object that;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_sprint_list);
        mSprintClickedResponse = getMethod("SprintClickedResponse");
        that = this;
        Intent i = getIntent();
        final boolean myCreater = (boolean)i.getSerializableExtra("myCreater");
        if(myCreater){
            new GetCreatedSprint(getMethod("getSprintsResponse"),this).execute();
        }else{
            new GetAssignedSprint(getMethod("getSprintsResponse"),this).execute();
        }

    }
    public void getSprintsResponse(String s){
        final Context context  = getApplicationContext();
        if(s.startsWith("ERR")){
            ReusableTemp.showToast(getApplicationContext(),"Error in getting list of sprints");
        }else{
            List<SprintSummary> sprints = ReusableTemp.sprints;
            ArrayAdapter<SprintSummary> adapter = new ArrayAdapter<SprintSummary>(this,android.R.layout.simple_list_item_1,sprints);
            setListAdapter(adapter);
            final ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SprintSummary sprint = (SprintSummary)((ListView) parent).getAdapter().getItem(position);
                    ReusableTemp.showToast(context,sprint.getId());
                    new GetSprintDetails(mSprintClickedResponse,that).execute(sprint.getId());
                }
            });
        }
    }
    public void SprintClickedResponse(String s){
        if(s.startsWith("ERR")){
            ReusableTemp.showToast(getApplicationContext(),"Can't get the Sprint Details");
            Log.v("SprintClickedResponse",s);
        }else{
            Sprint sprint = ReusableTemp.sprintDetails;
            Intent myIntent = new Intent(CreatedSprintList.this, SprintDetails.class);
            myIntent.putExtra("sprint",sprint);
            CreatedSprintList.this.startActivity(myIntent);
        }
    }
    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return CreatedSprintList.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
}
