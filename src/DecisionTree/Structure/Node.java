package DecisionTree.Structure;

import java.util.TreeMap;

/**
 * Abstract base class of both decision and leaf nodes in the decision tree.
 */
public abstract class Node
{
    protected final TreeMap<String, Node> children = new TreeMap<String, Node>();
    protected int occurrencesInDataSet;

    /**
     * @param occurrencesInDataSet Number of times the value leading to this node appeared in the training data set
     */
    public Node(int occurrencesInDataSet)
    {
        this.occurrencesInDataSet = occurrencesInDataSet;
    }

    /**
     * @return A detailed string describing this node
     */
    public abstract String toLongString();

    /**
     * @return Number of times the value leading to this node appeared in the training data set
     */
    public int getOccurrencesInDataSet()
    {
        return occurrencesInDataSet;
    }
}
