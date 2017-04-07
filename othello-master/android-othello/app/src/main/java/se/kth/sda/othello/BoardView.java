package se.kth.sda.othello;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;
import se.kth.sda.othello.imp.Statistic;

/**
 * Created by robertog on 2/7/17.
 */

public class BoardView extends View {
    private Board model;


    public void setModel(Board model) {
        this.model = model;
    }

    public interface BoardViewListener {
        public void onClick(int x, int y);
    }

    private final GestureDetector mDetector;
    private BoardViewListener eventListener = null;

    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public BoardView(Context context) {
        super(context);

        mDetector = new GestureDetector(this.getContext(), new mListener());
    }
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetector(this.getContext(), new mListener());
    }

    public void setEventListener(BoardViewListener listener) {
        this.eventListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        Paint lightgreenPaint = new Paint();
        Paint darkgreenPaint = new Paint();
        //Paint paint = new Paint();
        //canvas.drawLine(100, 200, 300, 400, paint);
        //canvas.drawLine(200, 100, 500, 200, paint);
        //paint.setColor(Color.BLACK);

        darkgreenPaint.setARGB(210, 0, 125, 180);
        lightgreenPaint.setARGB(250, 0, 125, 180);
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                RectF rect = new RectF(width/8*i, height/8*j, width/8*(i+1), height/8*(j+1));
                if ((i+j)%2 == 0)
                    canvas.drawRect(rect, lightgreenPaint);
                else
                    canvas.drawRect(rect, darkgreenPaint);
            }
        }

        /*
        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.kth);
        draw.setBounds(width/8*xpos, height/8*ypos, width/8*(xpos+1), height/8*(ypos+1));
        draw.draw(canvas);
        */

        if (model == null)
            return;


        //Paint redPaint = new Paint();
        //redPaint.setARGB(255, 255, 0, 0);
        //Paint greenPaint = new Paint();
        //greenPaint.setARGB(255, 0, 255, 0);
        for (Node node : model.getNodes())
            if (node.isMarked()) {

                //x==true ? player1Disc(node, width, height, canvas) : player2disc(node, width, height, canvas);
                if (node.getOccupantPlayerId().equals("P1")){
                    player1Disc(node, width, height, canvas);
                }else {
                    player2Disc(node, width, height, canvas);
                }


                //canvas.drawCircle(
                //      (float) (width / 8 * (node.getXCoordinate() + 0.5)),
                //    (float) (height / 8 * (node.getYCoordinate() + 0.5)),
                //  (float) (Math.min(width / 16, height / 16)),
                //color);
            }
    }
    //Drawing the black disc
    public void player1Disc (Node node, int width, int height, Canvas canvas) {

        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.black);
        draw.setBounds(node.getXCoordinate()*width/8, node.getYCoordinate()*height/8, (node.getXCoordinate()+1)*width/8, (node.getYCoordinate()+1)*height/8);
        draw.draw(canvas);
    }
    //Drawing the white disc
    public void player2Disc (Node node, int width, int height, Canvas canvas) {

        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.white);
        draw.setBounds(node.getXCoordinate()*width/8, node.getYCoordinate()*height/8, (node.getXCoordinate()+1)*width/8, (node.getYCoordinate()+1)*height/8);
        draw.draw(canvas);

    }

    //Compute the discs for the board
    public  Statistic analyse() {
        int P1Discs = 0;
        int P2Discs = 0;
        for (Node node : model.getNodes()) {
            if (node.isMarked()) {
                if (node.getOccupantPlayerId().equals("P1")) {
                    P1Discs += 1;
                } else {
                    P2Discs += 1;
                }
            }

        }
        return new Statistic(P1Discs, P2Discs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (eventListener == null)
            return true;

        boolean result = mDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                eventListener.onClick((int)(event.getX()/this.getWidth()*8), (int)(event.getY()/this.getHeight()*8));

                result = true;
            }
        }
        return result;
    }
}
