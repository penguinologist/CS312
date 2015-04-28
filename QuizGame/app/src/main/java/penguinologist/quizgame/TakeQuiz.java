package penguinologist.quizgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class TakeQuiz extends ActionBarActivity {
    int current;
    int numQuestions;
    ArrayList<String> rawQuestions;
    Button btn;
    Spinner multiple_choice;
    EditText text;
    TextView question;
    String type;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takequiz);

        Bundle b = getIntent().getExtras();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout4);
        rl.setBackgroundColor(Color.LTGRAY);

        name = (String) b.get("name");
        //add all the questions of the quiz to this arraylist
        rawQuestions = new ArrayList<>();

        ParseQuery<ParseObject> t = new ParseQuery<>("Quiz");
        t.whereEqualTo("Quizname", name);
        t.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject i : parseObjects) {
                        //make sure the format is correct....

                        if (i.getString("Type").contains("S")) {
                            //it's a short answer question
                            //SA1,What decade was the waterfall model 'invented'?,1970s,QUIZ1

                            rawQuestions.add(i.getString("Type") + "," + i.getString("Question") + "," + i.getString("RightAnswer") + "," + i.getString("Quizname"));
                        } else if (i.getString("Type").startsWith("C")) {
                            //it's a coding question
                            //C3,Write code (assume main is already set up) to print out Hello World,System.out.println("Hello World!");,,Hello World!,QUIZ2

                            rawQuestions.add(i.getString("Type") + "," + i.getString("Question") + "," + i.getString("RightAnswer") + ",," + i.getString("PossibleAnswers") + "," + i.getString("Quizname"));
                        } else {
                            //it's a MC question
                            //MC2,Which of the following was not true about SE in the 1950s?,Customers were developers#Hardware was cheap#Developers were generally engineers/mathematicians,2,QUIZ1
                            rawQuestions.add(i.getString("Type") + "," + i.getString("Question") + "," + i.getString("PossibleAnswers") + "," + i.getString("RightAnswer") + "," + i.getString("Quizname"));

                        }
                    }


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
                        multiple_choice.setVisibility(View.VISIBLE);
                        ArrayList<String> colors = new ArrayList<>();
                        String[] pcs = rawQuestions.get(current).split(",")[2].split("#");
                        for (String t : pcs) {
                            colors.add(t);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TakeQuiz.this, android.R.layout.simple_spinner_item, colors);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        multiple_choice.setAdapter(adapter);


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


                    numQuestions = rawQuestions.size();
                }

                if (numQuestions == 1) {
                    btn.setText("Finish");
                }

            }
        });
        current = 0;


        //get all the questions related to the quiz
        btn = (Button) findViewById(R.id.button8);
        multiple_choice = (Spinner) findViewById(R.id.multiplechoice);
        multiple_choice.setGravity(17);
        text = (EditText) findViewById(R.id.longanswerandcode);
        question = (TextView) findViewById(R.id.question);
        type = "";
        btn.setText("Submit");
        //create button click listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct = false;
                String[] questionPieces = rawQuestions.get(current - 1).split(",");
                if (type.equalsIgnoreCase("S")) {
                    //short answer
                    String answer = questionPieces[2];
                    correct = text.getText().toString().equalsIgnoreCase(answer);
                } else if (type.equalsIgnoreCase("C")) {
                    //it's a code question... we're just going to accept any response for now...

                    //saving the response to Parse for teachers to grade later...
                    ParseObject code = new ParseObject("Code");
                    code.put("Username", ParseUser.getCurrentUser().get("username"));
                    code.put("Quizname", name);
                    code.put("submission", text.getText().toString().trim());
                    code.saveInBackground();


                    correct = true;
                } else if (type.equalsIgnoreCase("M")) {
                    //multiple choice
                    int answer = Integer.valueOf(questionPieces[questionPieces.length - 2]);
                    correct = (answer == multiple_choice.getSelectedItemPosition());
                }

                //wrong answer
                if (correct == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ooops! That's incorrect. Try again!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if (correct && current < numQuestions) {
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
                        ArrayList<String> colors = new ArrayList<>();
                        String[] pcs = rawQuestions.get(current).split(",")[2].split("#");
                        for (String t : pcs) {
                            colors.add(t);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TakeQuiz.this, android.R.layout.simple_spinner_item, colors);
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
                if (correct && btn.getText().equals("Finish")) {

                    // sumbit grades to parse
                    ParseObject grade = new ParseObject("Grades");
                    grade.put("Username", ParseUser.getCurrentUser().get("username"));
                    grade.put("Quizname", name);
                    grade.put("Score", current);
                    grade.put("MaxGrade", numQuestions);
                    grade.saveInBackground();

                    //start a new activity
                    startActivity(new Intent(TakeQuiz.this, MainPage.class));
                    finish();
                }
                if (correct && current == numQuestions) {
                    btn.setText("Finish");
                }
            }
        });

        //hide all input sources by default.
        multiple_choice.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
        //reveal input sources as the questions get shown

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

