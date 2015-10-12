package handler;

import events.ToDoItemCompletedEvent;
import events.ToDoItemCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * Created by Nikitash Pawar on 10/11/2015.
 */
public class ToDoEventHandler {
    @EventHandler
    public void handle(ToDoItemCreatedEvent event) {
        System.out.println("We've got something to do: " + event.getDescription() + " (" + event.getTodoId() + ")");
    }

    @EventHandler
    public void handle(ToDoItemCompletedEvent event) {
        System.out.println("We've completed a task: " + event.getTodoId());
    }
}
