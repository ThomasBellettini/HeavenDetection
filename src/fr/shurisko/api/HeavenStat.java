package fr.shurisko.api;

public class HeavenStat {

    int hit = 0;
    int kothBlockBreak = 0;
    int damage = 0;
    int totemInterrupt = 0;
    int kills = 0;
    int timeCap = 0;

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getKothBlockBreak() {
        return kothBlockBreak;
    }

    public void setKothBlockBreak(int kothBlockBreak) {
        this.kothBlockBreak = kothBlockBreak;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getTotemInterrupt() {
        return totemInterrupt;
    }

    public void setTotemInterrupt(int totemInterrupt) {
        this.totemInterrupt = totemInterrupt;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getTimeCap() {
        return timeCap;
    }

    public void setTimeCap(int timeCap) {
        this.timeCap = timeCap;
    }
}
