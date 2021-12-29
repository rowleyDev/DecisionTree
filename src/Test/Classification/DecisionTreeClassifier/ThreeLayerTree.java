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

public class ThreeLayerTree
{
    static CSVDataContext dataContext;
    static DecisionTreeClassifier classifier;

    @BeforeAll
    static void setup() throws DataContextException
    {
        dataContext = new CSVDataContext("Attr1,Attr2,Attr3");

        dataContext.addData("1,1,3", "C1");
        dataContext.addData("1,1,4", "C2");
        dataContext.addData("1,2,3", "C2");
        dataContext.addData("1,2,4", "C2");
        dataContext.addData("2,1,3", "C3");
        dataContext.addData("2,1,4", "C3");
        dataContext.addData("2,2,3", "C4");
        dataContext.addData("2,2,4", "C4");

        Tree tree = new Tree(dataContext.getDataSet(), dataContext.getAttributes(), new MostCommonClassResolver(), new MostCommonValueResolver(), null);
        classifier = new DecisionTreeClassifier(tree);
    }

    @DisplayName("it generates the correct tree")
    @Test
    void generate() throws DataContextException
    {
        assertEquals("(Attr1:1(Attr2:1(Attr3:3>C1,4>C2),2>C2),2(Attr2:1>C3,2>C4))", classifier.toSingleLineString());
    }

    @DisplayName("it classifies known values")
    @Test
    void classifyKnown() throws DataContextException
    {
        assertEquals("C1", classifier.classify(dataContext.parse("1,1,3")).toString());
        assertEquals("C2", classifier.classify(dataContext.parse("1,1,4")).toString());

        assertEquals("C3", classifier.classify(dataContext.parse("2,1,3")).toString());
        assertEquals("C4", classifier.classify(dataContext.parse("2,2,3")).toString());
    }

    @DisplayName("it classifies unknown values")
    @Test
    void classifiesUnknown() throws DataContextException
    {
        assertEquals("C4", classifier.classify(dataContext.parse("2,2,3")).toString());
    }
}
