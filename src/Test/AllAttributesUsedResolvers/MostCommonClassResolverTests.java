package Test.AllAttributesUsedResolvers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import DecisionTree.AllAttributesUsedResolvers.MostCommonClassResolver;
import DecisionTree.Data.CSVDataContext;
import DecisionTree.Data.DataContextException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostCommonClassResolverTests
{
    @DisplayName("it returns most common value from a list of 1")
    @Test
    void mostCommon() throws DataContextException
    {
        MostCommonClassResolver mcr = new MostCommonClassResolver();
        CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
        dataContext.addData("1,2,3", "A");

        assertEquals("A", mcr.getClass(dataContext.getDataSet()));
    }

    @DisplayName("it returns most common value from a non trivial list")
    @Test
    void mostCommonNonTrivial() throws DataContextException
    {
        MostCommonClassResolver mcr = new MostCommonClassResolver();
        CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
        dataContext.addData("1,2,3", "A");
        dataContext.addData("1,2,3", "B");
        dataContext.addData("1,2,3", "B");
        dataContext.addData("1,2,3", "C");
        dataContext.addData("1,2,3", "C");
        dataContext.addData("1,2,3", "C");

        assertEquals("C", mcr.getClass(dataContext.getDataSet()));
    }

    @DisplayName("it returns earliest class alphabetically in event of a tie")
    @Test
    void earliestAlphabetically() throws DataContextException
    {
        MostCommonClassResolver mcr = new MostCommonClassResolver();
        CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
        dataContext.addData("1,2,3", "A");
        dataContext.addData("1,2,3", "B");

        assertEquals("A", mcr.getClass(dataContext.getDataSet()));
    }

    @DisplayName("it returns earliest class alphabetically in event of a tie across multiple values")
    @Test
    void earliestAlphabeticallyAcrossMultiple() throws DataContextException
    {
        MostCommonClassResolver mcr = new MostCommonClassResolver();
        CSVDataContext dataContext = new CSVDataContext("a1,a2,a3");
        dataContext.addData("1,2,3", "A");
        dataContext.addData("1,2,3", "B");
        dataContext.addData("1,2,3", "C");
        dataContext.addData("1,2,3", "A");
        dataContext.addData("1,2,3", "B");
        dataContext.addData("1,2,3", "C");

        assertEquals("A", mcr.getClass(dataContext.getDataSet()));
    }

}
