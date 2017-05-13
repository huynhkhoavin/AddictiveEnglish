package khoavin.sillylearningenglish.Function.Arena.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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

    private char[] battleStateArray;
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
            battleStateArray =  battleState.toCharArray();
        }

    }

    public void SetBattleState(String battleState)
    {
        this.battleState = battleState;
        if(battleState != null)
        {
            battleStateArray =  battleState.toCharArray();
            invalidate();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewHeightHalf = this.getMeasuredHeight()/2;

        if(battleStateArray != null)
        {

            float startDraw = 0;
            for(int i = 0; i < battleStateArray.length; i++)
            {
                boolean key = false;
                if(battleStateArray[i] == '0')
                {
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(failureColor);
                    key = true;
                }
                else if(battleStateArray[i] == '1')
                {
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(winColor);
                    key = true;
                }
                else if(battleStateArray[i] == '2')
                {
                    battlePaint.setStyle(Style.STROKE);
                    battlePaint.setStrokeWidth(3);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(lockColor);
                    key = true;
                }
                else if(battleStateArray[i] == '3')
                {
                    battlePaint.setStyle(Style.FILL);
                    battlePaint.setAntiAlias(true);
                    battlePaint.setColor(lockColor);
                    key = true;
                }

                if(key)
                {
                    canvas.drawCircle( itemWidth + startDraw + i * itemWidth + i * (itemWidth + itemPadding), viewHeightHalf, itemWidth, battlePaint);
                }

                Log.d("BATTLESTATE", Integer.toString(i));

            }
        }
    }
}
