package nightgames.global;

import java.util.Collection;

import nightgames.characters.Character;
import nightgames.modifier.Modifier;

public class SimpleMatch extends Match {

    private MatchListener matchListener;
    
    public SimpleMatch(Collection<Character> combatants, Modifier condition, MatchListener matchListener) {
        super(combatants, condition);
        // TODO Auto-generated constructor stub
        this.matchListener = matchListener;
    }
    
    @Override
    public void resume() {
        for(Character combatant : combatants)
        {
            combatant.resupply();//award points
        }
        
        matchListener.matchEnd(score);
        
        Global.gui().showNone();//fixes the map
        
        for(Character combatant : combatants)
        {
            for (Character other : combatants) {
                while (combatant.has(other.getTrophy())) {
                    combatant.consume(other.getTrophy(), 1, false);//get rid of gained trophies
                }
            }
            combatant.change();//put clothes back on
        }
        
        //TODO check for any other "bugs" 
    }
    
    

}
