package com.uaulight.lfazenda.Comandos;

import com.uaulight.lfazenda.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class FazendaCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            Inventory inv = Bukkit.createInventory(p, 27, "§eFazenda");

            int quantidade = (Main.quebrados.containsKey(p.getName()) ? Main.quebrados.get(p.getName()) : 0);

            //Top
            ItemStack Top = new ItemStack(Material.GOLD_INGOT);
            ItemMeta topmeta = Top.getItemMeta();
            topmeta.setDisplayName("§eTOP Fazendeiros");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Veja aqui os que mais farmaram.");
            lore.add("§7");

            int i = 0;
            for (Map.Entry<String, Integer> stringIntegerEntry : Main.reverseSortedMap.entrySet()) {
                i++; //i=i+1;
                if (i>9){
                    break;
                }
                lore.add("§e"+i+"ª " + stringIntegerEntry.getKey() + "§8 - §7" + stringIntegerEntry.getValue());

            }

            topmeta.setLore(lore);
            Top.setItemMeta(topmeta);

            inv.setItem(11, Top);

            //Info player
            ItemStack Info = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) Info.getItemMeta();
            meta.setOwner(p.getName());
            meta.setDisplayName("§eSuas Stats");
            meta.setLore(Arrays.asList("§aBlocos quebrados: §7" + quantidade));
            Info.setItemMeta(meta);


            inv.setItem(13, Info);

            //Teleportar
            ItemStack tp = new ItemStack(Material.GOLD_HOE);
            ItemMeta tpmeta = tp.getItemMeta();
            tpmeta.setDisplayName("§eIr Para a Fazenda");
            tpmeta.setLore(Arrays.asList("§7Teleporte-se para a fazenda."));
            tp.setItemMeta(tpmeta);

            inv.setItem(15, tp);


            p.openInventory(inv);
        }

        return false;
    }
}
