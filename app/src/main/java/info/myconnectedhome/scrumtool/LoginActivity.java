package info.myconnectedhome.scrumtool;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import AsyncTasks.ActivateUser;
import AsyncTasks.Login;
import AsyncTasks.MyRole;
import Rest.RestHandler;
import scrumtool.*;
import scrumtool.sprint.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

public class LoginActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------
    public Method getMethod(String name){
        try{
            Class[] parameterTypes = new Class[1];
            parameterTypes[0] = String.class;
            return LoginActivity.class.getMethod(name, parameterTypes);
        }catch(Exception e){
            showToast(this.getApplicationContext(),e.toString());
        }
        return null;
    }
    public void doInitialization(){
        ScrumTool s = ScrumTool.getInstance(new RestHandler());


        try{
            FileWriter.userid  = FileWriter.readData(this.getApplicationContext(),"user.info");

        }catch (Exception e){
            FileWriter.userid = "";
        }
    }
    public void onLoginClick(View v){
        TextView userid = (TextView)findViewById(R.id.login_userid);
        EditText password = (EditText)findViewById(R.id.login_password);
        doLogin(userid.getText().toString(),password.getText().toString());
    }
    public void otheruser_login_click(View v){
        try{
            EditText userid = (EditText)findViewById(R.id.otheruser_userid);
            EditText password = (EditText)findViewById(R.id.otheruser_password);
            if(userid.getText().toString().length()==0 || password.getText().toString().length() == 0){
                Toast.makeText(this.getBaseContext(),"Please enter User ID and Password",Toast.LENGTH_LONG).show();
                return;
            }
            doLogin(userid.getText().toString(),password.getText().toString());
            FileWriter.appendData(this.getApplicationContext(),"user.info",userid.getText().toString());
            FileWriter.userid = userid.getText().toString();
        }catch(Exception e){
            Toast.makeText(this.getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void doLogin(String userid, String password){
        new Login(getMethod("doLoginResponse"),this).execute(userid,password);
    }
    public void doLoginResponse(String s){
        if(s.startsWith("ERR")){
            showToast(getApplicationContext(),"Can not login. Check User ID and Password");
        }else{
            new MyRole(getMethod("MyRoleResponse"),this).execute();
        }
    }
    public void MyRoleResponse(String s){
        if(s.startsWith("ERR")){
            showToast(getApplicationContext(),"Unable to get Role information");
        }else{
            showToast(getApplicationContext(),"Login Successful. Welcome " + FileWriter.userid);
            if(s.contains("true")){
                Log.v("","He is admin");
                FileWriter.isAdmin = true;
            }else{
                FileWriter.isAdmin = false;
                Log.v("","Not a admin");
            }
            //TODO:Navigate to next activity
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            myIntent.putExtra("isAdmin", (FileWriter.isAdmin)?"Y":"N"); //Optional parameters
            LoginActivity.this.startActivity(myIntent);
            finish();
        }
    }
    public void activateuser(View v){
        EditText userid = (EditText)findViewById(R.id.activate_userid);
        EditText password = (EditText)findViewById(R.id.activate_password);
        EditText token = (EditText)findViewById(R.id.activate_token);
        activateNow(userid.getText().toString(),password.getText().toString(),token.getText().toString());
    }
    public void activateNow(String userid,String password, String token){
        if(userid.length()<=0 || password.length()<=0 || token.length()<=0){
            Toast.makeText(getApplicationContext(),"Please provide userid, PIN and activation token",Toast.LENGTH_LONG).show();
            return;
        }
        new ActivateUser(getMethod("activateNowResponse"),this).execute(userid,password,token);
    }
    public void activateNowResponse(String s){
        if(s.startsWith("ERR")){
            showToast(getApplicationContext(),"Can't activate user. Check Activation Token and User ID again.");
        }else{
            showToast(this.getApplicationContext(),"Successfully activated the user");
        }
    }
    public void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    //----------------------------------------------------------------------------------------------

















    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        doInitialization();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    if(FileWriter.userid == ""){
                        return new otheruserTab();
                    }else{
                        return new loginTab();
                    }
                case 1:
                    return new otheruserTab();
                case 2:
                    return new activateuserTab();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Other User";
                case 2:
                    return "Activate User";
            }
            return null;
        }
    }
}
