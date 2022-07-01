package org.pieszku.bot.service;

import org.pieszku.bot.configuration.MeshGenerator;

import java.util.ArrayList;
import java.util.List;

public class MeshGeneratorService {

    private final List<MeshGenerator> meshGenerators = new ArrayList<>();

    public MeshGenerator findMeshByCordinate(int minZ, int minX, int maxX, int maxZ) {
        return this.meshGenerators
                .stream()
                .filter(meshGenerator -> meshGenerator.getMaxX() == maxX)
                .filter(meshGenerator -> meshGenerator.getMaxZ() == maxZ)
                .filter(meshGenerator -> meshGenerator.getMinX() == minX)
                .filter(meshGenerator -> meshGenerator.getMinZ() == minZ)
                .findFirst()
                .orElse(null);
    }

    public List<MeshGenerator> getMeshGenerators() {
        return meshGenerators;
    }

    public void generate(int count) {
        final int xOrigin = 200;
        final int yOrigin = 200;
        final int xAndYDelta = 3200;


        for (int xIndex = 0; xIndex <= 9; xIndex++) {
            for (int yIndex = 0; yIndex <= 9; yIndex++) {
                int maxX = -xOrigin + xIndex * xAndYDelta;
                int maxZ = -yOrigin + yIndex * xAndYDelta;
                int minX = -(maxX * xOrigin) / xOrigin;
                int minZ = -(maxZ * yOrigin) / yOrigin;

                if (this.findMeshByCordinate(minZ, minX, maxX, maxZ) != null) continue;
                this.getMeshGenerators().add(new MeshGenerator(minX, minZ, maxX, maxZ));
            }
        }
        for (int i = 0; i <= 2; i++) {
            MeshGenerator meshGenerator = this.meshGenerators.get(i);
            int maxX1 = meshGenerator.getMaxX();
            int maxZ1 = meshGenerator.getMaxZ();
            int minX1 = meshGenerator.getMinX();
            int minZ1 = meshGenerator.getMinZ();

            System.out.println("Sector: s" + i);
            System.out.println(maxX1);
            System.out.println(maxZ1);
            System.out.println(minX1);
            System.out.println(minZ1);
            System.out.println(" ");
        }
    }
}
