package fr.shurisko;
;
import fr.shurisko.api.loader.PlayerLoader;
import fr.shurisko.general.event.UserJoinEvent;
import fr.shurisko.general.utils.LoadRank;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Heaven extends JavaPlugin {

    public static Heaven getInstance;
    public static PlayerLoader getPlayerLoader;
    public static LoadRank getRankLoader;

    public void onEnable()
    {
        getInstance = this;
        getRankLoader = new LoadRank();
        getPlayerLoader = new PlayerLoader();

        /* Event */
        Bukkit.getPluginManager().registerEvents(new UserJoinEvent(), this);

    }


}
