package khoavin.sillylearningenglish.CustomView;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattleChainView extends View {

    private String battleState;
    private int winColor;
    private int failureColor;
    private int lockColor;
    private float itemPadding;
    private float itemWidth;

    private String[] battleStateArray;
    private Paint battlePaint;

    public BattleChainView(Context context, AttributeSet attrs){
        super(context, attrs);

        battlePaint = new Paint();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BattleChainView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            battleState = a.getString(R.styleable.BattleChainView_battleState);
            winColor = a.getColor(R.styleable.BattleChainView_winColor, 0);
            failureColor = a.getInteger(R.styleable.BattleChainView_failureColor, 0);
            lockColor = a.getInteger(R.styleable.BattleChainView_lockColor, 0);
            itemPadding = a.getDimension(R.styleable.BattleChainView_contentPadding, 0f);
            itemWidth = a.getDimension(R.styleable.BattleChainView_itemWidth, 0f);
        } finally {
            a.recycle();
        }

        if(battleState != null)
        {
            battleStateArray =  battleState.split("");
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;

        float radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;

        radius = itemWidth;

        if(battleStateArray != null)
        {
            float startDraw = viewWidthHalf - 5 * radius - 2 * itemPadding;
            for(int i = 0; i < battleStateArray.length; i++)
            {
                if(battleStateArray[i].equals("0"))
                {
                    Log.d("BATTLESTATE", battleStateArray[i]);
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(failureColor);
                    canvas.drawCircle(startDraw + i * radius + i * itemPadding, viewHeightHalf, radius, battlePaint);
                }
                else if(battleStateArray[i].equals("1"))
                {
                    Log.d("BATTLESTATE", battleStateArray[i]);
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(winColor);
                    canvas.drawCircle( startDraw + i * radius + i * itemPadding, viewHeightHalf, radius, battlePaint);
                }
                else if(battleStateArray[i].equals("2"))
                {
                    Log.d("BATTLESTATE", battleStateArray[i]);
                    battlePaint.setStyle(Style.STROKE);
                    battlePaint.setStrokeWidth(3);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(lockColor);
                    canvas.drawCircle( startDraw + i * radius + i * itemPadding, viewHeightHalf, radius, battlePaint);
                }
                else if(battleStateArray[i].equals("3"))
                {
                    Log.d("BATTLESTATE", battleStateArray[i]);
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(lockColor);
                    canvas.drawCircle( startDraw + i * radius + i * itemPadding, viewHeightHalf, radius, battlePaint);
                }
            }
        }
    }
}
