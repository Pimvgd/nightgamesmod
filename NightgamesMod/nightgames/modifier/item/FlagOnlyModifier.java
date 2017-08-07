package nightgames.modifier.item;

import nightgames.characters.Character;
import nightgames.ftc.FTCMatch;
import nightgames.global.Match;
import nightgames.items.Item;

public class FlagOnlyModifier extends ItemModifier {
    private static final String name = "flag-only";

    @Override
    public boolean itemIsBanned(Character c, Item i) {
        return ((FTCMatch) Match.getMatch()).isPrey(c) && i != Item.Flag;
    }

    @Override
    public String toString() {
        return name;
    }

    public String name() {
        return name;
    }
}
