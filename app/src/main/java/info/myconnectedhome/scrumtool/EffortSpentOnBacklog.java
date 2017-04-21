package info.myconnectedhome.scrumtool;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

import AsyncTasks.BacklogComment;
import AsyncTasks.EffortUpdate;
import scrumtool.sprint.AssignedUsers;
import scrumtool.sprint.Backlogs;
import scrumtool.sprint.DeveloperAssigned;
import scrumtool.sprint.EffortSpent;
import scrumtool.sprint.Sprint;

public class EffortSpentOnBacklog extends AppCompatActivity {

    Sprint sprint;
    Backlogs backlog;
    public void onEffortSpentInput(View v){
        final Method m = getMethod("backlogCommentSuccess");
        final Object that = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How much unit time spent (in " + sprint.EffortUnit + ")?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("2");
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new EffortUpdate(m,that).execute(sprint.getId(),backlog.BacklogId.toString(),input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void backlogCommentSuccess(String s){
        if(s.startsWith("ERR")){
            ReusableTemp.showToast(getApplicationContext(),"Can not post your comment");
        }else{
            ReusableTemp.showToast(getApplicationContext(),"Successfully posted your comment. Please reload the view");
        }
    }

    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return EffortSpentOnBacklog.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effort_spent_on_backlog);
        Intent i = getIntent();
        sprint = (Sprint)i.getSerializableExtra("sprint");
        backlog = (Backlogs) i.getSerializableExtra("backlog");
        final DeveloperAssigned user = (DeveloperAssigned)i.getSerializableExtra("user");
        ((TextView)findViewById(R.id.effort_spent_user)).setText(user.userId);
        ((TextView)findViewById(R.id.effort_spent_total)).setText("Effort Allocated: " + user.EffortAssigned.toString() + " " + sprint.EffortUnit);

        List<EffortSpent> effortSpentList = user.EffortSpent;
        ArrayAdapter<EffortSpent> effortSpentArrayAdapter = new ArrayAdapter<EffortSpent>(this,android.R.layout.simple_list_item_1,effortSpentList);
        final ListView effortView = (ListView) findViewById(R.id.effort_spent_list);
        effortView.setAdapter(effortSpentArrayAdapter);

    }
}
