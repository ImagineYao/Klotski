package com.imagine.klotski;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.GridLayout;

public class GameBoard extends GridLayout {
    public static int level;
    public static int spanSize;
    public static int[][] pieces = new int[5][4];
    public static int padding = 4;

    public GameBoard(Context context) {
        super(context);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setColumnCount(4);
        setRowCount(5);
        setPadding(padding, padding, padding, padding);
        setSpanSize(getContext());
        initGameBoard();
    }

    public void clearPieces() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i][j] = 0;
            }
        }
    }

    public void setSpanSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        int length = Math.min(screenHeight, screenWidth);
        int margin = getResources().getDimensionPixelOffset(R.dimen.margin);
        spanSize = (length - margin * 2 - padding * 2) / 4;
    }

    public void addPiece(String name, int row, int column, int rowSpan, int columnSpan) {
        Piece piece = new Piece(getContext(), name);

        piece.setSpan(rowSpan, columnSpan);
        piece.setPosition(row, column);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(row, rowSpan);
        params.columnSpec = GridLayout.spec(column, columnSpan);
        params.height = rowSpan * spanSize;
        params.width = columnSpan * spanSize;
        addView(piece, params);

        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                pieces[row + i][column + j] = 1;
            }
        }
    }

    public void initGameBoard() {
        switch (level) {
            case 1:
                clearPieces();
                addLevelOnePieces();
                break;
            case 2:
                clearPieces();
                addLevelTwoPieces();
                break;
            case 3:
                clearPieces();
                addLevelThreePieces();
                break;
            case 4:
                clearPieces();
                addLevelFourPieces();
                break;
            case 5:
                clearPieces();
                addLevelFivePieces();
                break;
        }
    }

    public void addLevelOnePieces() {
        addPiece("兵", 0, 0, 1, 1);
        addPiece("关   羽", 0, 1, 1, 2);
        addPiece("兵", 0, 3, 1, 1);
        addPiece("赵\n\n云", 1, 0, 2, 1);
        addPiece("曹   操", 1, 1, 2, 2);
        addPiece("黄\n\n忠", 1, 3, 2, 1);
        addPiece("马\n\n超", 3, 0, 2, 1);
        addPiece("兵", 3, 1, 1, 1);
        addPiece("兵", 3, 2, 1, 1);
        addPiece("张\n\n飞", 3, 3, 2, 1);
    }

    public void addLevelTwoPieces() {
        addPiece("赵\n\n云", 0, 0, 2, 1);
        addPiece("黄\n\n忠", 0, 1, 2, 1);
        addPiece("兵", 0, 2, 1, 1);
        addPiece("兵", 0, 3, 1, 1);
        addPiece("兵", 1, 2, 1, 1);
        addPiece("兵", 1, 3, 1, 1);
        addPiece("马\n\n超", 2, 0, 2, 1);
        addPiece("张\n\n飞", 2, 1, 2, 1);
        addPiece("关   羽", 2, 2, 1, 2);
        addPiece("曹   操", 3, 2, 2, 2);
    }

    public void addLevelThreePieces() {
        addPiece("兵", 0, 0, 1, 1);
        addPiece("兵", 1, 0, 1, 1);
        addPiece("曹   操", 0, 1, 2, 2);
        addPiece("兵", 0, 3, 1, 1);
        addPiece("兵", 1, 3, 1, 1);
        addPiece("赵\n\n云", 2, 0, 2, 1);
        addPiece("关   羽", 2, 1, 1, 2);
        addPiece("黄\n\n忠", 2, 3, 2, 1);
        addPiece("马\n\n超", 3, 1, 2, 1);
        addPiece("张\n\n飞", 3, 2, 2, 1);
    }

    public void addLevelFourPieces() {
        addPiece("赵\n\n云", 0, 0, 2, 1);
        addPiece("曹   操", 0, 1, 2, 2);
        addPiece("兵", 0, 3, 1, 1);
        addPiece("兵", 1, 3, 1, 1);
        addPiece("关   羽", 2, 2, 1, 2);
        addPiece("黄\n\n忠", 3, 0, 2, 1);
        addPiece("兵", 3, 1, 1, 1);
        addPiece("兵", 4, 1, 1, 1);
        addPiece("马\n\n超", 3, 2, 2, 1);
        addPiece("张\n\n飞", 3, 3, 2, 1);
    }

    public void addLevelFivePieces() {
        addPiece("张\n\n飞", 0, 0, 2, 1);
        addPiece("曹   操", 0, 1, 2, 2);
        addPiece("赵\n\n云", 0, 3, 2, 1);
        addPiece("黄\n\n忠", 2, 0, 2, 1);
        addPiece("关   羽", 2, 1, 1, 2);
        addPiece("马\n\n超", 2, 3, 2, 1);
        addPiece("兵", 3, 1, 1, 1);
        addPiece("兵", 3, 2, 1, 1);
        addPiece("兵", 4, 0, 1, 1);
        addPiece("兵", 4, 3, 1, 1);
    }
}
