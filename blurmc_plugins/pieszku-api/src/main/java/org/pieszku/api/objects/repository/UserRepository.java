package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.user.User;

public class UserRepository extends JsonRepository<String, User> {

    public UserRepository() {
        super(User.class, "nickName", "users");
    }
}
