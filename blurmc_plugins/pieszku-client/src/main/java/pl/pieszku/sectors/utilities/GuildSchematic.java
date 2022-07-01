package pl.pieszku.sectors.utilities;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.pieszku.api.objects.guild.impl.Guild;
import pl.pieszku.sectors.BukkitMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GuildSchematic {

    public static void paste(Guild guild) {
        Location location = new Location(Bukkit.getWorld("world"), guild.getLocationSerializer().getX(), 38, guild.getLocationSerializer().getZ());
        File schematic = new File(BukkitMain.getInstance().getDataFolder() + File.separator + "/schematics/guild.schem");
         com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(location.getWorld());
         ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
            Clipboard clipboard = reader.read();
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, 1200)) {
                final Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                        .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ())).ignoreAirBlocks(false).build();
                try {
                    Operations.complete(operation);
                    editSession.flushSession();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
