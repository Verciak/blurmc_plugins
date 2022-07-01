package org.pieszku.bot.configuration;

public class Vector {

    protected double x;
    protected double z;

    public Vector() {
        this.x = 0.0D;
        this.z = 0.0D;
    }

    public Vector(int x,int z) {
        this.x = (double)x;
        this.z = (double)z;
    }

    public Vector(double x, double z) {
        this.x = x;
        this.z = z;
    }
    public static Vector getMinimum(Vector v1, Vector v2) {
        return new Vector(Math.min(v1.x, v2.x), Math.min(v1.z, v2.z));
    }

    public static Vector getMaximum(Vector v1, Vector v2) {
        return new Vector(Math.max(v1.x, v2.x), Math.max(v1.z, v2.z));
    }

    public double getX() {
        return x;
    }


    public double getZ() {
        return z;
    }
}
