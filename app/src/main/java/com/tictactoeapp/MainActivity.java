package com.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private boolean isPlayerOneTurn = true;
    private int[][] board = new int[3][3]; // 0 = empty, 1 = player 1 (X), 2 = player 2 (O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        removeStatusBarWithBlackIcon();
    }
    public void onGridButtonClick(View view) {
        Button clickedButton = (Button) view;
        String tag = view.getTag().toString();
        int row = Character.getNumericValue(tag.charAt(0));
        int col = Character.getNumericValue(tag.charAt(1));

        if (board[row][col] != 0) {
            Toast.makeText(this, "Cell already occupied", Toast.LENGTH_SHORT).show();
            return;
        }

        board[row][col] = isPlayerOneTurn ? 1 : 2;
        clickedButton.setText(isPlayerOneTurn ? "X" : "O");

        if (checkForWin()) {
            String winner = isPlayerOneTurn ? "Player 1 (X)" : "Player 2 (O)";
            Toast.makeText(this, winner + " wins!", Toast.LENGTH_LONG).show();
            disableBoard();
        } else if (isBoardFull()) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
        }

        isPlayerOneTurn = !isPlayerOneTurn;
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableBoard() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }
    }

    public void resetGame(View view) {
        isPlayerOneTurn = true;
        board = new int[3][3];

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
            button.setEnabled(true);
        }
    }

}