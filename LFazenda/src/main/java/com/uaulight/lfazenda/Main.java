package com.uaulight.lfazenda;

import com.google.common.io.Resources;
import com.uaulight.lfazenda.Comandos.FazendaCmd;
import com.uaulight.lfazenda.Eventos.BreakE;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main extends JavaPlugin {

    public static Main plugin;

    public static Economy economy = null;

    public static ArrayList<String> worlds;

    public static File savesF;
    public static FileConfiguration saves;

    public static HashMap<String, Integer> quebrados = new HashMap<>();
    public static LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();;


    @Override
    public void onEnable(){

        Bukkit.getConsoleSender().sendMessage("§a[LFazenda] Plugin ativado com sucesso.");
        plugin = this;
        saveDefaultConfig();


        savesF = new File(getDataFolder(), "saves.yml");
        if (!savesF.exists()){
            try{
                savesF.createNewFile();
            }catch (Exception ignored){

            }
        }
        saves = YamlConfiguration.loadConfiguration(savesF);

        if (saves.isSet("Saves")){

            for (String key : saves.getConfigurationSection("Saves").getKeys(false)) {
                quebrados.put(key, saves.getInt("Saves."+ key));
            }

        }

        try {
            File file = new File(getDataFolder() + File.separator, "config.yml");
            String allText = Resources.toString(file.toURI().toURL(), StandardCharsets.UTF_8);
            getConfig().loadFromString(allText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new BreakE(), this);
        setupEconomy();
        getCommand("fazenda").setExecutor(new FazendaCmd());
        worlds = (ArrayList<String>) getConfig().getStringList("Worlds");

        new BukkitRunnable() {
            @Override
            public void run() {
                reverseSortedMap = new LinkedHashMap<>();
                quebrados.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
            }
        }.runTaskTimerAsynchronously(this, 20, 20*60*15);


    }

    @Override
    public void onDisable(){

        for (Map.Entry<String, Integer> stringIntegerEntry : quebrados.entrySet()) {
            saves.set("Saves." + stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }

        try {
            saves.save(savesF);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getConsoleSender().sendMessage("§c[LFazenda] Plugin desativado com sucesso.");

    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = (Economy)economyProvider.getProvider();
    }

}
