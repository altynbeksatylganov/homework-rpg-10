package com.narxoz.rpg.guild;

import java.util.List;


public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    protected List<String> subscriptions() {
        return List.of("orders", "scouting", "urgent", "lore", "history");
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        if ("scouting".equals(topic)) {
            System.out.println("[Scout] " + getName()
                    + " maps a route after report from "
                    + senderName(from) + ": " + payload);
        } else if ("lore".equals(topic) || "history".equals(topic)) {
            System.out.println("[Scout] " + getName()
                    + " marks old ruins on the map: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("[Scout] " + getName()
                    + " sends fastest runners for urgent intel.");
        } else {
            System.out.println("[Scout] " + getName()
                    + " receives order from " + senderName(from) + ": " + payload);
        }
    }
}