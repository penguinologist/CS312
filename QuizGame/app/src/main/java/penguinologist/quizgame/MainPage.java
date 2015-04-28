package penguinologist.quizgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.parse.ParseUser;

public class MainPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        RelativeLayout curr = (RelativeLayout) findViewById(R.id.RelativeLayout5);
        curr.setBackgroundColor(Color.LTGRAY);
        Button submit = (Button) findViewById(R.id.button5);
        Button taq = (Button) findViewById(R.id.button2);
        Button cq = (Button) findViewById(R.id.button3);
        Button vg = (Button) findViewById(R.id.button4);

        //view grades
        vg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, ViewGrades.class));
                finish();
            }
        });

        //create a quiz
        cq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the user is a teacher, allow this. Else disable button
                startActivity(new Intent(MainPage.this, CreateQuestion.class));
                finish();
            }
        });

        //take a quiz button
        taq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, ChooseQuiz.class));
                finish();
            }
        });

        //logout button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOutInBackground();//logout and go back to login page
                startActivity(new Intent(MainPage.this, Login.class));
                finish();

            }
        });


    }


    //other stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
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
