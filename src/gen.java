import java.util.ArrayList;
import java.util.List;

public class gen {

    int[] indexTable;
    int fitnessScore;
//    static int minGlobal_fitnessScore = -1;
//    static chessboard minBoard;


    chessboard board;

    public gen(int N)
    {
        indexTable = new int[N];
    }

    public gen(int[] table,int N)
    {
        this.indexTable = table;
        board = new chessboard(N);
        calcualteFitnessFunction(N);
        newFitness(N);
//        printTable();
    }

    public void print(int[] lookupTable ,int N)
    {
        List<Integer> tableOne = new ArrayList<>();
        List<Integer> tableTwo = new ArrayList<>();
        for (int num: this.indexTable
             ) {
            if( num == 0){tableOne.add(num);}
            else{ tableTwo.add(num);}
        }
        System.out.print("Table one: ");
        for(int i = 0; i < indexTable.length; i++)
        {
            if(indexTable[i] == 0)
            {
                System.out.print(lookupTable[i]+",");
            }
        }
        System.out.println();
        System.out.print("table Two: ");
        for(int i = 0; i < indexTable.length; i++)
        {
            if(indexTable[i] == 1)
            {
                System.out.print(lookupTable[i]+",");
            }
        }
        System.out.println();       System.out.println("Score: "+this.fitnessScore);
    }

    public void calcualteFitnessFunction(int N)
    {
        int sumOne = 0;
        //calculate the difference between the 2 tables
        for(int index = 0; index < indexTable.length; index++)
        {
            sumOne += board.placeQueen(index,indexTable[index],N);
        }
        fitnessScore = sumOne;
//        if(minGlobal_fitnessScore == -1 || fitnessScore < minGlobal_fitnessScore)
//        {
//            minGlobal_fitnessScore =fitnessScore;
//            minBoard = this.board;
//        }
    }

    public void newFitness(int N)
    {
        fitnessScore = 0;
        for(int x = 0; x < N-1; x++)
        {
            int noneAttackedQueens= N-1-x;
            int y = indexTable[x];
//            System.out.println("X="+x+",nonAttackedQueens max="+noneAttackedQueens+" for queen("+x+","+y+")");
            for(int j = 0; j< N; j++)
            {
                noneAttackedQueens -= checkAttack(x,j);
            }
            for(int K = x; K< N; K++)
            {
                noneAttackedQueens -= checkAttack(K,y );
            }
            for( int k = x,j = y;k < N && j< N; k++,j++)
            {
                noneAttackedQueens -= checkAttack(k,j  );
            }
            for( int k = x,j = y;k < N && j >= 0; k++,j--)
            {
                noneAttackedQueens -= checkAttack(k,j  );
            }
            if(noneAttackedQueens+4 < 0){
                System.out.println("error noneAttackedQueens negative");
                this.board.printBoard();
                System.exit(2);
            }
            fitnessScore +=noneAttackedQueens+4;
        }
    }
    public int checkAttack(int x,int y)
    {
        if(board.board[x][y] == 1)
        {
//            System.out.println("queen(x,y)("+x+","+y+")"+" is attacked");
            return 1;
        }
        return 0;
    }

    public void printTable()
    {
        System.out.print("Gen "+ Main.iter+" : {"+indexTable[0]);
        for(int i = 1; i < indexTable.length-1;i++)
        {
            System.out.print(","+indexTable[i]);
        }
        System.out.print(","+indexTable[0]+"} ,");
        System.out.println("fitness ="+fitnessScore);
    }

}
