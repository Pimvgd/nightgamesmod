package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.gui.GUI;
import nightgames.gui.RunnableButton;

public abstract class Activity {
    protected String name;
    protected int time;
    protected Character player;
    protected int page;

    public Activity(String name, Character player) {
        this.name = name;
        time = 1;
        this.player = player;
        page = 0;
    }

    public abstract boolean known();

    public abstract void visit(String choice);

    public int time() {
        return time;
    }

    public void next() {
        page++;
    }

    public void done(boolean acted) {
        if (acted) {
            Daytime.getDay().advance(time);
        }
        page = 0;
        GUI.gui.clearImage();
        GUI.gui.clearPortrait();
        Daytime.getDay().plan();
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract void shop(Character npc, int budget);

    public void choose(String choice, String tooltip, GUI gui) {
        gui.commandPanel.add(EventButton.eventButton(this, choice, tooltip));
        gui.commandPanel.refresh();
    }

    public void choose(String choice, GUI gui) {
        gui.commandPanel.add(EventButton.eventButton(this, choice, null));
        gui.commandPanel.refresh();
    }

    public void next(GUI gui) {
        next();
        gui.clearCommand();
        gui.addButton(EventButton.eventButton(this,"Next", null));
    }

    public void addActivity(GUI gui) {
        RunnableButton button = new RunnableButton(toString(), () -> {
            visit("Start");
        });
        gui.addButton(button);
    }
}
