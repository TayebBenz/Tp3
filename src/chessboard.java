import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class chessboard {

    private static int counter = 0;
    int[][] board;

    int Nsquares;
    public chessboard(int N)
    {
        board = new int[N][N];
        Nsquares = N;
        for(int i = 0; i< N; i++)
        {
            for(int j = 0; j<N;j++)
            {
                board[i][j] = 0;
            }
        }
    }

    public void printBoard()
    {
        for (int i = 0; i < Nsquares; i++)
        {
            for( int j = 0; j<Nsquares ;j++)
            {
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }
    }

    public int placeQueen(int x, int y,int N)
    {
        int result = 0;
        if(board[x][y] == 2)
        {
            result++;
        }
        for(int j = 0; j< N; j++)
        {
            result += changeBoardValue(x,j,2  );
        }
        for(int K = 0; K< N; K++)
        {
            result += changeBoardValue(K,y,2  );
        }
        for( int k = x,j = y;k < N && j< N; k++,j++)
        {
            result += changeBoardValue(k,j,2  );
        }
        for( int k = x,j = y;k < N && j >= 0; k++,j--)
        {
            result += changeBoardValue(k,j,2  );
        }
        for( int k = x,j = y;k >= 0 && j >= 0; k--,j--)
        {
            result += changeBoardValue(k,j,2  );
        }
        for( int k = x,j = y;k >= 0 && j <N; k--,j++)
        {
            result += changeBoardValue(k,j,2  );
        }
        updateResult(result,changeBoardValue(x,y,1  ));
        return result;
    }

    public int changeBoardValue(int x, int y, int newValue)
    {
        if(newValue == 2 && board[x][y] == 1)
        {
            return 1;
        }
        board[x][y] = newValue;
        return 0;
    }

    public int updateResult(int oldV,int newV)
    {
        if(newV == 0)
        {
            return oldV;
        }
        else{
            return newV;
        }
    }
}

