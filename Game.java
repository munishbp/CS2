/* Munish Persaud
 * Dr.Steinberg
 * COP 3503 Spring 2025
 * Programming Assignment 1
*/
import java.util.*;
import java.util.Random;

public class Game {
    //i is row, j is column
    int i=0,j=0;
    int[][] chessboard= new int[8][8];
    //char array for the player2 moves
    char[] moves={'d','r','b'};
    Random rand=new Random();


    Game(Random seed)
    {
    }

    public char selectPlayerTwoMove()
    {
        int pick;
        //at bottom and can only go right
        if(i==7)
        {
            pick=1;
        }
        //at right and can only go down
        if(j==7)
        {
            pick=2;
        }
        //method of picking random move for player2
        pick=rand.nextInt(3);
        return moves[pick];
    }

    //player1 function to decide movement
    public int PlayerOneMove(int row, int col)
    //calculates the odd box from the right, diagonal, and down movements possible
    //then moves to the odd box to ensure win
    {
        //at bottom can only go right
        if(i==7)
        {
            return 1;
        }
        //at right can only go down
        if(j==7)
        {
            return 3;
        }
        if(((row + 1) * col) % 2 == 1)
        {
            return 1; //go right
        }
        if(((row + 1) * (col+1)) % 2 == 1)
        {
            return 2;//go diagonal down
        }
        return 3; //go down
    }


    //play function
    public int play()
    {
        //player one always makes the first move
        if(chessboard[0][0]==0)
        {
            chessboard[0][0]=1;
            i++;
            j++;
            chessboard[i][j]=1;
        }

        //while loop to ensure not out of bounds
        while(i<7&&j<7)
        {
            char p2move=selectPlayerTwoMove();
            if(p2move=='b')
            {
                //go down
                i++;
                chessboard[i][j]=2;
                if(chessboard[7][7]==2)
                {
                    return 0;
                }
            }
            if(p2move=='r')
            {
                //go right
                j++;
                chessboard[i][j]=2;
                if(chessboard[7][7]==2)
                {
                    return 0;
                }
            }
            if(p2move=='d')
            {
                //go diagonal
                i++;
                j++;
                chessboard[i][j]=2;
                if(chessboard[7][7]==2)
                {
                    return 0;
                }
            }

            //player one move function call
            int P1move=PlayerOneMove(i, j);
            if(P1move==1)
            {
                //go down
                i++;
                chessboard[i][j]=1;
            }
            if(P1move==2)
            {
                //go diagonal
                i++;
                j++;
                chessboard[i][j]=1;
            }
            if(P1move==3)
            {
                //go right
                j++;
                chessboard[i][j]=1;
            }
        }
        return 1;
    }
}