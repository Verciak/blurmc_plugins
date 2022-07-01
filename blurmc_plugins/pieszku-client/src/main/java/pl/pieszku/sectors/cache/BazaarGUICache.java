package pl.pieszku.sectors.cache;

import java.util.HashSet;
import java.util.Set;

public class BazaarGUICache {


    private final Set<BazaarGUI> bazaarGUIList = new HashSet<>();

    public BazaarGUI findBazaarGUIByPage(int page){
        return this.bazaarGUIList.stream()
                .filter(bazaarGUI -> bazaarGUI.getPage() == page)
                .findFirst()
                .orElse(null);
    }

    public BazaarGUI findBazaarGUIOrCreate(int page){
        return this.bazaarGUIList
                .stream()
                .filter(bazaarGUI -> bazaarGUI.getPage() == page)
                .findFirst()
                .orElse(create(page));
    }

    public BazaarGUI create(int page){
        BazaarGUI bazaarGUI = new BazaarGUI(page);
        this.bazaarGUIList.add(bazaarGUI);
        return bazaarGUI;
    }

}
