package com.example.wraith55.swipeblok;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.lang.Object;
import android.os.Handler;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.security.AccessController.getContext;


public class MainActivity extends ActionBarActivity {

    GridView gridView;
    static final Random rand = new Random();
    String[] numbers = new String[16];
    String[] numbersOld = new String[16];
    public static final String empty = "()";
    public static int explodeSpot;
    String[] characters = {"1","2","3","4", "5", "6", "7"};
    public boolean gameOver = false;

    Context myContext;
    public static MainActivity activity;
    public int points;
    public int highScore = 0;
    public String target;
    public int difficulty = 4;



    float[] touchData = new float[2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activity = this;

        gridView = (GridView) findViewById(R.id.gridView1);

        myContext = this.getApplicationContext();
        final Application myApplication = this.getApplication();


        startNewGame(gridView);

        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //myActivity.dispatchTouchEvent(event);
                MainActivity.activity.onTouchEvent(event);

                //((MainActivity)getContext()).dispatchTouchEvent(event);
                //myActivity.dispatchTouchEvent(event);
                return true;

            }
        });

        Button undoButton = (Button) findViewById(R.id.undobutton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.undo(activity.gridView);
            }
        });


        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }


    public boolean onTouchEvent(MotionEvent event){
        float x, y;
        if(gameOver){
            return true;
        }

        if(anyLeft() < 0){
            gameOver = true;
        }

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();
            touchData[0] = x;
            touchData[1] = y;
        }

        if(event.getActionMasked() == MotionEvent.ACTION_UP){
            x = event.getX();
            y = event.getY();
            float diffX = x - touchData[0];
            float diffY = y - touchData[1];

            if(Math.abs(diffX) > Math.abs(diffY)){
                if(diffX < 0){
                    //swiped left
                    swipeLeft();
                }
                else{
                    //swiped right
                    swipeRight();
                }
            }
            else{
                if(diffY > 0){
                    //swiped down
                    swipeDown();
                }
                else{
                    //swiped up
                    swipeUp();
                }
            }

        }

        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //myActivity.dispatchTouchEvent(event);
                MainActivity.activity.onTouchEvent(event);

                //((MainActivity)getContext()).dispatchTouchEvent(event);
                //myActivity.dispatchTouchEvent(event);
                return true;

            }
        });


        //this.postInvalidate();
        return true;
    }

    private void swipeDown(){
        boolean moved = false;
        String[] numbersTmp = new String[numbers.length];
        numbersTmp = arrayCopy(numbers, numbersTmp);
        for(int i=8;i>=0;i=i-4){
            for(int j=i;j<i+4;j++){
                if(numbers[j] == empty) continue;
                if(numbers[j+4] == empty){
                    numbers[j+4] = numbers[j];
                    numbers[j] = empty;
                    moved = true;
                }
                else if(numbers[j] == characters[characters.length-1]){
                   explode(j);
                   points += 100;
                   moved = true;
                }
                else if(numbers[j] == numbers[j+4]){
                    numbers[j] = numbers[j+4] = empty;
                    points += 10;
                    moved = true;
                }

            }
        }
        if(moved == true){
            numbersOld = arrayCopy(numbersTmp,numbersOld);
            spawnNew();

        }
        update();

    }

    private void swipeUp(){
        //Toast.makeText(getApplicationContext(),"Swiped up!", Toast.LENGTH_SHORT).show();
        boolean moved = false;
        String[] numbersTmp = new String[numbers.length];
        numbersTmp = arrayCopy(numbers,numbersTmp);
        for(int i=4; i<16;i=i+4){
            for(int j=i;j<i+4;j++){
                if(numbers[j] == empty) continue;
                else if(numbers[j-4] == empty){
                    numbers[j-4] = numbers[j];
                    numbers[j] = empty;
                    moved = true;
                }
                else if(numbers[j] == characters[characters.length-1]){
                    explode(j);
                    points += 100;
                    moved = true;
                }
                else if(numbers[j]==numbers[j-4]){
                    numbers[j] = numbers[j-4] = empty;
                    points += 10;
                    moved=true;
                }

            }
        }

        if(moved == true){
            numbersOld = arrayCopy(numbersTmp, numbersOld);
            spawnNew();
        }
        update();
    }

    private void swipeRight(){
        boolean moved = false;
        String[] numbersTmp = new String[numbers.length];
        numbersTmp = arrayCopy(numbers, numbersTmp);
        for(int i=2;i>=0;i--){
            for(int j=i;j<16;j=j+4){
                if(numbers[j] == empty) continue;
                else if(numbers[j+1] == empty){
                    numbers[j+1] = numbers[j];
                    numbers[j] = empty;
                    moved = true;
                }
                else if(numbers[j] == characters[characters.length-1]){
                    explode(j);
                    points += 100;
                    moved = true;
                }
                else if(numbers[j] == numbers[j+1]){
                    numbers[j]=numbers[j+1]=empty;
                    points += 10;
                    moved = true;
                }

            }
        }
        if(moved == true){
            numbersOld = arrayCopy(numbersTmp, numbersOld);
            spawnNew();
        }
        update();
    }

    private void swipeLeft(){
        boolean moved = false;
        String[] numbersTmp = new String[numbers.length];
        numbersTmp = arrayCopy(numbers, numbersTmp);
        for(int i=1;i<4;i++){
            for(int j=i;j<16;j=j+4){
                if(numbers[j] == empty) continue;
                else if(numbers[j-1] == empty){
                    numbers[j-1] = numbers[j];
                    numbers[j]=empty;
                    moved = true;
                }
                else if(numbers[j] == characters[characters.length-1]){
                    explode(j);
                    points += 100;
                    moved = true;
                }
                else if(numbers[j] == numbers[j-1]){
                    numbers[j]=numbers[j-1]=empty;
                    points += 10;
                    moved = true;
                }

            }
        }
        if(moved == true){
            numbersOld = arrayCopy(numbersTmp, numbersOld);
            spawnNew();
        }
        update();
    }

    private void spawnNew(){
        int addSpot = anyLeft();
        if(addSpot<0){
            gameOver();
            return;
        }
        boolean bomb = false;
        if (rand.nextInt(100) > 90) bomb = true;
        if(bomb){
            numbers[addSpot] = characters[characters.length - 1];
        }
        else{
            numbers[addSpot] = characters[rand.nextInt(characters.length - difficulty)];
        }
        update();

    }

    private int anyLeft(){
        boolean roomLeft = false;
        ArrayList<Integer> freeSpots = new ArrayList<Integer>();
        for(int i=0; i<numbers.length; i++){
            if(numbers[i] == empty){
                roomLeft = true;
                freeSpots.add(i);
            }
        }
        if(roomLeft){
            return freeSpots.get(rand.nextInt(freeSpots.size()));
        }
        else return -1;
    }

    public void update(){

        if(points > highScore){
            highScore = points;
        }
        if(points > (5-difficulty) * 1000 && difficulty > 2) difficulty--;

        setContentView(R.layout.activity_main);

        TextView levelView = (TextView) findViewById(R.id.level);
        levelView.setText("Level " + (5-difficulty));

        gridView = (GridView) findViewById(R.id.gridView1);
        TextView pointView = (TextView)findViewById(R.id.pointText);
        pointView.setText("Points: " + points);

        TextView highScoreView = (TextView) findViewById(R.id.highscore);
        highScoreView.setText("High score: " + highScore);

        TextView gameOverView = (TextView)findViewById(R.id.gameover);
        if(gameOver){
            gameOverView.setText("Game Over!");
            gridView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return true;

                }
            });
        }
        else gameOverView.setText("");


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);*/

        ImageAdapter adapter = new ImageAdapter(activity);
        adapter.update(numbers);

        gridView.setAdapter(adapter);



    }



    public void explode(int position){
        boolean up,down,left,right;
        up = down = left = right = true;

        if (position + 4 > 15) down = false;
        if(position - 4 < 0) up = false;
        if((position % 4 - 1) < 0) left = false;
        if ((position % 4 + 1) > 3) right = false;

        if (up && left) numbers[position - 5] = empty;
        if(up) numbers[position - 4] = empty;
        if(up && right) numbers[position - 3] = empty;
        if(left) numbers[position - 1] = empty;
        if(right) numbers[position + 1] = empty;
        if(down && left) numbers[position + 3] = empty;
        if(down) numbers[position + 4] = empty;
        if(down && right) numbers[position + 5] = empty;
        numbers[position] = empty;

        /*if (up && left) numbers[position - 5] = characters[characters.length-1];
        if(up) numbers[position - 4] = characters[characters.length-1];
        if(up && right) numbers[position - 3] = characters[characters.length-1];
        if(left) numbers[position - 1] = characters[characters.length-1];
        if(right) numbers[position + 1] = characters[characters.length-1];
        if(down && left) numbers[position + 3] = characters[characters.length-1];
        if(down) numbers[position + 4] = characters[characters.length-1];
        if(down && right) numbers[position + 5] = characters[characters.length-1];
        numbers[position] = characters[characters.length-1];

        explodeSpot = position;


        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView1);
        ImageAdapter adapter = new ImageAdapter(activity);
        adapter.update(numbers);
        gridView.setAdapter(adapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                removeExplosion();
            }
        }, 200);*/

    }

    public void removeExplosion(){

        boolean up,down,left,right;
        up = down = left = right = true;
        int position = explodeSpot;

        if (position + 4 > 15) down = false;
        if(position - 4 < 0) up = false;
        if((position % 4 - 1) < 0) left = false;
        if ((position % 4 + 1) > 3) right = false;

        if (up && left) numbers[position - 5] = empty;
        if(up) numbers[position - 4] = empty;
        if(up && right) numbers[position - 3] = empty;
        if(left) numbers[position - 1] = empty;
        if(right) numbers[position + 1] = empty;
        if(down && left) numbers[position + 3] = empty;
        if(down) numbers[position + 4] = empty;
        if(down && right) numbers[position + 5] = empty;
        numbers[position] = empty;
    }


    public void undo(View v){
        points -= 100;
        if (points < 0) points = 0;
        gameOver = false;

        numbers = arrayCopy(numbersOld, numbers);

        update();

        Button undoButton = (Button) findViewById(R.id.undobutton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.undo(activity.gridView);
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.activity.onTouchEvent(event);
                return true;
            }
        });

    }

    public void startNewGame(View v){
        for(int i=0; i < numbers.length;i++){
            numbers[i] = empty;
        }


        difficulty = 4;

        numbers[rand.nextInt(16)] = characters[rand.nextInt(characters.length - difficulty)];
        numbersOld = arrayCopy(numbers, numbersOld);
        points = 0;

        setContentView(R.layout.activity_main);


        /*TextView gameOverView = (TextView)activity.findViewById(R.id.gameover);
        gameOverView.setText("poop");*/
        //gameOverView.setVisibility(View.GONE);

        gameOver = false;
        update();

        Button newgamebutton = (Button) findViewById(R.id.newgamebutton);
        newgamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.startNewGame(activity.gridView);
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.activity.onTouchEvent(event);
                return true;

            }
        });


    }

    public void gameOver(){


        /*setContentView(R.layout.activity_main);

        TextView gameOverView = (TextView)findViewById(R.id.gameover);
        gameOverView.setText("GAME OVER!");*/
        gameOver = true;
        update();

    }

    /**
     * copies contents of arr1 to arr2
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] arrayCopy(String[] arr1, String[] arr2){
        for (int i=0; i<arr1.length;i++){
            arr2[i] = arr1[i];
        }
        return arr2;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
