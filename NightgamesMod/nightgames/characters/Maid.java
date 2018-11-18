package nightgames.characters;

import nightgames.characters.body.*;
import nightgames.characters.body.mods.*;
import nightgames.characters.custom.CharacterLine;
import nightgames.combat.Combat;
import nightgames.combat.Result;
import nightgames.global.Global;
import nightgames.items.Item;
import nightgames.items.clothing.Clothing;
import nightgames.skills.strategy.IdleStrategy;
import nightgames.start.NpcConfiguration;

import java.util.Optional;

public class Maid extends BasePersonality {

    public Maid() {
        this(Optional.empty(), Optional.empty());
    }

    public Maid(Optional<NpcConfiguration> charConfig, Optional<NpcConfiguration> commonConfig) {
        super("Mai", charConfig, commonConfig, false);
        constructLines();
    }

    private void constructLines() {
        character.addLine(CharacterLine.BB_LINER, (c, self, other) -> {
            return "<i>Master, does it hurt?</i>";
        });

        character.addLine(CharacterLine.NAKED_LINER, (c, self, other) -> {
            return "Mai stares at you, seemingly unworried about her naked body. <i>\"Time for service?\"</i>";
        });

        character.addLine(CharacterLine.STUNNED_LINER, (c, self, other) -> {
            return "<i>Oof... Master...</i>";
        });

        character.addLine(CharacterLine.TAUNT_LINER, (c, self, other) -> {
            return "<i>\"You don't have to do anything, Master. Just let me take care of everything.\"</i>";
        });

        character.addLine(CharacterLine.TEMPT_LINER, (c, self, other) -> {
            return "Mai runs her hands all over her body while teasing you, <i>\"Master, what kind of service would you like?\"</i>";
        });

        character.addLine(CharacterLine.ORGASM_LINER, (c, self, other) -> {
            return "Mai yelps as she cums <i>\"Oh fuuuuckk, Master!\"</i>";
        });

        character.addLine(CharacterLine.MAKE_ORGASM_LINER, (c, self, other) -> {
            return "Mai looks a bit flushed as {other:subject-action:cum|cums} hard. However she changes neither her blank demeanor nor her stance.";
        });

        character.addLine(CharacterLine.LEVEL_DRAIN_LINER, (c, self, other) -> {
            String part = Global.pickRandom(c.getStance()
                    .getPartsFor(c, self, other))
                    .map(bp -> bp.getType())
                    .orElse("pussy");
            if (other.getLevel() < self.getLevel() - 5) {
                return "Mai stares at you with a possessive look in her eyes as her " + self.body.getRandom(part)
                        .describe(self)
                        + " plunders your strength once again. <i>\"Master... I have understood. I'll take care of everything for you from now on.\"</i> "
                        + "Her lips curl into a smile, <i>\"Don't worry... since Master seems to love giving everything to me, I'll let Master feel that pleasure every night from now on.\"</i>";
            } else if (other.getLevel() >= (self.getLevel() + 5)) {
                return "Mai clings on to your struggling body as your strength streams out of you and into the subservient maid. As the flow subsides, she looks up at you "
                        + "<i>\"Master, thanks for the reward...\"</i>";
            } else if (other.getLevel() >= self.getLevel()) {
                return "Mai clings on to your struggling body as your strength streams out of you and into the subservient maid. As the flow subsides, she looks up at you, somewhat puzzled "
                        + "<i>\"Master, is it really okay for me to have all this? Shouldn't you struggle more?\"</i>";
            } else {
                return "{self:SUBJECT} clings to your convulsing body as {self:pronoun} once again steals your experiences and training from your body as you helplessly cum. "
                        + "When you finally collapse exhausted, Mai looks at you in a strange, affectionate manner. "
                        + "<i>\"Master, I am happy you enjoy my special service. "
                        + "Even if you cannot fight back, please try. Didn't Master purchase me for training?\"</i>";
            }
        });

        character.addLine(CharacterLine.CHALLENGE, (c, self, other) -> {
            return "{self:SUBJECT} stares at you, her head slightly tilted. <i>\"Master, would you like a sparring match, a blowjob, or... me?\"</i>";
        });
    }

    @Override
    public void setGrowth() {
        character.getGrowth().stamina = 2;
        character.getGrowth().arousal = 8;
        character.getGrowth().willpower = 10.8f;
        character.getGrowth().bonusStamina = 2;
        character.getGrowth().bonusArousal = 3;


        character.getGrowth().addBodyPartMod(0, "mouth", ExtendedTonguedMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "mouth", TrainedMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "mouth", SecondPussyMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "mouth", CyberneticMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "mouth", TentacledMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "pussy", ExtendedTonguedMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "pussy", TrainedMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "pussy", CyberneticMod.INSTANCE);
        character.getGrowth().addBodyPartMod(0, "pussy", TentacledMod.INSTANCE);


        //this.addFirstFocusScene();

        //this.addSecondFocusScene();

        //TODO replace this (yoinked from Angel)
        character.getGrowth()
                .addTrait(0, Trait.obedient);
        character.getGrowth()
                .addTrait(0, Trait.alwaysready);
        character.getGrowth()
                .addTrait(0, Trait.lickable);
        character.getGrowth()
                .addTrait(0, Trait.oiledass);
        character.getGrowth()
                .addTrait(3, Trait.responsive);
        character.getGrowth()
                .addTrait(9, Trait.sexTraining1);
        // 12 - first choice 1
        character.getGrowth()
                .addTrait(15, Trait.expertGoogler);
        character.getGrowth()
                .addTrait(18, Trait.experienced);
        character.getGrowth()
                .addTrait(20, Trait.skeptical);
        // 21 - second choice 1
        character.getGrowth()
                .addTrait(24, Trait.tongueTraining1);
        // 27 - first choice 2
        // 30 - second choice 2
        character.getGrowth()
                .addTrait(33, Trait.sexTraining2);
        character.getGrowth()
                .addTrait(36, Trait.tongueTraining2);
        // 39 - first choice 3
        // 42 - second choice 3
        // 45 - second choice 4
        // 48 - second choice 5
        character.getGrowth()
                .addTrait(51, Trait.desensitized);
        // 54 - first choice 4
        character.getGrowth()
                .addTrait(57, Trait.desensitized2);
        // 60 - second choice 6
        preferredAttributes.add(
                c -> c.get(Attribute.Submissive) < 50 ? Optional.of(Attribute.Submissive) : Optional.empty());
        preferredAttributes.add(c -> Optional.of(Attribute.Seduction));
        preferredAttributes.add(
                c -> (c.has(Trait.nymphomania) && c.get(Attribute.Nymphomania) < (c.getLevel() - 10) / 2)
                        ? Optional.of(Attribute.Nymphomania) : Optional.empty());
    }

    @Override
    public String victory(Combat c, Result flag) {
        character.arousal.empty();
        return "Mai wins via " + flag;
    }

    @Override
    public String defeat(Combat c, Result flag) {
        character.arousal.empty();
        c.getOpponent(character).arousal.empty();
        return "Mai loses via " + flag;
    }

    @Override
    public String victory3p(Combat c, Character target, Character assist) {
        return "Mai helps to make " + target.directObject() + " cum.";
    }

    @Override
    public String intervene3p(Combat c, Character target, Character assist) {
        if (target.human()) {
            return "Your fight with " + assist.getName()
                    + " is interrupted by Mai, who makes you lose.";
        }

        return "Your fight with " + target.getName()
                + " is interrupted by Mai, who makes you win.";
    }

    @Override
    public String draw(Combat c, Result flag) {
        return "Mai draws via " + flag;
    }

    @Override
    public boolean fightFlight(Character opponent) {
        return true; //always fight
    }

    @Override
    public boolean attack(Character opponent) {
        return true;
    }

    @Override
    public boolean fit() {
        return true;
    }

    @Override
    public boolean checkMood(Combat c, Emotion mood, int value) {
        return value >= 100;
    }

    @Override
    public void applyBasicStats(Character self) {
        preferredCockMod = CockMod.bionic;
        self.outfitPlan.add(Clothing.getByID("bra"));
        self.outfitPlan.add(Clothing.getByID("blouse"));
        self.outfitPlan.add(Clothing.getByID("panties"));
        self.outfitPlan.add(Clothing.getByID("skirt"));
        self.outfitPlan.add(Clothing.getByID("shoes"));

        self.change();
        self.modAttributeDontSaveData(Attribute.Power, 1);
        self.modAttributeDontSaveData(Attribute.Seduction, 1);
        self.modAttributeDontSaveData(Attribute.Cunning, 1);
        self.modAttributeDontSaveData(Attribute.Perception, 1);
        self.modAttributeDontSaveData(Attribute.Submissive, 1);

        self.getStamina().setMax(70);
        self.getArousal().setMax(100);
        Global.gainSkills(self);
        self.setTrophy(Item.MiscTrophy);
        //TODO sensitivity when having mods?
        self.body.add(EarPart.pointed);
        self.body.add(new MouthPart("mouth", 0, 1, 0.1));
        self.body.add(new GenericBodyPart("hands", 0, 1, 0.1, "hands", ""));
        self.body.add(new GenericBodyPart("feet", 0, 1, 0.1, "feet", ""));
        self.body.add(new GenericBodyPart("skin", 0, 1, 0.1, "skin", ""));
        self.body.add(new PussyPart(0, 1.2, 0.1));
        self.body.add(new BreastsPart(0.1d).applyMod(new SizeMod(4)));
        self.body.add(new AssPart("ass", 0, 1.2, 3).upgrade().upgrade().upgrade());
        self.initialGender = CharacterSex.female;
    }

    @Override
    public void applyStrategy(NPC self) {
        NPC npcSelf = (NPC) self;
        npcSelf.plan = Plan.hunting;
        npcSelf.mood = Emotion.confident;

        self.addPersonalStrategy(new IdleStrategy());
    }
}
