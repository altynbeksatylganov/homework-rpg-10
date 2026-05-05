package com.narxoz.rpg.guild;

import java.util.List;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    protected List<String> subscriptions() {
        return List.of("orders", "healing", "urgent", "curse", "scouting");
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        if ("healing".equals(topic)) {
            System.out.println("[Healer] " + getName()
                    + " prepares recovery plan after message from "
                    + senderName(from) + ": " + payload);
        } else if ("curse".equals(topic)) {
            System.out.println("[Healer] " + getName()
                    + " prepares antidotes and blessing salts: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("[Healer] " + getName()
                    + " brings emergency bandages and mana tonics.");
        } else {
            System.out.println("[Healer] " + getName()
                    + " records council note from " + senderName(from) + ": " + payload);
        }
    }
}