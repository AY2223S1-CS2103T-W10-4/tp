package modtrekt.model.task;

/**
 * Represents a basic task in the task list.
 * Ensures that necessary details are valid, present and non-null.
 */
public class Task {

    /** String representing description of task */
    public final Description description;

    /**
     * Constructor for an instance of Task.
     *
     * @param description description of task
     */
    public Task(Description description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description.toString();
    }
}
