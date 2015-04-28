package penguinologist.quizgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * View grades class
 */
public class ViewGrades extends ActionBarActivity {
    ArrayList<String> grades;
    ListView list;

    /**
     * the oncreate method. this gets called upon creation of the new activity
     *
     * @param savedInstanceState the instance that gets passed along, combined with the intent and all extras
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgrades);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout6);
        rl.setBackgroundColor(Color.LTGRAY);
        list = (ListView) findViewById(R.id.listView);
        Button back = (Button) findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewGrades.this, MainPage.class));
                finish();
            }
        });
        grades = new ArrayList<>();
        // get the data from Parse
        if (ParseUser.getCurrentUser().getString("Role").equals("Teacher")) {
            //teacher logged in, meaning they get to see all the grades
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Grades");
            query.whereNotEqualTo("Username", "");
            query.orderByAscending("");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (e == null) {
                        for (ParseObject t : scoreList) {
                            grades.add(t.get("Username") + "\t\t" + t.get("Quizname") + "\t" + t.getNumber("Score") + "/" + t.getNumber("MaxGrade"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewGrades.this, android.R.layout.simple_list_item_1, grades);
                        list.setAdapter(adapter);
                    } else {
                        //no scores found
                    }
                }
            });

        } else {
            //it's a student, meaning they can only see their own grades
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Grades");
            query.whereEqualTo("Username", ParseUser.getCurrentUser().get("username"));
            query.orderByAscending("");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (e == null) {
                        for (ParseObject t : scoreList) {
                            grades.add(t.get("Username") + "\t\t" + t.get("Quizname") + "\t" + t.getNumber("Score") + "/" + t.getNumber("MaxGrade"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewGrades.this, android.R.layout.simple_list_item_1, grades);
                        list.setAdapter(adapter);
                    }
                }


            });


        }
    }


    /**
     * useless for now...
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewgrades, menu);
        return true;
    }

    /**
     * again, useless for now...
     *
     * @param item
     * @return
     */
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
