package penguinologist.quizgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * View grades class
 */
public class ViewGrades extends ActionBarActivity {

    /**
     * the oncreate method. this gets called upon creation of the new activity
     *
     * @param savedInstanceState the instance that gets passed along, combined with the intent and all extras
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgrades);

        ListView list = (ListView) findViewById(R.id.listView);
        Button back = (Button) findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewGrades.this, MainPage.class));
            }
        });


//        ArrayList<User> users = new ArrayList<User>();
//        try {
//            InputStream is = getResources().openRawResource(R.raw.database);
//            DataInputStream in = new DataInputStream(is);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String strLine;
//            while ((strLine = br.readLine()) != null) {
//
//                if (strLine.startsWith("U") && !strLine.equals("USERS")) {
//
//                    String[] pieces = strLine.split(",");//type, question, answer, quizname
//                    // we seek all the questions that have the correct quizname on them....
//
//                    //public User(String name, String role, String username, String password)
//                    User temp = new User(pieces[1], pieces[2], pieces[3], pieces[4]);
//                    users.add(temp);
//                }
//
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        ArrayList<String> grades = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.grades);
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {


//                    String[] pieces = strLine.split(",");//type, question, answer, quizname
//                    // we seek all the questions that have the correct quizname on them....
//
//                    //public User(String name, String role, String username, String password)
//
                grades.add(strLine);


            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewGrades.this, android.R.layout.simple_list_item_1, grades);

        list.setAdapter(adapter);

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
