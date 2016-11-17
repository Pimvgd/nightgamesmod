package nightgames.global;

import java.util.Map;
import nightgames.characters.Character;

public interface MatchListener {
    void matchEnd(Map<Character, Integer> score);
}
