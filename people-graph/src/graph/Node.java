package graph;
import java.util.Set;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 7:17 AM
 */
public interface Node {

    public String name();

    public Set<Node> children();
}
