package Test.Data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import DecisionTree.Data.CSVData;
import DecisionTree.Data.CSVDataContext;
import DecisionTree.Data.DataContextException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVDataContextTest
{
    @DisplayName("it returns the correct values from a parsed data point")
    @Test
    void parse() throws DataContextException
    {
        CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
        CSVData data = dataContext.parse("v1,v2,v3");

        assertEquals("v1", data.getAttributeValue("a1"));
        assertEquals("v2", data.getAttributeValue("a2"));
        assertEquals("v3", data.getAttributeValue("a3"));
    }

    @DisplayName("it throws a DataContextException when a data point with too many attributes is added")
    @Test
    void tooManyAttributes() throws DataContextException
    {
        assertThrows(DataContextException.class, () -> {
            CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
            dataContext.addData("1,2,3,4", "5");
        });
    }

    @DisplayName("it throws a DataContextException when multiple attributes have the same name")
    @Test
    void sameName() throws DataContextException
    {
        assertThrows(DataContextException.class, () -> {
            new CSVDataContext("a1,a1,a2");
        });
    }
}
