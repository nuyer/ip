package duke.commands;

import duke.DukeException.DukeException;
import duke.tasks.TaskList;
import java.util.List;

/**
 * The Command class is an abstract class that represents a command to be executed on a TaskList.
 */
public abstract class Command {
    public abstract List<String> execute(TaskList tasks) throws DukeException;
}
