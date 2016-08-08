package nightgames.skills;

import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.stance.ReverseMount;

public class ReverseStraddle extends Skill {

    public ReverseStraddle(Character self) {
        super("Mount(Reverse)", self);

    }

    @Override
    public boolean usable(Combat c, Character target) {
        return c.getStance().mobile(getSelf()) && c.getStance().mobile(target) && c.getStance().prone(target)
                        && getSelf().canAct();
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
            c.write(getSelf(), deal(c, 0, Result.normal, target));
        } else if (target.human() || c.isBeingWatchedFrom(target)) {
            c.write(getSelf(), receive(c, 0, Result.normal, target));
        }
        c.setStance(new ReverseMount(getSelf(), target));
        return true;
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new ReverseStraddle(user);
    }

    @Override
    public int speed() {
        return 6;
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.positioning;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You straddle " + target.name() + ", facing her feet.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return getSelf().name() + " sits on your chest, facing your crotch.";
    }

    @Override
    public String describe(Combat c) {
        return "Straddle facing groin";
    }

    @Override
    public boolean makesContact() {
        return true;
    }
}
