package fr.shurisko.api.loader;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.api.team.HeavenTeam;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyLoader {

    public List<HeavenTeam> heavenTeams = new ArrayList<>();

    public String createTeam (HeavenTeam player)
    {
        if (player == null) return "§cCette Team Existe déjà !";
        for (HeavenTeam p : heavenTeams) {
            if (p.getName().equalsIgnoreCase(player.getName())) {
                return "§cCette team existe déjà !";
            }
        }
        heavenTeams.add(player);
        return "§aVous venez de crée la team " + player.getName();
    }

    public HeavenTeam getTeamFromName(String name)
    {
        for (HeavenTeam p : heavenTeams) {
            if (p.getName().equalsIgnoreCase(name))
                return p;
        }
        return null;
    }

    public void disband(HeavenTeam team) {
        heavenTeams.removeIf(heavenTeam -> heavenTeam.getName().equalsIgnoreCase(team.getName()));
    }

}
