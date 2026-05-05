package com.narxoz.rpg.guild;

import java.util.List;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    protected List<String> subscriptions() {
        return List.of("orders", "scouting", "supplies", "healing", "rewards", "urgent", "lore", "curse", "history");
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        if ("urgent".equals(topic)) {
            System.out.println("[Captain] " + getName()
                    + " marks this as top priority after message from "
                    + senderName(from) + ": " + payload);
        } else if ("scouting".equals(topic)) {
            System.out.println("[Captain] " + getName()
                    + " updates the battle plan using scouting report: " + payload);
        } else {
            System.out.println("[Captain] " + getName()
                    + " reviews topic '" + topic + "' from "
                    + senderName(from) + ": " + payload);
        }
    }
}
