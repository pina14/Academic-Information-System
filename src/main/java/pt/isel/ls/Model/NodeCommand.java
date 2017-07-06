package pt.isel.ls.Model;

import pt.isel.ls.Model.Commands.Command;
import pt.isel.ls.Model.DataStructures.CustomMap;

public class NodeCommand {
    private final String[] EXCEPTIONALWORDS = {"{acr}", "{sem}", "{num}", "{pid}", "{numStu}"};
    private String command;
    private Command commandClassRef;
    private CustomMap<String, NodeCommand> children = new CustomMap<>();

    public NodeCommand() {
    }

    public NodeCommand(String command) {
        this.command = command;
    }


    public String getCommand() {
        return command;
    }

    public Command getCommandClassRef() {
        return commandClassRef;
    }

    public NodeCommand getChild(String cmd) {
        return children.get(cmd);
    }

    public boolean hasChildren() {
        return children.size() != 0;
    }

    public void setCommandClassRef(Command classRef) {
        this.commandClassRef = classRef;
    }

    public void addChild(String name) {
        children.put(name, new NodeCommand(name));
    }

    /**
     * Get the next different word from the command child.
     * If the word from the child is any of the EXCEPTIONALWORDS than return it.
     * @return Command that is represented in the EXCEPTIONALWORDS.
     */
    public NodeCommand getNextVariableCommand() {
        NodeCommand ret = null;
        for (int i = 0; i < EXCEPTIONALWORDS.length; i++) {
            ret = children.get(EXCEPTIONALWORDS[i]);
            if (ret != null) break;
        }
        return ret;
    }
}