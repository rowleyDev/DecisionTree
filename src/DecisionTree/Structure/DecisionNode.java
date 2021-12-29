package DecisionTree.Structure;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import DecisionTree.UnseenValueResolvers.IResolveUnseenValue;

/**
 * Represents a node in the decision tree splitting on a target attribute.
 * Additionally holds the information gain and count of the remaining training samples at this point when training the decision tree.
 */
public class DecisionNode extends Node
{
    private final String targetAttribute;
    private final double gain;
    private final IResolveUnseenValue unseenValueResolver;

    /**
     * Creates a new decision node.
     *
     * @param targetAttribute      The attribute this node is splitting on
     * @param unseenValueResolver  Resolver for handling values not seen in the training data
     * @param gain                 The information gain this node develops from the training data
     * @param occurrencesInDataSet The number of data points that remained in the training data at this point in the tree
     */
    public DecisionNode(String targetAttribute, IResolveUnseenValue unseenValueResolver, double gain, int occurrencesInDataSet)
    {
        super(occurrencesInDataSet);
        this.targetAttribute = targetAttribute;
        this.unseenValueResolver = unseenValueResolver;
        this.gain = gain;
    }

    /**
     * @return The name of the attribute this node splits on
     */
    @Override
    public String toString()
    {
        return targetAttribute;
    }

    /**
     * @return The name of the attribute this node splits on along with the information gain and number of data points in the training set used to develop this node
     */
    public String toLongString()
    {
        return targetAttribute + '|' + gain + '|' + occurrencesInDataSet;
    }

    /**
     * Adds a child node to this node, under the given attribute value.
     *
     * @param attributeValue The value of the attribute
     * @param node           The child node
     */
    public void addChild(String attributeValue, Node node)
    {
        children.put(attributeValue, node);
    }

    /**
     * Returns the child node for the given value of the attribute this node splits on, or uses the unseen attribute resolver if
     * this attribute value has not been seen before.
     *
     * @param attributeValue Value of the attribute
     * @param logger         Receives log message detailing the navigation decision at this node
     * @return The child node for the given value of the attribute
     * @throws TreeNavigationException If the unseen value resolver cannot if required provide an alternative value
     */
    public Node nextNode(String attributeValue, Consumer<String> logger) throws TreeNavigationException
    {
        if (children.containsKey(attributeValue))
        {
            return children.get(attributeValue);
        }
        else
        {
            return children.get(unseenValueResolver.resolve(targetAttribute, attributeValue, children, logger));
        }
    }

    /**
     * @return The target attribute of this decision node
     */
    public String getTargetAttribute()
    {
        return targetAttribute;
    }

    /**
     * @return The children of this node indexed by the associated attribute value
     */
    public Set<Entry<String, Node>> getChildren()
    {
        return children.entrySet();
    }

}
