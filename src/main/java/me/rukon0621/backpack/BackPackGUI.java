package me.rukon0621.backpack;

import me.rukon0621.guardians.GUI.MenuWindow;
import me.rukon0621.guardians.data.PlayerData;
import me.rukon0621.guardians.helper.InvClass;
import me.rukon0621.guardians.helper.ItemClass;
import me.rukon0621.guardians.helper.Msg;
import me.rukon0621.guardians.mailbox.MailBoxManager;
import me.rukon0621.gui.buttons.Button;
import me.rukon0621.gui.windows.Window;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackPackGUI extends Window {
    private int page;
    private int maxPage;
    private final List<ItemStack> backpackData;

    public BackPackGUI(Player player) {
        super(player, "&f\uF000\uF016", 6);
        backpackData = new PlayerData(player).getBackpackData();
        page = 1;
        reloadGUI();
        open();
    }

    @Override
    protected void reloadGUI() {
        maxPage = Math.max(1, (backpackData.size() - 1) / 45 + 1);
        page = Math.min(page, maxPage);
        map.clear();
        int slot = 0;
        for(int i = (page - 1) * 45; i < page * 45; i++) {
            if(backpackData.size() == i) break;
            map.put(slot, new ItemButton(i, backpackData.get(i)));
            slot++;
        }
        map.put(48, new Button() {
            @Override
            public void execute(Player player, ClickType clickType) {
                page--;
                if(page == 0) page = maxPage;
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1.5f);
            }

            @Override
            public ItemStack getIcon() {
                ItemClass item = new ItemClass(new ItemStack(Material.SCUTE), String.format("&9이전 페이지 &7( &f%d &7/ &e%d &7)", page, maxPage));
                item.setCustomModelData(7);
                return item.getItem();
            }
        });
        map.put(50, new Button() {
            @Override
            public void execute(Player player, ClickType clickType) {
                page++;
                if(page > maxPage) page = 1;
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1.5f);
            }

            @Override
            public ItemStack getIcon() {
                ItemClass item = new ItemClass(new ItemStack(Material.SCUTE), String.format("&c다음 페이지 &7( &f%d &7/ &e%d &7)", page, maxPage));
                item.setCustomModelData(7);
                return item.getItem();
            }
        });
        super.reloadGUI();
    }

    @Override
    public void close(boolean b) {
        disable();
        if(b) new MenuWindow(player, 1);
    }

    class ItemButton extends Button {
        private final int index;
        private final ItemStack item;

        public ItemButton(int index, ItemStack item) {
            this.index = index;
            this.item = item;
        }

        @Override
        public void execute(Player player, ClickType clickType) {
            if(InvClass.hasEnoughSpace(player.getInventory(), item)) {
                backpackData.remove(index);
                player.getInventory().addItem(item);
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1.5f);
                reloadGUI();
            }
            else {
                Msg.warn(player, "인벤토리에 공간이 부족해 아이템을 가져올 수 없습니다.");
            }
        }

        @Override
        public ItemStack getIcon() {
            return item;
        }
    }
}
