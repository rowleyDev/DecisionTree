package Test.UnseenValueResolvers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import DecisionTree.Data.DataContextException;
import DecisionTree.Structure.DecisionNode;
import DecisionTree.Structure.Node;
import DecisionTree.UnseenValueResolvers.MostCommonValueResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostCommonValueResolverTests
{
    @DisplayName("it returns most common value from a list of 1")
    @Test
    void mostCommon() throws DataContextException
    {
        MostCommonValueResolver mostCommonValueResolver = new MostCommonValueResolver();

        TreeMap<String, Node> children = new TreeMap<>();
        children.put("a1", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));

        assertEquals("a1", mostCommonValueResolver.resolve("TA", "unseen", children, null));
    }

    @DisplayName("it returns most common value from a non trivial list")
    @Test
    void mostCommonNonTrivial() throws DataContextException
    {
        MostCommonValueResolver mostCommonValueResolver = new MostCommonValueResolver();

        TreeMap<String, Node> children = new TreeMap<>();
        children.put("a1", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));
        children.put("a2", new DecisionNode("A1", mostCommonValueResolver, 1.0, 30));
        children.put("a3", new DecisionNode("A1", mostCommonValueResolver, 1.0, 20));

        assertEquals("a2", mostCommonValueResolver.resolve("TA", "unseen", children, null));
    }

    @DisplayName("it returns earliest value alphabetically in event of a tie")
    @Test
    void earliestAlphabetically() throws DataContextException
    {
        MostCommonValueResolver mostCommonValueResolver = new MostCommonValueResolver();

        TreeMap<String, Node> children = new TreeMap<>();
        children.put("a", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));
        children.put("b", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));

        assertEquals("a", mostCommonValueResolver.resolve("TA", "unseen", children, null));
    }

    @DisplayName("it returns earliest class alphabetically in event of a tie across multiple values")
    @Test
    void earliestAlphabeticallyAcrossMultiple() throws DataContextException
    {
        MostCommonValueResolver mostCommonValueResolver = new MostCommonValueResolver();

        TreeMap<String, Node> children = new TreeMap<>();
        children.put("a", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));
        children.put("b", new DecisionNode("A1", mostCommonValueResolver, 1.0, 20));
        children.put("c", new DecisionNode("A1", mostCommonValueResolver, 1.0, 5));
        children.put("d", new DecisionNode("A1", mostCommonValueResolver, 1.0, 20));
        children.put("e", new DecisionNode("A1", mostCommonValueResolver, 1.0, 10));

        assertEquals("b", mostCommonValueResolver.resolve("TA", "unseen", children, null));
    }
}
