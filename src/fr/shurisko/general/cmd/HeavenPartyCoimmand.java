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
                        if (arg[1].length() > 7) {
                            p.sendMessage("§eHeaven → §cLe nom de team doit faire au maximum 8 charactères !");
                            return true;
                        }
                        p.sendMessage("§eHeaven → " + Heaven.getPartyLoader.createTeam(new HeavenTeam(heavenPlayer, 6, arg[1])));
                        heavenPlayer.getPlayerTeam().teamScore.addPlayer(p);
                        Heaven.getRankLoader.updatePlayerRankScoreboard();
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
                    if (team.getOwner().getName().equalsIgnoreCase(heavenPlayer.getName()))
                        p.sendMessage("    §7→ §c" + team.getOwner().getName() + " §7(§cOwner§7) §7(§aYou§7)");
                    else
                        p.sendMessage("    §7→ §c" + team.getOwner().getName() + " §7(§cOwner§7)");
                    for (HeavenPlayer player : team.getMember()) {
                        if (player.getName().equalsIgnoreCase(team.getOwner().getName()))
                            continue;
                        else if (player.getName().equalsIgnoreCase(heavenPlayer.getName()))
                            p.sendMessage("    §7→ " + player.getName() + "§7(§aYou§7)");
                        else
                            p.sendMessage("    §7→ " + player.getName());
                    }
                    p.sendMessage(" ");
                } else if (arg[0].equalsIgnoreCase("kick")) {
                    HeavenTeam team = heavenPlayer.getPlayerTeam();
                    Player tmp = Bukkit.getPlayer(arg[1]);
                    HeavenPlayer heavenPlayer_tmp;

                    if (team == null) {
                        p.sendMessage("§eHeaven → §cTu n'es membre d'aucune team!");
                        return true;
                    } else if (team.getOwner().getUuid() != heavenPlayer.getUuid()) {
                        p.sendMessage("§eHeaven → §cTu n'es pas le chef de cette team!");
                        return true;
                    }
                    if (tmp == null) {
                        p.sendMessage("§eHeaven → §cLe joueur §e" + arg[1] + " §cne fait pas partie de ta team!");
                        return true;
                    }
                    heavenPlayer_tmp = Heaven.getPlayerLoader.getPlayerFromBukkit(tmp);
                    if (team.getMember().contains(heavenPlayer_tmp)) {
                        team.kickPlayer(heavenPlayer_tmp, true);
                    } else {
                        p.sendMessage("§eHeaven → §cLe joueur §e" + heavenPlayer_tmp.getName() + " §cne fait pas partie de ta team!");
                    }
                }
            } else if (arg.length == 1) {
                if (arg[0].equalsIgnoreCase("disband")) {
                    HeavenTeam team = heavenPlayer.getPlayerTeam();

                    if (team == null) {
                        p.sendMessage("§eHeaven → §cTu n'es membre d'aucune team!");
                        return true;
                    } else if (team.getOwner().getUuid() != heavenPlayer.getUuid()) {
                        p.sendMessage("§eHeaven → §cTu n'es pas le chef de cette team!");
                        return true;
                    }
                    team.kickPlayer(heavenPlayer, false);
                } else if (arg[0].equalsIgnoreCase("accept") || arg[0].equalsIgnoreCase("join")) {
                    if (heavenPlayer.getRequest() != null) {
                        if (heavenPlayer.getPlayerTeam() != null) {
                            heavenPlayer.getPlayerTeam().kickPlayer(heavenPlayer, false);
                        }
                        heavenPlayer.getRequest().joinTeam(heavenPlayer);
                        heavenPlayer.setRequest(null);
                    } else {
                        p.sendMessage("§eHeaven → §cTu n'as aucune invitation de team en attente.");
                        return true;
                    }
                } else if (arg[0].equalsIgnoreCase("leave")) {
                    if (heavenPlayer.getPlayerTeam() != null) {
                        heavenPlayer.getPlayerTeam().kickPlayer(heavenPlayer, false);
                    } else
                        p.sendMessage("§eHeaven → §cTu n'as pas de team.");
                }
            }
            if (arg.length > 1) {
                if (arg[0].equalsIgnoreCase("add") || arg[0].equalsIgnoreCase("invite")) {
                    HeavenTeam team = heavenPlayer.getPlayerTeam();
                    Player tmp;
                    HeavenPlayer heaven_tmp;

                    if (team == null) {
                        p.sendMessage("§eHeaven → §cTu n'as pas de team, Tu peux Utiliser §e/team create <name>");
                        return true;
                    } else if (team.getOwner().getUuid() != heavenPlayer.getUuid()) {
                        p.sendMessage("§eHeaven → §cTu n'es pas le chef de cette team!");
                        return true;
                    }
                    for (int i = 1; i < arg.length; ++i) {
                        tmp = Bukkit.getPlayer(arg[i]);
                        if (tmp == null) {
                            p.sendMessage("§eHeaven → §cLe joueur §e" + arg[i] + " §cn'est pas en ligne !");
                            continue;
                        }
                        heaven_tmp = Heaven.getPlayerLoader.getPlayerFromBukkit(tmp);
                        if (heaven_tmp.getPlayerTeam() != null && heaven_tmp.getPlayerTeam().getName() == team.getName()) {
                            p.sendMessage("§eHeaven → §cLe joueur §e" + tmp.getName() + " §cest déjà dans votre team.");
                            continue;
                        } else if (heaven_tmp.getRequest() != null && heaven_tmp.getRequest().getName().equalsIgnoreCase(team.getName())) {
                            p.sendMessage("§eHeaven → §cVous avez déjà invité le joueur §e"+ tmp.getName() + " §cdans votre team.");
                            continue;
                        }
                        heaven_tmp.sendTeamRequest(team);
                        p.sendMessage("§eHeaven → §aVous venez d'inviter le joueur §e" + tmp.getName() + " §aà rejoindre votre team.");
                    }
                }
            }
        }

        return true;
    }

}
