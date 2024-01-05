package me.rukon0621.backpack;

import me.rukon0621.guardians.main;
import org.bukkit.plugin.java.JavaPlugin;

public class RukonBackpack extends JavaPlugin {
    private static RukonBackpack inst;
    public static RukonBackpack inst() {
        return inst;
    }


    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, main.mainChannel);
        inst = this;

        //Commands

    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, main.mainChannel);
    }

}
