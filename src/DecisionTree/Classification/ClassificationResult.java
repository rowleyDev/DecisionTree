package DecisionTree.Classification;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Holds the result of a classification and a log of how that result was derived.
 */
public class ClassificationResult
{
    private final ArrayList<String> classificationLog = new ArrayList<>();
    private String assignedClass;
    private Status status = Status.NOT_RUN;

    /**
     * Sets the assigned classification.
     *
     * @param assignedClass The assigned classification
     **/
    public void setAssignedClass(String assignedClass)
    {
        this.assignedClass = assignedClass;
    }

    /**
     * Adds a log message associated with this classification result.
     *
     * @param message The message
     **/
    public void log(String message)
    {
        classificationLog.add(message);
    }

    /**
     * @return The assigned classification
     **/
    @Override
    public String toString()
    {
        return assignedClass;
    }

    /**
     * @return The classification status
     **/
    public Status getStatus()
    {
        return status;
    }

    /**
     * Sets the classification status.
     *
     * @param status New status
     */
    public void setStatus(Status status)
    {
        this.status = status;
    }

    /**
     * @return A detailed description of the classification result
     **/
    public String toLongString()
    {
        String logString = classificationLog.stream().collect(Collectors.joining(", "));

        if (status == Status.SUCCEEDED)
        {
            return assignedClass + " - " + logString;
        }
        else if (status == Status.FAILED)
        {
            return "Classification failed - " + logString;
        }
        else if (status == Status.NOT_RUN)
        {
            return "Classification not yet run";
        }
        else
        {
            throw new RuntimeException("Unknown classification status");
        }
    }
}
