package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();

    private int lastNotificationCount;
    private int totalMessagesRouted;
    private int totalMembersNotified;

    @Override
    public void register(GuildMember member) {
        if (member == null) {
            return;
        }

        for (String topic : member.subscriptions()) {
            addSubscriber(topic, member);
        }

        System.out.println("[GuildHall] Registered " + member.getName()
                + " for topics " + member.subscriptions());
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        String actualTopic = topic == null ? "general" : topic.toLowerCase();
        String sender = from == null ? "CouncilEngine" : from.getName();

        lastNotificationCount = 0;
        totalMessagesRouted++;

        System.out.println("[GuildHall] " + sender + " dispatches topic '"
                + actualTopic + "': " + payload);

        List<GuildMember> subscribers = subscribersFor(actualTopic);

        for (GuildMember member : subscribers) {
            if (member == from) {
                continue;
            }

            member.receive(actualTopic, from, payload);
            lastNotificationCount++;
            totalMembersNotified++;
        }

        if (lastNotificationCount == 0) {
            System.out.println("[GuildHall] No subscribers handled topic '" + actualTopic + "'.");
        }
    }

    protected void addSubscriber(String topic, GuildMember member) {
        if (topic == null || member == null) {
            return;
        }

        String actualTopic = topic.toLowerCase();
        List<GuildMember> subscribers = membersByTopic.computeIfAbsent(actualTopic, key -> new ArrayList<>());

        if (!subscribers.contains(member)) {
            subscribers.add(member);
        }
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }

    public int getLastNotificationCount() {
        return lastNotificationCount;
    }

    public int getTotalMessagesRouted() {
        return totalMessagesRouted;
    }

    public int getTotalMembersNotified() {
        return totalMembersNotified;
    }

    public void resetStatistics() {
        lastNotificationCount = 0;
        totalMessagesRouted = 0;
        totalMembersNotified = 0;
    }
}