package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        QuestLog safeQuestLog = questLog == null ? new QuestLog() : questLog;
        List<Hero> safeParty = party == null ? List.of() : party;

        if (hall instanceof GuildHall guildHall) {
            guildHall.resetStatistics();
        }

        int questsTraversed = 0;
        int messagesRouted = 0;
        int membersNotified = 0;

        System.out.println();
        System.out.println("=== Adventurers' Guild War Council Opens ===");

        System.out.println("Party attending the council:");
        for (Hero hero : safeParty) {
            System.out.println(" - " + hero);
        }

        System.out.println();
        System.out.println("=== Iterator Phase: Arrival Order Review ===");

        QuestIterator ordered = safeQuestLog.ordered();
        while (ordered.hasNext()) {
            Quest quest = ordered.next();
            questsTraversed++;

            System.out.println("[OrderedQuestIterator] " + quest);

            if (hall != null) {
                messagesRouted++;
                membersNotified += dispatchAndCount(
                        hall,
                        "orders",
                        "Prepare plan for quest: " + quest.getTitle()
                );

                if (quest.isUrgent() || quest.getPriority() == QuestPriority.URGENT) {
                    messagesRouted++;
                    membersNotified += dispatchAndCount(
                            hall,
                            "urgent",
                            "Urgent contract requires immediate council attention: " + quest.getTitle()
                    );
                }

                if (quest.getPriority().ordinal() >= QuestPriority.HIGH.ordinal()) {
                    messagesRouted++;
                    membersNotified += dispatchAndCount(
                            hall,
                            "scouting",
                            "Scout route needed for high-risk quest: " + quest.getTitle()
                    );

                    messagesRouted++;
                    membersNotified += dispatchAndCount(
                            hall,
                            "healing",
                            "Prepare healing resources for dangerous quest: " + quest.getTitle()
                    );
                }

                if (quest.getRewardGold() >= 250) {
                    messagesRouted++;
                    membersNotified += dispatchAndCount(
                            hall,
                            "rewards",
                            "Large reward contract requires budget review: " + quest.getRewardGold() + " gold"
                    );
                }

                if (quest.getTitle().toLowerCase().contains("curse")
                        || quest.getTitle().toLowerCase().contains("ruin")) {
                    messagesRouted++;
                    membersNotified += dispatchAndCount(
                            hall,
                            "curse",
                            "Possible curse or ancient danger in quest: " + quest.getTitle()
                    );
                }
            }
        }

        questsTraversed += walkOnly("ReverseQuestIterator: newest to oldest", safeQuestLog.reverse());
        questsTraversed += walkOnly("PriorityQuestIterator: HIGH and above", safeQuestLog.priorityAtLeast(QuestPriority.HIGH));
        questsTraversed += walkOnly("RewardSortedQuestIterator: highest reward first", safeQuestLog.rewardSorted());

        System.out.println();
        System.out.println("=== Adventurers' Guild War Council Closes ===");

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private int walkOnly(String label, QuestIterator iterator) {
        int count = 0;

        System.out.println();
        System.out.println("=== Iterator Phase: " + label + " ===");

        while (iterator.hasNext()) {
            Quest quest = iterator.next();
            count++;
            System.out.println("[" + label + "] " + quest);
        }

        return count;
    }

    private int dispatchAndCount(GuildMediator hall, String topic, String payload) {
        hall.dispatch(topic, null, payload);

        if (hall instanceof GuildHall guildHall) {
            return guildHall.getLastNotificationCount();
        }

        return 0;
    }
}