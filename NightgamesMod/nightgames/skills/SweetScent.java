package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.status.Frenzied;

public class SweetScent extends Skill {
    public SweetScent(Character self) {
        super("Sweet Scent", self, 5);
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return getSelf().canRespond() && !target.wary();
    }

    @Override
    public int getMojoCost(Combat c) {
        return 30;
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        Result res = target.roll(this, c, -2) ? Result.normal : Result.miss;

        if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
            c.write(getSelf(), deal(c, 0, res, target));
        } else if (target.human() || c.isBeingWatchedFrom(target)) {
            c.write(getSelf(), receive(c, 0, res, target));
        }
        if (res != Result.miss) {
            target.arouse(25, c);
            target.emote(Emotion.horny, 100);
            target.add(c, new Frenzied(target, 8));
        }
        return res != Result.miss;
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return user.get(Attribute.Bio) >= 5;
    }

    @Override
    public Skill copy(Character user) {
        return new SweetScent(user);
    }

    @Override
    public int speed() {
        return 9;
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.debuff;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        if (modifier != Result.miss) {
            return "You breathe out a dizzying pink gas which spreads through the area. " + target.getName()
                            + " quickly succumbs to the coying scent as her whole body flushes with arousal.";
        } else {
            return "You breathe out a dizzying pink gas, but " + target.getName()
                            + " covers her face and dodges out of the cloud.";
        }
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        if (modifier != Result.miss) {
            return getSelf().getName()
                            + " breathes out a dizzying pink gas which spreads through the area. You quickly succumbs to the coying scent as your whole body flushes with arousal.";
        } else {
            return getSelf().getName()
                            + " breathes out a dizzying pink gas, but you manage to cover your face and dodge out of the cloud.";
        }
    }

    @Override
    public String describe(Combat c) {
        return "Breathe out a sweet scent to send your opponent into a frenzy.";
    }
}
