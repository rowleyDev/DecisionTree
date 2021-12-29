package DecisionTree.AllAttributesUsedResolvers;

import java.util.List;

import DecisionTree.Data.IData;

/**
 * Chooses the classification when all attributes have been exhausted in the dataset.
 */
public interface IResolveAllAttributesUsed
{
    String getClass(List<? extends IData> dataSet);
}
