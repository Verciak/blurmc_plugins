package pl.pieszku.sectors.handler.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.pieszku.api.sector.Sector;

public class SectorChangeEvent extends Event implements Cancellable {


    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Sector sector;
    private final Location location;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public SectorChangeEvent(Player player, Sector sector, Location location){
        this.player = player;
        this.sector = sector;
        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }


    public Player getPlayer() {
        return player;
    }

    public Sector getSector() {
        return sector;
    }

    public Location getLocation() {
        return location;
    }
}
