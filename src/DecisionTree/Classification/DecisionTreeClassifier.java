package DecisionTree.Classification;

import java.util.Map.Entry;

import DecisionTree.Data.IData;
import DecisionTree.Structure.DecisionNode;
import DecisionTree.Structure.LeafNode;
import DecisionTree.Structure.Node;
import DecisionTree.Structure.Tree;

/**
 * Wraps a decision tree for classification and display.
 **/
public class DecisionTreeClassifier
{
    private final Tree decisionTree;
    private String treeAsMultilineCachedString = null;
    private String treeAsSingleLineCachedString = null;

    /**
     * Initialises a new classifier with the given tree.
     *
     * @param tree The target tree
     **/
    public DecisionTreeClassifier(Tree decisionTree)
    {
        this.decisionTree = decisionTree;
    }

    /**
     * Classifies a new data point.
     *
     * @param data The data to classify
     * @return The classification result
     **/
    public ClassificationResult classify(IData data)
    {
        ClassificationRunner classifier = new ClassificationRunner(data, decisionTree.getRoot());
        return classifier.run();
    }

    /**
     * @return A multiline indented representation of the decision tree
     **/
    @Override
    public String toString()
    {
        if (treeAsMultilineCachedString == null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            walkTreeToMultilineString(decisionTree.getRoot(), stringBuilder, 0);
            treeAsMultilineCachedString = stringBuilder.toString();
        }

        return treeAsMultilineCachedString;
    }

    /**
     * @return A single line representation of the decision tree in nested bracketed form
     **/
    public String toSingleLineString()
    {
        if (treeAsSingleLineCachedString == null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            walkTreeToSingleLineString(decisionTree.getRoot(), stringBuilder);
            treeAsSingleLineCachedString = stringBuilder.toString();
        }

        return treeAsSingleLineCachedString;
    }

    /**
     * Serialises the tree to an indented multiline string.
     *
     * @param currentNode   Current node in the tree
     * @param stringBuilder StringBuilder to append to
     * @param depth         Depth of current node in tree
     */
    private void walkTreeToMultilineString(Node currentNode, StringBuilder stringBuilder, int depth)
    {
        stringBuilder.append(indentString(currentNode.toLongString(), depth * 3));
        stringBuilder.append(System.getProperty("line.separator"));

        if (currentNode instanceof DecisionNode)
        {
            for (Entry<String, Node> node : ((DecisionNode) currentNode).getChildren())
            {
                stringBuilder.append(indentString("| " + node.getKey(), depth * 3));
                stringBuilder.append(System.getProperty("line.separator"));
                walkTreeToMultilineString(node.getValue(), stringBuilder, depth + 1);
            }
        }
    }

    /**
     * Serialises the tree to a single line nested bracketed string.
     *
     * @param currentNode   Current node in the tree
     * @param stringBuilder StringBuilder to append to
     */
    private void walkTreeToSingleLineString(Node currentNode, StringBuilder stringBuilder)
    {
        if (currentNode instanceof LeafNode)
        {
            stringBuilder.append(">" + currentNode.toString());
        }
        else
        {
            stringBuilder.append("(" + currentNode.toString() + ":");
            for (Entry<String, Node> node : ((DecisionNode) currentNode).getChildren())
            {
                stringBuilder.append(node.getKey());
                walkTreeToSingleLineString(node.getValue(), stringBuilder);
                stringBuilder.append(',');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");
        }
    }

    /**
     * Indents a string by the given number of spaces.
     *
     * @param message Text to indent
     * @param depth   Number of spaces to indent by
     * @return The indented string
     */
    private String indentString(String message, int depth)
    {
        return " ".repeat(depth) + message;
    }
}