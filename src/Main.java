import java.util.*;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        geneticAlgo(5000,100,25,5000);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + elapsedTime + " ms");

    }
    public static Set<int[]> generateFirstGen(int N,int maxPop)
    {
        // Create a Set to store the generated arrays
        Set<int[]> uniqueArrays = new HashSet<>();

        // Loop to generate N unique arrays
        while (uniqueArrays.size() < maxPop) {
            // Generate an array with M elements
            int[] array = generateArray(N);

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
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < N; i++)
        {
            float prob = random.nextFloat(1.0f);
            if( prob > 0.5f)
            {
                array[i] = 1;
            }
            else{
                array[i] = 0;
            }
        }
        return array;
    }

    public static List<gen> selectParents(List<gen> pop,int Nparents) {
        List<gen> parents = new ArrayList<>();
        int min = pop.get(0).fitnessScore;
        int minIndex = 0;

        for (int j = 0; j < Nparents; j++) {
            for (int index = 0; index < pop.size(); index++) {
                if (pop.get(index).fitnessScore < min) {
                    min = pop.get(index).fitnessScore;
                    minIndex = index;
                }
            }
            parents.add(pop.get(minIndex));
        }
        return parents;
    }
    public static int[] crossover(int[] parent1, int[] parent2, int N)
    {
        int[] child = new int[N];
        Random random = new Random(System.currentTimeMillis());
        int crossoverPoint = random.nextInt(N-2)+1; // Random crossover point
        // Copy the genes from parent1 and parent2 up to the crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
        }
        for (int i = crossoverPoint; i < N; i++) {
            child[i] = parent2[i];
        }
        return child;
    }

    public static gen randomParent(List<gen> parents)
    {
        Random random = new Random(System.currentTimeMillis());
        return parents.remove(random.nextInt(parents.size()));
    }

    public static void geneticAlgo(int N,int maxPop,int nParent,int iterations)
    {
        int[] lookupTable = generateTable(N);
        List<gen> newgen;
        Set<int[]> tablePop = generateFirstGen(N,maxPop);
        List<gen> population = new ArrayList<>();
        for (int[] table: tablePop
             ) {
            population.add(new gen(lookupTable,table));
        }

        for(int iter = 0; iter< iterations; iter++)
        {
            newgen = new ArrayList<>();
            List<gen> parents = selectParents(population,nParent);
            List<gen> selectedParents = new ArrayList<>();
            while(newgen.size() < maxPop)
            {
                selectedParents.add(randomParent(parents));
                selectedParents.add(randomParent(parents));
                newgen.add(new gen(lookupTable,crossover(selectedParents.get(0).indexTable,selectedParents.get(1).indexTable,N)));
                newgen.add(new gen(lookupTable,crossover(selectedParents.get(1).indexTable,selectedParents.get(0).indexTable,N)));
                parents.add(selectedParents.get(0));
                parents.add(selectedParents.get(1));
            }
            population = newgen;
        }
        selectParents(population,1).get(0).print(lookupTable,N);
    }


    public static int[] generateTable(int N)
    {
        int[] table = new int[N];

        Random rand = new Random(System.currentTimeMillis());

        for(int i = 0; i < N; i++)
        {
            table[i] = rand.nextInt(N*5);
        }
        return table;
    }





}