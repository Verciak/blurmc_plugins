package org.pieszku.bot.configuration;

public class MeshGenerator {

    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;

    public MeshGenerator(int minX, int minZ, int maxX, int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public int getMinX() {
        if(this.minX > -3000 && this.minX > 3000){
            this.minX =-3000;
        }
        return minX;
    }

    public int getMinZ() {
        if(this.minZ > -3000 && this.minZ > 3000){
            this.minZ =-3000;
        }
        return minZ;
    }

    public int getMaxX() {
        if(this.maxX > -3000 && this.maxX > 3000){
            this.maxX =-3000;
        }
        return maxX;
    }

    public int getMaxZ() {
        if(this.maxZ > -3000 && this.maxZ > 3000){
            this.maxZ =-3000;
        }
        return maxZ;
    }
}

