package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.stance.StandingOver;

public class Dominate extends Skill {

    public Dominate(Character self) {
        super("Dominate", self, 3);
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return user.get(Attribute.Dark) >= 9;
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return !target.wary() && !c.getStance().sub(getSelf()) && !c.getStance().prone(getSelf())
                        && !c.getStance().prone(target) && getSelf().canAct() && !getSelf().has(Trait.submissive);
    }

    @Override
    public String describe(Combat c) {
        return "Overwhelm your opponent to force her to lie down: 10 Arousal";
    }

    @Override
    public int getMojoCost(Combat c) {
        return 15;
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        getSelf().arouse(10, c);
        if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
            c.write(getSelf(), deal(c, 0, Result.normal, target));
        } else if (target.human() || c.isBeingWatchedFrom(target)) {
            c.write(getSelf(), receive(c, 0, Result.normal, target));
        }
        c.setStance(new StandingOver(getSelf(), target));
        getSelf().emote(Emotion.dominant, 20);
        target.emote(Emotion.nervous, 20);
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Dominate(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.positioning;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You take a deep breathe, gathering dark energy into your lungs. You expend the power to command "
                        + target.name() + " to submit. The demonic command renders her "
                        + "unable to resist and she drops to floor, spreading her legs open to you. As you approach, she comes to her senses and quickly closes her legs. Looks like her "
                        + "will is still intact.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        return getSelf().name()
                        + " forcefully orders you to \"Kneel!\" Your body complies without waiting for your brain and you drop to your knees in front of her. She smiles and "
                        + "pushes you onto your back. By the time you break free of her suggestion, you're flat on the floor with her foot planted on your chest.";
    }

}
