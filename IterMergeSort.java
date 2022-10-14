public class iterMergeSort {

        public static void merge(Integer[] arr, int aCtr, int bCtr, int rightEnd) {

          Integer[] tmp = new Integer[arr.length];
        
          int leftEnd = bCtr - 1; 
          int cCtr = aCtr;          
          int original_a = aCtr; 
          int original_right_end = rightEnd;
          
          while (aCtr <= leftEnd && bCtr <= rightEnd) {
            if (arr[aCtr] <= arr[bCtr]) {
              tmp[cCtr++] = arr[aCtr++];
            } else {
              tmp[cCtr++] = arr[bCtr++];
            }
          }
          while (aCtr <= leftEnd) 
            tmp[cCtr++] = arr[aCtr++];  
          while (bCtr <= rightEnd) 
            tmp[cCtr++] = arr[bCtr++];  
         
          // copy sorted subpartition back into arr 
          for (int i = original_a; i <= original_right_end; i++) {
            arr[i] = tmp[i]; 
          } 

        }

        public static void mergeSort(Integer[] inputArray) {
            int length = inputArray.length;
            int n = 2;
            //It partitions in multiples of 2
            for (int j = 1; j <= length; j = 2*j){
                //Iterates through numbers depending on partitions.
                for (int i = 0; i < length; i = i + 2*j) {
                    int mid = i + j;
                    int right = i + (2*j) - 1;
                    //Checks that it doesn't go out of bounds when sorting
                    if (right + 1 <= length){
                        merge(inputArray, i, mid, right);
                    } else if (length%(j*2) <= j) {
                        continue;
                    } else {
                        merge(inputArray, i, mid, length-1);
                    }
                    //Prints numbers
                    for (Integer counter :inputArray) {
                        System.out.print(counter);
                        System.out.print(" ");
                    }
                    System.out.println();

                }
            }
        }

        //Test cases
        public static void main(String[] args) {
            
            Integer[] test = {1,2,3,5,6,4};
                
            mergeSort(test);
            
            for (Integer i :test) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();

        }
}
