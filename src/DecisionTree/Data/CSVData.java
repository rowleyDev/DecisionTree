package DecisionTree.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a comma separated data point.
 */
public class CSVData implements IData
{
    private final String assignedClass;
    private final HashMap<String, String> values = new HashMap<>();

    /**
     * Creates a new data point from comma separated values.
     *
     * @param valuesString  Comma separated string representing the values of each attribute.
     *                      Must match the order as specified by the given list of attributes
     * @param attributes    List of attribute names
     * @param assignedClass The assigned classification of this data point
     * @throws DataContextException if the values are not compatible with the attributes or no values are given
     **/
    CSVData(String valuesString, ArrayList<String> attributes, String assignedClass) throws DataContextException
    {
        List<String> valuesSplit = Arrays.asList(valuesString.split(","));

        if (valuesSplit.size() != attributes.size())
        {
            throw new DataContextException("Attribute and value count do not match");
        }

        if (valuesSplit.size() == 0)
        {
            throw new DataContextException("No values present");
        }

        for (int i = 0; i < valuesSplit.size(); i++)
        {
            values.put(attributes.get(i), valuesSplit.get(i));
        }

        this.assignedClass = assignedClass;
    }

    /**
     * @return A string representation of all the attributes and values of this data point along with the assigned class
     **/
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(values.entrySet().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(",")));

        if (assignedClass != "")
        {
            stringBuilder.append(" | " + assignedClass);
        }
        return stringBuilder.toString();
    }

    /**
     * @param attribute The target attribute
     * @return The value of the given attribute for this data point
     **/
    @Override
    public String getAttributeValue(String attribute)
    {
        return values.get(attribute);
    }

    /**
     * @return The assigned classification of this data
     **/
    @Override
    public String assignedClass()
    {
        return assignedClass;
    }
}
