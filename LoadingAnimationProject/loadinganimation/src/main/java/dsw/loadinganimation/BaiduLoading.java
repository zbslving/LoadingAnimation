package dsw.loadinganimation;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
* Created by dsw on 2015/10/11.
*/
public class BaiduLoading extends View {
    //画笔
    private Paint mPaint;
    //圆形的半径
    private int circleRadius = 20;
    //浮动的边长
    private int halfDistance = 60;
    private float density;
    //颜色的下标
    private int colorIndex = 0;
    //指定的颜色
    private int colors[] = new int[3];
    //中心点的x、y，当前点的x
    private int centerX,centerY,currentX;
    //最左边的起始点坐标x
    private int startX;
    public BaiduLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.BaiduLoading);
        colors[0]=typedArray.getColor(R.styleable.BaiduLoading_FirstColor,Color.parseColor("#EE454A"));
        colors[1]=typedArray.getColor(R.styleable.BaiduLoading_SecondColor,Color.parseColor("#2E9AF2"));
        colors[2]=typedArray.getColor(R.styleable.BaiduLoading_ThirdColor,Color.parseColor("#616161"));
        density = getResources().getDisplayMetrics().density;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        startX = (int) (centerX - halfDistance * density);
        if(currentX == 0){
            playAnimator();
        }else{
            drawCircle(canvas);
        }
    }

    /**
     * 执行动画
     */
    private void playAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startX,centerX - circleRadius/2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setDuration(400);
        valueAnimator.setRepeatMode(2);
        valueAnimator.start();
    }

    /**
     * 绘制圆形
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        if(Math.abs(currentX - centerX) <= circleRadius/2){
            colorIndex++;
            mPaint.setColor(colors[colorIndex % 3]);
        }else{
            mPaint.setColor(colors[colorIndex]);
        }
        canvas.drawCircle(centerX, centerY, circleRadius, mPaint);

        mPaint.setColor(colors[(colorIndex + 1) % 3]);
        canvas.drawCircle(currentX, centerY, circleRadius, mPaint);

        mPaint.setColor(colors[(colorIndex + 2) % 3]);
        canvas.drawCircle(2 *centerX - currentX,centerY,circleRadius,mPaint);

        if(colorIndex == 3)colorIndex=0;
    }
}

