# CS4470-Computer-Networking-Protocols
In this project, you will be writing a "distributed'' set of procedures that implement a distributed asynchronous distance vector routing for the network shown below.

project1_1.png

You are to write the following functions which will  "execute'' asynchronously within the emulated environment written for the project.

For Entity0.java  Download Entity0.java, you will write:

the constructors initialize the distance table in node 0 to reflect the direct costs of 1, 3, and 7 to nodes 1, 2, and 3, respectively at the beginning of the emulation. In the above figure, all links are bi-directional and the costs in both directions are identical. After initializing the distance table,  it should then send its directly-connected neighbors (in this case, 1, 2 and 3) the cost of it minimum cost paths to all other network nodes.
update(Packet p). This function will be called when node 0 receives a routing packet that was sent to it by one if its directly connected neighbors. The parameter p is the packet that was received.
  

update(Packet p) is the "heart'' of the distance vector algorithm. The values it receives in a routing packet from some other node i contain i's current shortest path costs to all other network nodes. update(Packet p) uses these received values to update its own distance table (as specified by the distance vector algorithm). If its own minimum cost to another node changes as a result of the update, node 0 informs its directly connected neighbors of this change in minimum cost by sending them a routing packet. Recall that in the distance vector algorithm, only directly connected nodes will exchange routing packets. Thus nodes 1 and 2 will communicate with each other, but nodes 1 and 3 will node communicate with each other.

As we saw in class, the distance table inside each node is the principal data structure used by the distance vector algorithm. You will find it convenient to declare the distance table as a 4-by-4 array of int's, where entry [i,j] in the distance table in node 0 is node 0's currently computed cost to node i via direct neighbor j. If 0 is not directly connected to j, you can ignore this entry. We will use the convention that the integer value 999 is "infinity.''

The figure below provides a conceptual view of the relationship of the functions inside node 0.

project1_2.png

Similar functions are defined for nodes 1, 2 and 3. Thus, you will write 8 functions in all.

The simulated network environment

The four nodes send routing packets (whose format is described above) into the medium. The medium will deliver packets in-order, and without loss to the specified destination. Only directly-connected nodes can communicate. The delay between is sender and receiver is variable (and unknown).

When you compile your java files and provided java files together and run the resulting program, you will be asked to specify only one value regarding the simulated network environment:

Setting a tracing value of 1 or 2 will print out useful information about what is going on inside the emulation (e.g., what's happening to packets and timers). A tracing value of 0 will turn this off. A tracing value greater than 2 will display all sorts of odd messages that are for my own emulator-debugging purposes.

 

A tracing value of 2 may be helpful to you in debugging your code. You should keep in mind that real implementors do not have underlying networks that provide such nice information about what is going to happen to their packets!
 For your sample output, your functions should print out a message whenever the 4 constructors or the 4 update() functions are called. For the four update() functions,  you should print the identity of the sender of the routing packet that is being passed to your function, whether or not the distance table is updated, the contents of the distance table (you can use the provided pretty-print functions), and a description of any messages sent to neighboring nodes as a result of any distance table updates.

The sample output should be an output listing with a TRACE value of 2. Highlight the final distance table produced in each node. Your program will run until there are no more routing packets in-transit in the network, at which point my emulator will terminate.

The four java files you should modify:  

Entity0.java  Download Entity0.java, Entity1.java  Download Entity1.java,   Entity2.java  Download Entity2.java, Entity3.java Download Entity3.java

The provided java files (you should NOT modify these files):

Entity.java  Download Entity.java, NetworkSimulator.java  Download NetworkSimulator.java, Event.java  Download Event.java, Packet.java  Download Packet.java,  EventList.java  Download EventList.java,  EventListImpl.java  Download EventListImpl.java, Project.java Download Project.java

 

Acknowledgement:  The project is adapted from  the materials provided by Dr. Benyuan Liu.
