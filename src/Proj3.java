import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/***********************************************************************************************************************
 * @file: Proj3.java
 * @description: This program implements the main method of Project 3 along with all the required sorting methods.
 * @author: Olivia Sturges
 * @date: November 11, 2024
 * Note on changing the data: Took out a comma from the titles in rows 69 and 111.
 **********************************************************************************************************************/

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if (left == right ) {
            return; // only 1 element in arraylist
        }
        int mid = (left + right) / 2; // finding mid point
        mergeSort(a, left, mid); // sort first half
        mergeSort(a, mid + 1, right); // sort second half
        merge(a, left, mid, right); // merge two halves
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        // Finish Me
        int sl = mid - left + 1; // size of left half of the array
        int sr = right - mid; // size of the right half of the array

        ArrayList<T> templ = new ArrayList<>(); // temp array of left half
        for (int i = 0; i < sl; i++) {
            templ.add(a.get(left + i)); // starts at beginning of left and goes till midpoint
        }

        ArrayList<T> tempr = new ArrayList<>(); // temp array of right half
        for (int i = 0; i < sr; i++) {
            tempr.add(a.get(mid + 1 + i)); // starts at one after midpoint and goes till end
        }

        int i = 0; // templ index
        int j = 0; // tempr index
        int k = left; // beginning of original array
        while (i < sl && j < sr) { // adds smaller value to original array
            if (templ.get(i).compareTo(tempr.get(j)) <= 0) {
                a.set(k, templ.get(i));
                i++;
            }
            else {
                a.set(k, tempr.get(j));
                j++;
            }
            k++;
        }

        while (i < sl) { // adds rest of left array
            a.set(k, templ.get(i));
            i++;
            k++;
        }
        while (j < sr) { // adds rest of right array
            a.set(k, tempr.get(j));
            j++;
            k++;
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        int pivotindex = (left + right) / 2; // Finding a pivot
        swap(a, pivotindex, right); // stick pivot at the end
        int k = partition(a, left, right); // k will be the first position in the right subarray
        swap(a, k, right); // put pivot in place
        if ((k - left) > 1) {
            quickSort(a, left, k - 1); // sort left partition
        }
        if ((right - k ) > 1) {
            quickSort(a, k + 1, right); // sort right partition
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
        Comparable pivot = a.get(right); // pivot is at the end
        while (left <= right) { // move bounds inward until they meet
            while (a.get(left).compareTo(pivot) < 0 ) {
                left++;
            }
            while ((right >= left) && (a.get(right).compareTo(pivot) >= 0)) {
                right--;
            }
            if (right > left) {
                swap(a, left, right); // swap out of place values
            }
        }
        return left; // returns first position in the right partition
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        for (int i = (right - 1) / 2; i >= 0; i-- ) {
            heapify(a, i, right); // building max heap and you start in the middle
        }

        // Sort heap
        for (int i = right; i >= 0; i--) {
            swap(a, i, 0); // move root to end
            heapify(a, 0, i - 1); // heapifying the remainder of the array
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        // Finish Me
        int max = left; // root should be the max
        int l = 2 * left + 1; // left child
        int r = 2 * left + 2;  // right child

        // comparing with children
        if ((l < (right + 1) && a.get(l).compareTo(a.get(max)) > 0)) {
            max = l;
        }
       if  (r < (right + 1) && a.get(r).compareTo(a.get(max)) > 0) {
            max = r;
        }

        if (max != left) {
            swap(a, max, left); // swapping if root is not the max
            heapify(a, max, right); // repeating the process
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        // Finish Me
        int numComparisons = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size - i; j++) {
                numComparisons++; // increments # of comparisons
                if (a.get(j -1).compareTo(a.get(j)) > 0) {
                    swap(a, j -1, j);
                }
            }
        }
        return numComparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        // Finish Me
        int numComparison = 0;
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            // loop to perform bubble sort on odd indexed element
            for (int i = 1; i < size - 1; i = i + 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            numComparison++;
            // loop to perform bubble sort on even indexed element
            for (int i = 0; i < size - 1; i = i + 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            numComparison++;
        }
        return numComparison;
    }

    public static void main(String [] args)  throws IOException {
        //...
        // Finish Me
        //...

        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Usage: Proj3.java <dataset file> <sorting algorithm type> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[2]);
        String sortingType = args[1]; // Only accepting: bubble, merge, quick, heap, or transposition

        if (sortingType.equals("bubble") || sortingType.equals("quick") || sortingType.equals("merge") || sortingType.equals("heap") || sortingType.equals("transposition")) {
            // For file input
            FileInputStream inputFileNameStream = null;
            Scanner inputFileNameScanner = null;

            // Open the input file
            inputFileNameStream = new FileInputStream(inputFileName);
            inputFileNameScanner = new Scanner(inputFileNameStream);

            // ignore first line
            inputFileNameScanner.nextLine();

            // Create ArrayList to store PARKSANDRECData
            ArrayList<PARKSANDRECData> parksList = new ArrayList<PARKSANDRECData>();

            // Read the file line by line
            for (int i = 0; i < numLines; i++) { //
                String line = inputFileNameScanner.nextLine();
                String[] parts = line.split(","); // split the string into multiple parts
                // Create a new PARKS&RECDATA object
                // season, episode_num_in_season, episode_num_overall, title, directed_by, written_by, original_air_date, us_viewers
                PARKSANDRECData data = new PARKSANDRECData(
                        Integer.parseInt(parts[0]), // Season
                        Integer.parseInt(parts[1]), // Episode number in season
                        Integer.parseInt(parts[2]), // Overall episode number
                        parts[3], // Episode
                        parts[4], // Directed By
                        parts[5], // Written By
                        parts[6], // Original Air Date
                        Double.parseDouble(parts[7]) // Number of US viewers
                );
                parksList.add(data); // add the data onto the ArrayList
            }
            inputFileNameStream.close();

            // Making sorted, shuffled, and reversed copies  of the ArrayList
            // sort based off of number of us_viewers
            ArrayList<PARKSANDRECData> sorted = new ArrayList<PARKSANDRECData>(parksList);
            Collections.sort(sorted);

            //shuffled
            ArrayList<PARKSANDRECData> rand = new ArrayList<PARKSANDRECData>(parksList);
            Collections.shuffle(rand);

            // reversed
            ArrayList<PARKSANDRECData> rev = new ArrayList<PARKSANDRECData>(parksList);
            Collections.sort(rev, Collections.reverseOrder());

            // Initializing start time and end time
            long startTime = 0;
            long endTime = 0;
            double sortedTotalTime = 0.0;
            double shuffledTotalTime = 0.0;
            double reversedTotalTime = 0.0;
            int numComparisons = 0;

            // bubble case
            if (sortingType.equals("bubble")) {
                // Sorted ArrayList
                startTime = System.nanoTime();
                numComparisons = bubbleSort(sorted, sorted.size());
                endTime = System.nanoTime();
                sortedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: sorted, Num comparisons: " + numComparisons + ", " + "Time (sec): " + sortedTotalTime + "\n");

                // Shuffled ArrayList
                startTime = System.nanoTime();
                numComparisons = bubbleSort(rand, rand.size());
                endTime = System.nanoTime();
                shuffledTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: shuffled, Num comparisons: " + numComparisons + ", " + "Time (sec): " + shuffledTotalTime + "\n");

                // Reversed ArrayList
                startTime = System.nanoTime();
                numComparisons = bubbleSort(rev, rev.size());
                endTime = System.nanoTime();
                reversedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: reversed, Num comparisons: " + numComparisons + ", " + "Time (sec): " + reversedTotalTime);

            }

            // quick case
            else if (sortingType.equals("quick")) {
                // Sorted ArrayList
                startTime = System.nanoTime();
                quickSort(sorted, 0, sorted.size() - 1);
                endTime = System.nanoTime();
                sortedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: sorted, " + "Time (sec): " + sortedTotalTime + "\n");

                // Shuffled ArrayList
                startTime = System.nanoTime();
                quickSort(rand, 0, rand.size() - 1);
                endTime = System.nanoTime();
                shuffledTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: shuffled, " + "Time (sec): " + shuffledTotalTime + "\n");

                // Reversed ArrayList
                startTime = System.nanoTime();
                quickSort(rev, 0, rev.size() - 1);;
                endTime = System.nanoTime();
                reversedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: reversed, " + "Time (sec): " + reversedTotalTime);

            }

            // merge case
            else if (sortingType.equals("merge")) {
                // Sorted ArrayList
                startTime = System.nanoTime();
                mergeSort(sorted, 0, sorted.size() - 1);
                endTime = System.nanoTime();
                sortedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: sorted, " + "Time (sec): " + sortedTotalTime + "\n");

                // Shuffled ArrayList
                startTime = System.nanoTime();
                mergeSort(rand, 0, rand.size() - 1);
                endTime = System.nanoTime();
                shuffledTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: shuffled, " + "Time (sec): " + shuffledTotalTime + "\n");

                // Reversed ArrayList
                startTime = System.nanoTime();
                mergeSort(rev, 0, rev.size() - 1);;
                endTime = System.nanoTime();
                reversedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: reversed, " + "Time (sec): " + reversedTotalTime);

            }

            // heap case
            else if (sortingType.equals("heap")) {
                // Sorted ArrayList
                startTime = System.nanoTime();
                heapSort(sorted, 0, sorted.size() - 1);
                endTime = System.nanoTime();
                sortedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: sorted, " + "Time (sec): " + sortedTotalTime + "\n");

                // Shuffled ArrayList
                startTime = System.nanoTime();
                heapSort(rand, 0, rand.size() - 1);
                endTime = System.nanoTime();
                shuffledTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: shuffled, " + "Time (sec): " + shuffledTotalTime + "\n");

                // Reversed ArrayList
                startTime = System.nanoTime();
                heapSort(rev, 0, rev.size() - 1);;
                endTime = System.nanoTime();
                reversedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: reversed, " + "Time (sec): " + reversedTotalTime);

            }

            // transposition case
            else {
                // Sorted ArrayList
                startTime = System.nanoTime();
                numComparisons = transpositionSort(sorted, sorted.size());
                endTime = System.nanoTime();
                sortedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: sorted, Num comparisons: " + numComparisons + ", " + "Time (sec): " + sortedTotalTime + "\n");

                // Shuffled ArrayList
                startTime = System.nanoTime();
                numComparisons = transpositionSort(rand, rand.size());
                endTime = System.nanoTime();
                shuffledTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: shuffled, Num comparisons: " + numComparisons + ", " + "Time (sec): " + shuffledTotalTime + "\n");

                // Reversed ArrayList
                startTime = System.nanoTime();
                numComparisons = transpositionSort(rev, rev.size());
                endTime = System.nanoTime();
                reversedTotalTime = endTime - startTime / 1e9;
                System.out.print("N:" + numLines + ", " + "Sorting algorithm: " + sortingType + ", " + "ArrayList Type: reversed, Num comparisons: " + numComparisons + ", " + "Time (sec): " + reversedTotalTime);


            }

            // Making the sorted lists
            String s = ""; // sorted
            String r = ""; // shuffled
            String v = ""; // reversed
            for (int i = 0; i < sorted.size(); i++) {
                s += sorted.get(i) + " " + "\n";
                r += rand.get(i) + "\n";
                v += rev.get(i) + "\n";
            }

            // Print out sorted list to sorted.txt
            writeToFile1("Sorted ArrayList:\n" + s + "\n" + "Shuffled ArrayList:\n" + r + "\n" + "Reversed ArrayList:\n" + v, "./sorted.txt");

            // Writing to output.txt in CSV style format
            // N,sortingType,sortedTotalTime,shuffledTotalTime,reversedTotalTime,numComparisons
            // for quick, heap, and merge numComparisons will be 0 because we do not count the number of comparisons for those algorithms
            writeToFile2(numLines + "," + sortingType + "," + sortedTotalTime + "," + shuffledTotalTime + "," + reversedTotalTime + "," + numComparisons, "./analysis.txt");
        }
        else {
            System.out.println("Either not a sorting algorithm implemented in this project or entered incorrectly. " +
                    "Only accepting as sorting algorithm input: bubble, merge, quick, heap, or transposition.");
        }
    }

    // Method that generates sorted.txt file
    public static void writeToFile1(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))){
            writer.write(content);
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method that generates analysis.txt file
    public static void writeToFile2(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            writer.write(content);
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
