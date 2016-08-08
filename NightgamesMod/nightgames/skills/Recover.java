package nightgames.skills;

import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.stance.Neutral;
import nightgames.status.Stsflag;

public class Recover extends Skill {

    public Recover(Character self) {
        super("Recover", self);
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return c.getStance()
                .prone(getSelf())
                        && c.getStance()
                            .mobile(getSelf())
                        && getSelf().canAct();
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
            c.write(getSelf(), deal(c, 0, Result.normal, target));
        } else if (target.human() || c.isBeingWatchedFrom(target)) {
            if (target.is(Stsflag.blinded))
                printBlinded(c);
            else
                c.write(getSelf(), receive(c, 0, Result.normal, target));
        }
        c.setStance(new Neutral(getSelf(), target));
        getSelf().heal(c, Global.random(3));
        return true;
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Recover(user);
    }

    @Override
    public int speed() {
        return 0;
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.positioning;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You pull yourself up, taking a deep breath to restore your focus.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return getSelf().name() + " scrambles back to her feet.";
    }

    @Override
    public String describe(Combat c) {
        return "Stand up";
    }
}
