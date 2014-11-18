package uk.co.amberservices.Adventure;
//this allows classes in same package to be used without having to import them

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simple representation of a game.
 * Allows a player to wander around a number of locations, as 
 * defined in /data/simpleAdventure.txt
 * 
 * @author AdrianBell
 *
 */

/**
 * And now I'm going to try and add items to each room
 * 
 * @author Glen
 *
 */
public class SimpleGame
{
    private Set<String> regularVerbs;
    //set to hold verbs
    final private String[] verbs = {"north", "south", "east", "west", "exit"};
    //array of verbs we'll use
    private Map<Integer, Location> locations = new HashMap<Integer, Location>();
    //Locations we can wander around.  Keyed by direction.(???)
    //Keyed by ID number i think
    
    /**
     * Main method that is going to be run when the game is executed.
     * It just does.  It's a java thing.  Java will try to look for a method with this EXACT
     * signature when you try to run a class.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        /*
         * Create an instance of a SimpleGame.
         * I know this seems strange as we are already 'in' SimpleGame but you're just 
         * going to have to trust me.  For now.  For the perverts amongst us, it's 
         * that sneaky little word 'static' on the method that is providing the 'magic pixie dust'
         * and the need to create the instance.  (Right now, we're not really in an instance of
         * SimpleGame, even though it looks like we are.)
         */
        SimpleGame simpleGame = new SimpleGame();
        simpleGame.play();
    }
    
    public void play()
    {
        this.regularVerbs = new HashSet<String>(Arrays.asList(verbs));
        //this command adds in all the separate strings from the array one by one
        //instead of us having to do it. Is very clever what it does.
        
        //Read the file and point the locations reference to the returned location map
        this.locations = readFile();
        /*
         * Now allow the player to wander around.
         * Technically there is no player class!
         * Start in room 1 too!
         */
        System.out.println("Welcome to the simple game!  Rejoice!");
        System.out.println("typing exit will get you out...");
        System.out.println("");
        Location location = this.locations.get(1);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = "";
        
        /*
         * Just keep looping, getting input from the keyboard
         * until 'exit' is typed in.
         * Or the world ends.
         * Or some other event that I haven't considered (lottery win, not giving a fuck - that kind of thing)
         * 
         */
        while (!text.equalsIgnoreCase("exit")) 
        {
            //Display the location description and directions available
            System.out.println(location.getDescription());
            if (location.getNeighbours().size() > 0)
            {
                System.out.print("exits are ");
            }
            //Iterate over the neighbours and put the keys out.
            for (Map.Entry<String, Integer> entry: location.getNeighbours().entrySet())
            {
                System.out.print(entry.getKey() + " ");
            }
            System.out.println("");
            System.out.println("What now?");
            /*
             * Try-catch block.  Frightening at this stage.
             * Ignore it.  If it is the only thing you don't understand then
             * we're on to a winner and it can be explained easily.
             */
            try
            {
                //Read an input from the the console.
                text = br.readLine();
                //Validate the input
                if (!regularVerbs.contains(text))
                {
                    //Whatever you typed in was not in my list of known verbs!
                    System.out.println("Eh?  I don't understand!");
                }
                else
                {
                    //Ok, so what was typed in makes sense (a direction) so
                    //now ;et's see if if it is a valid directions
                    if (location.getNeighbours().containsKey(text))
                    {
                        //All good, lets set the location to the new location
                        Integer nextLocationId = location.getNeighbours().get(text);
                        location = locations.get(nextLocationId);
                    }
                    else if (text.equalsIgnoreCase("exit"))
                    {
                    	System.out.println("Exiting...Goodbye you fucker. Leave me then");
                    	return;
                    }
                    else
                    {
                        System.out.println("Nope, you can't go that way!");
                    }
                }
                
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
    }
    
    private Map<Integer, Location> readFile()
    //so this method is going to return a map. This will be popped into a map variable then
    //or rather the map variable will point to the map returned by this
    {
        final String currentDir = System.getProperty("user.dir");
        final String fileSeparator = System.getProperty("file.separator");
        final String dataLocation = currentDir + fileSeparator + "data" + fileSeparator;
        final String filename = dataLocation + "locationData.txt";
        //this is cool.
        //puts together the path for the datafile we need using properties that the machine returns
        //so the file separator is the correct one for the correct OS
        
        	
        //vars local to method
        String data = null;
        BufferedReader in;
                	
        //Create a map of locations - local to this method!
        //dont need to declare it as private then???
        Map<Integer, Location> localLocations = new HashMap<Integer, Location>();	
       
        try
        {
        	in = new BufferedReader(new FileReader(filename));
        	String str;
      		while ((str = in.readLine()) != null)
       		//so this will read stuff in until it gets a null, ie. nothing left in file
           	//then it passes back a reference to all the locations in the lo
       		{
       			data = str;
       			/*
       			 * Data is comma separated values (bleugh) to keep things simple.  For now.
       			 * Format (for locations) is the following:
       			 * id, description, direction, destination id
       			 * 
       			 *  direction, destination id is a repeating set, depending on the directions the
       			 *  player can travel
       			 *  
       			 *  e.g.
       			 *  1,A small room in a tiny hut,north,2,east,3,south,4,west,5
       			 *  
       			 *  Format for Items is:
       			 *  location, description, identifier noun, identifier adjective (optional)
       			 *  
       			 *  adjective is repeating set
       			 *  
       			 *  eg.
      			 *  1,A dull green lamp,lamp,green,dull
       			 */
       			String[] dataArray = data.split(",");
       			//split the line that was read in from file by commas and put each element in array
       			
       			//now if its a location file set a tempLocation var to point to Location we read in
       			Location tempLocation = new Location(Integer.valueOf(dataArray[0]), dataArray[1]);
           		//element 0=id, element 1=description
           		for (int i = 2; i < dataArray.length -1; i = i + 2)
           		//loop from element 2 to end of array, in steps of 2
           		{
           			String key = dataArray[i];//direction
           			Integer value = Integer.valueOf(dataArray[i + 1]);//id of room to go to
           			tempLocation.getNeighbours().put(key, value);
                    //	location class has method getNeighbours which returns a map
           			//the map is a collection of all the neighbours to this location keyed by direction
                    //	map collection has a method to put shit in the map
                    //	so it puts in direction, roomId
           		}
           		localLocations.put(tempLocation.getId(), tempLocation);
                //	locations is a map containing all the locations in the game
                //	this puts in the location after its filled it with it's id, description and neighbours
       				
       		}
       		in.close();
       	}
       	catch (final IOException e)
       	{
       		System.out.println(e);
       	}
        
       
       	//ok so pass these locations back to whatever called this method
       	return localLocations;
    }
}
