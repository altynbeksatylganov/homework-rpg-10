package com.narxoz.rpg.guild;

import java.util.List;


public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    protected List<String> subscriptions() {
        return List.of("orders", "supplies", "rewards", "urgent");
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        if ("rewards".equals(topic)) {
            System.out.println("[Quartermaster] " + getName()
                    + " calculates reward budget after message from "
                    + senderName(from) + ": " + payload);
        } else if ("supplies".equals(topic)) {
            System.out.println("[Quartermaster] " + getName()
                    + " prepares gear crates: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("[Quartermaster] " + getName()
                    + " unlocks emergency equipment for " + senderName(from) + ".");
        } else {
            System.out.println("[Quartermaster] " + getName()
                    + " notes order from " + senderName(from) + ": " + payload);
        }
    }
}