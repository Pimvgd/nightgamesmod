package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.global.Flag;
import nightgames.gui.GUI;
import nightgames.items.clothing.Clothing;
import nightgames.items.clothing.ClothingTable;

public class Boutique extends Store {
    public Boutique(Character player) {
        super("Boutique", player);
        ClothingTable.getAllBuyableFrom("Boutique").forEach(article -> add(article));
    }

    @Override
    public boolean known() {
        if (player.useFemalePronouns()) {
            return Flag.checkFlag(Flag.basicStores);
        }
        return false;
    }

    @Override
    public void visit(String choice) {
        GUI.gui.clearText();
        GUI.gui.clearCommand();
        if (choice.equals("Start")) {
            acted = false;
        }
        if (choice.equals("Leave")) {
            done(acted);
            return;
        }
        checkSale(choice);
        if (player.human()) {
            GUI.gui.message(
                            "This is a higher end store for women's clothing. Things may get a bit expensive here.");
            for (Clothing i : clothing().keySet()) {
                GUI.gui.message(i.getName() + ": " + i.getPrice() + (player.has(i) ? " (Owned)" : ""));
            }
            GUI.gui.message("You have: $" + player.money + " available to spend.");
            displayGoods();
            choose("Leave", GUI.gui);
        }
    }

    @Override
    public void shop(Character npc, int budget) {}

}
