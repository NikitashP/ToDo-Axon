package events;

/**
 * Created by Nikitash Pawar on 10/11/2015.
 */
public class ToDoItemCompletedEvent {
    private final String todoId;

    public ToDoItemCompletedEvent(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoId() {
        return todoId;
    }
}
