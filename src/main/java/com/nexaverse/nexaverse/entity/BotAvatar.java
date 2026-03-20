package com.nexaverse.nexaverse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bot_avatars")
public class BotAvatar extends Avatar {

    private String aiModel;
    private int intelligenceLevel;

    @Override
    public String getSpecialAbility() {
        return "AI Prediction — " + aiModel + " model use karta hai!";
    }

    @Override
    public int getAttackPower() {
        return 30 + (intelligenceLevel * 15);
    }

    public String getAiModel() { return aiModel; }
    public void setAiModel(String aiModel) { this.aiModel = aiModel; }
    public int getIntelligenceLevel() { return intelligenceLevel; }
    public void setIntelligenceLevel(int intelligenceLevel) { this.intelligenceLevel = intelligenceLevel; }
}