package nightgames.match;

import java.util.Collection;

import nightgames.characters.Character;
import nightgames.daytime.match.DaytimeMatch;
import nightgames.daytime.match.DaytimePrematch;
import nightgames.global.Flag;
import nightgames.global.Global;
import nightgames.match.defaults.DefaultPrematch;
import nightgames.match.ftc.FTCMatch;
import nightgames.match.ftc.FTCPrematch;
import nightgames.match.team.TeamMatch;
import nightgames.match.team.TeamPrematch;
import nightgames.modifier.Modifier;
import nightgames.modifier.standard.FTCModifier;
import nightgames.modifier.standard.NoModifier;

public enum MatchType {
    NORMAL,
    FTC,
    TEAM,
    DAYTIME;

    public Match buildMatch(Collection<Character> combatants, Modifier condition) {
        switch (this) {
            case FTC:
                assert condition.name().equals("FTC");
                if (combatants.size() != 5) {
                    return new Match(combatants, new NoModifier());
                }
                return new FTCMatch(combatants, ((FTCModifier) condition).getPrey());
            case NORMAL:
                return new Match(combatants, condition);
            case TEAM:
                return new TeamMatch(combatants, condition);
            case DAYTIME:
                return new DaytimeMatch(combatants, condition);
            default:
                throw new Error();
        }
    }
    
    public Prematch buildPrematch() {
        switch (this) {
            case FTC:
                return new FTCPrematch();
            case NORMAL:
                return new DefaultPrematch();
            case TEAM:
                return new TeamPrematch();
            default:
                throw new Error();
        }
    }
    
    public void runPrematch() {
        buildPrematch().run();
    }
    
    //exists entirely to make debugging with MatchModifierPicker.java work. runs a default event
    //with the given modifier while ignoring any requirements
    public void runWith(Modifier m) {
        Global.unflag(Flag.victory);
        DefaultPrematch selection = new DefaultPrematch();
        selection.type = m;
        Global.current = selection;
        selection.respond("Do");
    }

}
