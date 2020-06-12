package graph.impl;

import graph.Graph;
import graph.Node;

import java.util.Set;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 12:59 PM
 */
public class PersonGraph extends Graph<PersonNode> {

    public PersonGraph(Set<PersonNode> nodes) {
        super(nodes);
    }

    PersonGraph addConnection(PersonNode node1, PersonNode node2) {
        node1.addConnection(node2);
        return this;
    }
}
