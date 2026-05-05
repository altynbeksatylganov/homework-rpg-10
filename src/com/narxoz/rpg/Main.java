package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        Hero warrior = new Hero("Aruzhan the Blade", 120, 35, 18, 9, 150);
        Hero mage = new Hero("Daniyar the Chronomage", 85, 100, 9, 4, 90);

        List<Hero> party = List.of(warrior, mage);

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Clear the Goblin Road", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Rescue the Caravan", QuestPriority.HIGH, 220, false));
        questLog.add(new Quest("Seal the Cursed Ruin", QuestPriority.URGENT, 400, true));
        questLog.add(new Quest("Escort the Merchant Prince", QuestPriority.LOW, 80, false));
        questLog.add(new Quest("Hunt the Ash Drake", QuestPriority.HIGH, 300, true));

        GuildHall hall = new GuildHall();

        Quartermaster quartermaster = new Quartermaster("Mira the Quartermaster", hall);
        Scout scout = new Scout("Talgat the Scout", hall);
        Healer healer = new Healer("Aigerim the Healer", hall);
        Captain captain = new Captain("Captain Rustam", hall);
        Loremaster loremaster = new Loremaster("Sanzhar the Loremaster", hall);

        System.out.println();
        System.out.println("=== Mediator Warm-up: Officers Send Messages Through GuildHall ===");

        captain.issueOrder("orders", "Open the war council and prepare campaign plans.");
        scout.reportRoute("scouting", "Northern pass is clear, but the old bridge is unstable.");
        quartermaster.requestSupplies("supplies", "Prepare rope, torches, rations, and repair kits.");
        healer.prepareAid("healing", "Stock healing potions and mana tonics.");
        loremaster.shareLore("lore", "The cursed ruin was sealed once by the old guild masters.");

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println();
        System.out.println("=== Final CouncilRunResult ===");
        System.out.println(result);
    }
}