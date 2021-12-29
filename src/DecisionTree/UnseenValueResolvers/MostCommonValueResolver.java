package DecisionTree.UnseenValueResolvers;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Consumer;

import DecisionTree.Structure.Node;

/**
 * Chooses the value that occurred the most often in the training set.
 */
public class MostCommonValueResolver implements IResolveUnseenValue
{
    @Override
    public String resolve(String targetAttribute, String attributeValue, TreeMap<String, Node> children, Consumer<String> logger)
    {
        // Most common value seen in training data, then by alphabetical value in event of a tie
        String mostCommonTrainingValue = children.entrySet().stream().max(Comparator.comparing((Entry<String, Node> entry) -> entry.getValue().getOccurrencesInDataSet())
                .thenComparing(Entry::getKey, Comparator.reverseOrder())).get().getKey();

        if (logger != null)
        {
            logger.accept("Substituting " + mostCommonTrainingValue + " for " + attributeValue);
        }

        return mostCommonTrainingValue;
    }
}
