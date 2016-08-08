package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.stance.Mount;
import nightgames.stance.ReverseMount;
import nightgames.stance.Stance;
import nightgames.status.addiction.Addiction;
import nightgames.status.addiction.AddictionType;

public class Stumble extends Skill {

    public Stumble(Character self) {
        super("Stumble", self);
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return getSelf().getPure(Attribute.Submissive) >= 9;
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return getSelf().canAct() && c.getStance().en == Stance.neutral;
    }

    @Override
    public String describe(Combat c) {
        return "An accidental pervert classic";
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        if (Global.random(2) == 0) {
            c.setStance(new Mount(target, getSelf()));
        } else {
            c.setStance(new ReverseMount(target, getSelf()));
        }
        if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
            c.write(deal(c, 0, Result.normal, target));
            if (Global.getPlayer().checkAddiction(AddictionType.MIND_CONTROL, target)) {
                Global.getPlayer().unaddictCombat(AddictionType.MIND_CONTROL, 
                                target, Addiction.LOW_INCREASE, c);
                c.write(getSelf(), "Acting submissively voluntarily reduces Mara's control over you.");
            }
        } else {
            c.write(receive(c, 0, Result.normal, target));
        }
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Stumble(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.negative;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You slip and fall to the ground, pulling " + target.name() + " awkwardly on top of you.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return String.format(
                        "%s stumbles and falls, grabbing you to catch %sself. Unfortunately, "
                                        + "you can't keep your balance and you fall on top of %s. Maybe that's not so unfortunate.",
                        getSelf().name(), getSelf().directObject(), getSelf().directObject());
    }

}
