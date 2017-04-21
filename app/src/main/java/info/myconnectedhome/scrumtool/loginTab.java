package info.myconnectedhome.scrumtool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by I322345 on 4/15/2017.
 */
public class loginTab extends Fragment implements View.OnClickListener{
    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.logintab,container,false);
        TextView username=(TextView)rootView.findViewById(R.id.login_userid);
        username.setText(FileWriter.userid);
        return rootView;
    }
    public void onLoginClick(View v){
        Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
    }
}
