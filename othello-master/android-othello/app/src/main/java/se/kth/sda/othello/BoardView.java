package se.kth.sda.othello;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;

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
        Paint blackPaint = new Paint();
        Paint whitePaint = new Paint();

        whitePaint.setARGB(255, 255, 255, 255);
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                RectF rect = new RectF(width/8*i, height/8*j, width/8*(i+1), height/8*(j+1));
                if ((i+j)%2 == 0)
                    canvas.drawRect(rect, blackPaint);
                else
                    canvas.drawRect(rect, whitePaint);
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

        Paint redPaint = new Paint();
        redPaint.setARGB(255, 255, 0, 0);
        Paint greenPaint = new Paint();
        greenPaint.setARGB(255, 0, 255, 0);
        for (Node node : model.getNodes()) {
            if (node.isMarked()) {
                Paint color = node.getOccupantPlayerId().equals("P1") ? redPaint : greenPaint;
                canvas.drawCircle(
                        (float)(width/8*(node.getXCoordinate()+0.5)),
                        (float)(height/8*(node.getYCoordinate()+0.5)),
                        (float)(Math.min(width/16,height/16)),
                        color);
            }
        }
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