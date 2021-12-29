package DecisionTree.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a collection of data points in comma separated format with respect to the same set of attributes, such as a training data set.
 */
public class CSVDataContext
{
    private final ArrayList<String> attributes = new ArrayList<String>();
    private final ArrayList<CSVData> dataSet = new ArrayList<CSVData>();

    /**
     * Creates a new data context for comma separated data with the proposed attributes.
     *
     * @param proposedAttributes Comma separated string of attribute names that all data points in the collection are in reference to
     * @throws DataContextException if invalid attributes are given
     */
    public CSVDataContext(String proposedAttributes) throws DataContextException
    {
        List<String> attributesList = Arrays.asList(proposedAttributes.split(","));

        if (attributesList.size() == 0)
        {
            throw new DataContextException("No attributes specified");
        }

        for (String attribute : attributesList)
        {
            if (!attributes.contains(attribute))
            {
                attributes.add(attribute);
            }
            else
            {
                throw new DataContextException("Multiple attributes with the same name: " + attribute);
            }

            if (attribute == "")
            {
                throw new DataContextException("Attribute name is empty");
            }
        }
    }

    /**
     * Adds a new set of comma separated values with an assigned classification to this data context.
     *
     * @param csvValues     The comma separated values representing the data point
     * @param assignedClass The assigned classification of the data point
     * @throws DataContextException if invalid values are given
     */
    public void addData(String csvValues, String assignedClass) throws DataContextException
    {
        dataSet.add(new CSVData(csvValues, attributes, assignedClass));
    }

    /**
     * Parses a comma separated data point with respect to the attributes defined on this data context.
     *
     * @param values The comma separated values representing this data point
     * @return A new data point parsed with respect to the attributes defined on this data context
     * @throws DataContextException if these values are incompatible with this data context
     */
    public CSVData parse(String values) throws DataContextException
    {
        return new CSVData(values, attributes, "");
    }

    /**
     * @return The data set
     */
    public List<CSVData> getDataSet()
    {
        return dataSet;
    }

    /**
     * @return The attribute list
     */
    public ArrayList<String> getAttributes()
    {
        return attributes;
    }

}
