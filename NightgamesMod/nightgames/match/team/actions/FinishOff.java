package nightgames.match.team.actions;

import nightgames.actions.IMovement;
import nightgames.characters.Character;
import nightgames.global.Global;
import nightgames.items.clothing.ClothingSlot;
import nightgames.match.team.TeamMatch;

public class FinishOff extends TeammateAction {

    public FinishOff(TeamMatch match, Character self) {
        super("Finish Off Captain", match, self);
    }

    @Override
    public String describe() {
        Character captain = getCaptain();
        return Global.format(
                        "{self:SUBJECT-ACTION:place|places} {self:possessive} hand"
                                        + " on {other:name-possessive} chest. \"Come on, {other:name}, you"
                                        + " can't go on like this. Let me take care of that for you.\""
                                        + " {other:PRONOUN}, not one to turn away such an offer,"
                                        + " {other:action:lean|leans} back against a wall while"
                                        + " {self:pronoun-action:kneel|kneels} down in front of {other:direct-object}."
                                        + " {self:PRONOUN} and %s. %s. It does not take long for {self:possessive} skilled"
                                        + " mouth to carry {other:direct-object} to the edge, and then over."
                                        + " {self:SUBJECT-ACTION:stand|stands} back up, wipes {self:possessive} mouth"
                                        + " clean, and then steps back, ready to follow orders again.",
                        self, captain, captain.getOutfit()
                                              .slotOpen(ClothingSlot.bottom)
                                                              ? (captain.hasDick()
                                                                              ? "{self:action:grab|grabs} hold of {other:possessive} turgid {other:body-part:cock}"
                                                                              : "{self:action:spread|spreads} {other:possessive} labia")
                                                              : (captain.hasDick()
                                                                              ? "{self:action:fish|fishes} out {other:possessive} {other:body-part:cock}"
                                                                              : "{self:action:grant|grants} {self:reflective} access to {other:possessive} {other:body-part:pussy}"),
                        captain.hasDick()
                                        ? "{self:PRONOUN} then starts giving {other:direct-object} a very enthusiastic blowjob"
                                        : "{self:PRONOUN} then starts lapping away at {other:direct-object} with a passion");
    }

    @Override
    boolean additionalRequirements() {
        return match.getTeamOf(self).captain.getArousal().percent() >= 25;
    }

    @Override
    void effects() {
        getCaptain().getArousal()
                    .empty();
    }

}
