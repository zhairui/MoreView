package com.wdj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.w3c.dom.Attr;

/**
 * Created by zhairui on 2017/1/13.
 */

public class MoreView extends View{
    String normalString="";
    TextPaint mTextPaint,mTextPaintmore;
    int ScreenWidth;
    StaticLayout layout2,layout;
    private String mText;
    private int mTextColor;
    private int mTextSize;
    public MoreView(Context context) {
        this(context,null);
    }

    public MoreView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MoreView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MoreView);
        try{
            mText=ta.getString(R.styleable.MoreView_text);
            if(mText==null)
                throw new NullPointerException("不能为空!");
            mTextColor=ta.getColor(R.styleable.MoreView_color,getResources().getColor(R.color.colorPrimary));
            mTextSize=ta.getDimensionPixelSize(R.styleable.MoreView_textsize,20);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ta.recycle();
        }
        init(context);
    }
    private void init(Context context){
        mTextPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);

        mTextPaintmore=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintmore.setAntiAlias(true);
        mTextPaintmore.setTextSize(mTextSize);
        mTextPaintmore.setColor(mTextColor);
        ScreenWidth=ScreenUtils.getScreenWidth(context);
    }
    public void setData(String message){
        normalString=message;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!TextUtils.isEmpty(normalString)){
            canvas.save();
            layout=new StaticLayout(normalString,mTextPaint,ScreenWidth-50, Layout.Alignment.ALIGN_NORMAL
                    ,1.0F,0.0F,true);

            layout.draw(canvas);
            canvas.restore();
            layout2=new StaticLayout(normalString+mText,mTextPaintmore,ScreenWidth-50, Layout.Alignment.ALIGN_NORMAL
                    ,1.0F,0.0F,true);
            if(layout2.getLineCount()>layout.getLineCount()){
                canvas.drawText(mText,0,layout2.getHeight()-mTextPaintmore.getFontMetrics().bottom,mTextPaintmore);
            }else{
                canvas.drawText(mText,layout.getLineWidth(layout.getLineCount()-1),layout.getHeight()-mTextPaintmore.getFontMetrics().bottom,mTextPaintmore);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            float x=event.getX();
            float y=event.getY();
            if(layout2.getLineCount()>layout.getLineCount()){ //另起一行
                if((x>=0 && x<=mTextPaintmore.measureText(mText))&&
                        (y>=layout2.getHeight()-(mTextPaintmore.getFontMetrics().descent-mTextPaintmore.getFontMetrics().ascent)
                                &&y <=layout2.getHeight())){
                    onMoreClicklistener.onClick();
                    return true;
                }
            }else{ //同一行
                if((x>=layout.getLineWidth(layout.getLineCount()-1) && x<=(layout.getLineWidth(layout.getLineCount()-1) + mTextPaintmore.measureText(mText)))
                        &&(y>=(layout.getHeight()-(mTextPaintmore.getFontMetrics().descent-mTextPaintmore.getFontMetrics().ascent))
                        &&y <=layout.getHeight())){
                    onMoreClicklistener.onClick();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }
    public interface OnMoreClicklistener{
        void onClick();
    }
    private OnMoreClicklistener onMoreClicklistener;
    public void setOnMoreClicklistener(OnMoreClicklistener onMoreClicklistener){
        this.onMoreClicklistener=onMoreClicklistener;
    }
}
