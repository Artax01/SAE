package app.model.entity;

import app.model.fight.Spell;
import app.model.parser.JSONObject;
import app.model.parser.ParserConfig;

public class Monster extends Entity {
    private final String name;
    public Monster() {
        this("", 100, 100, 5, 10);
    }

    public Monster(String name, int currentHP, int maximumHP, int armor, int attack) {
        super(currentHP, maximumHP, armor, attack);
        this.name = name;
    }

    public static Monster createFromJSONObject(JSONObject json) {
        try {
            int HP = json.getNumber(ParserConfig.KEY_MONSTER_HP).intValue();
            int armor = json.getNumber(ParserConfig.KEY_MONSTER_ARMOR).intValue();
            int attack = json.getNumber(ParserConfig.KEY_MONSTER_ATTACK).intValue();
            String name = json.getString(ParserConfig.KEY_MONSTER_NAME);
            return new Monster(name, HP, HP, armor, attack);
        } catch (Exception e) {
            return null;
        }
    }

    public Monster clone() {
        Monster clone = new Monster(name, currentHP, maximumHP, armor, attack);

        for (Spell s : effects) {
            clone.addEffect(s.clone(), false);
        }

        return clone;
    }

    public Object asJSON() {
        return ("""
                 {
                       "name": "%s",
                        "HP": %d,
                        "Armor": %d,
                        "Attack": %d
                      }\
                """).formatted(name, maximumHP, armor, attack);
    }
}
