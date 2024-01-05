package me.rukon0621.backpack;

import me.rukon0621.guardians.data.PlayerData;
import me.rukon0621.guardians.helper.InvClass;
import me.rukon0621.guardians.helper.Msg;
import me.rukon0621.guardians.story.StoryManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackPackUtil {

    private static final Map<String, Long> warnedList = new HashMap<>();

    public static void giveOrBackPack(Player player, ItemStack item) {
        if(InvClass.hasEnoughSpace(player.getInventory(), item)) {
            player.getInventory().addItem(item);
            return;
        }

        PlayerData pdc = new PlayerData(player);
        List<ItemStack> backpack = pdc.getBackpackData();
        if(!StoryManager.getReadStory(player).contains("전리품 가방") && StoryManager.getPlayingStory(player) == null) {
            StoryManager.readStory(player, "전리품 가방");
        }
        if(backpack.size()==pdc.getBackpackSlot()) {
            long deft = System.currentTimeMillis();
            long warn = warnedList.getOrDefault(player.getUniqueId().toString(), deft);
            if(warn <= deft) {
                warnedList.put(player.getUniqueId().toString(), System.currentTimeMillis() + 60L * 1000);
                Msg.warn(player, "전리품 가방이 가득차 일부 아이템이 소실되었습니다.");
            }
        }
        else {
            backpack.add(item);
        }
    }

    public static void giveOrBackPack(Player player, List<ItemStack> items) {
        for(ItemStack item : items) {
            giveOrBackPack(player, item);
        }
    }

}
