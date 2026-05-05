package com.narxoz.rpg.guild;

import java.util.List;


public abstract class GuildMember {

    private final String name;
    private final GuildMediator mediator;

    protected GuildMember(String name, GuildMediator mediator) {
        this.name = name;
        this.mediator = mediator;

        if (mediator != null) {
            mediator.register(this);
        }
    }

    public String getName() {
        return name;
    }

    protected GuildMediator getMediator() {
        return mediator;
    }

    protected List<String> subscriptions() {
        return List.of("orders", "urgent");
    }

    protected String senderName(GuildMember from) {
        return from == null ? "CouncilEngine" : from.getName();
    }

    public abstract void receive(String topic, GuildMember from, String payload);
}