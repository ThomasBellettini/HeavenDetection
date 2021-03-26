package fr.shurisko.general.utils;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import net.minecraft.server.v1_8_R3.BiomeForest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class LoadRank {

    public Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
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

        if (scoreboard.getTeam("aaowner") == null) owner = scoreboard.registerNewTeam("aaowner");
        else owner = scoreboard.getTeam("aaowner");
        if (scoreboard.getTeam("badmin") == null) admin = scoreboard.registerNewTeam("badmin");
        else admin = scoreboard.getTeam("badmin");
        if (scoreboard.getTeam("cmod") == null) mod = scoreboard.registerNewTeam("cmod");
        else mod = scoreboard.getTeam("cmod");
        if (scoreboard.getTeam("dmember") == null) member = scoreboard.registerNewTeam("dmember");
        else member = scoreboard.getTeam("dmember");
        if (scoreboard.getTeam("efriend") == null) friend = scoreboard.registerNewTeam("efriend");
        else friend = scoreboard.getTeam("efriend");
        if (scoreboard.getTeam("fother") == null) other = scoreboard.registerNewTeam("fother");
        else other = scoreboard.getTeam("fother");

        owner.setPrefix("§4Owner ");
        admin.setPrefix("§cAdmin ");
        mod.setPrefix("§9Moderator ");
        member.setPrefix("§eMember ");
        friend.setPrefix("§dFriend ");
        other.setPrefix("§7Aspirant ");

        teamPermission.put(PermissionEnum.OWNER, owner);
        teamPermission.put(PermissionEnum.ADMIN, admin);
        teamPermission.put(PermissionEnum.MOD, mod);
        teamPermission.put(PermissionEnum.MEMBER, member);
        teamPermission.put(PermissionEnum.FRIEND, friend);
        teamPermission.put(PermissionEnum.OTHER, other);
    }

    public void updatePlayerRankScoreboard ()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(scoreboard);
        }
    }

    public void updateAllPlayerRank ()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            HeavenPlayer heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);

            if (heavenPlayer == null) {
                Heaven.getPlayerLoader.addPlayer(new HeavenPlayer(p.getName()));
                heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
            }
            updatePlayerRank(heavenPlayer);
            updatePlayerRankScoreboard();
        }
    }

    public void updatePlayerRank (HeavenPlayer heavenPlayer)
    {
        Player p = Bukkit.getPlayer(heavenPlayer.getName());

        if (heavenPlayer.getRank() == PermissionEnum.OWNER) {
            p.setDisplayName("§4Owner " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.OWNER).addPlayer(p);
        } else if (heavenPlayer.getRank() == PermissionEnum.ADMIN) {
            p.setDisplayName("§cAdmin " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.ADMIN).addPlayer(p);
        } else if (heavenPlayer.getRank() == PermissionEnum.MOD) {
            p.setDisplayName("§9Mod " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.MOD).addPlayer(p);
        } else if (heavenPlayer.getRank() == PermissionEnum.MEMBER) {
            p.setDisplayName("§eMember " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.MEMBER).addPlayer(p);
        } else if (heavenPlayer.getRank() == PermissionEnum.FRIEND) {
            p.setDisplayName("§dFriend " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.FRIEND).addPlayer(p);
        } else if (heavenPlayer.getRank() == PermissionEnum.OTHER) {
            p.setDisplayName("§7Aspirant " + p.getPlayer().getName());
            teamPermission.get(PermissionEnum.OTHER).addPlayer(p);
        } else {
            p.kickPlayer("§cError, Can't load ur rank !");
        }
        updatePlayerRankScoreboard();
    }

}
