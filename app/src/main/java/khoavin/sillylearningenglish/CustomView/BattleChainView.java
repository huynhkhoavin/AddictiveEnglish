package khoavin.sillylearningenglish.CustomView;

import android.graphics.Color;
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

    private Paint battlePaint;

    public String getBattleState() {
        return battleState;
    }

    public int getWinColor() {
        return winColor;
    }

    public int getFailureColor() {
        return failureColor;
    }

    public int getLockColor() {
        return lockColor;
    }

    public float getItemPadding() {
        return itemPadding;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
        invalidate();
        requestLayout();
    }

    public void setBattleState(String battleState) {
        this.battleState = battleState;
        invalidate();
        requestLayout();
    }

    public void setWinColor(int winColor) {
        this.winColor = winColor;
        invalidate();
        requestLayout();
    }

    public void setFailureColor(int failureColor) {
        this.failureColor = failureColor;
        invalidate();
        requestLayout();
    }

    public void setLockColor(int lockColor) {
        this.lockColor = lockColor;
        invalidate();
        requestLayout();
    }

    public void setItemPadding(float itemPadding) {
        this.itemPadding = itemPadding;
        invalidate();
        requestLayout();
    }

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;


        int radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;

        battlePaint.setStyle(Style.FILL);
        battlePaint.setAntiAlias(true);
        battlePaint.setColor(winColor);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, battlePaint);

        battlePaint.setColor(failureColor);
        battlePaint.setTextAlign(Paint.Align.CENTER);
        battlePaint.setTextSize(50);
        canvas.drawText("haha", viewWidthHalf, viewHeightHalf, battlePaint);
    }
}
