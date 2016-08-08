package nightgames.skills;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.body.TentaclePart;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.status.Bound;
import nightgames.status.Oiled;
import nightgames.status.Stsflag;

public class TentaclePorn extends Skill {

    public TentaclePorn(Character self) {
        super("Tentacle Porn", self);
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return user.get(Attribute.Fetish) >= 12;
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return !target.wary() && !c.getStance().sub(getSelf()) && !c.getStance().prone(getSelf())
                        && !c.getStance().prone(target) && getSelf().canAct() && getSelf().getArousal().get() >= 20;
    }

    @Override
    public int getMojoCost(Combat c) {
        return 10;
    }

    @Override
    public String describe(Combat c) {
        return "Create a bunch of hentai tentacles.";
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        if (target.roll(this, c, accuracy(c))) {
            if (target.mostlyNude()) {
                int m = Global.random(getSelf().get(Attribute.Fetish)) / 2 + 1;
                if (target.bound()) {
                    if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
                        c.write(getSelf(), deal(c, 0, Result.special, target));
                    } else if (target.human() || c.isBeingWatchedFrom(target)) {
                        c.write(getSelf(), receive(c, 0, Result.special, target));
                    }
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandomCock());
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandomPussy());
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandomBreasts());
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandomAss());
                } else if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
                    c.write(getSelf(), deal(c, 0, Result.normal, target));
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandom("skin"));
                } else if (target.human() || c.isBeingWatchedFrom(target)) {
                    c.write(getSelf(), receive(c, 0, Result.normal, target));
                    TentaclePart.pleasureWithTentacles(c, target, m, target.body.getRandom("skin"));
                }
                if (!target.is(Stsflag.oiled)) {
                    target.add(c, new Oiled(target));
                }
                target.emote(Emotion.horny, 20);
            } else {
                if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
                    c.write(getSelf(), deal(c, 0, Result.weak, target));
                } else if (target.human() || c.isBeingWatchedFrom(target)) {
                    c.write(getSelf(), receive(c, 0, Result.weak, target));
                }
            }
            target.add(c, new Bound(target, Math.min(10 + 3 * getSelf().get(Attribute.Fetish), 50), "tentacles"));
        } else {
            if (getSelf().human() || c.isBeingWatchedFrom(getSelf())) {
                c.write(getSelf(), deal(c, 0, Result.miss, target));
            } else if (target.human() || c.isBeingWatchedFrom(target)) {
                c.write(getSelf(), receive(c, 0, Result.miss, target));
            }
            return false;
        }
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new TentaclePorn(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.positioning;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        if (modifier == Result.miss) {
            return "You summon a mass of tentacles that try to snare " + target.name()
                            + ", but she nimbly dodges them.";
        } else if (modifier == Result.weak) {
            return "You summon a mass of phallic tentacles that wrap around " + target.name()
                            + "'s arms, holding her in place.";
        } else if (modifier == Result.normal) {
            return "You summon a mass of phallic tentacles that wrap around " + target.name()
                            + "'s naked body. They squirm against her and squirt slimy fluids on her body.";
        } else {
            return "You summon tentacles to toy with " + target.name()
                            + "'s helpless form. The tentacles toy with her breasts and penetrate her pussy and ass.";
        }
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character target) {
        if (modifier == Result.miss) {
            return getSelf().name()
                            + " stomps on the ground and a bundle of tentacles erupt from the ground. You're barely able to avoid them.";
        } else if (modifier == Result.weak) {
            return getSelf().name()
                            + " stomps on the ground and a bundle of tentacles erupt from the ground around you, entangling your arms and legs.";
        } else if (modifier == Result.normal) {
            return getSelf().name()
                            + " stomps on the ground and a bundle of tentacles erupt from the ground around you, entangling your arms and legs. The slimy appendages "
                            + "wriggle over your body and coat you in the slippery liquid.";
        } else {
            return getSelf().name()
                            + " summons slimy tentacles that cover your helpless body, tease your dick, and probe your ass.";
        }
    }
}
