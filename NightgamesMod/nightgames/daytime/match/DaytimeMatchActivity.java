package nightgames.daytime.match;

import nightgames.characters.Character;
import nightgames.daytime.Activity;
import nightgames.global.Global;
import nightgames.match.MatchType;
import nightgames.modifier.standard.NoModifier;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DaytimeMatchActivity extends Activity {

    public DaytimeMatchActivity(Character player) {
        super("Daytime Match", player);
    }

    @Override
    public boolean known() {
        return true;
    }

    @Override
    public void visit(String choice) {
        Global.gui().clearText();
        Global.gui().clearCommand();
        if ("Back".equalsIgnoreCase(choice)) {
            done(false);
            return;
        }
        
        List<Character> availableOpponents = Global.getEnabledCharacters().stream().filter(c -> !c.human()).collect(Collectors.toList());
        Optional<Character> selectedOpponent = availableOpponents.stream().filter(c -> c.getName().equalsIgnoreCase(choice)).findAny();
        if (selectedOpponent.isPresent()) {
            Global.currentMatchType = MatchType.DAYTIME;
            Set<Character> lineup = new HashSet<>();
            lineup.add(selectedOpponent.get());
            lineup.add(player);
            Global.setUpMetersForMatch(new NoModifier(), lineup);
        } else {
            Global.gui().message("Daytime matches! Pick an opponent.");
            availableOpponents.forEach(c -> Global.gui().choose(this, c.getName()));
            Global.gui().choose(this, "Back", "Don't spar.");
        }
    }

    @Override
    public void shop(Character npc, int budget) {
        //Not for NPCs.
    }
}
