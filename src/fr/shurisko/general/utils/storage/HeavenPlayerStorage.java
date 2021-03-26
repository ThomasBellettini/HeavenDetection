package fr.shurisko.general.utils.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.storage.adapter.HeaverPlayerAdapter;

import java.io.*;
import java.util.Objects;

public class HeavenPlayerStorage {

    private File FILES = new File(Heaven.getInstance.getDataFolder(), "/storage/");
    private Gson gson;

    public HeavenPlayerStorage() {
        gson = new GsonBuilder()
                .registerTypeAdapter(HeavenPlayer.class, new HeaverPlayerAdapter())
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        if (!FILES.exists())
            FILES.mkdir();
    }

    public void loadPlayer()
    {
        for (File f : Objects.requireNonNull(FILES.listFiles())) {
            if (f.getName().endsWith(".json")) {
                HeavenPlayer heavenPlayer = deserialize(f);
                if (heavenPlayer == null)
                    continue;
                Heaven.getPlayerLoader.addPlayer(heavenPlayer);
            }
        }
    }

    public void write(HeavenPlayer heavenPlayer)
    {
        final FileWriter files;
        File f = new File(FILES, heavenPlayer.getName().toLowerCase() + ".json" );
        try {
            if(!f.exists())
                f.createNewFile();
            files = new FileWriter(f);
            files.write(serialize(heavenPlayer));
            files.flush();
            files.close();
        }
        catch (IOException e) { }
    }

    public String read(File f)
    {
        if(f.exists()) {
            try {
                final BufferedReader r = new BufferedReader(new FileReader(f));
                final StringBuilder t = new StringBuilder();
                String l;
                while((l = r.readLine()) != null)
                    t.append(l);
                r.close();
                return t.toString();
            }
            catch (IOException e) {}
        }
        return " ";
    }

    public String serialize(HeavenPlayer heavenPlayer) {
        return gson.toJson(heavenPlayer);
    }

    public HeavenPlayer deserialize(File f) {
        return gson.fromJson(read(f), HeavenPlayer.class);
    }

}
