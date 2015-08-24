package cn.liyueyang.pinball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by liyy on 2015/8/23.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final int SPEED = 20;
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private int ballX = 0, ballY = 0;
    private int ballPreX = 0, ballPreY = 0;
    private int ballRadius = 20;
    private static final int GAME_READY = 0;
    private static final int GAME_START = 1;
    private static final int GAME_OVER = -1;
    private static final int GAME_PAUSE = 2;
    private int gameState = GAME_READY;
    private boolean moveToRight = false;
    private boolean moveToDown = false;


    public MainView(Context context) {
        super(context);
        sfh = getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(0xff8B00FF);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ballX = getWidth() / 2;
        ballY = getHeight() - 50;
        myDraw();
        th = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameState = GAME_OVER;
    }

    private void myDraw() {
        Canvas canvas = sfh.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(ballX, ballY, ballRadius, paint);
        sfh.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameState == GAME_READY && event.getAction() == MotionEvent.ACTION_UP) {
            gameState = GAME_START;
            th.start();
        } else if (gameState == GAME_START && event.getAction() == MotionEvent.ACTION_UP) {
            gameState = GAME_PAUSE;
        } else if (gameState == GAME_PAUSE && event.getAction() == MotionEvent.ACTION_UP) {
            gameState = GAME_START;
            th = new Thread(this);
            th.start();
        }
        return true;
    }

    @Override
    public void run() {
        while (gameState == GAME_START) {
            calcBallLocation();
            drawBall();
        }
    }

    /**
     * »­Çò
     */
    private void drawBall() {
        Canvas canvas = sfh.lockCanvas();
        canvas.drawColor(Color.WHITE);
        int color = 0;
        int perHeight = getHeight() / 7;
        if (ballY > perHeight * 6) {
            color = 0xffff0000;
        } else if (ballY > perHeight * 5) {
            color = 0xffff7F00;
        } else if (ballY > perHeight * 4) {
            color = 0xffffff00;
        } else if (ballY > perHeight * 3) {
            color = 0xff00ff00;
        } else if (ballY > perHeight * 2) {
            color = 0xff00ffff;
        } else if (ballY > perHeight * 1) {
            color = 0xff0000ff;
        } else if (ballY > perHeight * 0) {
            color = 0xff8b00ff;
        }

        paint.setColor(color);
        canvas.drawCircle(ballX, ballY, ballRadius, paint);
        sfh.unlockCanvasAndPost(canvas);
    }

    /**
     * ¼ÆËãÇòµÄ×ø±ê
     */
    private void calcBallLocation() {
        if (!isCollisionWall()) {
            ballPreX = ballX;
            ballPreY = ballY;
            if (moveToRight) {
                ballX = ballX + SPEED;
            } else {
                ballX = ballX - SPEED;
            }
            if (moveToDown) {
                ballY = ballY + SPEED;
            } else {
                ballY = ballY - SPEED;
            }

        }
    }

    /**
     * ¼ì²âÇòÊÇ·ñÅöµ½Ç½±Ú
     *
     * @return
     */
    private boolean isCollisionWall() {
        int tempX = ballX;
        int tempY = ballY;
        if (ballX <= ballRadius || ballX >= getWidth() - ballRadius) {
            ballY = 2 * ballY - ballPreY;
            ballX = ballPreX;
            ballPreX = tempX;
            ballPreY = tempY;
            if (ballX > ballPreX) {
                moveToRight = true;
            } else {
                moveToRight = false;
            }
            return true;
        }
        if (ballY <= ballRadius || ballY >= getHeight() - ballRadius) {
            ballX = 2 * ballX - ballPreX;
            ballY = ballPreY;
            ballPreX = tempX;
            ballPreY = tempY;
            if (ballY > ballPreY) {
                moveToDown = true;
            } else {
                moveToDown = false;
            }
            return true;
        }
        return false;
    }
}
