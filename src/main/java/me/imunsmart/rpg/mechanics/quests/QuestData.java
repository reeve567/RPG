package me.imunsmart.rpg.mechanics.quests;

public class QuestData {
    private int flag;
    private Quest quest;

    public QuestData(Quest q, int flag) {
        this.quest = q;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public void incrementFlag() {
        flag++;
    }
}
