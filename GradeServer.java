package penguinologist.quizgame;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class GradeServer extends Activity implements Runnable {

    private static StringBuffer buffer = new StringBuffer();
    private static BufferedWriter gradeFile = null;
    private File gradefile;

    public void run() {
        // gradeFile = new File(context.getFilesDir(), "grades.txt");
        while (true) {
            try {
                Thread.sleep(6 * 1000);
                FileOutputStream fos;

//TODO fix the fact that the app is unable to read from the grades.txt fil because it's inaccessible...

                try {
                    fos = openFileOutput("src/quizgame/grades.txt", Context.MODE_APPEND);


                    fos.write(buffer.toString().getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.e("error", "file not found");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception ie) {
                ie.printStackTrace();
            }
        }

    }

    public static void record(String item) {
        //System.err.println("adding " + item + " to buffer");
        buffer.append(item);
    }


}