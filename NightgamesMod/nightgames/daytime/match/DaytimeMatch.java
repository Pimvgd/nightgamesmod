package nightgames.daytime.match;

import nightgames.areas.Area;
import nightgames.characters.Character;
import nightgames.characters.Player;
import nightgames.characters.State;
import nightgames.characters.Trait;
import nightgames.global.Flag;
import nightgames.global.Global;
import nightgames.match.Encounter;
import nightgames.match.Match;
import nightgames.match.Postmatch;
import nightgames.modifier.Modifier;
import nightgames.status.addiction.Addiction;

import java.util.Collection;
import java.util.Optional;

public class DaytimeMatch extends Match {
    public DaytimeMatch(Collection<Character> combatants, Modifier condition) {
        super(combatants, condition);
    }

    @Override
    protected Postmatch buildPostmatch() {
        return new DaytimePostmatch(combatants);
    }

    @Override
    protected void placeCharacters() {
        combatants.forEach(c -> c.place(map.get("Dorm")));
    }

    @Override
    public Encounter buildEncounter(Character first, Character second, Area location) {
        return new DaytimeEncounter(first, second, location);
    }

    @Override
    protected boolean shouldEndMatch() {
        return index >= 3 || time > 0;
    }

    @Override
    protected void end() {
        beforeEnd();
        for (Character next : combatants) {
            next.finishMatch();
        }
        Global.gui()
                .clearText();

        for (Character combatant : score.keySet()) {
            for (Character other : combatants) {
                while (combatant.has(other.getTrophy())) {
                    combatant.consume(other.getTrophy(), 1, false);
                }
            }
            combatant.challenges.clear();
            combatant.state = State.ready;
            condition.undoItems(combatant);
            combatant.change();
            finalizeCombatant(combatant);
        }

        afterEnd();
        Postmatch post = buildPostmatch();
        post.run();
    }
}
