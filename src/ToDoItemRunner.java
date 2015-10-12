import aggregate.ToDoItem;
import commands.CreateToDoItemCommand;
import commands.MarkCompletedCommand;
import handler.ToDoEventHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.domain.AggregateRoot;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.repository.LockManager;
import org.axonframework.repository.OptimisticLockManager;
import org.jgroups.util.UUID;

import java.io.File;

/**
 * Created by Nikitash Pawar on 10/11/2015.
 */
public class ToDoItemRunner {
    public static void main(String[] args) {
        // let's start with the Command Bus
        CommandBus commandBus = new SimpleCommandBus();

        // the CommandGateway provides a friendlier API
        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        // we'll store Events on the FileSystem, in the "events/" folder
        EventStore eventStore = new FileSystemEventStore(new SimpleEventFileResolver(new File("./eventStore")));

        // a Simple Event Bus will do
        EventBus eventBus = new SimpleEventBus();

        // we need to configure the repository
        EventSourcingRepository repository = new EventSourcingRepository(ToDoItem.class);
        repository.setEventBus(eventBus);
        repository.setEventStore(eventStore);

        // Axon needs to know that our ToDoItem Aggregate can handle commands
        AggregateAnnotationCommandHandler.subscribe(ToDoItem.class, repository, commandBus);

        AnnotationEventListenerAdapter.subscribe(new ToDoEventHandler(), eventBus);

        // and let's send some Commands on the CommandBus.
        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateToDoItemCommand(itemId, "Need to do this"));
        commandGateway.send(new MarkCompletedCommand(itemId));
    }
}
