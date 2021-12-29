package DecisionTree.Structure;

/**
 * Represents a leaf node in the decision tree that contains the classification that should be assigned to a new data point.
 */
public class LeafNode extends Node
{
    private final String classification;
    private final String reason;

    public LeafNode(String classification, String reason, int occurrencesInDataSet)
    {
        super(occurrencesInDataSet);
        this.classification = classification;
        this.reason = reason;
    }

    public String getClassification()
    {
        return classification;
    }

    public String getReason()
    {
        return reason;
    }

    @Override
    public String toString()
    {
        return classification;
    }

    @Override
    public String toLongString()
    {
        return classification + '|' + reason + '|' + occurrencesInDataSet;
    }
}
