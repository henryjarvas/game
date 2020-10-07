package com.example.make_a_call;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyingfishview extends View
{
    //making the fish fly on the screen
    private Bitmap fish[] = new Bitmap[2];
    private int fishx = 10;
    private int fishy;
    private int fishspeed;

    private int yellowx , yellowy , yellowspeed = 8;
    private Paint yellowpaint = new Paint();

    private int greenx, greeny, greenspeed = 12;
    private Paint greenpaint = new Paint();

    private int redx, redy, redspeed = 10;
    private Paint redpaint = new Paint();

    //calculate the score
    private int score, lifecounteroffish;

    private int canvaswidth, canvasheight;

    //ontapig the screen, let the fish fly
    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorepaint = new Paint();

    //drawing the life of the fish
    private Bitmap life[] = new Bitmap[2];

    public flyingfishview(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowpaint.setColor(Color.YELLOW);
        yellowpaint.setAntiAlias(false);

        greenpaint.setColor(Color.GREEN);
        greenpaint.setAntiAlias(false);

        redpaint.setColor(Color.RED);
        redpaint.setAntiAlias(false);

        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);

life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

fishy = 550;
score = 0;
lifecounteroffish = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvaswidth = canvas.getWidth();
        canvasheight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minfishy = fish[0].getHeight();
        int maxfishy = canvasheight - fish[0].getHeight() *3;
        fishy = fishy + fishspeed;

        if (fishy<minfishy)
        {
            fishy = minfishy;
        }

        if (fishy> maxfishy)
        {
            fishy = maxfishy;
        }
        fishspeed = fishspeed +2;

        if (touch)
        {
          canvas.drawBitmap(fish[1],fishx,fishy,null);
          touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishx, fishy, null);
        }


//the randomly moving yellow ball
        yellowx = yellowx - yellowspeed;

        if (hitballchecker(yellowx, yellowy))
        {
            score = score + 10;
            yellowx = yellowx - 100;
        }

        if (yellowx <0)
        {
            yellowx = canvaswidth + 21;
            yellowy = (int) Math.floor(Math.random() * (maxfishy - minfishy) ) +minfishy;
        }
        canvas.drawCircle(yellowx,yellowy,10,yellowpaint);//size of the yellow ball

//the randomly moving green ball
        greenx = greenx - greenspeed;

        if (hitballchecker(greenx, greeny))
        {
            score = score + 20; //increase the score on eating this ball
            greenx = greenx - 100;
        }

        if (greenx <0)
        {
            greenx = canvaswidth + 21;
            greeny = (int) Math.floor(Math.random() * (maxfishy - minfishy) ) +minfishy;
        }
        canvas.drawCircle(greenx,greeny,10,greenpaint);//size of the yellow ball

        //the randomly moving red ball
        redx = redx - redspeed;

        if (hitballchecker(redx, redy)) {
            redx = redx - 100;
            lifecounteroffish--;


            if (lifecounteroffish == 0)
            {
                Toast.makeText(getContext(),"GAME OVER",Toast.LENGTH_SHORT).show();

                Intent gameoverintent = new Intent(getContext(),game_over.class);
                gameoverintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameoverintent);
            }
        }

        if (redx <0)
        {
            redx = canvaswidth + 21;
            redy = (int) Math.floor(Math.random() * (maxfishy - minfishy) ) +minfishy;
        }
        canvas.drawCircle(redx,redy,10,redpaint);//size of the red ball

        canvas.drawText("score :" + score ,20,60,scorepaint);

        for (int i=0; i<3; i++)
        {
            int x = (int) (580 + life[0].getWidth() *1.5 * i);
            int y =30;

            if (i <lifecounteroffish)
            {
                canvas.drawBitmap(life[0], x, y,null);
            }
            else
                {
                    canvas.drawBitmap(life[0], x, y,null);

                }
        }

    }

    public boolean hitballchecker(int x , int y)
    {
        if (fishx < x && x < ( fishx + fish[0].getWidth() ) && fishy < y && y < (fishy + fish[0].getHeight()) ) {

            return true;
        }
        return false;

        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            touch = true;
            fishspeed = -22;
        }
        return true;
    }
}
