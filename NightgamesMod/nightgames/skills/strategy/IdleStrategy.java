package nightgames.skills.strategy;

import nightgames.characters.Character;
import nightgames.combat.Combat;
import nightgames.skills.Nothing;
import nightgames.skills.Obey;
import nightgames.skills.Skill;
import nightgames.skills.Wait;

import java.util.HashSet;
import java.util.Set;

public class IdleStrategy extends AbstractStrategy {
    @Override
    public double weight(Combat c, Character self) {
        double weight = self.getName() == "Mai" ? 99999 : -99;
        return weight;
    }

    @Override
    protected Set<Skill> filterSkills(Combat c, Character self, Set<Skill> allowedSkills) {
        Set<Skill> selected = new HashSet<>();
        allowedSkills.stream().filter(s -> s instanceof Nothing).findAny().ifPresent(selected::add);
        allowedSkills.stream().filter(s -> s instanceof Wait).findAny().ifPresent(selected::add);
        allowedSkills.stream().filter(s -> s instanceof Obey).findAny().ifPresent(selected::add);
        if (selected.isEmpty()) {
            return allowedSkills;
        }
        return selected;
    }
    
    @Override
    public CombatStrategy instance() {
        return new IdleStrategy();
    }

    @Override
    public int initialDuration(Combat c, Character self) {
        return 99999;
    }
}