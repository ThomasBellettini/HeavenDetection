package fr.shurisko.general.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class LoadRank {

    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    public static HashMap<PermissionEnum, Team> teamPermission = new HashMap<PermissionEnum, Team>();
    public static Team heavenValidation;

    public LoadRank ()
    {
        Team owner;
        Team admin;
        Team mod;
        Team member;
        Team friend;
        Team other;
        Team heaven;

        if (scoreboard.getTeam("owner") == null) owner = scoreboard.registerNewTeam("owner");
        else owner = scoreboard.getTeam("owner");
        if (scoreboard.getTeam("admin") == null) admin = scoreboard.registerNewTeam("admin");
        else admin = scoreboard.getTeam("admin");
        if (scoreboard.getTeam("mod") == null) mod = scoreboard.registerNewTeam("mod");
        else mod = scoreboard.getTeam("mod");
        if (scoreboard.getTeam("member") == null) member = scoreboard.registerNewTeam("member");
        else member = scoreboard.getTeam("member");
        if (scoreboard.getTeam("friend") == null) friend = scoreboard.registerNewTeam("friend");
        else friend = scoreboard.getTeam("friend");
        if (scoreboard.getTeam("other") == null) other = scoreboard.registerNewTeam("other");
        else other = scoreboard.getTeam("other");
        if (scoreboard.getTeam("heaven") == null) heaven = scoreboard.registerNewTeam("heaven");
        else heaven = scoreboard.getTeam("heaven");

        owner.setPrefix("§4Owner ");
        admin.setPrefix("§cAdmin ");
        mod.setPrefix("§9Moderator ");
        member.setPrefix("§eMember ");
        friend.setPrefix("§dFriend ");
        other.setPrefix("§7Aspirant ");
        heaven.setSuffix(" §7[§aHeaven§7]");

        teamPermission.put(PermissionEnum.OWNER, owner);
        teamPermission.put(PermissionEnum.ADMIN, admin);
        teamPermission.put(PermissionEnum.MOD, mod);
        teamPermission.put(PermissionEnum.MEMBER, member);
        teamPermission.put(PermissionEnum.FRIEND, friend);
        teamPermission.put(PermissionEnum.OTHER, other);
        heavenValidation = heaven;
    }

    private void updatePlayerRankScoreboard ()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(scoreboard);
        }
    }

    public void updatePlayerRank (Player p)
    {
        if (p.hasPermission(PermissionEnum.OWNER.getPermission())) {
            p.setDisplayName("§4Owner " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.OWNER).addPlayer(p);
        } else if (p.hasPermission(PermissionEnum.ADMIN.getPermission())) {
            p.setDisplayName("§cAdmin " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.ADMIN).addPlayer(p);
        } else if (p.hasPermission(PermissionEnum.MOD.getPermission())) {
            p.setDisplayName("§9Mod " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.MOD).addPlayer(p);
        } else if (p.hasPermission(PermissionEnum.MEMBER.getPermission())) {
            p.setDisplayName("§eMember " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.OWNER).addPlayer(p);
        } else if (p.hasPermission(PermissionEnum.FRIEND.getPermission())) {
            p.setDisplayName("§dFriend " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.FRIEND).addPlayer(p);
        } else if (p.hasPermission(PermissionEnum.OTHER.getPermission())) {
            p.setDisplayName("§7Aspirant " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.OTHER).addPlayer(p);
        } else {
            p.kickPlayer("§cError, Can't load ur rank !");
        }
        updatePlayerRankScoreboard();
    }

}
