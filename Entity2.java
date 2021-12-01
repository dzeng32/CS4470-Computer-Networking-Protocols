import java.util.*;

public class Entity2 extends Entity
{   
	int entity_number = 2;									//INTEGER - For keeping track of current entity number
	List<Integer> neighbors = new ArrayList<Integer>();		//INTEGER LIST - For keeping track of which entities are neighbors
	
    // Perform any necessary initialization in the constructor
    public Entity2()
    {		
    	//Initializes everything to infinity
    	for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
    		
    		for(int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
    			distanceTable[i][j] = 999;
    		}
    		distanceTable[i][i] = 0;
    	}
    	
    	//initializes known neighbor costs
    	for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
    		distanceTable[entity_number][i] = NetworkSimulator.cost[entity_number][i];
    		if(distanceTable[entity_number][i] < 999 && i != entity_number) {
    			neighbors.add(i);
    		}
    	}
    	
    	//prints table after been initialized
    	printDT();
    	
    	//sends out update of minimum costs to neighbors
    	for(int i = 0; i < neighbors.size(); i++) {
    		Packet initialUpdate = new Packet(entity_number, neighbors.get(i), distanceTable[entity_number]);
	    	NetworkSimulator.toLayer2(initialUpdate);
    	}
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	int[] tempNewCosts = new int[NetworkSimulator.NUMENTITIES];		//INT ARRAY - For saving updates in a temporary array
    	boolean updated = false;										//BOOLEAN - For keeping track of if an update has occurred
    	
    	//prints out message to user of received packet
    	System.out.println("\nRecieving packet from entity" + p.getSource() + ": ");
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			System.out.print(p.getMincost(i) + " ");
		}
    	System.out.print("\n");
    	System.out.println("___________________________________________");
    	System.out.println("The begining of update() in node " + entity_number);
    	System.out.println("___________________________________________");
        
    	//prints table before any changes made to it
    	printDT();    	
		
    	//replace row belonging to packet source
    	for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
    		distanceTable[p.getSource()][i] = p.getMincost(i);
    	}
    	
    	//copies array of current entity's costs to tempNewCosts array
    	for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
    		tempNewCosts[i] = distanceTable[entity_number][i];
    	}
    	
    	//find new costs based on received packet
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i ++) {
    		for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
    			if (tempNewCosts[i] > distanceTable[entity_number][j] + distanceTable[j][i]) {
    				//value saved in temporary array so that future comparisons will continue to use older values
    				tempNewCosts[i] = (distanceTable[entity_number][j] + distanceTable[j][i]);
    				//change in values has occurred so will need to update costs and notify neighbors later
    				updated = true;
    			}
    		}
    	}
    	
    	//update costs of current entity to other entities
    	if(updated) {
	    	for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
	    		distanceTable[entity_number][i] = tempNewCosts[i];
	    	}
    	}
    	
    	System.out.println("___________________________________________");
    	System.out.println("The end of update() in node " + entity_number);
    	System.out.println("___________________________________________");
    	
    	//prints table after changes have been made to it
    	printDT();

    	//prints message and sends packets to neighbors with updated costs
        if(updated){
        	//print out message of nodes to be updated
        	System.out.println("Distance vector updated!");
    		System.out.print("Sending packet with updated costs " + Arrays.toString(distanceTable[entity_number]) + " to neighboring entities ");
    		for (int i = 0; i < neighbors.size(); i++)
    		{
    			if (i != neighbors.size()-1)
    				System.out.print(neighbors.get(i) + ", ");
    			else
    				System.out.print(neighbors.get(i) + "\n");
    		}
            //sends out update of minimum costs to neighbors
    	    for(int i = 0; i < neighbors.size(); i++) {
	    		Packet newUpdate = new Packet(entity_number, neighbors.get(i), distanceTable[entity_number]);
		    	NetworkSimulator.toLayer2(newUpdate);
    	    }
            
        } else {
    		System.out.println("No change!");
    	}    
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
		System.out.println();
	    System.out.println("           via");
	    System.out.println(" D2 |  0   1   2   3");
	    System.out.println("----+-----------------");
	    for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
	    {
	        System.out.print("   " + i + "|");
	        for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
	        {
	            if (distanceTable[i][j] < 10)
	            {
	                System.out.print("   ");
	            }
	            else if (distanceTable[i][j] < 100)
	            {
	                System.out.print("  ");
	            }
	            else
	            {
	                System.out.print(" ");
	            }
	
	            System.out.print(distanceTable[i][j]);
	        }
	        System.out.println();
	    }
    }
}
