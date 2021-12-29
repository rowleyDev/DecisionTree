package Test.Classification.DecisionTreeClassifier;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import DecisionTree.AllAttributesUsedResolvers.MostCommonClassResolver;
import DecisionTree.Classification.DecisionTreeClassifier;
import DecisionTree.Data.CSVDataContext;
import DecisionTree.Data.DataContextException;
import DecisionTree.Structure.Tree;
import DecisionTree.UnseenValueResolvers.MostCommonValueResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleNodeTree
{
    static CSVDataContext dataContext;
    static DecisionTreeClassifier classifier;

    @BeforeAll
    static void setup() throws DataContextException
    {
        dataContext = new CSVDataContext("Attr1");
        dataContext.addData("1", "Yes");
        dataContext.addData("2", "No");
        dataContext.addData("2", "No");

        Tree tree = new Tree(dataContext.getDataSet(), dataContext.getAttributes(), new MostCommonClassResolver(), new MostCommonValueResolver(), null);

        classifier = new DecisionTreeClassifier(tree);
    }

    @DisplayName("it generates the correct single node tree")
    @Test
    void generate() throws DataContextException
    {
        assertEquals("(Attr1:1>Yes,2>No)", classifier.toSingleLineString());
    }

    @DisplayName("it classifies known values")
    @Test
    void classifyKnown() throws DataContextException
    {
        assertEquals("Yes", classifier.classify(dataContext.parse("1")).toString());
        assertEquals("No", classifier.classify(dataContext.parse("2")).toString());
    }

    @DisplayName("it classifies unknown values to the most common training value")
    @Test
    void classifyUnknown() throws DataContextException
    {
        assertEquals("No", classifier.classify(dataContext.parse("3")).toString());
    }
}