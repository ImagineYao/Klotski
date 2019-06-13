package com.imagine.klotski;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Piece extends FrameLayout {

    private static int[][] pieces = GameBoard.pieces;
    private static int spanSize = GameBoard.spanSize;
    private static int padding = GameBoard.padding;
    private int row, column;
    private int rowSpan, columnSpan;

    public Piece(final Context context, String name) {
        super(context);
        setPadding(padding, padding, padding, padding);

        TextView label = new TextView(getContext());
        label.setText(name);
        label.setTextSize(40);
        label.setBackgroundResource(R.drawable.block_brown_light);
        label.setGravity(Gravity.CENTER);

        LayoutParams layoutParams = new LayoutParams(-1, -1);
        addView(label, layoutParams);

        setOnTouchListener(new OnTouchListener() {
            float vx, vy;
            float ex, ey;
            int[] moveSpan;
            float moveX, moveY;
            boolean xMoving = false, yMoving = false;
            float precision = 1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ex = event.getX(); // 触摸点在Piece中的坐标
                        ey = event.getY();
                        vx = v.getX(); // Piece左上角在GameBoard中的坐标
                        vy = v.getY();
                        moveSpan = canMoveSpan();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (moveSpan[0] != 0) { // 上移
                            if (!xMoving) {
                                moveY = vy - v.getY();
                                if (moveY >= 0 && moveY <= moveSpan[0] * spanSize) {
                                    v.setY(event.getY() - ey + v.getY());
                                    if (moveY > precision) {
                                        yMoving = true;
                                    }
                                }
                            }
                        }

                        if (moveSpan[1] != 0) { // 下移
                            if (!xMoving) {
                                moveY = v.getY() - vy;
                                if (moveY >= 0 && moveY <= moveSpan[1] * spanSize) {
                                    v.setY(event.getY() - ey + v.getY());
                                    if (moveY > precision) {
                                        yMoving = true;
                                    }
                                }
                            }
                        }

                        if (moveSpan[2] != 0) { // 左移
                            if (!yMoving) {
                                moveX = vx - v.getX();
                                if (moveX >= 0 && moveX <= moveSpan[2] * spanSize) {
                                    v.setX(event.getX() - ex + v.getX());
                                    if (moveX > precision) {
                                        xMoving = true;
                                    }
                                }
                            }
                        }

                        if (moveSpan[3] != 0) { //右移
                            if (!yMoving) {
                                moveX = v.getX() - vx;
                                if (moveX >= 0 && moveX <= moveSpan[3] * spanSize) {
                                    v.setX(event.getX() - ex + v.getX());
                                    if (moveX > precision) {
                                        xMoving = true;
                                    }
                                }
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        moveX = Math.abs(v.getX() - vx);
                        moveY = Math.abs(v.getY() - vy);
                        int xSpan = Math.round((v.getX() - vx) / spanSize);
                        int ySpan = Math.round((v.getY() - vy) / spanSize);
                        v.setX(vx + xSpan * spanSize);
                        v.setY(vy + ySpan * spanSize);
                        updatePosition(xSpan, ySpan);
                        xMoving = false;
                        yMoving = false;
                        int step = (xSpan != 0 || ySpan != 0) ? 1 : 0;
                        GameActivity.getGameActivity().increaseStep(step);
                        if (gameIsOver()) {
                            GameActivity.getGameActivity().finishGame();
                        } else if (moveX >= spanSize * 1.0 / 3 || moveY >= spanSize * 1.0 / 3) {
                            GameActivity.getGameActivity().audioPlayer.playMoveSound();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void setSpan(int rowSpan, int columnSpan) {
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void updatePosition(int xSpan, int ySpan) {
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                pieces[row + i][column + j] = 0;
            }
        }
        this.row = row + ySpan;
        this.column = column + xSpan;
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                pieces[row + i][column + j] = 1;
            }
        }
    }

    public int[] canMoveSpan() {
        int[] spans = {0, 0, 0, 0};

        if (canMoveUp()) {
            spans[0] = 1;
            if (columnSpan == 1 && row > 1 && (pieces[row - 2][column] == 0)) {
                spans[0] = 2;
            }
        }

        if (canMoveDown()) {
            spans[1] = 1;
            if (columnSpan == 1 && row < (4 - rowSpan) && pieces[row + rowSpan + 1][column] == 0) {
                spans[1] = 2;
            }
        }

        if (canMoveLeft()) {
            spans[2] = 1;
            if (rowSpan == 1 && column > 1 && pieces[row][column - 2] == 0) {
                spans[2] = 2;
            }
        }

        if (canMoveRight()) {
            spans[3] = 1;
            if (rowSpan == 1 && column < (3 - columnSpan) && pieces[row][column + columnSpan + 1] == 0) {
                spans[3] = 2;
            }
        }

        return spans;
    }

    public boolean canMoveUp() {
        if (row == 0) return false;
        return (pieces[row - 1][column] == 0) && (pieces[row - 1][column + columnSpan - 1] == 0);
    }

    public boolean canMoveDown() {
        if (row == 5 - rowSpan) return false;
        return (pieces[row + rowSpan][column] == 0) && (pieces[row + rowSpan][column + columnSpan - 1] == 0);
    }

    public boolean canMoveLeft() {
        if (column == 0) return false;
        return (pieces[row][column - 1] == 0) && (pieces[row + rowSpan - 1][column - 1] == 0);
    }

    public boolean canMoveRight() {
        if (column == 4 - columnSpan) return false;
        return (pieces[row][column + columnSpan] == 0) && (pieces[row + rowSpan - 1][column + columnSpan] == 0);
    }

    boolean gameIsOver() {
        return this.row == 3 && this.column == 1 && this.rowSpan == 2 && this.columnSpan == 2;
    }
}
