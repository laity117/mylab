package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static int now = 0;
    private static int max = 6;
    private static Map<Integer, Integer> map = new HashMap<>();
    private static Map<Integer, String> word = new HashMap<>();
    static {
        map.put(0, R.drawable.p0);
        map.put(1, R.drawable.p1);
        map.put(2, R.drawable.p2);
        map.put(3, R.drawable.p2);
        map.put(4, R.drawable.p3);
        map.put(5, R.drawable.p4);

        word.put(0, "图片0");
        word.put(1, "图片1");
        word.put(2, "图片2");
        word.put(3, "图片3");
        word.put(4, "图片4");
        word.put(5, "图片5");
    }
    private GestureDetector mGestureDetector;
    private ImageView iv;
    private ScaleGestureDetector mScaleGestureDetector;

    private int visible1;
    private int visible2;
    private int visible3;
    private Button button;

    private TextView text1;
    private TextView text2;



    private Matrix matrix;
    private float preScale;//之前的伸缩值
    private float curScale;//当前的伸缩值

    private Bitmap sourceBitmap;
    private int[] screenSize; //屏幕尺寸信息
    private float translateX = 0; //平移到屏幕中心的X轴距离
    private float translateY = 0; //平移到屏幕中心的Y轴距离
    private boolean isChangeScaleType; //是否转换为Matrix模式


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        visible1 = View.INVISIBLE;
        visible2 = View.INVISIBLE;
        button = findViewById(R.id.button);
        visible3 = View.INVISIBLE;


        iv = findViewById(R.id.img);
        mGestureDetector = new GestureDetector(new simpleGestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(this,new MyScaleGestureDetector());

        screenSize = getScreenSize();
        init(0);

        iv.setOnTouchListener(this);

        iv.setFocusable(true);
        iv.setClickable(true);
        iv.setLongClickable(true);
    }

    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }

    //获取屏幕尺寸
    private int[] getScreenSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] screenSize=new int[2];
        screenSize[0] = displayMetrics.widthPixels;
        screenSize[1] = displayMetrics.heightPixels;
        return screenSize;
    }

    public void commit(View view) {
        visible2 = View.INVISIBLE;
        text2.setVisibility(visible2);
        visible3 = View.INVISIBLE;
        button.setVisibility(visible3);
        String s = text2.getText().toString();
        word.put(now, s);
        text1.setText(word.get(now));
        visible1 = View.VISIBLE;
        text1.setVisibility(visible1);
    }

    private class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector){
            if(isChangeScaleType) {
                iv.setScaleType(ImageView.ScaleType.MATRIX);
                isChangeScaleType=false;
            }
            curScale=detector.getScaleFactor() * preScale;// 当前的伸缩值 * 之前的伸缩值 保持连续性

            if(curScale>1.5||curScale<0.1) {// 当放大倍数大于5或者缩小倍数小于0.1倍 就不伸缩图片 返回true取消处理伸缩手势事件
                preScale=curScale;
                return true;
            }

            matrix.setScale(curScale, curScale, iv.getWidth() / 2, iv.getHeight() / 2);//在屏幕中心伸缩
            matrix.preTranslate(translateX, translateY);//使图片平移到屏幕中心显示

            try {
                Thread.sleep(42);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            iv.setImageMatrix(matrix);//改变矩阵值显示图片
            preScale = curScale;//保存上一次的伸缩值
            return false;
        }
    }

    public void init(int x) {
        text1.setText(word.get(x));
        curScale=1.0f;
        sourceBitmap = BitmapFactory.decodeResource(getResources(),map.get(x));

        translateX = screenSize[0] / 2 - sourceBitmap.getWidth() / 2;//使图片显示在中心
        translateY = screenSize[1] / 2 - sourceBitmap.getHeight() / 2;

        matrix = new Matrix();
        preScale = screenSize[0] * 1.0f / sourceBitmap.getWidth();//图片完全显示的伸缩值
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        iv.setImageBitmap(sourceBitmap);
        isChangeScaleType=true;

        iv.setImageResource(map.get(x));
    }

    public void next(int x) {
        ImageView imageView = findViewById(R.id.img);
        now = now + x;
        if (now == max) {
            now = 0;
        }
        if (now == -1) {
            now = max - 1;
        }
        init(now);
    }

    private class simpleGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;

        // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
        ///轻击一下屏幕，立刻抬起来，才会有这个触发
        //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
        public boolean onSingleTapUp(MotionEvent e) {
            visible2 = View.INVISIBLE;
            text2.setVisibility(visible2);
            visible3 = View.INVISIBLE;
            button.setVisibility(visible3);
            if (visible1 == View.INVISIBLE) {
                visible1 = View.VISIBLE;
                text1.setVisibility(visible1);
            } else {
                visible1 = View.INVISIBLE;
                text1.setVisibility(visible1);
            }
            return true;
        }

        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
        public void onLongPress(MotionEvent e) {
            visible1 = View.INVISIBLE;
            text1.setVisibility(visible1);
            visible2 = View.VISIBLE;
            text2.setText(word.get(now));
            text2.setVisibility(visible2);
            visible3 = visible2;
            button.setVisibility(visible3);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {


            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling left
                next(1);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
                next(-1);
            }
            return true;
        }
    }
}