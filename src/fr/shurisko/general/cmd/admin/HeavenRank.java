package fr.shurisko.general.cmd.admin;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.PermissionEnum;
import fr.shurisko.general.utils.api.inventory.HeavenRankInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeavenRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {

        if (sender instanceof Player) {
            Player p = (Player)sender;
            HeavenPlayer heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);

            if (heavenPlayer.getRank() == PermissionEnum.OWNER || heavenPlayer.getRank() == PermissionEnum.ADMIN) {
                if (arg.length == 1) {
                    HeavenPlayer heavenPlayer_victim = Heaven.getPlayerLoader.getPlayerFromName(arg[0]);

                    if (heavenPlayer_victim == null) {
                        p.sendMessage("§9HeavenStaff → §cLe joueur n'existe pas !");
                        return true;
                    }
                    if (heavenPlayer_victim.getRank() == PermissionEnum.OWNER && heavenPlayer.getRank() != PermissionEnum.OWNER) {
                        p.sendMessage("§9HeavenStaff → §cLe joueur possède un plus haut rank que vous !");
                        return true;
                    }
                    p.openInventory(HeavenRankInventory.refreshRankInventory(heavenPlayer_victim, heavenPlayer));
                } else {
                    p.sendMessage("§9HeavenStaff → §eUtilisation: /rank <player>");
                }
            } else {
                p.sendMessage("§eHeaven → §cPermission denied ! §7(§4<ADMINISTRATOR§7)");
            }

        }

        return true;
    }
}
