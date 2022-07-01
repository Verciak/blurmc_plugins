package org.pieszku.api.serializer;

import java.io.Serializable;


public class LocationSerializer implements Serializable {

    private final String world;
    private final int x;
    private final int z;
    private int y;
    private float pitch;
    private float yaw;
    private int size;

    public LocationSerializer(String world, int x, int z){
        this.world = world;
        this.x = x;
        this.z = z;
        this.size = 0;
    }
    public LocationSerializer(String world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = 0;
    }
    public LocationSerializer(String world, int x, int y, int z, float pitch, float yaw){
        this.world = world;
        this.x = x;
        this.z = z;
        this.y = y;
        this.pitch = pitch;
        this.yaw = yaw;
        this.size = 0;
    }
    public LocationSerializer(String world, int x, int y, int z, int size){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public int getY() {
        return y;
    }
}
