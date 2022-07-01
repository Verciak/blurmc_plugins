package org.pieszku.api.sector.json;

import org.pieszku.api.sector.Sector;

import java.io.Serializable;

public class SectorJson implements Serializable {

    public Sector[] sectors;

    public Sector[] getSectors() {
        return sectors;
    }
}
