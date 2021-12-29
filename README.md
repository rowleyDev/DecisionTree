# Decision Tree

Java implementation of the ID3 Decision Tree Algorithm.

Both the tree builder and classification runner maintain an internal log so you can see exactly how the classification has been arrived at.

Various resolvers can be injected to handle inconsistent, real-world data.

# Example

### 1 - Create a data set containing the training examples

```
CSVDataContext dataContext = new CSVDataContext("Attribute 1,Attribute 2");
dataContext.addData("1,1", "Yes");
dataContext.addData("1,2", "No");
dataContext.addData("2,1", "Yes");
dataContext.addData("2,2", "No");`
```

### 2 - Train the decision tree on the training data with the chosen resolvers to handle unseen or inconsistent data

```
DecisionTreeClassifier classifier = new DecisionTreeClassifier(new Tree(dataContext.getDataSet(), dataContext.getAttributes(), new MostCommonClassResolver(), new MostCommonValueResolver(), null));
```

### 3 - Display the resulting tree

```
System.out.println(classifier.toString());
```

### 4 - Classify new examples

```
String result = classifier.classify(dataContext.parse("3,1")).toString();
```

# Resolvers

## All Attributes Used Resolvers

These are used when building the decision tree in the event that the training examples cannot be split further but the 
remaining examples have different classifications.

This suggests some 'fuzziness' in the system being modelled and is expected in real-world examples.

### Most common class resolvers

This resolver selects the most common class seen in the remaining training examples.
The earliest class name alphabetically is used in the event of a tie.

## Unseen Value Resolvers

These are used when navigating the decision tree to classify a new data point in the event that the new data point
contains an attribute value not seen in the training data.

### Most common value resolver

This resolver selects the most common value seen in the training examples and substitutes it for the 
unseen value. The earliest attribute value alphabetically is used in the event of a tie.