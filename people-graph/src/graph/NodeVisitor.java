package graph;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 9:40 AM
 */
public interface NodeVisitor {
    public void visit(Node node);
    public boolean done();
}
