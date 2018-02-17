package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.nskills.tags.SkillTag;
import nightgames.stance.Kneeling;
import nightgames.stance.Stance;
import nightgames.status.addiction.Addiction;
import nightgames.status.addiction.AddictionType;

public class Kneel extends Skill {

    public Kneel(Character self) {
        super("Kneel", self);
        addTag(SkillTag.positioning);
        addTag(SkillTag.suicidal);
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return getSelf().getPure(Attribute.Submissive) >= 10 || user.human() || user.humanControlled(c);
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return getSelf().canAct() && target.canAct() && c.getStance().en == Stance.neutral;
    }

    @Override
    public String describe(Combat c) {
        return "Get down on your knees before your opponent.";
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        c.setStance(new Kneeling(target, getSelf()));
        if (getSelf().human()) {
            c.write(getSelf(), deal(c, 0, Result.normal, target));
            if (Global.getPlayer().checkAddiction(AddictionType.MIND_CONTROL, target)) {
                Global.getPlayer().unaddictCombat(AddictionType.MIND_CONTROL, 
                                target, Addiction.LOW_INCREASE, c);
                c.write(getSelf(), "Acting submissively voluntarily reduces Mara's control over you.");
            }
        } else {
            c.write(getSelf(), receive(c, 0, Result.normal, target));
        }
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Kneel(user);
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
    public float priorityMod(Combat c) {
        return -2;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You drop to your hands and knees in front of " + target.getName() + " and look up at "+target.directObject()+" demurely.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return String.format("%s drops down to %s knees in front of %s.",
                        getSelf().subject(), getSelf().possessiveAdjective(),
                        target.nameDirectObject());
    }
}
