package fr.shurisko.api.team;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeavenTeam {

    public String name;

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
    }

    public HeavenTeam(List<HeavenPlayer> member, int maxPlayers, String name) {
        this.owner = null;
        this.member = member;
        this.maxPlayers = maxPlayers;
        this.makeByAdmin = true;
        this.name = name;
        for (HeavenPlayer h : member)
            h.setPlayerTeam(this);
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
            if (getOwner().getName() == heavenPlayer.getName()) {
                sendPartyChat("§cLe groupe vient d'être disband !", null);
                for (HeavenPlayer heavenPlayers : getMember()) {
                    heavenPlayer.setPlayerTeam(null);
                }
                Heaven.getPartyLoader.disband(this);
                return;
            }
            if (!isKick) sendPartyChat("§cLe joueur §e" + heavenPlayer.getName() + " §cvient de quitter votre partie !", null);
            else sendPartyChat("§cLe joueur §e" + heavenPlayer.getName() + " §cvient d'être kick de votre partie !", null);
            getMember().removeIf(heavenPlayer1 -> heavenPlayer1.getName().equalsIgnoreCase(heavenPlayer.getName()));
        }
    }

    public void joinTeam(HeavenPlayer heavenPlayer) {
        if (heavenPlayer.getPlayerTeam() == null) {
            heavenPlayer.setPlayerTeam(this);
            sendPartyChat("§aLe joueur §e" + heavenPlayer.getName() + " §aà rejoint votre partie.", null);
            getMember().add(heavenPlayer);
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
