package nightgames.daytime.match;

import nightgames.areas.Area;
import nightgames.characters.Character;
import nightgames.match.defaults.DefaultEncounter;

public class DaytimeEncounter extends DefaultEncounter {

    public DaytimeEncounter(Character first, Character second, Area location) {
        super(first, second, location);
    }

    @Override
    public boolean spotCheck() {
        startFight();
        return true;
    }
}