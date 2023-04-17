import java.util.ArrayList;
import java.util.List;

public class gen {

    int[] indexTable;
    int fitnessScore;

    public gen(int N)
    {
        indexTable = new int[N];
    }

    public gen(int[] lookupTable,int[] table)
    {
        this.indexTable = table;
        CalcualteFitnessFunction(lookupTable);
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

    public void CalcualteFitnessFunction(int[] lookupTable)
    {
        int sumOne = 0;
        int sumTwo = 0;
        //calculate the difference between the 2 tables
        for(int index = 0; index < indexTable.length; index++)
        {
            // if its == 0 it means its allocated to table 1
            if(indexTable[index] == 0)
            {
                sumOne+=  lookupTable[index];
            }
            else{
                sumTwo+= lookupTable[index];
            }
        }

        fitnessScore = Math.abs(sumOne-sumTwo);
    }

}
