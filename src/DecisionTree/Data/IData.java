package DecisionTree.Data;

public interface IData
{
    /**
     * @return The assigned classification for this data point
     */
    String assignedClass();

    /**
     * @param attribute The target attribute
     * @return The value associated with the given attribute for this data point
     */
    String getAttributeValue(String attribute);
}
