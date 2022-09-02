
import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args){



        Random random = new Random();


        int[] ranArray10000 = new int[10000];
        for(int i = 0; i<10000; i++){
            int randomNumber = random.nextInt();
            ranArray10000[i] = randomNumber;
        }

        int[] unsortedRandomArray10000ForSS = ranArray10000.clone();
        int[] unsortedRandomArray10000ForIS = ranArray10000.clone();



        long startTime;
        long estimatedTime;


        HeapSort hs = new HeapSort();


        startTime = System.nanoTime();
        hs.sort(ranArray10000);
        estimatedTime = System.nanoTime() - startTime;


        System.out.println("Heap Sort for random array with size 10000 : " + estimatedTime );


        ShellSort ss = new ShellSort();

        startTime = System.nanoTime();
        ss.sort(unsortedRandomArray10000ForSS);
        estimatedTime = System.nanoTime() - startTime;


        System.out.println("Shell Sort for random array with size 10000 : " + estimatedTime );




        IntroSort is = new IntroSort();

        startTime = System.nanoTime();
        is.sort(unsortedRandomArray10000ForIS);
        estimatedTime = System.nanoTime() - startTime;


        System.out.println("Intro Sort for random array with size 10000 : " + estimatedTime );



    }

}
