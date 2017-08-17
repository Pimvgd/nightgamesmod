package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.global.Flag;
import nightgames.global.GameState;
import nightgames.gui.GUI;

public class AddictionRemoval extends Activity {

    private static final String UNSAFE_OPT = "Overload it: $5000";
    private static final String SAFE_OPT = "Safe but expensive: $15000";

    public AddictionRemoval(Character player) {
        super("Addiction Removal", player);
    }

    @Override
    public boolean known() {
        return Flag.checkFlag(Flag.AddictionAdvice) && GameState.gameState.characterPool.getPlayer()
                                                               .checkAddiction();
    }

    @Override
    public void visit(String choice) {
        GUI.gui
              .clearText();
        GUI.gui
              .clearCommand();
        if (choice.equals("Start")) {
            GUI.gui
                  .message("You walk to the place Aesop told you about "
                                  + "where you're supposed to be able to get rid of your addictions."
                                  + " An imperious-looking woman in a lab coat is there to meet you, and explains"
                                  + " that you have two options. The cheap options will overload the addiction,"
                                  + " basically making it enormously strong for one night and then snuffing it out"
                                  + " completely. The other, more expensive choice will reduce or eliminate the addiction"
                                  + " without side-effects, although very strong addictions may require several treatments."
                                  + "\n\n(this is a placeholder -- note that these treatments only affect your current"
                                  + " strongest addiction)");
            if (player.money >= 5000) {
                choose(UNSAFE_OPT, GUI.gui);
                if (player.money >= 15000) {
                    choose(SAFE_OPT, GUI.gui);
                } else {
                    GUI.gui
                          .message("\n\nA quick look at your finances reveal that only the risky option is"
                                          + " affordable for you right now. That may be a problem.");
                }
            } else {
                GUI.gui
                      .message("\n\nUnfortunately, you don't have the cash for either option right now.");
            }
        } else if (choice.equals(UNSAFE_OPT)) {
            player.money -= 5000;
            GUI.gui.message("Nervously, you handed over the money for the overload treatment. You don't"
                            + " remember what happened next, but you do know that now your addiction is far"
                            + " stronger than before. Let's hope this works.");
            GameState.gameState.characterPool.getPlayer().getStrongestAddiction().get().overload();
        } else if (choice.equals(SAFE_OPT)) {
            player.money -= 15000;
            GUI.gui.message("You dole out the mountain of cash and are taken to the back for your treatment."
                            + " When you emerge, you are completely free of your addiction.");
            GameState.gameState.characterPool.getPlayer().removeStatusImmediately(
                            GameState.gameState.characterPool.getPlayer().getStrongestAddiction().get());
        } else if (choice.equals("Leave")) {
            done(true);
            return;
        }
        choose("Leave", GUI.gui);

    }

    @Override
    public void shop(Character npc, int budget) {}

}
