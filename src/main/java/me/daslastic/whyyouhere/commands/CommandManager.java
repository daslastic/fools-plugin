package me.daslastic.whyyouhere.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.commands.subcommands.ranks.*;
import me.daslastic.whyyouhere.player.PlayerManager;
import me.daslastic.whyyouhere.util.UHelp;
import me.daslastic.whyyouhere.util.UText;

import java.util.*;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {
    
    private final Map<String, CommandData> commandManager = new HashMap<>();
    private final SMP plugin;

    public CommandManager(SMP plugin) {
        this.plugin = plugin;

        commandManager.put("rank", new CommandData(
            "whyyouhere.admin",
            null, false, Arrays.asList(
                new AssignRank()
            ),
            arg -> {
                if (arg.equals("<id>")) {
                    //return new ArrayList<>(SMP.getInstance().playerManager().getRanks().keySet());
                }
                return null;
            }
        ));


        for (String key : commandManager.keySet()) {
            plugin.getCommand(key).setExecutor(this);
        }

        plugin.getCommand("help").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("help")) {
            sender.sendMessage("");
            for (String command : commandManager.keySet()) {
                sendHelp(sender, command);
                sender.sendMessage("");
            }
            return true;
        }

        for (String command : commandManager.keySet()) {
            if (Objects.equals(command, label)) {
                if (args.length == 0) {
                    sendHelp(sender, command);
                    return true;
                }
                
                CommandData commandData = commandManager.get(command);
                if (commandData.getPermission() != null && !sender.hasPermission(commandData.getPermission())) {
                    sender.sendMessage(UText.color("&cYou are not permitted to execute this command!"));
                    return true;
                }

                for (SubCommand subCommand : commandData.getSubCommands()) {
                    if (args[0].equalsIgnoreCase(subCommand.getName())) {
                        if(commandData.requirePlayer() && !(sender instanceof Player) ) {
                            sender.sendMessage(UText.color("&cOnly players may execute this command."));
                            return true;
                        } else if (subCommand.getSyntax() == null) {
                            subCommand.execute(plugin, sender, args);
                            return true;
                        }
                        List<String> subArgs = Arrays.stream(subCommand.getSyntax().split("\\s+")).toList();
                        subArgs = subArgs.stream().filter(s -> !s.contains("optional")).collect(Collectors.toList());
                        int len = subArgs.size();
                        if (len == 0 || args.length - 1 >= len) {
                            subCommand.execute(plugin, sender, args);
                            return true;
                        } else {
                            sender.sendMessage(UText.cmdNoArgs("/" + label + " " + subCommand.getName() + " " + subCommand.getSyntax()));
                            return true;
                        }
                    }
                }
            }
        }

        sender.sendMessage(UText.color("&cInvalid arguments! &f/help&c can guide you ;)"));
        return true;
    }

    private void sendHelp(CommandSender sender, String command) {
        StringBuilder subCommands = new StringBuilder();
        CommandData commandData = commandManager.get(command);
        if(commandData.getPermission() != null && !sender.hasPermission(commandData.getPermission())) {
            return;
        }
        if (commandData.getSubCommands().size() > 0) {
            for (int i = 0; i < commandData.getSubCommands().size(); i++) {
                SubCommand subCommand = commandData.getSubCommands().get(i);
                subCommands
                        .append("&a/")
                        .append(command)
                        .append(" ")
                        .append(subCommand.getName())
                        .append((subCommand.getSyntax() != null) ? " " : "")
                        .append((subCommand.getSyntax() != null) ? subCommand.getSyntax() : "")
                        .append("&f - ")
                        .append(subCommand.getDescription())
                        .append("\n");
            }
        }
        sender.sendMessage(UHelp.getTree(UText.capitalize(command) + " Manager", subCommands.toString()));
        if(commandData.getDescription() != null)
            sender.sendMessage("\n" + UText.color(commandData.getDescription()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        for (String key : commandManager.keySet()) {
            if (label.equals(key)) {
                if (args.length == 1) {
                    return commandManager.get(label).getSubCommands().stream().map(SubCommand::getName).collect(Collectors.toList());
                } else if (args.length > 1) {
                    CommandData commandData = commandManager.get(key);
                    for (SubCommand subCommand : commandData.getSubCommands()) {
                        if (subCommand.getName().equals(args[0])) {
                            if(subCommand.getSyntax() == null) break;
                            String[] cmdArgs = subCommand.getSyntax().split("\\s+");
                            if (args.length >= cmdArgs.length + 2) {
                                break;
                            } else {
                                String arg = cmdArgs[args.length - 2];
                                if (arg.equals("<player>")) {
                                    return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
                                }
                                return Objects.requireNonNullElseGet(commandData.filter(arg), () -> List.of(arg));
                            }
                        }
                    }
                }
                break;
            }
        }
        return null;
    }
    
}
