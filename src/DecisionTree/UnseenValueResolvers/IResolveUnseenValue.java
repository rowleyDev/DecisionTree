package DecisionTree.UnseenValueResolvers;

import java.util.TreeMap;
import java.util.function.Consumer;

import DecisionTree.Structure.Node;
import DecisionTree.Structure.TreeNavigationException;

/**
 * Chooses the next node in the decision tree when the data includes a value not seen in the training data.
 * Resolve may throw a TreeNavigationException if no node is appropriate and the classification will immediately fail.
 * The logger should be called with an explanation as to how the unseen value has been resolved.
 */
public interface IResolveUnseenValue
{
    String resolve(String targetAttribute, String attributeValue, TreeMap<String, Node> children, Consumer<String> logger) throws TreeNavigationException;
}
