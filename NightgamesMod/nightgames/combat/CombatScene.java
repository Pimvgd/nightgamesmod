package nightgames.combat;

import nightgames.characters.Character;
import nightgames.characters.NPC;
import nightgames.gui.GUI;
import nightgames.gui.RunnableButton;
import nightgames.requirements.Requirement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CombatScene {
    public static interface StringProvider {
        String provide(Combat c, Character self, Character other);
    }
    private StringProvider message;
    private List<CombatSceneChoice> choices;
    private Requirement requirement;

    public CombatScene(Requirement requirement, StringProvider message, Collection<CombatSceneChoice> choices) {
        this.choices = new ArrayList<>(choices);
        this.message = message;
        this.requirement = requirement;
    }

    public void visit(Combat c, Character npc) {
        FutureTask<String> future =
                        new FutureTask<String>(new Callable<String>() {
                          public String call() {
                            return "";
                        }});
        c.updateAndClearMessage();
        c.write("<br/>");
        c.write(message.provide(c, npc, c.getOpponent(npc)));
        c.updateGUI();
        choices.forEach(choice -> {
            RunnableButton button = RunnableButton.genericRunnableButton(choice.getChoice(), () -> {
                c.write("<br/>");
                choice.choose(c, npc);
                c.updateGUI();
                future.run();
            });
            GUI.gui.commandPanel.add(button);
            GUI.gui.commandPanel.refresh();
        });
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c.promptNext(GUI.gui);
    }

    public boolean meetsRequirements(Combat c, NPC npc) {
        return requirement.meets(c, npc, c.getOpponent(npc));
    }
}
