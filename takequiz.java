package penguinologist.quizgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class takequiz extends ActionBarActivity {
    int current;
    int numQuestions;
    ArrayList<String> rawQuestions;
    Button btn;
    Spinner multiple_choice;
    EditText text;
    TextView question;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takequiz);

        int quizposition;
        Bundle b = getIntent().getExtras();

        quizposition = Integer.valueOf((String) b.get("pos")) + 1;
        //add all the questions of the quiz to this arraylist
        rawQuestions = new ArrayList<>();

        try {
            InputStream is = getResources().openRawResource(R.raw.database);
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = br.readLine();
            while ((strLine) != null) {

                if (strLine.startsWith("USERS")) {
                    break;

                } else {
                    String[] pieces = strLine.split(",");//type, question, answer, quizname
                    // we seek all the questions that have the correct quizname on them....
                    if (pieces[pieces.length - 1].contains("" + Integer.valueOf(quizposition))) {
                        rawQuestions.add(strLine);//add them to the list of raw questions...
                        Log.e("question", strLine + " added");
                    }
                }
                strLine = br.readLine();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        current = 0;
        //TODO figure out some way to find which quiz they took...


        numQuestions = rawQuestions.size();

        //get all the questions related to the quiz
        btn = (Button) findViewById(R.id.button8);
        multiple_choice = (Spinner) findViewById(R.id.multiplechoice);
        text = (EditText) findViewById(R.id.longanswerandcode);
        question = (TextView) findViewById(R.id.question);
/*
 text.setVisibility(View.INVISIBLE);
                        multiple_choice.setVisibility(View.VISIBLE);
                        //add options to the spinner


                        // Array of choices

                        ArrayList<String> colors = new ArrayList<>();
                        String[] pcs = rawQuestions.get(current).split(",")[2].split("#");
                        for (String t : pcs) {
                            colors.add(t);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(takequiz.this, android.R.layout.simple_spinner_item, colors);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        multiple_choice.setAdapter(adapter);


                        question.setText(questionPieces[1]);
                        type = "M";
 */

        type = "";
        btn.setText("Submit");
        //create button click listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct = false;
                String[] questionPieces = rawQuestions.get(current - 1).split(",");
                Log.e("type", type);
                if (type.equalsIgnoreCase("S")) {
                    //short answer

                    Log.e("text.getText()", text.getText().toString());
                    String answer = questionPieces[2];
                    Log.e("answer is", answer);
                    correct = text.getText().toString().equalsIgnoreCase(answer);
                    if (correct) {
                        Log.e("correct", "is true");
                    }
                } else if (type.equalsIgnoreCase("C")) {
                    //code
//                    String answer = questionPieces[2] ;
//                    correct=text.getText().toString().equalsIgnoreCase(answer);

                    correct = true;
                } else if (type.equalsIgnoreCase("M")) {
                    //multiple choice
                    int answer = Integer.valueOf(questionPieces[questionPieces.length - 2]);
                    correct = (answer == multiple_choice.getSelectedItemPosition());
                    Log.e("answer: " + answer, ", and was: " + multiple_choice.getSelectedItemPosition());
//TODO check that this works correctly....
                }


                if (correct && current < numQuestions) {

                    Log.e("question", rawQuestions.get(current).toString());
                    String[] pieces = rawQuestions.get(current).split(",");
                    //identified a shortanswer question

                    if (pieces[0].startsWith("S")) {
                        //identified a short answer question
                        //set field visibility
                        text.setVisibility(View.VISIBLE);
                        multiple_choice.setVisibility(View.INVISIBLE);
                        //set question
                        question.setText(pieces[1]);
                        current++;
                        type = "S";
                    }
                    //identified a coding question.

                    else if (pieces[0].contains("M")) {
                        text.setVisibility(View.INVISIBLE);
                        multiple_choice.setVisibility(View.VISIBLE);
                        //add options to the spinner


                        // Array of choices

                        ArrayList<String> colors = new ArrayList<>();
                        String[] pcs = rawQuestions.get(current).split(",")[2].split("#");
                        for (String t : pcs) {
                            colors.add(t);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(takequiz.this, android.R.layout.simple_spinner_item, colors);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        multiple_choice.setAdapter(adapter);


                        question.setText(questionPieces[1]);
                        type = "M";
                    }


                    //identified a coding question.

                    else if (pieces[0].startsWith("C")) {
                        text.setVisibility(View.VISIBLE);
                        multiple_choice.setVisibility(View.INVISIBLE);
                        question.setText(pieces[1]);
                        current++;
                        type = "C";
                    }

                }

                if (correct && current < numQuestions) {

                    current++;
                }
                if (correct && btn.getText().equals("finish")) {
                    startActivity(new Intent(takequiz.this, MainPage.class));
                }
                if (correct && current == numQuestions) {
                    btn.setText("finish");
                }
                Log.e("current", "" + current);
            }
        });


        //hide all input sources by default.
        multiple_choice.setVisibility(View.GONE);
        text.setVisibility(View.GONE);

        //reveal input sources as the questions get answered

        if (current == 0) {

            String[] questionPieces = rawQuestions.get(current).split(",");
            //identified a shortanswer question

            if (questionPieces[0].startsWith("S")) {
                //identified a short answer question
                //set field visibility
                text.setVisibility(View.VISIBLE);
                multiple_choice.setVisibility(View.INVISIBLE);
                //set question
                question.setText(questionPieces[1]);
                current++;
                type = "S";
            }
            //identified a coding question.

            else if (questionPieces[0].contains("M")) {
                text.setVisibility(View.INVISIBLE);
                multiple_choice.setVisibility(View.INVISIBLE);
                question.setText(questionPieces[1]);
                current++;
                type = "M";
            }


            //identified a coding question.

            else if (questionPieces[0].startsWith("C")) {
                text.setVisibility(View.VISIBLE);
                multiple_choice.setVisibility(View.INVISIBLE);
                question.setText(questionPieces[1]);
                current++;
                type = "C";
            }
        }
    }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_takequiz, menu);
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
