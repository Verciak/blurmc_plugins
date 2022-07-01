package org.pieszku.api.cache;

import org.pieszku.api.objects.bazaar.Bazaar;

import java.util.*;

public class BazaarCache {

    private Set<Bazaar> bazaars = new HashSet<>();

    public Bazaar create(String nickName, int countSell, String serializedItem){
        Bazaar bazaar = new Bazaar(this.bazaars.size() + 1, nickName, countSell, serializedItem);
        this.bazaars.add(bazaar);
        return bazaar;
    }
    public Optional<Bazaar> findBazaarById(int id){
        return this.bazaars
                .stream()
                .filter(bazaar -> bazaar.getId() == id)
                .findFirst();
    }

    public void setBazaars(Set<Bazaar> bazaars) {
        this.bazaars = bazaars;
    }

    public Set<Bazaar> getBazaars() {
        return bazaars;
    }
}
