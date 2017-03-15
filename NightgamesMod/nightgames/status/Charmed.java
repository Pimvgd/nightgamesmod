package nightgames.status;

import java.util.Optional;

import com.google.gson.JsonObject;

import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.body.BodyPart;
import nightgames.combat.Combat;

public class Charmed extends DurationStatus {
    public Charmed(Character affected) {
        super("Charmed", affected, 5);
        flag(Stsflag.charmed);
        flag(Stsflag.purgable);
        flag(Stsflag.debuff);
        flag(Stsflag.mindgames);
    }

    public Charmed(Character affected, int duration) {
        this(affected);
        super.setDuration(duration);
    }

    @Override
    public String describe(Combat c) {
        if (affected.human()) {
            return "You feel an irresistible attraction to " + c.getOpponent(affected).nameDirectObject() + " and can't imagine harming "+c.getOpponent(affected).directObject()+".";
        } else {
            return affected.getName() + " is looking at "+c.getOpponent(affected).nameDirectObject()
                            +" like a lovestruck teenager.";
        }
    }

    @Override
    public float fitnessModifier() {
        return -(2 + getDuration() / 2.0f);
    }

    @Override
    public int mod(Attribute a) {
        return 0;
    }

    @Override
    public void onRemove(Combat c, Character other) {
        affected.addlist.add(new Cynical(affected));
    }

    @Override
    public void tick(Combat c) {
        affected.emote(Emotion.horny, 15);
        affected.loseWillpower(c, 1, 0, false, " (Charmed)");
    }

    @Override
    public int damage(Combat c, int x) {
        return 0;
    }

    @Override
    public double pleasure(Combat c, BodyPart withPart, BodyPart targetPart, double x) {
        return 0;
    }

    @Override
    public String initialMessage(Combat c, Optional<Status> replacement) {
        return String.format("%s now charmed.\n", affected.subjectAction("are", "is"));
    }

    @Override
    public int weakened(Combat c, int x) {
        return 0;
    }

    @Override
    public int tempted(Combat c, int x) {
        return 3;
    }

    @Override
    public int evade() {
        return 0;
    }

    @Override
    public int escape() {
        return -10;
    }

    @Override
    public int gainmojo(int x) {
        return 0;
    }

    @Override
    public int spendmojo(int x) {
        return 0;
    }

    @Override
    public int counter() {
        return -10;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Status instance(Character newAffected, Character newOther) {
        return new Charmed(newAffected);
    }

    @Override  public JsonObject saveToJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", getClass().getSimpleName());
        return obj;
    }

    @Override public Status loadFromJson(JsonObject obj) {
        return new Charmed(null);
    }
}
