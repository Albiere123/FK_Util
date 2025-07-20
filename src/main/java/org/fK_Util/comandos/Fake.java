package org.fK_Util.comandos;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fake implements CommandExecutor {

    private final String[] fakeNicks = {
            "RocketZeiro", "ChoraProGoat", "Jalim Rab@@", "Paraopebas"
    };

    private final List<String> skins = List.of(
            "069a79f4-44e9-4726-a5be-fca90e38aaf5",
            "853c80ef-3c37-49fd-aa49-938b674adae6",
            "61699b2e-d327-4a01-9f1e-0ea8c3f06bc6",
            "ec70bcaf-702f-4bb8-b48d-276fa52a780c",
            "b876ec32-e396-476b-a115-8438d83c67d4"
    );

    public static final Map<Player, String> ORIGINAL_NAMES = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Somente jogadores!");
            return true;
        }
        PlayerCustom pl = new PlayerCustom(((Player) sender).getPlayer());
        FileConfiguration config = FK_Util.getConfig("config");
        if (!player.hasPermission("FK_UTIL.Fake")) {
            pl.sendColouredMessage(config.getString("options.prefix") + " &cVocê não possue permissão!");
            return true;
        }
        String fakeNick = "";
        if (args.length > 0) {
            fakeNick = args[0];
        } else {
            fakeNick = fakeNicks[new Random().nextInt(fakeNicks.length)];
        }
        if (Objects.equals(args[0], "reset")) {
            CraftPlayer cp = (CraftPlayer) player;

            try {
                String original = ORIGINAL_NAMES.getOrDefault(player, null);
                if (original == null) {
                    pl.sendColouredMessage(config.getString("options.prefix") + " &aNenhum Fake está ativo!");
                    return true;
                }
                GameProfile profile = cp.getProfile();

                Field nameField = GameProfile.class.getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(profile, original);

                player.setDisplayName(original);
                player.setPlayerListName(original);
                profile.getProperties().removeAll("textures");

                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(player);
                    online.showPlayer(player);
                }

                pl.sendColouredMessage(config.getString("options.prefix") + " &aFake retirado com sucesso!");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        String original = ORIGINAL_NAMES.getOrDefault(player, null);
        if (original == null) {
            ORIGINAL_NAMES.put(player, player.getDisplayName());
        }
        UUID skin1 = UUID.fromString(skins.get(new Random().nextInt(skins.size())));
        SkinData skin = getSkin(skin1);

        applyFake(player, fakeNick, skin.value, skin.signature);

        pl.sendColouredMessage(config.getString("options.prefix") + " &aFake aplicado como: &f" + fakeNick);

        return true;
    }

    private void applyFake(Player player, String fakeNick, String value, String signature) {
        CraftPlayer cp = (CraftPlayer) player;

        try {
            GameProfile profile = cp.getProfile();

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(profile, fakeNick);
            player.setDisplayName(fakeNick);
            player.setPlayerListName(fakeNick);
            profile.getProperties().removeAll("textures");
            profile.getProperties().put("textures", new Property("textures", value, signature));

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
                online.showPlayer(player);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SkinData getSkin(UUID uuid) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("SessionServer: UUID não encontrado: " + uuid);
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            Pattern valuePattern = Pattern.compile("\"value\"\\s*:\\s*\"(.*?)\"");
            Pattern sigPattern = Pattern.compile("\"signature\"\\s*:\\s*\"(.*?)\"");

            Matcher valM = valuePattern.matcher(sb.toString());
            Matcher sigM = sigPattern.matcher(sb.toString());

            if (valM.find() && sigM.find()) {
                return new SkinData(valM.group(1), sigM.group(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private record SkinData(String value, String signature) {
    }
}
