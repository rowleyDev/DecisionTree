package DecisionTree.Classification;

import DecisionTree.Data.IData;
import DecisionTree.Structure.DecisionNode;
import DecisionTree.Structure.LeafNode;
import DecisionTree.Structure.Node;
import DecisionTree.Structure.TreeNavigationException;

/**
 * Runs unseen data through an existing decision tree to obtain the classification result.
 */
class ClassificationRunner
{
    private final ClassificationResult classificationResult = new ClassificationResult();
    private final IData data;
    private final Node rootNode;

    /**
     * Creates a new classification runner.
     *
     * @param data     The data point to classify
     * @param rootNode The root node of the target decision tree
     **/
    public ClassificationRunner(IData data, Node rootNode)
    {
        this.data = data;
        this.rootNode = rootNode;
    }

    /**
     * Runs the data through the decision tree.
     *
     * @return The computed classification result
     **/
    public ClassificationResult run()
    {
        classifyData(rootNode);
        return classificationResult;
    }

    /**
     * Recursively classify the data stored in the data instance variable
     *
     * @param currentNode Current position in the tree
     */
    private void classifyData(Node currentNode)
    {
        if (currentNode instanceof DecisionNode)
        {
            DecisionNode currentDecisionNode = (DecisionNode) currentNode;
            String attributeValue = data.getAttributeValue(currentDecisionNode.getTargetAttribute());

            try
            {
                classifyData(currentDecisionNode.nextNode(attributeValue, classificationResult::log));
            }
            catch (TreeNavigationException treeNavigationException)
            {
                classificationResult.setStatus(Status.FAILED);
                classificationResult.log("Classification Failed");
            }
        }
        else
        {
            classificationResult.setStatus(Status.SUCCEEDED);
            classificationResult.setAssignedClass(((LeafNode) currentNode).getClassification());
        }
    }
}
