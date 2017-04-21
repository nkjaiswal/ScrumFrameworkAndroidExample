package info.myconnectedhome.scrumtool;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

import AsyncTasks.BacklogComment;
import scrumtool.ScrumTool;
import scrumtool.sprint.AssignedUsers;
import scrumtool.sprint.Backlogs;
import scrumtool.sprint.Comments;
import scrumtool.sprint.DeveloperAssigned;
import scrumtool.sprint.Sprint;

public class BacklogDetails extends AppCompatActivity {
    Sprint sprint;
    Backlogs backlog;
    public void onCommentClick(View v){
        final Method m = getMethod("backlogCommentSuccess");
        final Object that = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comment on Backlog");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new BacklogComment(m,that).execute(sprint.getId(),backlog.BacklogId.toString(),input.getText().toString());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog_details);
        Intent i = getIntent();
        sprint = (Sprint)i.getSerializableExtra("sprint");
        backlog = (Backlogs)i.getSerializableExtra("backlog");
        ((TextView)findViewById(R.id.bd_desc)).setText(backlog.Description);
        ((TextView)findViewById(R.id.bd_status)).setText("Status: " + backlog.Status);
        ((TextView)findViewById(R.id.bd_effort)).setText("Effort: " + backlog.EffortEstimated + " " + sprint.EffortUnit);

        List<Comments> comments = backlog.Comments;
        if(comments.size()<=0){
            ((TextView)findViewById(R.id.textView10)).setVisibility(View.INVISIBLE);
        }
        ArrayAdapter<Comments> commentAdapter = new ArrayAdapter<Comments>(this,android.R.layout.simple_list_item_1,comments);
        final ListView commentView = (ListView) findViewById(R.id.bd_comments);
        commentView.setAdapter(commentAdapter);
        commentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> backlogs, View view, int position, long id) {

            }
        });

        List<DeveloperAssigned> developer = backlog.DeveloperAssigned;
        if(comments.size()<=0){
            ((TextView)findViewById(R.id.textView10)).setVisibility(View.INVISIBLE);
        }
        ArrayAdapter<DeveloperAssigned> developerAdapter = new ArrayAdapter<DeveloperAssigned>(this,android.R.layout.simple_list_item_1,developer);
        final ListView developerView = (ListView) findViewById(R.id.bd_developer);
        developerView.setAdapter(developerAdapter);
        developerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> developers, View view, int position, long id) {
                DeveloperAssigned user = (DeveloperAssigned) ((ListView) developers).getAdapter().getItem(position);
                Intent myIntent = new Intent(BacklogDetails.this, EffortSpentOnBacklog.class);
                myIntent.putExtra("sprint",sprint);
                myIntent.putExtra("user",user);
                myIntent.putExtra("backlog",backlog);
                BacklogDetails.this.startActivity(myIntent);
            }
        });
    }

    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return BacklogDetails.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            ReusableTemp.showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
}
