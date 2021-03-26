package fr.shurisko.api.team;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.api.HeavenStat;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class HeavenTeam {

    public String name;

    public Map<HeavenPlayer, HeavenStat> personalStat = new HashMap<>();
    public Team teamScore;
    private Scoreboard score = Heaven.getRankLoader.scoreboard;

    public HeavenPlayer owner;
    public List<HeavenPlayer> member;

    public int maxPlayers;
    public boolean makeByAdmin;

    public HeavenTeam(HeavenPlayer owner, int maxPlayers, String name) {
        this.owner = owner;
        this.member = new ArrayList<>(Collections.singletonList(owner));
        this.maxPlayers = maxPlayers;
        this.makeByAdmin = false;
        this.name = name;
        owner.setPlayerTeam(this);
        if (score.getTeam("g" + name) == null) score.registerNewTeam("g" + name);
        teamScore = score.getTeam("g" + name);
        teamScore.setPrefix("§7");
        teamScore.setSuffix(" §7[§6" + name + "§7]");
    }

    public HeavenTeam(List<HeavenPlayer> member, int maxPlayers, String name) {
        this.owner = null;
        this.member = member;
        this.maxPlayers = maxPlayers;
        this.makeByAdmin = true;
        this.name = name;
        for (HeavenPlayer h : member)
            h.setPlayerTeam(this);
        if (score.getTeam("g" + name) == null) score.registerNewTeam("g" + name);
        teamScore = score.getTeam("g" + name);
        teamScore.setPrefix("§7");
        teamScore.setSuffix(" §7[§6" + name + "§7]");
    }

    public HeavenPlayer getOwner() {
        return owner;
    }

    public void setOwner(HeavenPlayer owner) {
        this.owner = owner;
    }

    public List<HeavenPlayer> getMember() {
        return member;
    }

    public void setMember(List<HeavenPlayer> member) {
        this.member = member;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isMakeByAdmin() {
        return makeByAdmin;
    }

    public void setMakeByAdmin(boolean makeByAdmin) {
        this.makeByAdmin = makeByAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendPartyChat(String string, HeavenPlayer heavenPlayer)
    {
        if (heavenPlayer == null) {
            for (HeavenPlayer player : getMember()) {
                Bukkit.getPlayer(player.getName()).sendMessage("§eHeavenParty → §b" + string);
            }
        } else {
            for (HeavenPlayer player : getMember()) {
                Bukkit.getPlayer(player.getName()).sendMessage("§eHeavenParty → §7(" + Bukkit.getPlayer(heavenPlayer.getName()).getDisplayName() + " §7) §7⇨ " + string);
            }
        }
    }

    public void kickPlayer(HeavenPlayer heavenPlayer, boolean isKick) {
        if (heavenPlayer.playerTeam.getName().equalsIgnoreCase(getName())) {
            if (getOwner().getName().equalsIgnoreCase(getOwner().getName())) {
                sendPartyChat("§cLe groupe vient d'être disband !", null);
                for (HeavenPlayer heavenPlayers : getMember()) {
                    heavenPlayers.setPlayerTeam(null);
                    teamScore.removePlayer(Bukkit.getPlayer(heavenPlayer.getName()));
                    Heaven.getRankLoader.updatePlayerRank(heavenPlayer);
                }
                Heaven.getPartyLoader.disband(this);
                return;
            }
            if (!isKick) sendPartyChat("§cLe joueur §e" + heavenPlayer.getName() + " §cvient de quitter votre partie !", null);
            else sendPartyChat("§cLe joueur §e" + heavenPlayer.getName() + " §cvient d'être kick de votre partie !", null);
            getMember().removeIf(heavenPlayer1 -> heavenPlayer1.getName().equalsIgnoreCase(heavenPlayer.getName()));
            personalStat.remove(heavenPlayer);
            heavenPlayer.setPlayerTeam(null);
            teamScore.removePlayer(Bukkit.getPlayer(heavenPlayer.getName()));
            Heaven.getRankLoader.updatePlayerRank(heavenPlayer);
            Heaven.getRankLoader.updatePlayerRankScoreboard();
            if (getMember().isEmpty() && owner == null)
                Heaven.getPartyLoader.disband(this);
        }
    }

    public void joinTeam(HeavenPlayer heavenPlayer) {
        if (heavenPlayer.getPlayerTeam() == null) {
            getMember().add(heavenPlayer);
            personalStat.put(heavenPlayer, new HeavenStat());
            heavenPlayer.setPlayerTeam(this);
            teamScore.addPlayer(Bukkit.getPlayer(heavenPlayer.getName()));
            Heaven.getRankLoader.updatePlayerRankScoreboard();
            sendPartyChat("§aLe joueur §e" + heavenPlayer.getName() + " §aà rejoint votre partie.", null);
        }
    }

    public String forceJoin(HeavenPlayer heavenPlayer)
    {
        if (heavenPlayer.getPlayerTeam() == null) {
            joinTeam(heavenPlayer);
            Bukkit.getPlayer(heavenPlayer.getName()).sendMessage("§eHeéaven → §aVous venez d'être forc à rejoindre la team §e" + getName());
            return "§aVous venez de set le joueur §e" + heavenPlayer.getName() + " §adans la team §6" + getName();
        }
        kickPlayer(heavenPlayer, false);
        joinTeam(heavenPlayer);
        return "§aVous venez de set le joueur §e" + heavenPlayer.getName() + " §adans la team §6" + getName();
    }

}
