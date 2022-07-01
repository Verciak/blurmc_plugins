package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.guild.impl.Guild;

public class GuildRepository extends JsonRepository<String, Guild> {

    public GuildRepository() {
        super(Guild.class, "name", "guilds");
    }

}
