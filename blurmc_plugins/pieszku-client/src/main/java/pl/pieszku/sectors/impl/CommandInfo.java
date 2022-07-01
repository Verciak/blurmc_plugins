package pl.pieszku.sectors.impl;

import org.pieszku.api.type.GroupType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface CommandInfo {

    String name() default "";
    GroupType permission() default GroupType.PLAYER;
    String usage() default "/test";
    String description() default "default command";
    String[] aliases() default {};
}
