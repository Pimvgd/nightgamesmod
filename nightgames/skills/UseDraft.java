package nightgames.skills;

import java.util.ArrayList;
import java.util.Collection;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.global.Modifier;
import nightgames.items.Item;
import nightgames.items.ItemEffect;

public class UseDraft extends Skill {
	public UseDraft(Character self) {
		super("Use draft", self);
	}

	@Override
	public boolean requirements(Combat c, Character user, Character target) {
		return true;
	}

	@Override
	public boolean usable(Combat c, Character target) {
		boolean hasItems = subChoices().size() > 0;
		return hasItems&&getSelf().canAct()&&c.getStance().mobile(getSelf())&&(!getSelf().human()||Global.getMatch().condition!=Modifier.noitems);
	}

	@Override
	public Collection<String> subChoices() {
		ArrayList<String> usables = new ArrayList<String>();
		for (Item i : getSelf().getInventory().keySet()) {
			if (i.getEffects().get(0).drinkable()) {
				usables.add(i.getName());
			}
		}
		return usables;
	}

	@Override
	public boolean resolve(Combat c, Character target) {
		Item used = null;
		if (getSelf().human()) {
			for (Item i : getSelf().getInventory().keySet()) {
				if (i.getName().equals(choice)) {
					used = i;
					break;
				}
			}
		} else {
			ArrayList<Item> usables = new ArrayList<Item>();
			for (Item i : getSelf().getInventory().keySet()) {
				if (i.getEffects().get(0).drinkable()) {
					usables.add(i);
				}
			}
			if (usables.size() > 0) {
				used = usables.get(Global.random(usables.size()));
			}
		}
		if (used == null) {
			c.write(getSelf(), "Skill failed...");
		} else {
			boolean eventful = false;
			c.write(getSelf(), Global.format(String.format("{self:SUBJECT-ACTION:%s|%ss} %s%s",used.getEffects().get(0).getSelfVerb(),used.getEffects().get(0).getSelfVerb(), used.pre(), used.getName()), getSelf(), target));
			for (ItemEffect e : used.getEffects()) {
				eventful = e.use(c, getSelf(), target, used) || eventful;
			}
			if (!eventful) {
				c.write("...But nothing happened.");
			}
			getSelf().consume(used, 1);
		}
		return true;
	}

	@Override
	public Skill copy(Character user) {
		return new UseDraft(user);
	}

	@Override
	public Tactics type(Combat c) {
		return Tactics.debuff;
	}

	@Override
	public String deal(Combat c, int damage, Result modifier, Character target) {
		return "";
	}

	@Override
	public String receive(Combat c, int damage, Result modifier, Character target) {
		return "";
	}

	@Override
	public String describe() {
		return "Drink a draft";
	}

	@Override
	public boolean makesContact() {
		return false;
	}
}
