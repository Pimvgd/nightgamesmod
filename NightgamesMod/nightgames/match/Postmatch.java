package nightgames.match;

import java.util.Collections;
import java.util.List;

import nightgames.characters.Character;
import nightgames.characters.Player;
import nightgames.global.Global;
import nightgames.global.Scene;

public abstract class Postmatch implements Scene {

    protected final Player player;
    protected final List<Character> combatants;
    private final boolean endsNight;

    protected Postmatch(List<Character> combatants, boolean endsNight) {
        this.combatants = Collections.unmodifiableList(combatants);
        this.endsNight = endsNight;
        player = Global.getPlayer();
    }
    
    protected abstract void runInternal();
    
    public final void run() {
        runInternal();
        if (endsNight) {
            Global.endNight();          //NOTE: This file is extended by a default, which is confusing and located elsewhere in the project. When someone wants to work on the the normal postMatch, they should be in this file. - DSM
        }
    }
    
}
