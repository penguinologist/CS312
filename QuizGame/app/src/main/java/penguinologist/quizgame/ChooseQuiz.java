package penguinologist.quizgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ChooseQuiz extends ActionBarActivity {
    ArrayList<String> quizzes;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosingquiz);
        //add all quizzes to the list

        RelativeLayout curr = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        curr.setBackgroundColor(Color.LTGRAY);
        Button back = (Button) findViewById(R.id.button12);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseQuiz.this, MainPage.class));
                finish();
            }
        });


        lv = (ListView) findViewById(R.id.listView2);

        lv.setBackgroundColor(Color.LTGRAY);
        quizzes = new ArrayList<>();
        //Get the text file


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Quiz");
        query.whereNotEqualTo("Quizname", "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject t : parseObjects) {
                        if (!quizzes.contains(t.getString("Quizname"))) {
                            quizzes.add(t.getString("Quizname"));
                        }
                    }
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, quizzes);

                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // ListView Clicked item index
                        int itemPosition = position;
                        //get the name of the quiz chosen to the next class passed along as extra
                        // ListView Clicked item value
                        String itemValue = (String) ((ListView) findViewById(R.id.listView2)).getItemAtPosition(itemPosition);

                        // redirect to new page with quiz info
                        //pass along the quiz located at quizzes.get(position)

                        Intent i = new Intent(ChooseQuiz.this, TakeQuiz.class);
                        i.putExtra("name", itemValue);
                        startActivity(i);
                        finish();
                    }
                });

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_takingquiz, menu);
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
