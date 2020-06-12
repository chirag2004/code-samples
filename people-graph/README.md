--------------------------------------
Coding Exercise
--------------------------------------
Instructions :
- Answer the below problems by implementing the solutions complete
with accompanying unit tests to verify expected behavior.
- Create a file called notes.txt and describe all design decisions,
assumptions, or potential issues related to the solutions implemented.
- Package all source and tests into a zip with the following format :
<name>_apple_coding_exercise-<date>.zip
--------------------------------------
### Typesafe and Threadsafe cache
1. Build a simple cache implementation that conforms to the following
interface :
```java
 public interface Cache {
 public void put(Object key, Object value);
 public Object get(Object key);
 }
```

 Your cache implementation should provide the following :
 - Guarantee that there is a single instance of the cache
 - Guarantee the type safety of the elements stored in the cache
 - Guarantee the thread safety of the cache
 Build a unit test that demonstrates the functionality of the cache
based on the above requirements.
 * Note : You are allowed to slightly alter the Cache interface
described above to satisfy the above requirements.

### Directed Cyclic graph traversal

Implement a directed graph of people. Each node in the graph
should conform to the following interface :
 public interface Node {
 public String name();
 public Set<Node> children();
 }
 The graph should conform to the following properties :
 - The graph should contain at least 10 people
 - Each person should have at least 3, first level
"connections" to other people in the graph. i.e. Node.children() >= 3 should hold true for all nodes
in the graph.
 - The graph should be cyclic. 
 - Build a unit test that builds a graph of people and demonstrates
that all three properties described above are satisfied.

 - Implement a function that traverses the graph and returns a
map that consists of all people in the graph
 as "keys" that map to a list of all associated connections up
to a certain depth.
    - For example, if the depth is given as 2, the list of
"connections" will contain all 1st and 2nd level connections
 for each person.
 The function should look like the following :
 
```java
public Map<Node, List<Node>> findConnections(Node node, int depth);
```


- Implement a function that traverses the graph and returns
whether or not two people are "connected" and what
 "level" (or depth) the connection is within the graph.
 
    - For example if we have a simple connection path in the
graph : 
    John -> Suzy -> Bill -> Beth
 Then invoking connected(John, Beth) would result in 3, since
Beth is a 3rd level connection with John.
 If the two users are not connected, then the function should
return -1.
```
public int connected(Node node1, Node node2)
```