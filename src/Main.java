import java.util.*;

public class Main {

    static int maxFitness;
    static int iter;

    static Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) {
        int N = 4;
        maxFitness = (N*(N-1))/2;
        System.out.println("max fitness ="+ maxFitness);
        long startTime = System.currentTimeMillis();
        geneticAlgo(N,10,5,3);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + elapsedTime + " ms");

    }
    public static Set<int[]> generateFirstGen(int Nsquares,int maxPop)
    {
        // Create a Set to store the generated arrays
        Set<int[]> uniqueArrays = new HashSet<>();

        // Loop to generate N unique arrays
        while (uniqueArrays.size() < maxPop) {
            // Generate an array with M elements
            int[] array = generateArray(Nsquares);

            // Add the array to the Set if it's unique
            if (isUniqueArray(array, uniqueArrays)) {
                uniqueArrays.add(array);
            }
        }
        return uniqueArrays;
    }

    public static boolean isUniqueArray(int[] array, Set<int[]> uniqueArrays) {
        for (int[] uniqueArray : uniqueArrays) {
            if (Arrays.equals(array, uniqueArray)) {
                return false;
            }
        }
        return true;
    }

    public static int[] generateArray(int N)
    {
        int [] array = new int[N];
        for(int i = 0; i < N; i++)
        {
            array[i] = random.nextInt(N);
        }
        return array;
    }

    public static List<gen> selectParents(List<gen> pop,int Nparents) {
        List<gen> parents = new ArrayList<>();
        int max = pop.get(0).fitnessScore;
        int maxIndex = 0;

        for (int j = 0; j < Nparents; j++) {
            for (int index = 0; index < pop.size(); index++) {
                if (pop.get(index).fitnessScore > max) {
                    max = pop.get(index).fitnessScore;
                    maxIndex = index;
                }
            }
            if(max == Main.maxFitness)
            {
                pop.get(maxIndex).board.printBoard();
                System.out.println(max);
                System.exit(1);
            }
            parents.add(pop.remove(maxIndex));
        }
        return parents;
    }
    public static gen crossover(gen parent1, gen parent2, int N)
    {
        int[] childTable = new int[N];
        gen child;
        int crossoverPoint = random.nextInt(N-1)+1; // Random crossover point
//        parent1.printTable();
//        parent2.printTable();
//        System.out.print("crosspoint = "+crossoverPoint+",");
        // Copy the genes from parent1 and parent2 up to the crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            childTable[i] = parent1.indexTable[i];
//            System.out.print(":child["+i+"] = "+childTable[i]+",parent1["+i+"] = "+parent1.indexTable[i]);
        }
        for (int i = crossoverPoint; i < N; i++) {
            childTable[i] = parent2.indexTable[i];
//            System.out.print(":child["+i+"] = "+childTable[i]+",parent2["+i+"] = "+parent2.indexTable[i]);
        }
//        System.out.println();
        System.out.println("parents");
        parent1.printTable();
        parent2.printTable();
        System.out.println("child");
        child = new gen(childTable,N);
        child.printTable();

        return child;
    }

    public static gen mutation(gen victim,float mutation_prop,int N)
    {
        if(random.nextFloat(1.0f) < mutation_prop)
        {
            Random rand2 = new Random(System.currentTimeMillis());
            victim.indexTable[random.nextInt(N)] = rand2.nextInt(N);
        }
        return victim;
    }

    public static gen randomParent(List<gen> parents)
    {

        return parents.remove(random.nextInt(parents.size()));
    }

    public static void geneticAlgo(int N,int maxPop,int nParent,int iterations)
    {
//        int[] lookupTable = generateTable(N);
        List<gen> newgen;
        Set<int[]> tablePop = generateFirstGen(N,maxPop);
        List<gen> population = new ArrayList<>();
        for (int[] table: tablePop
             ) {
            population.add(new gen(table,N));
        }

        for(iter = 0; iter< iterations; iter++)
        {
            newgen = new ArrayList<>();
            List<gen> parents = selectParents(population,nParent);
            List<gen> selectedParents = new ArrayList<>();
            while(newgen.size() < maxPop)
            {
                System.out.println("parents");
                for (gen gene: parents
                     ) {
                    gene.printTable();
                }
                System.out.println("end of parents");
                selectedParents.add(randomParent(parents));
                selectedParents.add(randomParent(parents));

                newgen.add(
                        mutation(crossover(selectedParents.get(0),selectedParents.get(1),N)
                                ,0.05f,N));
                newgen.add(
                        mutation(crossover(selectedParents.get(1),selectedParents.get(0),N),
                                0.05f,
                                N));
                parents.add(selectedParents.get(0));
                parents.add(selectedParents.get(1));
            }
            population = newgen;
        }
         List<gen> sol = new ArrayList<>();
         sol.add(selectParents(population,1).get(0));
         sol.get(0).board.printBoard();
         System.out.println(sol.get(0).fitnessScore);

//         gen.minBoard.printBoard();
//         System.out.println(gen.minGlobal_fitnessScore);
    }


//    public static int[] generateTable(int N)
//    {
//        int[] table = new int[N];
//
//        Random rand = new Random(System.currentTimeMillis());
//
//        for(int i = 0; i < N; i++)
//        {
//            table[i] = rand.nextInt(N*5);
//        }
//        return table;
//    }





}