package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;

public class DepositPlayerInventoryRunnable implements Runnable{


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @Override
    public void run() {
        if (!masterConnectionHeartbeatService.isConnected()) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

                int amountEnchantmentApple = ItemUtilities.getAmountOf(player, Material.ENCHANTED_GOLDEN_APPLE, (short) 0);
                int amountApple = ItemUtilities.getAmountOf(player, Material.GOLDEN_APPLE, (short) 0);
                int amountEnderPearl = ItemUtilities.getAmountOf(player, Material.ENDER_PEARL, (short) 0);
                int amountAntiBoots = ItemUtilities.getAmountOfCustom(player, new ItemHelper(Material.GOLDEN_BOOTS).visibleFlag().setName("&9&lANTY-NOGI"));
                int amountTntInteract = ItemUtilities.getAmountOfCustom(player, new ItemHelper(Material.TNT).setName("&c&lRZUCANE TNT"));
                int amountFishingRod = ItemUtilities.getAmountOf(player, Material.FISHING_ROD, (short) 0);
                int amountSnowball = ItemUtilities.getAmountOf(player, Material.SNOWBALL, (short) 0);

                String message =
                                "               &6&lSCHOWEK\n" +
                                "&fPomyślnie przeprowadzono synchronizację przedmiotów\n" +
                                "&fTwoje&8(&3{ITEM}&f, &b{COUNT}&fsztuk&8) &ftrafiły do depozytu\n" +
                                "&fTeraz posidasz&8(&3{ITEM}&f, &b{COUNT_USER}&fsztuk&8) &fw depozycie\n" +
                                "&fAby wypłacić swoje przedmioty do limitu wpisz: &6&l&n/schowek\n&e ";

                if(amountEnchantmentApple > 1){
                    int remove = amountEnchantmentApple - 1;
                    ItemUtilities.remove(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, remove, (short) 0), player, remove);
                    user.addDepositItem("goldenAppleEnchantment", remove);
                    player.sendMessage(ChatUtilities.colored(message
                            .replace("{ITEM}", "enchantowane złote jabłka")
                            .replace("{COUNT}", String.valueOf(remove))
                            .replace("{COUNT_USER}", String.valueOf(user.getDepositCount("goldenAppleEnchantment")))));
                }
                if(amountApple > 12){
                    int remove = amountApple - 12;
                    ItemUtilities.remove(new ItemStack(Material.GOLDEN_APPLE, remove, (short) 0), player, remove);
                    user.addDepositItem("goldenApple", remove);
                    player.sendMessage(ChatUtilities.colored(message
                            .replace("{ITEM}", "złote jabłka")
                            .replace("{COUNT}", String.valueOf(remove))
                            .replace("{COUNT_USER}", String.valueOf(user.getDepositCount("goldenApple")))));
                }
                if(amountEnderPearl > 3){
                    int remove = amountEnderPearl - 3;
                    ItemUtilities.remove(new ItemStack(Material.ENDER_PEARL,  remove, (short) 0), player, remove);
                    user.addDepositItem("enderPearl", remove);
                    player.sendMessage(ChatUtilities.colored(message
                            .replace("{ITEM}", "perły endermana")
                            .replace("{COUNT}", String.valueOf(remove))
                            .replace("{COUNT_USER}", String.valueOf(user.getDepositCount("enderPearl")))));
                }
                if(amountSnowball > 16){
                    int remove = amountSnowball - 16;
                    ItemUtilities.remove(new ItemStack(Material.SNOWBALL,  remove, (short) 0), player, remove);
                    user.addDepositItem("snowball", remove);
                    player.sendMessage(ChatUtilities.colored(message
                            .replace("{ITEM}", "śnieżki")
                            .replace("{COUNT}", String.valueOf(remove))
                            .replace("{COUNT_USER}", String.valueOf(user.getDepositCount("snowball")))));
                }
                if(amountFishingRod > 1){
                    int remove = amountFishingRod - 1;
                    ItemUtilities.remove(new ItemStack(Material.FISHING_ROD,  remove, (short) 0), player, remove);
                    user.addDepositItem("fishing", remove);
                    player.sendMessage(ChatUtilities.colored(message
                            .replace("{ITEM}", "wędki")
                            .replace("{COUNT}", String.valueOf(remove))
                            .replace("{COUNT_USER}", String.valueOf(user.getDepositCount("fishing")))));
                }

            });
        });
    }
    public void start(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(BukkitMain.getInstance(), this, 1L, 1L);
    }
}
