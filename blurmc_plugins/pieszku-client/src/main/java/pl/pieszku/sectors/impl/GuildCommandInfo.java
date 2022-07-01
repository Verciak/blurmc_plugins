package pl.pieszku.sectors.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface GuildCommandInfo {

    String name();
    String description();
    String usage();
    String[] aliases() default {};
}
