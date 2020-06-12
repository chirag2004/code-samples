package graph.impl;

import graph.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 7:18 AM
 */
public class PersonNode implements Node {
    private final String name;
    private final Set<Node> connections;

    public PersonNode(String name) {
        if(name == null || name.length() == 0) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
        this.connections = new HashSet<>();
    }
    @Override
    public String name() {
        return name;
    }

    @Override
    public Set<Node> children() {
        return Collections.unmodifiableSet(this.connections);
    }

    PersonNode addConnection(PersonNode person) {
        this.connections.add(person);
        return this;
    }

    public String toString(){
        return name;
    }
}
