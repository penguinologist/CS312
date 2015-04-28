package penguinologist.quizgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class CreateQuestion extends ActionBarActivity {
    RadioButton sa;
    RadioButton mc;
    RadioButton cq;
    EditText question;
    EditText answer;
    RadioButton mcanswer1;
    RadioButton mcanswer2;
    RadioButton mcanswer3;
    RadioButton mcanswer4;
    TextView tv;
    EditText quizlabel;
    Button set1;
    Button set2;
    Button set3;
    Button set4;
    Button submit;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        RelativeLayout curr = (RelativeLayout) findViewById(R.id.RelativeLayout3);
        curr.setBackgroundColor(Color.LTGRAY);
        question = (EditText) findViewById(R.id.editText4);
        answer = (EditText) findViewById(R.id.editText3);
        mcanswer1 = (RadioButton) findViewById(R.id.radioButton4);
        mcanswer2 = (RadioButton) findViewById(R.id.radioButton5);
        mcanswer3 = (RadioButton) findViewById(R.id.radioButton6);
        mcanswer4 = (RadioButton) findViewById(R.id.radioButton7);
        tv = (TextView) findViewById(R.id.textView10);
        quizlabel = (EditText) findViewById(R.id.editText5);
        set1 = (Button) findViewById(R.id.button9);
        set2 = (Button) findViewById(R.id.button10);
        set3 = (Button) findViewById(R.id.button11);
        set4 = (Button) findViewById(R.id.button13);
        submit = (Button) findViewById(R.id.button6);
        back = (Button) findViewById(R.id.button14);
        //hiding all the elements immediatly except for the first 3 choices...
        set1.setVisibility(View.INVISIBLE);
        set2.setVisibility(View.INVISIBLE);
        set3.setVisibility(View.INVISIBLE);
        set4.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        mcanswer1.setVisibility(View.INVISIBLE);
        mcanswer2.setVisibility(View.INVISIBLE);
        mcanswer3.setVisibility(View.INVISIBLE);
        mcanswer4.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        quizlabel.setVisibility(View.INVISIBLE);
        quizlabel.setText("");
        quizlabel.setHint("Enter quiz name here");
        sa = (RadioButton) findViewById(R.id.radioButton);
        mc = (RadioButton) findViewById(R.id.radioButton2);
        cq = (RadioButton) findViewById(R.id.radioButton3);
        question.setText("");
        question.setHint("Enter the question here");
        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sa.setChecked(true);
                mc.setChecked(false);
                cq.setChecked(false);
                // show the short answer thing
                question.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                quizlabel.setVisibility(View.VISIBLE);
                answer.setText("");
                answer.setHint("Enter the answer here");
                // hide the mc stuff
                mcanswer1.setVisibility(View.INVISIBLE);
                mcanswer2.setVisibility(View.INVISIBLE);
                mcanswer3.setVisibility(View.INVISIBLE);
                mcanswer4.setVisibility(View.INVISIBLE);
                set1.setVisibility(View.INVISIBLE);
                set2.setVisibility(View.INVISIBLE);
                set3.setVisibility(View.INVISIBLE);
                set4.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
            }
        });
        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sa.setChecked(false);
                mc.setChecked(true);
                cq.setChecked(false);
                // show the short answer thing
                mcanswer1.setVisibility(View.VISIBLE);
                mcanswer2.setVisibility(View.VISIBLE);
                mcanswer3.setVisibility(View.VISIBLE);
                mcanswer4.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                quizlabel.setVisibility(View.VISIBLE);
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.VISIBLE);
                set3.setVisibility(View.VISIBLE);
                set4.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                answer.setText("");
                answer.setHint("Enter answer here, then click set");
                // hide the mc stuff
                question.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);

            }
        });
        cq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sa.setChecked(false);
                mc.setChecked(false);
                cq.setChecked(true);
                // show the short answer thing
                question.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                quizlabel.setVisibility(View.VISIBLE);
                answer.setText("");
                answer.setHint("Enter the answer here");
                // hide the mc stuff
                mcanswer1.setVisibility(View.INVISIBLE);
                mcanswer2.setVisibility(View.INVISIBLE);
                mcanswer3.setVisibility(View.INVISIBLE);
                mcanswer4.setVisibility(View.INVISIBLE);
                set1.setVisibility(View.INVISIBLE);
                set2.setVisibility(View.INVISIBLE);
                set3.setVisibility(View.INVISIBLE);
                set4.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateQuestion.this, MainPage.class));
                finish();
            }
        });

        mcanswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcanswer1.setChecked(true);
                mcanswer2.setChecked(false);
                mcanswer3.setChecked(false);
                mcanswer4.setChecked(false);
            }
        });

        mcanswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcanswer1.setChecked(false);
                mcanswer2.setChecked(true);
                mcanswer3.setChecked(false);
                mcanswer4.setChecked(false);
            }
        });

        mcanswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcanswer1.setChecked(false);
                mcanswer2.setChecked(false);
                mcanswer3.setChecked(true);
                mcanswer4.setChecked(false);
            }
        });

        mcanswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcanswer1.setChecked(false);
                mcanswer2.setChecked(false);
                mcanswer3.setChecked(false);
                mcanswer4.setChecked(true);
            }
        });

        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().length() > 0) {
                    mcanswer1.setText(answer.getText().toString());
                    answer.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid answer", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().length() > 0) {
                    mcanswer2.setText(answer.getText().toString());
                    answer.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid answer", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        set3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().length() > 0) {
                    mcanswer3.setText(answer.getText().toString());
                    answer.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid answer", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        set4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().length() > 0) {
                    mcanswer4.setText(answer.getText().toString());
                    answer.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid answer", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sa.isChecked() || cq.isChecked()) {
                    if (answer.getText().toString().length() > 0 && question.getText().toString().length() > 0) {
                        Log.e("got", "it");
                        wrapitup();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Fill in all the fields please...", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (mc.isChecked()) {

                    int count = 0;
                    if (!mcanswer1.getText().equals("SampleAnswer")) {
                        count++;
                    }
                    if (!mcanswer2.getText().equals("SampleAnswer")) {
                        count++;
                    }
                    if (!mcanswer3.getText().equals("SampleAnswer")) {
                        count++;
                    }
                    if (!mcanswer4.getText().equals("SampleAnswer")) {
                        count++;
                    }


                    if (count >= 2) {
                        if (question.getText().toString().length() == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(), "What's the question???", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {

                            if (mcanswer1.isChecked() && !mcanswer1.getText().toString().equals("SampleAnswer")) {
                                Log.e("Got", "it");
                                wrapitup();
                            } else if (mcanswer2.isChecked() && !mcanswer2.getText().toString().equals("SampleAnswer")) {
                                Log.e("Got", "it");
                                wrapitup();
                            } else if (mcanswer3.isChecked() && !mcanswer3.getText().toString().equals("SampleAnswer")) {
                                Log.e("Got", "it");
                                wrapitup();
                            } else if (mcanswer4.isChecked() && !mcanswer4.getText().toString().equals("SampleAnswer")) {
                                Log.e("Got", "it");
                                wrapitup();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Select one of the possible answers to be the correct one.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Fill in more than 1 answer please...", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    //
                }

            }
        });

    }

    private void wrapitup() {
        if (mc.isChecked()) {
            //this part gets the answers separated by #
            String answer = "";

            if (!mcanswer1.getText().toString().equals("SampleAnswer")) {
                answer += mcanswer1.getText().toString().trim() + "#";
            }
            if (!mcanswer2.getText().toString().equals("SampleAnswer")) {
                answer += mcanswer2.getText().toString().trim() + "#";
            }
            if (!mcanswer3.getText().toString().equals("SampleAnswer")) {
                answer += mcanswer3.getText().toString().trim() + "#";
            }
            if (!mcanswer4.getText().toString().equals("SampleAnswer")) {
                answer += mcanswer4.getText().toString().trim() + "#";
            }
            answer = answer.substring(0, answer.length() - 1);
            //this next part gets the index of the selected answer
            int selected = 0;
            if (mcanswer1.isChecked()) {
                selected = 0;
            }
            if (mcanswer2.isChecked()) {
                selected = 1;
            }
            if (mcanswer3.isChecked()) {
                selected = 2;
            }
            if (mcanswer4.isChecked()) {
                selected = 3;
            }

            Log.e("saving", "....");
            ParseObject quiz = new ParseObject("Quiz");
            quiz.put("Quizname", quizlabel.getText().toString().trim());//sets the quizlabel
            quiz.put("Question", question.getText().toString().trim());//sets the question
            quiz.put("Type", "MC");
            quiz.put("PossibleAnswers", answer);
            quiz.put("RightAnswer", selected + "");
            quiz.saveInBackground();
            try {
                quiz.fetch();
            }catch (Exception E){
                Log.e("oops","I did it again");
            }
            startActivity(new Intent(CreateQuestion.this, MainPage.class));
            finish();


        } else if (sa.isChecked()) {
            ParseObject quiz = new ParseObject("Quiz");
            quiz.put("Quizname", quizlabel.getText().toString().trim());//sets the quizlabel
            quiz.put("Question", question.getText().toString().trim());//sets the question
            quiz.put("Type", "SA");
            quiz.put("RightAnswer", answer.getText().toString().trim());
            quiz.saveInBackground();
            startActivity(new Intent(CreateQuestion.this, MainPage.class));
            finish();

        } else if (cq.isChecked()) {
            ParseObject quiz = new ParseObject("Quiz");
            quiz.put("Quizname", quizlabel.getText().toString().trim());//sets the quizlabel
            quiz.put("Question", question.getText().toString().trim());//sets the question
            quiz.put("Type", "C");
            quiz.put("RightAnswer", answer.getText().toString().trim());
            quiz.saveInBackground();
            startActivity(new Intent(CreateQuestion.this, MainPage.class));
            finish();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Select one of the question types to get started", Toast.LENGTH_SHORT);
            toast.show();
        }

//


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_question, menu);
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
