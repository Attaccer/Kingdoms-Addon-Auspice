package top.mckingdom.auspice.commands.general;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.*;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.events.members.LeaveReason;
import org.kingdoms.locale.KingdomsLang;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.entitlements.KingdomPermissionRegister;
import top.mckingdom.auspice.entitlements.RelationAttributeRegister;

import java.util.Collections;
import java.util.List;

public class CommandTransferMember extends KingdomsParentCommand {
    public CommandTransferMember() {
        super("transferMember", true);
    }

    @Override
    public @NotNull CommandResult executeX(@NonNull CommandContext context) {
        String[] args = context.getArgs();

        KingdomPlayer senderKP = context.getKingdomPlayer();
        if (senderKP == null) {
            return CommandResult.FAILED;
        }
        Kingdom senderKingdom = senderKP.getKingdom();
        if (senderKingdom == null) {
            return CommandResult.FAILED;
        }
        OfflinePlayer offlinePlayer = context.getOfflinePlayer(0);
        if (!senderKP.hasPermission(KingdomPermissionRegister.PERMISSION_TRANSFER_MEMBERS)) {
            AuspiceLang.PERMISSION_TRANSFER_MEMBERS.sendError(offlinePlayer.getPlayer());
            return CommandResult.FAILED;
        }
        KingdomPlayer kPlayer = KingdomPlayer.getKingdomPlayer(offlinePlayer);
        Kingdom takerKingdom = context.getKingdom(1);
        if (!senderKingdom.hasAttribute(kPlayer.getKingdom(), RelationAttributeRegister.DIRECTLY_TRANSFER_MEMBER)) {
            context.getPlayer(0).sendMessage("转移成员请求功能未实现");
            //TODO 转移成员申请
        } else {
            directlyTransfer(kPlayer, senderKP, takerKingdom);
            return CommandResult.SUCCESS;
        }
        return CommandResult.SUCCESS;
    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandTabContext context) {
        KingdomPlayer kingdomPlayer = context.getKingdomPlayer();
        if (kingdomPlayer == null) {
            return emptyTab();
        }
        Kingdom kingdom = kingdomPlayer.getKingdom();
        if (kingdom == null) {
            return emptyTab();
        }

        List<String> players = kingdom.getMembers(player -> {
            if (player.getRank().getPriority() > kingdomPlayer.getRank().getPriority()) {
                return player.getPlayer().getName();
            }
            return "";
        });

        players.removeAll(Collections.singleton(null));

        return players;
    }
    public static boolean directlyTransfer(KingdomPlayer player, KingdomPlayer sender, Kingdom taker) {
        if (sender.getRank().getPriority() >= player.getRank().getPriority()) {    //如果送人的玩家职级优先级比被送出去的玩家差
            KingdomsLang.COMMAND_PROMOTE_CANT_PROMOTE.sendError(sender.getPlayer());  //TODO
            return false;
        } else {
            if (taker.getMaxMembers() <= taker.getMembers().size()) {       //如果接收玩家的王国成员满了
                KingdomsLang.COMMAND_ACCEPT_MAX_MEMBERS.sendError(sender.getPlayer());
                return false;
            } else {
                Bukkit.getPluginManager().callEvent(player.leaveKingdom(LeaveReason.CUSTOM));   //让被送出去的玩家离开原先王国
                player.joinKingdom(taker);
                return true;
            }

        }

    }

}
