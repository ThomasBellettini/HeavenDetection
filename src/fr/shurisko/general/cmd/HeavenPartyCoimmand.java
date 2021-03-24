package fr.shurisko.general.cmd;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.api.team.HeavenTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeavenPartyCoimmand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] arg) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            HeavenPlayer heavenPlayer;

            heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
            if (heavenPlayer == null) {
                Heaven.getPlayerLoader.addPlayer(new HeavenPlayer(p.getName()));
                heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
            }

            if (arg.length == 2) {
                if (arg[0].equalsIgnoreCase("create")) {
                    if (heavenPlayer.getPlayerTeam() == null) {
                        if (Heaven.getPartyLoader.getTeamFromName(arg[1]) != null) {
                            p.sendMessage("§eHeaven → §cCette team existe déjà !");
                            return true;
                        }
                        p.sendMessage("§eHeaven → " + Heaven.getPartyLoader.createTeam(new HeavenTeam(heavenPlayer, 6, arg[1])));
                        return true;
                    } else {
                        p.sendMessage("§eHeaven → §cVous devez d'abord disband votre party avant d'en crée une nouvelle !");
                        return true;
                    }
                } else if (arg[0].equalsIgnoreCase("show") || arg[0].equalsIgnoreCase("info") || arg[0].equalsIgnoreCase("list")) {
                    HeavenTeam team = Heaven.getPartyLoader.getTeamFromName(arg[1]);
                    Player players = Bukkit.getPlayer(arg[1]);

                    if (team == null && players == null) {
                        p.sendMessage("§eHeaven → §cCette team est introuvable !");
                        return true;
                    } else if (team == null && players != null) {
                        HeavenPlayer heavenPlayer_second = Heaven.getPlayerLoader.getPlayerFromBukkit(players);
                        team = heavenPlayer_second.getPlayerTeam();

                        if (team == null) {
                            p.sendMessage("§eHeaven → §cCe joueur ne fait pas partie de Team !");
                            return true;
                        }
                    }
                    p.sendMessage("§eHeaven → §aInformation sur la team §6" + team.getName());
                    p.sendMessage(" ");
                    for (HeavenPlayer player : team.getMember()) {
                        if (player.getName().equalsIgnoreCase(team.getOwner().getName()) && player.getName().equalsIgnoreCase(heavenPlayer.getName()))
                            p.sendMessage("    §7→ §c" + player.getName() + " §7(§cOwner§7) §7(§aYou§7)");
                        else if (player.getName().equalsIgnoreCase(team.getOwner().getName()))
                            p.sendMessage("    §7→ §c" + player.getName() + " §7(§cOwner§7)");
                        else if (player.getName().equalsIgnoreCase(heavenPlayer.getName()))
                            p.sendMessage("    §7→ " + player.getName() + "§7(§aYou§7)");
                        else
                            p.sendMessage("    §7→ " + player.getName());
                    }
                    p.sendMessage(" ");
                }
            }
        }

        return true;
    }

}
