package com.example.android.quizjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    int randNumber;
    CheckBox checkBox1, checkBox2, checkBox3;
    AlertDialog.Builder mBuilder;
    int score, total;
    TextView numberquestion;
    int[] checkRandNumber;
    boolean login;
    EditText nameField;
    Button donebutton;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameField = (EditText) findViewById(R.id.name_field);
        //Editable nameEditable = nameField.getText();
        //name = nameEditable.toString();

        donebutton = (Button) findViewById(R.id.donebu);

    }


    public void gotogame(View b) {
        nameField.setVisibility(View.GONE);
        TextView your_name = (TextView) findViewById(R.id.your_name);
        your_name.setVisibility(View.GONE);
        donebutton.setVisibility(View.GONE);
        numberquestion = (TextView) findViewById(R.id.question);
        numberquestion.setVisibility(View.VISIBLE);
        name = nameField.getText().toString();
        checkRandNumber = new int[9];
        randNumber = randInt(0, 8);
        img = (ImageView) findViewById(R.id.whole_question);
        img.setVisibility(View.VISIBLE);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox1.setVisibility(View.VISIBLE);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox2.setVisibility(View.VISIBLE);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkBox3.setVisibility(View.VISIBLE);
        Button nextb = (Button) findViewById(R.id.nextb);
        nextb.setVisibility(View.VISIBLE);
        Button stopb = (Button) findViewById(R.id.stopb);
        stopb.setVisibility(View.VISIBLE);


        createQuestion(randNumber);



    }

    int[] imageArray = {R.drawable.question1, R.drawable.question2, R.drawable.question3, R.drawable.question4,
            R.drawable.question5, R.drawable.question6, R.drawable.question7, R.drawable.question8,
            R.drawable.question9};

    String[][] answersArray = {{"A. 123456", "B. An exception could be thrown at runtime.", "C. 654321"},
            {"A. 0 ", "B. No output", "C. Compilation fails "},
            {"A. class F implements B,C{ }", "B. class F implements B{ }", "C. class Fextends A,E{ }"},
            {"A. Will produce output as false", "B. Compilation fails due to error at line 3.", "C. Will produce output as true."},
            {"A. true true ", "B. false false", "C. false true"},
            {"A. Compilation fails due to line 3.", " B. 2", "C. An exception will be thrown in runtime."},
            {" A. 10", "B. Compilation fails. ", " C. 8"},
            {"A. Compilation fails due to an error on line 12.", " B. Compilation fails due to an error on line 8. ", " C. Compilation fails due to an error on line 7. "},
            {" A. 4", "B. 10", "C. Compilation fails."}};


    String[] arrayDialog = {"The length of a array 'a' is 6, so the value of the variable 'i' is 5.",
            "You can't enter code between the try and catch clause. Thus line 7 causes failure.",
            "We can use the keyword 'extends' to either two classes or two interfaces.",
            "Always are objects so,  'true' will be returned",
            "Indexing of array elements begins with 0",
            "We can use several steps. We have assigned two one dimensions int arrays to the two dimensional array a. So, the code compiles fine and produces 2 as output",
            "Inside  a class method, when a variable has the same name as one of the instances variables, the local variable shadows the instance variable inside the method block",
            "Both variables 'a' and 'b' are local, so they can't be accessed from other methods. Trying to use variable b in print() will therefore cause an error",
            "The life of variable x ends with the end of the 'for' loop as the scope of the variable x is limited to within that loop."};

    //Check which answer is right, 1=A, 2=B,3=C
    int[] solutionArray = {3, 3, 2, 3, 1, 2, 3, 1, 2};

    public int randInt(int min, int max) {
        boolean repeatNum = true;
        boolean w;
        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        while (repeatNum) {
            w = false;
            for (int i = 0; i == 8; i++) {
                if (checkRandNumber[i] == randomNum) {
                    randomNum = rand.nextInt((max - min) + 1) + min;
                    w = true;
                }

            }
            if (w == false) {
                repeatNum = false;
            }
        }
        return randomNum;
    }

    public void createQuestion(int randNumber) {
        total = total + 1;
        checkRandNumber[total - 1] = randNumber;
        img.setImageResource(imageArray[randNumber]);
        checkBox1.setText(answersArray[randNumber][0]);
        checkBox2.setText(answersArray[randNumber][1]);
        checkBox3.setText(answersArray[randNumber][2]);
        numberquestion.setText("Question: " + total);
    }


    public void stopQuiz(View v) {
        mBuilder = new AlertDialog.Builder(MainActivity.this);

        mBuilder.setTitle("Your Score");
        mBuilder.setMessage("You did " + score + " right out of " + (total - 1));
        mBuilder.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                total = 0;
                score = 0;
                randNumber = randInt(0, 8);
                createQuestion(randNumber);
            }
        });
        mBuilder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Use an intent to launch an email app.
                // Send the order summary in the email body.
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "QuizJava App");
                intent.putExtra(Intent.EXTRA_TEXT, ""+name +" did " + score + " right out of " + (total - 1));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });

        mBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                total = 0;
                score = 0;
                finish();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();

    }

    public void finishGame() {
        boolean j = true;
        while (j == true) {
            for (long h = 0; h == 10000; h++) {
                h = h + 1;
            }
            j = false;
        }
        mBuilder.setTitle("Finish!");
        mBuilder.setMessage("The game ends here. You did " + score + " right out of " + (total));

        mBuilder.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                total = 0;
                score = 0;
                randNumber = randInt(0, 8);
                createQuestion(randNumber);
            }
        });

        mBuilder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Use an intent to launch an email app.
                // Send the order summary in the email body.
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "QuizJava App");
                intent.putExtra(Intent.EXTRA_TEXT, ""+name + "did " + score + " right out of " + (total));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });

        mBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                total = 0;
                score = 0;
                finish();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }


    public void CheckAnswer(View view) throws InterruptedException {

        TextView dddd = (TextView) findViewById(R.id.question);
        dddd.setVisibility(View.VISIBLE);
        mBuilder = new AlertDialog.Builder(MainActivity.this);

        if (checkBox1.isChecked() && checkBox2.isChecked() || checkBox1.isChecked() && checkBox3.isChecked() || checkBox2.isChecked() && checkBox3.isChecked() || checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {
            mBuilder.setTitle("Attention");
            mBuilder.setMessage("Please choose only one option");
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();

        } else {

            if (checkBox1.isChecked()) {
                if ((solutionArray[randNumber]) == 1) {
                    score = score + 1;
                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Correct!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                } else {
                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Wrong!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                }


            } else if (checkBox2.isChecked()) {

                if ((solutionArray[randNumber]) == 2) {
                    score = score + 1;
                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Correct!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }

                } else {

                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Wrong!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                }

            } else if (checkBox3.isChecked()) {
                if ((solutionArray[randNumber]) == 3) {
                    score = score + 1;

                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Correct!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                } else {
                    if (total == 9) {
                        finishGame();
                    } else {
                        mBuilder.setTitle("Wrong!");
                        mBuilder.setMessage(arrayDialog[randNumber]);
                        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                randNumber = randInt(0, 8);
                                createQuestion(randNumber);
                            }
                        });
                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                }
            }
            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
        }


    }
}



