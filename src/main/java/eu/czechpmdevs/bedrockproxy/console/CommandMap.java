package eu.czechpmdevs.bedrockproxy.console;

import eu.czechpmdevs.bedrockproxy.console.commands.HelpCommand;
import eu.czechpmdevs.bedrockproxy.console.commands.VersionCommand;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandMap {

    @Getter
    private Console console;

    @Getter
    private Map<String, Command> commands = new HashMap<String, Command>();

    public CommandMap(Console console) {
        this.console = console;
        this.registerDefaults();
    }

    public void registerDefaults() {
        this.registerCommand(new HelpCommand(this));
        this.registerCommand(new VersionCommand(this));
    }

    public void registerCommand(Command command) {
        registerCommand(command, false);
    }

    public void registerCommand(Command command, boolean override) {
        if(!override && this.commands.containsKey(command.getName())) {
            return;
        }

        this.commands.put(command.getName(), command);
    }

    public boolean executeCommand(CommandSender sender, String commandLine) {
        String[] split = commandLine.split(" ");
        String alias = split[0];
        
        if(!this.commands.containsKey(alias)) {
            for(Command command : this.commands.values()) {
                if(command instanceof AliasedCommand) {
                    if(Arrays.asList(((AliasedCommand) command).getAliases()).contains(alias)) {
                        alias = command.getName();
                        break;
                    }
                }
            }
        }

        if(!this.commands.containsKey(alias)) {
            if(sender instanceof ConsoleCommandSender) { // We won't send it to in-game players. This should be handled by server
                sender.sendMessage("§cUnknown command. Try 'help' to get list of commands.");
            }
            return false;
        }

        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length - 1);

        this.commands.get(alias).execute(sender, args);
        return true;
    }
}
