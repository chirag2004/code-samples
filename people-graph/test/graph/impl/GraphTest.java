package graph.impl;

import graph.Graph;
import graph.Node;
import graph.impl.GraphTestHarness;
import graph.impl.PersonGraph;
import graph.impl.PersonNode;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GraphTest extends TestCase {

    private GraphTestHarness harness = new GraphTestHarness();
    public GraphTest(){

    }
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testMinimumNodes() {
        Graph<PersonNode> graph = harness.createGraph();
        assertTrue("graph has 10 nodes",graph.nodeCount() >= 10);
    }

    @Test
    public void testMinimumConnections() {
        PersonGraph graph = harness.createConnectedGraph();
        assertTrue("graph has less than 3 minimum node connections",graph.getMinimumNodeConnections() < 3);
        graph = harness.minThreeConnectionGraph();
        assertTrue("graph has less than 3 minimum node connections",graph.getMinimumNodeConnections() >= 3);
    }

    @Test
    public void testCycles() {
        PersonGraph graph = harness.createGraph();
        assertFalse("graph has no cycles", graph.hasCycle());
        graph = harness.createSingleCycleGraph();
        assertTrue("graph has cycle",graph.hasCycle());

    }

    @Test
    public void testConnectionLevel() {
        GraphTestHarness harness = new GraphTestHarness();
        PersonGraph graph = harness.minThreeConnectionGraph();
        assertEquals(1, graph.connected(harness.suzy, harness.beth));
        assertEquals(1, graph.connected(harness.suzy, harness.bill));
        assertEquals(2, graph.connected(harness.beth, harness.suzy));
        assertEquals(2,graph.connected(harness.tom, harness.john));
        assertEquals(2,graph.connected(harness.mario, harness.suzy));
        assertEquals(2, graph.connected(harness.bugsBunny, harness.mario));
        assertEquals(1, graph.connected(harness.warrio, harness.tom));
        assertEquals(1, graph.connected(harness.warrio, harness.tom));
        assertEquals(-1, graph.connected(harness.luigi, harness.lonely));
        assertEquals(3, graph.connected(harness.lonely, harness.mario));
    }

    @Test
    public void testFindConnectionsLevel1() {
        GraphTestHarness harness = new GraphTestHarness();
        PersonGraph graph = harness.minThreeConnectionGraph();
        Map<Node, List<Node>> connections = graph.findConnections(harness.suzy,1);
        assertEquals(10, connections.size());
        assertTrue(connections.get(harness.bugsBunny).containsAll(Arrays.asList(harness.tom,harness.bill, harness.suzy)));
        assertTrue(connections.get(harness.suzy).containsAll(Arrays.asList(harness.beth,harness.john, harness.bill, harness.jerry)));
    }

    @Test
    public void testFindConnectionsLevel2() {
        GraphTestHarness harness = new GraphTestHarness();
        PersonGraph graph = harness.minThreeConnectionGraph();
        Map<Node, List<Node>> connections = graph.findConnections(harness.suzy,2);
        assertEquals(10, connections.size());
        List<PersonNode> suzyAndBugsBunnyConnections = new ArrayList<>(Arrays.asList(harness.tom,harness.bill, harness.suzy));
        suzyAndBugsBunnyConnections.addAll(Arrays.asList(harness.beth, harness.john, harness.bill, harness.jerry));
        assertTrue(connections.get(harness.bugsBunny).containsAll(suzyAndBugsBunnyConnections));
    }
}

