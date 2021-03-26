package fr.shurisko.general.utils.storage.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.PermissionEnum;

import java.io.IOException;

public class HeaverPlayerAdapter extends TypeAdapter<HeavenPlayer> {


    @Override
    public void write(JsonWriter writer, HeavenPlayer heavenPlayer) throws IOException {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        writer.beginObject();
        writer.name("playerUUID").value(heavenPlayer.getUuid());
        writer.name("playerName").value(heavenPlayer.getName());
        writer.name("rank").value(heavenPlayer.getRank().permission);
        writer.name("isHeaven").value(heavenPlayer.isHeaven);
        writer.name("kill").value(heavenPlayer.getKill());
        writer.name("death").value(heavenPlayer.getKill());
        writer.name("eventWon").value(heavenPlayer.getKill());
        writer.endObject();
    }

    private PermissionEnum convert(String permission)
    {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (permissionEnum.permission.equalsIgnoreCase(permission)) {
                return permissionEnum;
            }
        }
        return PermissionEnum.OTHER;
    }

    @Override
    public HeavenPlayer read(JsonReader reader) throws IOException {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        String name = "NULL";
        String uuid = "NULL";
        PermissionEnum rank = PermissionEnum.OTHER;
        boolean isHeaven = false;
        int kill = 0;
        int death = 0;
        int eventWon = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String s = reader.nextName();
            switch (s) {
                case "playerName":
                    name = reader.nextString();
                    break;
                case "kill":
                    kill = reader.nextInt();
                    break;
                case "death":
                    death = reader.nextInt();
                    break;
                case "eventWon":
                    eventWon = reader.nextInt();
                    break;
                case "playerUUID":
                    uuid = reader.nextString();
                    break;
                case "isHeaven":
                    isHeaven = reader.nextBoolean();
                    break;
                case "rank":
                    rank = convert(reader.nextString());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return new HeavenPlayer(uuid, name, rank, isHeaven, kill, death, eventWon);
    }
}
