package fr.shurisko.api;

import fr.shurisko.api.team.HeavenTeam;
import fr.shurisko.general.utils.PermissionEnum;
import org.bukkit.Bukkit;

import java.util.UUID;

public class HeavenPlayer {

    public String uuid;
    public String name;

    public PermissionEnum rank;
    public Boolean isHeaven;

    public transient HeavenTeam request;
    public transient HeavenTeam playerTeam;

    public int kill;
    public int death;

    public int eventWon;

    public HeavenPlayer(String uuid, String name, PermissionEnum rank, Boolean isHeaven, int kill, int death, int eventWon) {
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.isHeaven = isHeaven;
        this.kill = kill;
        this.death = death;
        this.eventWon = eventWon;
        request = null;
    }

    public HeavenPlayer (String name) {
        this.uuid = UUID.randomUUID().toString();
        this.kill = 0;
        this.death = 0;
        this.eventWon = 0;
        this.name = name;
        this.rank = PermissionEnum.OTHER;
        this.isHeaven = false;
        request = null;
    }

    public String getUuid() {
        return uuid;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getEventWon() {
        return eventWon;
    }

    public void setEventWon(int eventWon) {
        this.eventWon = eventWon;
    }

    public String getName() {
        return name;
    }

    public Boolean getHeaven() {
        return isHeaven;
    }

    public void setHeaven(Boolean heaven) {
        isHeaven = heaven;
    }

    public PermissionEnum getRank() {
        return rank;
    }

    public void setRank(PermissionEnum rank) {
        this.rank = rank;
    }

    public HeavenTeam getRequest() {
        return request;
    }

    public void setRequest(HeavenTeam request) {
        this.request = request;
    }

    public HeavenTeam getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(HeavenTeam playerTeam) {
        this.playerTeam = playerTeam;
    }

    public String sendTeamRequest(HeavenPlayer sender) {
        if (sender.playerTeam == null) return "§cErreur, Vous n'avez pas de team.";
        if (sender.playerTeam.owner != sender) return "§cErreur Vous n'êtes pas le chef de la team.";
        if (this.request != null && this.request.getName() == sender.playerTeam.getName()) return "§cErreur, Vous avez déjà demandée à cette personne de rejoindre votre team.";

        Bukkit.getPlayer(this.getName()).sendMessage("§eHeaven → §aVous avez reçu une invitation pour rejoindre la team §e" + this.request.getName());
        this.setRequest(sender.playerTeam);
        return "§aVous avez invité le joueur §e" + request.getName() + " §aà rejoindre votre team.";
    }

}
