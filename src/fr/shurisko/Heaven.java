package fr.shurisko;
;
import fr.shurisko.api.loader.PartyLoader;
import fr.shurisko.api.loader.PlayerLoader;
import fr.shurisko.general.cmd.HeavenPartyCoimmand;
import fr.shurisko.general.cmd.admin.HeavenRank;
import fr.shurisko.general.event.UserJoinEvent;
import fr.shurisko.general.event.UserQuitEvent;
import fr.shurisko.general.event.UserSendMessage;
import fr.shurisko.general.event.rank.UserModifyRank;
import fr.shurisko.general.utils.HeavenNMS;
import fr.shurisko.general.utils.LoadRank;
import fr.shurisko.general.utils.storage.HeavenPlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Heaven extends JavaPlugin {

    public static Heaven getInstance;
    public static PlayerLoader getPlayerLoader;
    public static LoadRank getRankLoader;
    public static PartyLoader getPartyLoader;
    public static HeavenPlayerStorage getPlayerStorage;
    public static HeavenNMS getHeavenNMS;


    public void onEnable()
    {
        getInstance = this;
        if (!Heaven.getInstance.getDataFolder().exists())
            Heaven.getInstance.getDataFolder().mkdir();

        getRankLoader = new LoadRank();
        getPlayerLoader = new PlayerLoader();
        getPartyLoader = new PartyLoader();
        getPlayerStorage = new HeavenPlayerStorage();
        getHeavenNMS = new HeavenNMS();

        /* Event */
        Bukkit.getPluginManager().registerEvents(new UserJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new UserSendMessage(), this);
        Bukkit.getPluginManager().registerEvents(new UserQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new UserModifyRank(), this);

        /* Command */
        getCommand("party").setExecutor(new HeavenPartyCoimmand());
        getCommand("rank").setExecutor(new HeavenRank());

        getPlayerLoader.refreshTab();
        getPlayerStorage.loadPlayer();
        getRankLoader.updateAllPlayerRank();
    }

    public void onDisable() {
        getPlayerLoader.saveAllProfile();
    }
}
