package com.narxoz.rpg.guild;

import java.util.List;

public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    protected List<String> subscriptions() {
        return List.of("lore", "curse", "history", "urgent", "orders");
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        if ("lore".equals(topic)) {
            System.out.println("[Loremaster] " + getName()
                    + " opens the old guild archives after message from "
                    + senderName(from) + ": " + payload);
        } else if ("curse".equals(topic)) {
            System.out.println("[Loremaster] " + getName()
                    + " identifies curse symbols: " + payload);
        } else if ("history".equals(topic)) {
            System.out.println("[Loremaster] " + getName()
                    + " recalls a similar campaign from the ancient records.");
        } else if ("urgent".equals(topic)) {
            System.out.println("[Loremaster] " + getName()
                    + " searches emergency records for warnings.");
        } else {
            System.out.println("[Loremaster] " + getName()
                    + " records council order from " + senderName(from) + ": " + payload);
        }
    }
}