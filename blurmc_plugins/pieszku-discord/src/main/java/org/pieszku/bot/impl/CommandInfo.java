package org.pieszku.bot.impl;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface CommandInfo {

    String name() default "";

    String usage() default "";

    String[] aliases() default {};

    Permission[] permission() default {};


    OptionData[] options = {};
    boolean onlyOwners() default false;

    boolean slashCommand() default true;

    String description() default "";
}
