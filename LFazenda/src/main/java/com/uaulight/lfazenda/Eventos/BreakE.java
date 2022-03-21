package com.uaulight.lfazenda.Eventos;

import com.uaulight.lfazenda.Main;
import com.uaulight.lfazenda.Utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.material.MaterialData;

public class BreakE implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getInventory() == null)return;

        if (event.getInventory().getName().equals("§eFazenda")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (e.getBlock().getType().equals(Material.getMaterial(Main.plugin.getConfig().getString("Plantacoes.Planta1")))) {
            MaterialData data = e.getBlock().getState().getData();

            if (data.getData() == 3) {
                //totalmente crescido

                int money = 200;

                e.setCancelled(true);
                Location test = e.getBlock().getLocation();

                test.getBlock().setType(Material.NETHER_WARTS);

                if (!Main.quebrados.containsKey(p.getName()))Main.quebrados.put(p.getName(), 0);
                int total = Main.quebrados.get(p.getName()) + 1;
                Main.quebrados.put(p.getName(), total);

                Main.economy.depositPlayer(p.getName(), money);
                ActionBar.send(p, "&a+".replace("&", "§") + money + " : &7Blocos quebrados: ".replace("&", "§") + Main.quebrados.get(p.getName()));

                if (Main.quebrados.get(p.getName()) == Main.plugin.getConfig().getInt("Metas.meta1")) {
                    int meta = Main.plugin.getConfig().getInt("Metas.meta1");

                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Main.plugin.getConfig().getString("Mensagens.Atingiu_meta").replace("&", "§").replace("@jogador", p.getName()).replace("@meta1", "" + meta));
                    Bukkit.broadcastMessage(" ");
                }

            } else{
                e.setCancelled(true);
                ActionBar.send(p, "&cAinda não cresceu essa plantação.".replace("&", "§"));
            }
        } else if (e.getBlock().getType().equals(Material.getMaterial(Main.plugin.getConfig().getString("Plantacoes.Planta2")))) {
            MaterialData data = e.getBlock().getState().getData();

            if (data.getData() == 7) {
                //totalmente crescido

                int money = 500;

                e.setCancelled(true);
                Location test = e.getBlock().getLocation();

                test.getBlock().setType(Material.CROPS);

                if (!Main.quebrados.containsKey(p.getName()))Main.quebrados.put(p.getName(), 0);
                int total = Main.quebrados.get(p.getName()) + 5;
                Main.quebrados.put(p.getName(), total);

                Main.economy.depositPlayer(p.getName(), money);
                ActionBar.send(p, "&a+".replace("&", "§") + money + " : &7Blocos quebrados: ".replace("&", "§") + Main.quebrados.get(p.getName()));

                if (Main.quebrados.get(p.getName()) == Main.plugin.getConfig().getInt("Metas.meta1")) {
                    int meta = Main.plugin.getConfig().getInt("Metas.meta1");

                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Main.plugin.getConfig().getString("Mensagens.Atingiu_meta").replace("&", "§").replace("@jogador", p.getName()).replace("@meta1", "" + meta));
                    Bukkit.broadcastMessage(" ");
                }

            }else{
                e.setCancelled(true);
                ActionBar.send(p, "&cAinda não cresceu essa plantação.".replace("&", "§"));
            }
        }
    }
}
