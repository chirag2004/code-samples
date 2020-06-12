package graph.impl;

import graph.impl.PersonGraph;
import graph.impl.PersonNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 4:32 PM
 */
public class GraphTestHarness {

    public PersonNode suzy, bill, john, beth, mario, luigi, warrio, tom, jerry, bugsBunny, lonely;

    private PersonNode[][] connections;

    private PersonNode[][] marioGroupCycle;

    private PersonNode[][] minThreeConnections;

    public PersonGraph createGraph() {
        createNodes();
        Set<PersonNode> personNodes = new HashSet<>();
        personNodes.add(suzy);
        personNodes.add(bill);
        personNodes.add(beth);
        personNodes.add(john);

        personNodes.add(mario);
        personNodes.add(luigi);
        personNodes.add(warrio);

        personNodes.add(tom);
        personNodes.add(jerry);
        personNodes.add(bugsBunny);
        personNodes.add(lonely);

        PersonGraph graph = new PersonGraph(personNodes);

        return graph;

    }

    public PersonGraph createConnectedGraph() {
        PersonGraph graph = createGraph();
        createConnections();
        addConnections(graph, connections);
        return graph;
    }
    public PersonGraph createSingleCycleGraph() {
        PersonGraph graph = createGraph();
        createMarioGroupCycle();
        addConnections(graph, marioGroupCycle);
        return graph;
    }
    public PersonGraph minThreeConnectionGraph() {
        PersonGraph graph = createGraph();
        createConnections();
        createMinThreeConnections();
        addConnections(graph, connections);
        addConnections(graph, minThreeConnections);
        return graph;
    }
    private PersonGraph addConnections(PersonGraph graph, PersonNode[][] connections) {
        for (PersonNode[] connection : connections) {
            addConnection(graph,connection);
        }
        return graph;
    }
    private void addConnection(PersonGraph graph, PersonNode[] connectionPair) {
        graph.addConnection(connectionPair[0],connectionPair[1]);
    }

    private void createNodes(){
        suzy= new PersonNode("Suzy");
        bill = new PersonNode("Bill");
        john = new PersonNode("John");
        beth = new PersonNode("Beth");

        mario = new PersonNode("Mario");
        luigi = new PersonNode("Luigi");
        warrio = new PersonNode("Warrio");

        tom = new PersonNode("Tom");
        jerry = new PersonNode("Jerry");
        bugsBunny = new PersonNode("Bugs Bunny");
        lonely = new PersonNode("Lonley");
    }

    private void createConnections(){
        this.connections = new PersonNode[][]{
                //group 1
                {suzy, bill},
                {suzy, john},
                {suzy, beth},
                {bill, john},
                {bill, beth},
                {john, beth},

                //group2
                {mario, luigi},
                {luigi, warrio},
                {warrio, mario},

                //group3
                {tom, jerry},
                {jerry, bugsBunny},
                {bugsBunny, tom}
        };
    }

    private void createMarioGroupCycle(){
        this.marioGroupCycle = new PersonNode[][] {
            //group2
            {mario, luigi},
            {luigi, warrio},
            {warrio, mario}
        };
    }

    private void createMinThreeConnections() {
        this.minThreeConnections = new PersonNode[][] {
            {bill, mario},
            {john, luigi},
            {john, tom},
            {suzy, jerry},
            {beth, bugsBunny},
            {beth, luigi},
            {beth, warrio},

            {mario, tom},
            {mario, jerry},
            {luigi,tom},
            {luigi, jerry},
            {warrio, tom},
            {warrio, jerry},


            {tom, bill},
            {tom, suzy},
            {jerry, john},
            {jerry, bill},
            {bugsBunny, suzy},
            {bugsBunny,bill},

            {lonely, tom},
            {lonely, jerry},
            {lonely, suzy}

        };
    }

}
