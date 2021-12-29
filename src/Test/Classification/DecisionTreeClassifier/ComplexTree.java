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

class ComplexTree
{
    static CSVDataContext dataContext;
    static DecisionTreeClassifier classifier;

    @BeforeAll
    static void setup() throws DataContextException
    {
        dataContext = new CSVDataContext("Sector,Market,Structure,Debt Ratio");
        dataContext.addData("Tech,INT,LLP,Low", "Yes");
        dataContext.addData("Tech,UK,GP,Low", "Yes");
        dataContext.addData("Tech,INT,Corp,Low", "No");
        dataContext.addData("Tech,US,LLP,Med", "Yes");
        dataContext.addData("Tech,US,Corp,Low", "Yes");
        dataContext.addData("Health,UK,LLP,Low", "Yes");
        dataContext.addData("Health,INT,LLP,High", "Yes");
        dataContext.addData("Health,US,GP,High", "No");
        dataContext.addData("Health,EU,Corp,Low", "No");
        dataContext.addData("Energy,UK,LLP,Low", "Yes");
        dataContext.addData("Energy,UK,Corp,Low", "No");
        dataContext.addData("Energy,US,GP,Med", "Yes");
        dataContext.addData("Energy,EU,GP,High", "No");
        dataContext.addData("Energy,EU,GP,Low", "Yes");
        dataContext.addData("Media,EU,GP,Low", "Yes");
        dataContext.addData("Media,INT,LLP,Low", "Yes");
        dataContext.addData("Media,US,Corp,Med", "Yes");
        dataContext.addData("Media,EU,GP,High", "No");

        Tree tree = new Tree(dataContext.getDataSet(), dataContext.getAttributes(), new MostCommonClassResolver(), new MostCommonValueResolver(), null);
        classifier = new DecisionTreeClassifier(tree);

    }

    @DisplayName("it generates the correct tree")
    @Test
    void generate() throws DataContextException
    {
        assertEquals("(Structure:Corp(Market:EU>No,INT>No,UK>No,US>Yes),GP(Debt Ratio:High>No,Low>Yes,Med>Yes),LLP>Yes)", classifier.toSingleLineString());
    }

    @DisplayName("it classifies values correctly")
    @Test
    void classify() throws DataContextException
    {

        assertEquals("Yes", classifier.classify(dataContext.parse("Tech,UK,LLP,Low")).toString());

        assertEquals("Yes", classifier.classify(dataContext.parse("Energy,INT,GP,Low")).toString());
        assertEquals("Yes", classifier.classify(dataContext.parse("Health,INT,GP,Med")).toString());
        assertEquals("No", classifier.classify(dataContext.parse("Media,INT,GP,High")).toString());

        assertEquals("No", classifier.classify(dataContext.parse("Tech,INT,Corp,Med")).toString());
        assertEquals("No", classifier.classify(dataContext.parse("Energy,UK,Corp,Low")).toString());
        assertEquals("Yes", classifier.classify(dataContext.parse("Health,US,Corp,High")).toString());
        assertEquals("No", classifier.classify(dataContext.parse("Media,EU,Corp,High")).toString());

    }

    @DisplayName("it substitutes unseen values")
    @Test
    void substitutesUnknownValues() throws DataContextException
    {
        assertEquals(true, classifier.classify(dataContext.parse("Media,EU,LLC,Zero")).toLongString().contains("Substituting GP for LLC"));
        assertEquals(true, classifier.classify(dataContext.parse("Media,EU,LLC,Zero")).toLongString().contains("Substituting High for Zero"));
    }
}
