package commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Nikitash Pawar on 10/11/2015.
 */
public class MarkCompletedCommand {


    @TargetAggregateIdentifier
    private final String todoId;


    public MarkCompletedCommand(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoId() {
        return todoId;
    }
}
