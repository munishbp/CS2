/* Munish Persaud
 * Dr. Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 3
 */


import java.util.*;

public class Tomatoes {

    public int minTomatoMoves(int pots[]){

        //var to return
        int moves=0;
        //gets total sum of tomatoes
        int sum=0;

        //placed i outside of a for loop because it needs to be accessed in other places
        int i;
        for(i=0;i<pots.length;i++){
            sum+=pots[i];
        }
        //make sure all the tomatoes can be evenly divided among all the pots
        if(sum%i!=0){
            return -1;
        }

        //avg is the target for each pot to balance to
        int avg=sum/i;

        //deficit counter. use this to keep track of how different each pot is from the avg.
        int balance=0;

        //in the same step of the for loop, update the balance
        //set moves equal to the max between moves and the max index of pots at i minus the avg or
        //the absolute value of balance
        //through every step balance is updating so these comparisons are different at every value of i
        for(i=0;i<pots.length;i++){
            balance+=pots[i]-avg;

            moves=Math.max(moves, Math.max(pots[i]-avg,Math.abs(balance)));
        }
        return moves;
     }
}
