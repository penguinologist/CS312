package penguinologist.quizgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class login extends ActionBarActivity {

    EditText username;
    EditText password;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button submit = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        info = (TextView) findViewById(R.id.textView9);
        info.setText("");



        /*this is used to start the Grade Server; normally the server would
        * run on its own and would not be part of this application. */
        //CONNECT_TO_GRADE_SERVER(); TODO we can implement reading grades etc. no problem, but writing is another story....


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validLogin()) {
                    startActivity(new Intent(login.this, MainPage.class));
                } else {
                    //set the text to show wrong input
                    info.setText("Wrong username or password");
                }
            }
        });


    }


    private boolean validLogin() {
//add all users in an arraylist
        ArrayList<User> users = new ArrayList<User>();
        try {
            InputStream is = getResources().openRawResource(R.raw.database);
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {

                if (strLine.startsWith("U") && !strLine.equals("USERS")) {

                    String[] pieces = strLine.split(",");//type, question, answer, quizname
                    // we seek all the questions that have the correct quizname on them....

                    //public User(String name, String role, String username, String password)
                    User temp = new User(pieces[1], pieces[2], pieces[3], pieces[4]);
                    users.add(temp);
                }

            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //now check if the user exists
        for (User t : users) {
            if (t.getUsername().trim().equals(username.getText().toString().trim())) {

                if (t.getPassword().trim().equals(password.getText().toString().trim())) {
                    info.setText("");
                    return true;
                }
            }
        }

        return true;//TODO change when operating. This is just to bypass the login screen quickly
    }


    public static void CONNECT_TO_GRADE_SERVER() {
        //Ideally the Grade Server would be an external system, not a thread running in this
        //application.
        (new Thread(new GradeServer())).start();
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
}
