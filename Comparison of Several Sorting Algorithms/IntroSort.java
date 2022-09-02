
import java.io.IOException;
import java.util.Arrays;
//Reference: https://www.sanfoundry.com/python-program-implement-introsort/
// The codes of the introsort algorithm were written in Python language in this website.
// Using these codes and the given pseudocode,
// I coded it in Java language. I made changes in several places.

public class IntroSort {

    int counter = 0;

    public void sort(int[] arr){
        int depthLimit = (int)(2 * Math.floor(Math.log(arr.length) / Math.log(2)));
        quickSortOrHeapSort(arr, 0,arr.length-1, depthLimit);
    }

    static int partition(int[] arr, int low, int high)
    {

        int pivot = arr[high];

        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {

            if (arr[j] < pivot)
            {

                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }


    public void quickSortOrHeapSort(int[] arr, int start, int end, int depthLimit) {
        if (end - start < 1) {
            return;
        }
        else if(depthLimit ==0){
            heapsort(arr, start, end+1);
        }
        else{
            int p = partition(arr, start, end);
            quickSortOrHeapSort(arr, start, p-1, depthLimit - 1);
            quickSortOrHeapSort(arr, p + 1, end, depthLimit - 1);
        }


    }



    public void heapsort(int[] arr,int start,int end) {
        counter++;
        build_max_heap(arr, start, end);
        for(int i = end-1; i>start;i--){
            swap(arr, start, i);
            int index = 0;
            max_heapify(arr, index, start, i);

        }


    }

    public void build_max_heap(int[] arr, int start, int end) {
        int length = end - start;
        int index = parent(length - 1);
        while(index >= 0){
            max_heapify(arr, index, start, end);
            index = index - 1;
        }

    }

    public int parent(int i){
        return (i - 1)/2;
    }

    public void max_heapify(int[] arr, int index,int start,int end) {
        int largest;
        int size = end - start;
        int l = left(index);
        int r = right(index);
        if (l < size && arr[ start + l] >arr[start + index]){
            largest = l;
        }else{
            largest = index;
        }
        if (r < size && arr[ start + r] >arr[start + largest]){
            largest = r;
        }
        if (largest != index){
            swap(arr, start + largest, start + index);
            max_heapify(arr, largest, start, end);
        }
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }



    public int left (int i){
        return 2 * i + 1;
    }

    public int right (int i){
        return 2 * i + 2;
    }


}
