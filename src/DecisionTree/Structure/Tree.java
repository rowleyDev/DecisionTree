package DecisionTree.Structure;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import DecisionTree.AllAttributesUsedResolvers.IResolveAllAttributesUsed;
import DecisionTree.Data.IData;
import DecisionTree.UnseenValueResolvers.IResolveUnseenValue;

/**
 * Represents a decision tree constructed using the ID3 algorithm
 */
public class Tree
{
    private final Node root;
    private final List<String> attributes;
    private final Consumer<String> logger;
    private final IResolveAllAttributesUsed allAttributesUsedResolver;
    private final IResolveUnseenValue unseenValuesResolver;

    /**
     * Trains a new decision tree on the given training data.
     *
     * @trainingData The training data
     * @attributes List of attribute names
     * @allAttributesUsedResolver Resolver for inconsistent classes in training examples
     * @unseenAttributeResolver Resolver for classifying values not seen in training examples
     * @logger Receives log messages detailing the construction of the decision tree, may be null if no logging is required
     **/
    public Tree(List<? extends IData> trainingData, List<String> attributes, IResolveAllAttributesUsed allAttributesUsedResolver,
                IResolveUnseenValue unseenValuesResolver, Consumer<String> logger)
    {
        this.logger = logger;
        this.attributes = attributes;
        this.allAttributesUsedResolver = allAttributesUsedResolver;
        this.unseenValuesResolver = unseenValuesResolver;

        root = createTree(trainingData, new HashSet<String>(), 0);
    }

    /**
     * @return The root node of this decision tree
     */
    public Node getRoot()
    {
        return root;
    }

    /**
     * Recursively creates the decision tree by the ID3 algorithm.
     *
     * @param dataSet        The training set
     * @param usedAttributes List of attributes that have been previously used prior to this node in the tree
     * @param depth          Depth of the node within the tree
     * @return The root node of the tree
     */
    private Node createTree(List<? extends IData> dataSet, HashSet<String> usedAttributes, int depth)
    {
        if (allSameClass(dataSet))
        {
            String sameClass = dataSet.get(0).assignedClass();
            log("All remaining samples in same class", depth * 3);
            return new LeafNode(sameClass, "All remaining samples in same class", dataSet.size());
        }
        else if (usedAllAttributes(usedAttributes))
        {
            String resolvedClass = allAttributesUsedResolver.getClass(dataSet);
            log("All attributes used, assigning the following class: " + resolvedClass, depth * 3);
            return new LeafNode(resolvedClass, "All attributes used, assigning the following class: ", dataSet.size());
        }
        else
        {
            HashMap<String, Double> gains = computeAttributeGains(dataSet, attributes.stream().filter(attribute -> !usedAttributes.contains(attribute)).collect(Collectors.toList()));

            // Sort by highest gain, then by alphabetical attribute name
            String bestAttribute = gains.entrySet().stream().max(Comparator.comparing(Entry<String, Double>::getValue).thenComparing(Entry::getKey, Comparator.reverseOrder())).get().getKey();

            DecisionNode node = new DecisionNode(bestAttribute, unseenValuesResolver, gains.get(bestAttribute), dataSet.size());
            usedAttributes.add(bestAttribute);

            log(bestAttribute + " is the best attribute", depth * 3);

            for (var subset : dataSet.stream().collect(Collectors.groupingBy(dataPoint -> dataPoint.getAttributeValue(bestAttribute))).entrySet())
            {
                log("| " + subset.getKey(), depth * 3);

                HashSet<String> copyOfUsedAttributes = new HashSet<>(usedAttributes);
                node.addChild(subset.getKey(), createTree(subset.getValue(), copyOfUsedAttributes, depth + 1));
            }

            return node;
        }
    }

    /**
     * Computes the information gain for all the available attributes.
     *
     * @param dataSet             The target data set
     * @param availableAttributes List of available attributes
     * @return Map listing each attribute and the information gain it provides by splitting the data set on this attribute
     */
    private HashMap<String, Double> computeAttributeGains(List<? extends IData> dataSet, List<String> availableAttributes)
    {
        HashMap<String, Double> attributeGains = new HashMap<>();
        availableAttributes.forEach(attribute -> attributeGains.put(attribute, gain(dataSet, attribute)));
        return attributeGains;
    }

    /**
     * Returns the information gain by spitting the given data set on the given attribute.
     *
     * @param dataSet   The target data set
     * @param attribute The attribute to split on
     * @return The information gain
     */
    private double gain(List<? extends IData> dataSet, String attribute)
    {
        double totalEntropy = 0;

        for (var subSet : dataSet.stream().collect(Collectors.groupingBy(dataPoint -> dataPoint.getAttributeValue(attribute))).entrySet())
        {
            double proportion = (double) subSet.getValue().size() / dataSet.size();

            totalEntropy += proportion * entropy(subSet.getValue());
        }

        return entropy(dataSet) - totalEntropy;
    }

    /**
     * Returns the entropy in the given data set.
     *
     * @param dataSet The target data set
     * @return The entropy
     */
    private double entropy(List<? extends IData> dataSet)
    {
        double entropy = 0;

        for (var subset : dataSet.stream().collect(Collectors.groupingBy(dataPoint -> dataPoint.assignedClass())).entrySet())
        {
            double proportion = (double) subset.getValue().size() / dataSet.size();

            entropy -= proportion * Math.log(proportion);
        }

        return entropy;
    }

    /**
     * Determines if all data points in the data set have the same assigned classification
     *
     * @param dataSet The target data set
     * @return True if all data points in the data set have the same assigned classification
     */
    private boolean allSameClass(List<? extends IData> dataSet)
    {
        return dataSet.stream().collect(Collectors.groupingBy(dataPoint -> dataPoint.assignedClass(), Collectors.counting())).size() == 1;
    }

    /**
     * Determines if all attributes have been used.
     *
     * @param usedAttributes Set of used attributes
     * @return True if all attributes have been used
     */
    private boolean usedAllAttributes(HashSet<String> usedAttributes)
    {
        return usedAttributes.size() == attributes.size();
    }

    /**
     * Adds a message to the tree construction log.
     *
     * @param message Message text
     * @param depth   Indention depth
     */
    private void log(String message, int depth)
    {
        if (logger != null)
        {
            logger.accept(" ".repeat(depth) + message);
        }
    }

}
