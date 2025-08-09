/* Munish Persaud
 * Dr. Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 5
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

//railroad class
public class Railroad
{
    int tracknum;
    String file;

    //use tracknum and file so i dont have to make global vars and potentially mess up values
    public Railroad(int tracks, String filename)
    {
        tracknum=tracks;
        file=filename;
    }

    //disjoint set class, using union by rank and path compression
    class DisjointSet
    {
        int[] parent;
        int[] rank;

        //initialize parent and rank to the size of the amount of cities passed in
        public DisjointSet(int size)
        {
            parent=new int[size];
            rank=new int[size];

            //puts each elements in its own set
            for (int i=0;i<size;i++)
            {
                parent[i]=i;
                rank[i]=0;
            }
        }

        //root of set with path compression
        public int Find(int x)
        {
            if (parent[x]!=x)
            {
                //path compression step
                parent[x]= Find(parent[x]);
            }
            return parent[x];
        }

        //union by rank
        public void Union(int x, int y)
        {
            int rootX= Find(x);
            int rootY= Find(y);

            //already in same set
            if (rootX==rootY)
            {
                return;
            }


            if (rank[rootX]<rank[rootY])
            {
                parent[rootX]=rootY;
            }
            else if (rank[rootX]>rank[rootY])
            {
                parent[rootY]=rootX;
            }
            else
            {
                parent[rootY]=rootX;
                rank[rootX]++;
            }
        }
    }

    //my custom comparator that uses stringbuilder and a helper function to get the distance from the back end of the string.
    //doesnt need to separate the string, but sorts strings only based on the digits at the end
    //returns based on Integer.compare of positive, zero, or negative value to decide placement
    //utilized to make the sorted list before kruskal's algo
    class byDistance implements Comparator<StringBuilder>
    {
        @Override
        public int compare(StringBuilder first, StringBuilder second)
        {
            int firstDistance=getDistance(first);
            int secondDistance=getDistance(second);
            return Integer.compare(firstDistance, secondDistance);
        }
    }


    //design the cost efficient railroad here
    public String buildRailroad() throws IOException
    {
        //arraylist to initially store and then sort travel by distance
        ArrayList<StringBuilder> tracks=new ArrayList<StringBuilder>();
        //hash map to split the cities up and create "nodes" for the map
        HashMap<String, Integer> paths=new HashMap<String, Integer>();
        //update of the arraylist by using helper file reader function
        tracks = fileReader(file);

        //collections sort call on the arraylist and my custom comparator
        Collections.sort(tracks, new byDistance());


        //kruskal's algo incoming
        //used to add the value to each key when passing in the cities, making sure each key is unique
        int count=0;
        //loop for the number of paths in the arraylist
        for (StringBuilder track:tracks)
        {
            String trackConversion=track.toString();
            //split string builder into three parts
            String[] srcdst=trackConversion.split(" ");

            //checks to make sure neither city is in the hashmap yet, if it isn't, it gets added
            if (!paths.containsKey(srcdst[0]))
            {
                paths.put(srcdst[0], count++);
            }

            if (!paths.containsKey(srcdst[1]))
            {
                paths.put(srcdst[1], count++);
            }

        }
        //new disjoint object made
        DisjointSet set=new DisjointSet(count);
        //will use this to append my print statements to
        StringBuilder result=new StringBuilder();
        //total is going to be the smallest cost to transverse to each city
        int total=0;

        //loops for number of paths in tracks arraylist
        for (StringBuilder track:tracks)
        {
            String trackConversion=track.toString();
            String[] srcdst=trackConversion.split(" ");
            int cost=Integer.parseInt(srcdst[2]);

            //source is first index, destination is second index
            int src=paths.get(srcdst[0]);
            int dst=paths.get(srcdst[1]);

            //if find function in Disjointset object isn't found, we make a set out of them
            if(set.Find(src)!=set.Find(dst))
            {
                set.Union(src,dst);

                //new StringBuilders to decide the source and destination between pairs of cities
                StringBuilder source;
                StringBuilder destination;

                //ensure alphabetical output of the MST
                if(srcdst[0].compareTo(srcdst[1])<0)
                {
                    source=new StringBuilder(srcdst[0]);
                    destination=new StringBuilder(srcdst[1]);
                }
                else
                {
                    source=new StringBuilder(srcdst[1]);
                    destination=new StringBuilder(srcdst[0]);
                }
                //appends because stringbuilder lifestyle
                //makes big multistring paragraph to be returned at the end
                result.append(source).append("---").append(destination).append("\t$").append(cost).append("\n");
                total+=cost;
            }
        }
        //last thing to append is the total cost
        result.append("The cost of the railroad is $").append(total).append(".");
        return result.toString();
    }

    //this parses the stringbuilder from the arraylist to get the distances and then sort the list
    //its called in the comparator
    public int getDistance(StringBuilder path)
    {
        String[] num;
        num=path.toString().split(" ");
        int distance=Integer.parseInt(num[2]);
        return distance;
    }

    //file reader function
    public ArrayList<StringBuilder> fileReader(String file) throws IOException
    {
        ArrayList<StringBuilder> tracks=new ArrayList<StringBuilder>();
        FileReader fr=new FileReader(file);
        BufferedReader input=new BufferedReader(fr);
        String line;

        while ((line=input.readLine())!=null)
        {
            StringBuilder add=new StringBuilder(line);
            tracks.add(add);
        }
        return tracks;
    }
}
