/*
* Munish Persaud
* Dr. Steinberg
* COP3503C Spring 2025
* Programming Assignment 2
*/


import java.util.*;



public class TreasureCoordinates {
    ArrayList<String> coordinates = new ArrayList<>();

    //Driver method, like a function. Main purpose is to clear the arraylist for each test case.
    public ArrayList<String> determineCoordinates(String Coordinate)
    {
        coordinates.clear();
        coordinatePermuations(Coordinate);
        return coordinates;
    }


    public void coordinatePermuations(String Coordinate)
    {
        //Base case to add, this is the only place that adds the coordinate based off help method and if its actually split by a comma to make sure I dont pass in a number block.
        if(!(contains(coordinates,Coordinate))&&Coordinate.contains(","))
        {
            coordinates.add("("+Coordinate+")");
            return;
        }


        //Used to clean up Coordinate every time its passed in to make it easier to split and move around the comma and move the decimals.
        if(Coordinate.contains(".")||Coordinate.contains(",")||Coordinate.contains("(")||Coordinate.contains(")")||Coordinate.contains(" "))
        {
            Coordinate=Coordinate.replace(".","");
            Coordinate=Coordinate.replace(",","");
            Coordinate=Coordinate.replace("(","");
            Coordinate=Coordinate.replace(")","");
            Coordinate=Coordinate.replace(" ","");
        }


        //Iterate through string
        for(int i=0;i<Coordinate.length()-1;i++)
        {
            //String builders split the coordinate into x and y, I move the numbers around the comma, y starts larger and gradually gets smaller, moving everything to x.
            StringBuilder x=new StringBuilder(Coordinate.substring(0,i+1));
            StringBuilder y=new StringBuilder(Coordinate.substring(i+1,Coordinate.length()));

            //I decide to do my initial split by their lengths with x and y and move decimals that way.
            int xlen = x.length();
            int ylen = y.length();


            if(xlen>=ylen)
            {
                //This goes through x and places decimals.
                for(int j=0;j<xlen;j++)
                {
                    x.insert(j,'.');
                    //Will go onto do recursive call x and y coordinates are valid. I'm including the contains method to stop from recurisvely calling too much and causing overflow.
                    if(xValid(x)&&yValid(y)&&!(contains(coordinates,x+", "+y)))
                    {
                        coordinatePermuations(x+", "+y);
                        //For each x and x decimal I will produce and try all y decimals and determine whether its valid.
                        for(int k=0;k<ylen;k++)
                        {
                            y.insert(k,'.');
                            if(xValid(x)&&yValid(y))
                            {
                                coordinatePermuations(x + ", " + y);
                            }
                            //Backtrack the y decimal.
                            y.deleteCharAt(k);
                        }
                        //Backtrack the x decimal.
                        x.deleteCharAt(j);
                    }
                    //If that x decimal isn't valid. I delete the decimal and check if the previous version is valid and its not already in the list.
                    else
                    {
                        x.deleteCharAt(j);
                        if(xValid(x)&&yValid(y)&&!(contains(coordinates,x+", "+y)))
                        {
                            coordinatePermuations(x + ", " + y);
                        }
                    }
                }
            }

            //Same as when x is bigger.
            if(ylen>=xlen)
            {
                for(int j=0;j<ylen;j++)
                {
                    y.insert(j,'.');
                    if(xValid(x)&&yValid(y)&&!(contains(coordinates,x+", "+y)))
                    {
                        coordinatePermuations(x+", "+y);
                        for(int k=0;k<xlen;k++)
                        {
                            x.insert(k,'.');
                            if(xValid(x)&&yValid(y)&&!(contains(coordinates,x+", "+y)))
                            {
                                coordinatePermuations(x + ", " + y);
                            }
                            x.deleteCharAt(k);

                        }
                        y.deleteCharAt(j);
                    }
                    else
                    {
                        y.deleteCharAt(j);
                        if(xValid(x)&&yValid(y)&&!(contains(coordinates,x+", "+y)))
                        {
                            coordinatePermuations(x + ", " + y);
                        }
                    }
                }
            }
        }
    }


    //Checks to see if given coordinate is in the array list. if its not it returns false-this is what I want
    public boolean contains(ArrayList coordinates,String Coordinate)
    {
        //Same logic, used to clean the string of everything except the comma.
        Coordinate=Coordinate.replace("(","").replace(")","").replace(" ","");

        //Splits each coordinate by their comma x will be in index 0 and y will be in index 1.
        String[] arr=Coordinate.split(",");

        //Goes through the arraylist, cleaning and splitting it the same way then comparing the index 0's and 1's by parsing to a double. Better than strings because eliminates floating comparisons.
        for(int i=0;i<coordinates.size();i++)
        {
            String compareCoordinate=coordinates.get(i).toString().replace("(","").replace(")","").replace(" ","");

            String[] arr2=compareCoordinate.split(",");
            if((Double.parseDouble(arr[0]))==(Double.parseDouble(arr2[0]))&&(Double.parseDouble(arr[1]))==(Double.parseDouble(arr2[1]))){
                return true;
            }
        }
        return false;
    }


    //I decided to evaluate x and y validity independently.


    public boolean xValid(StringBuilder x)
    {
        //If empty by chance. It messes up.
        if(x.length()==0)
        {
            return false;
        }

        //Anything by length 1 is valid.
        if(x.length()==1)
        {
            return true;
        }

        //Start to cascade through numbers starting with 0.
        if(x.charAt(0)=='0')
        {
            //If the length is greater than one and a decimal at index 1 of the string, we keep evaluating.
            if(x.length()>1&&x.charAt(1)=='.')
            {
                //Need to make sure there's not a ton of 0's in this decimal. That won't work. So I go through indices 2-end to make sure its not all trailing 0's
                for(int i=2;i<x.length();i++)
                {
                    if(x.charAt(i)!='0')
                    {
                        return true;
                    }
                }
            }
            //If I never hit a nonzero I return false.
            return false;
        }

        //This is where I handle a decimal. As its not out of bounds and placed before I start evaluating. I start by finding its place.
        if(x.indexOf(".")!=-1)
        {
            int decimalPos=x.indexOf(".");

            //If its at index 0 or the very end, making a whole number I get false.
            if (decimalPos<=0||decimalPos>=x.length()-1)
            {
                return false;
            }

            //Create a boolean variable to evaluate if there is any nonzeros after the decimal. Automatically assume false
            boolean nonZero=false;
            for(int i=decimalPos+1;i<x.length();i++)
            {
                //Trigger that makes nonzero true and therefore return true if any of the next indices are nonzero or if nonzero was previously true.
                if(x.charAt(i)!='0'||nonZero)
                {
                    nonZero=true;
                }
            }
            return nonZero;
        }
        return true;
    }


    //Same as xValid, just mirrored for y.
    public boolean yValid(StringBuilder y)
    {
        //If empty by chance. It messes up.
        if(y.length()==0)
        {
            return false;
        }

        //Anything by length 1 is valid.
        if(y.length()==1)
        {
            return true;
        }

        //Start to cascade through numbers starting with 0.
        if(y.charAt(0)=='0')
        {
            //If the length is greater than one and a decimal at index 1 of the string, we keep evaluating.
            if(y.length()>1&&y.charAt(1)=='.')
            {
                //Need to make sure there's not a ton of 0's in this decimal. That won't work. So I go through indices 2-end to make sure its not all trailing 0's
                for(int i=2;i<y.length();i++)
                {
                    if(y.charAt(i)!='0')
                    {
                        return true;
                    }
                }
            }
            //If I never hit a nonzero I return false.
            return false;
        }

        //This is where I handle a decimal. As its not out of bounds and placed before I start evaluating. I start by finding its place.
        if(y.indexOf(".")!=-1)
        {
            int decimalPos=y.indexOf(".");

            //If its at index 0 or the very end, making a whole number I get false.
            if (decimalPos<=0||decimalPos>=y.length()-1)
            {
                return false;
            }

            //Create a boolean variable to evaluate if there is any nonzeros after the decimal. Automatically assume false
            boolean nonZero=false;
            for(int i=decimalPos+1;i<y.length();i++)
            {
                //Trigger that makes nonzero true and therefore return true if any of the next indices are nonzero or if nonzero was previously true.
                if(y.charAt(i)!='0'||nonZero)
                {
                    nonZero=true;
                }
            }
            return nonZero;
        }
        return true;
    }
}