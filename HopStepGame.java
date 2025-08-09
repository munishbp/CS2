/* Munish Persaud
 * Dr. Andrew Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 4
 */


public class HopStepGame {
    //recursive, think top-down
    public int minCost(int arr[],int squares){
        //base case of recursion to just return the single square
        if(squares==1){
            return arr[squares];
        }
        //this base case is if only two squares are presented, pick the lesser one by stepping (-1) or hopping (-2)
        if(squares==2){
            if(arr[squares-1]<arr[squares-2]){
                return arr[squares-1]+arr[squares];
            }
            else{
                return arr[squares-2]+arr[squares];
            }
        }

        //step is -1 and hop is -2 from the current square you're at. you want to return the min of these.
        int step=arr[squares]+minCost(arr,squares-1);
        int hop=arr[squares]+minCost(arr,squares-2);

        return Math.min(step,hop);
    }


    //memo is also top down
    public int minCostMemoization(int arr[],int squares,int prevResults[]){
        //base cases follow the same logic in memoization as they did in recursion
        if(squares==1){
            return arr[squares];
        }
        if(squares==2){
            if(arr[squares-1]<arr[squares-2]){
                return arr[squares-1]+arr[squares];
            }
            else{
                return arr[squares-2]+arr[squares];
            }
        }
        //additional base case where when a square from prevResults is not 0, we return that result.
        if(prevResults[squares]!=0){
            return prevResults[squares];
        }

        //same logic for hop and step here as in the recursive function
        int step=arr[squares]+minCostMemoization(arr,squares-1,prevResults);
        int hop=arr[squares]+minCostMemoization(arr,squares-2,prevResults);

        //return the minimum between hop and step to that specific index of the array you pass through when making more recursive calls
        return prevResults[squares]=Math.min(step,hop);
    }

    //bottom up
    public int minCostTabulation(int arr[]) {
        //setting the table to the same length of the array
        int table[] = new int[arr.length];
        //start by setting indices 0 and 1 to the same as arr
        table[0] = arr[0];
        table[1] = arr[1];

        //if a sneaky test case and we get a null array
        if (arr.length==0) {
            return 0;
        }
        //if theres only one index
        if (arr.length==1) {
            return arr[0];
        }

        //start at index 2 since I set indices 0 and 1, the next indices of table will be the min between hopping and stepping
        for (int i=2; i<arr.length; i++) {
            table[i]=arr[i]+Math.min(table[i-1], table[i-2]);
        }
        //returns the minimum between the last or 2nd to last indices
        return Math.min(table[arr.length-1], table[arr.length-2]);
    }
}