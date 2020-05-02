package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Creature gender. Typically there are males and females. But who knows, maybe
 * NC can invent something new ;)
 *
 * @author SoulKeeper
 */
@XmlEnum
public enum Gender {

    /**
     * Males
     */
    MALE(0),
    /**
     * Females
     */
    FEMALE(1),
    /**
     * Dummy for create
     */
    DUMMY(8);
    /**
     * id of gender
     */
    private int genderId;

    /**
     * Constructor.
     *
     * @param genderId id of the gender
     */
    private Gender(int genderId) {
        this.genderId = genderId;
    }

    /**
     * Get id of this gender.
     *
     * @return gender id
     */
    public int getGenderId() {
        return genderId;
    }

    /**
     * Returns <tt>Gender</tt> object correlating with given classId.
     *
     * @param genderId - id of player gender
     * @return Gender objects that matches the given genderId. If there isn't
     * any objects that matches given id, then
     * <b>IllegalArgumentException</b> is being thrown.
     */
    public static Gender getGenderById(int genderId) {
        for (Gender gender : values()) {
            if (gender.getGenderId() == genderId) {
                return gender;
            }
        }

        throw new IllegalArgumentException("There is no player gender with id " + genderId);
    }

    public static Gender getGenderByString(String fieldName) {
        for (Gender gender : values()) {
            if (gender.toString().equals(fieldName)) {
                return gender;
            }
        }
        return null;
    }
}
