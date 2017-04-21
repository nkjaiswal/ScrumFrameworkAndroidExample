package info.myconnectedhome.scrumtool;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import scrumtool.sprint.Sprint;
import scrumtool.sprint.SprintSummary;

/**
 * Created by I322345 on 4/16/2017.
 */
public class ReusableTemp {
    public static List<SprintSummary> sprints;
    public static Sprint sprintDetails;
    public static Sprint newSprint;
    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
