package nightgames.daytime.match;

import nightgames.characters.Character;
import nightgames.global.Global;
import nightgames.match.Postmatch;

import java.util.List;

public class DaytimePostmatch extends Postmatch {
    protected DaytimePostmatch(List<Character> combatants) {
        super(combatants, false);
    }

    @Override
    protected void runInternal() {
        Global.gui().endMatch();
        Global.gui().clearCommand();
        Global.day.plan();
    }

    @Override
    public void respond(String response) {

    }
}
