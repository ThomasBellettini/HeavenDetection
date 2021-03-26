package fr.shurisko.api.loader;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoader {

    public List<HeavenPlayer> heavenPlayers = new ArrayList<>();

    public void addPlayer (HeavenPlayer player)
    {
        for (HeavenPlayer p : heavenPlayers) {
            if (p.getUuid().equalsIgnoreCase(player.getUuid()))
                return;
        }
        heavenPlayers.add(player);
        saveProfile(player);
    }

    public HeavenPlayer getPlayerFromName(String name)
    {
        for (HeavenPlayer p : heavenPlayers) {
            if (p.getName().equalsIgnoreCase(name))
                return p;
        }
        return null;
    }


    public HeavenPlayer getPlayerFromBukkit(Player player)
    {
        for (HeavenPlayer p : heavenPlayers) {
            if (p.getName().equalsIgnoreCase(player.getName()))
                return p;
        }
        return null;
    }

    public void saveAllProfile()
    {
        for (HeavenPlayer p : heavenPlayers)
            Heaven.getPlayerStorage.write(p);
    }

    public void saveProfile(HeavenPlayer heavenPlayer)
    {
        Heaven.getPlayerStorage.write(heavenPlayer);
    }

    public void refreshTab ()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Heaven.getHeavenNMS.sendTabTitle(p, "§eHeaven \n", "\n§7§oServeur Gérée par la Faction §e§oHeaven§7\n§7§oImaginé par §c§oRyZoW §7§oDéveloppé par §c§oShurisko");
        }
    }

}
