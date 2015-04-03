package penguinologist.quizgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChooseQuiz extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosingquiz);
        //TODO add all quizzes to the list

        ListView lv = (ListView) findViewById(R.id.listView2);

        ArrayList<String> quizzes = new ArrayList<>();
        //TODO read the list of quizzes
        //Get the text file

//Read text from file
        try{
            //opening file of words
            InputStream is = getResources().openRawResource(R.raw.database);
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //reading file of words
            while ((strLine = br.readLine()) != null) {
                if(strLine.startsWith("QUIZ")) {
                    String[] pieces = strLine.split(",");
                    quizzes.add(pieces[1].trim());  //adding word to the list
                }
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, quizzes);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) ((ListView) findViewById(R.id.listView2)).getItemAtPosition(itemPosition);

                // redirect to new page with quiz info
                //pass along the quiz located at quizzes.get(position)

                Intent i = new Intent(ChooseQuiz.this, takequiz.class);
                i.putExtra("pos", String.valueOf(itemPosition));
                startActivity(i);
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
