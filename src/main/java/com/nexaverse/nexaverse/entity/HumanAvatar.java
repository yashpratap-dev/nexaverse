package com.nexaverse.nexaverse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "human_avatars")
public class HumanAvatar extends Avatar {

    private String weaponType;
    private int shield;

    @Override
    public String getSpecialAbility() {
        return "Shield Bash — " + weaponType + " se attack!";
    }

    @Override
    public int getAttackPower() {
        return 50 + (getLevel() * 10);
    }

    public String getWeaponType() { return weaponType; }
    public void setWeaponType(String weaponType) { this.weaponType = weaponType; }
    public int getShield() { return shield; }
    public void setShield(int shield) { this.shield = shield; }
}