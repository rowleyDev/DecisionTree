package DecisionTree.AllAttributesUsedResolvers;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import DecisionTree.Data.IData;

/**
 * Returns the most common classification in the dataset, and the earliest alphabetically in the event of a tie.
 */
public class MostCommonClassResolver implements IResolveAllAttributesUsed
{
    @Override
    public String getClass(List<? extends IData> dataSet)
    {
        return dataSet.stream().collect(
                Collectors.groupingBy(dataPoint -> dataPoint.assignedClass(), Collectors.counting())).entrySet()
                .stream().max(Comparator.comparing((Entry<String, Long> entry) -> entry.getValue())
                        .thenComparing(Entry::getKey, Comparator.reverseOrder()))
                .get().getKey();
    }
}
