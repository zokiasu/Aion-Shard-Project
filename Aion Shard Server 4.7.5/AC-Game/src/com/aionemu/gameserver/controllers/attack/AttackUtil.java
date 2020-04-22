package com.aionemu.gameserver.controllers.attack;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.SkillElement;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.templates.item.ItemAttackType;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_SELECTED;
import com.aionemu.gameserver.skillengine.change.Func;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HitType;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ATracer
 */
public class AttackUtil {

    private static final Logger log = LoggerFactory.getLogger(AttackUtil.class);

    /**
     * Calculate physical attack status and damage
     */
    public static List<AttackResult> calculatePhysicalAttackResult(final Creature attacker, final Creature attacked) {
        final AttackStatus attackerStatus = null;
        final int damage = StatFunctions.calculateAttackDamage(attacker, attacked, true, SkillElement.NONE);
        final List<AttackResult> attackList = new ArrayList<AttackResult>();
        final AttackStatus mainHandStatus = calculateMainHandResult(attacker, attacked, attackerStatus, damage, attackList);

        if (attacker instanceof Player && ((Player) attacker).getEquipment().getOffHandWeaponType() != null) {
            calculateOffHandResult(attacker, attacked, mainHandStatus, attackList);
        }
        attacked.getObserveController().checkShieldStatus(attackList, null, attacker);
        return attackList;
    }

    /**
     * Calculate physical attack status and damage of the MAIN hand
     */
    private static final AttackStatus calculateMainHandResult(final Creature attacker, final Creature attacked, final AttackStatus attackerStatus, final int damage, final List<AttackResult> attackList) {
        AttackStatus mainHandStatus = attackerStatus;
        if (mainHandStatus == null) {
            mainHandStatus = calculatePhysicalStatus(attacker, attacked, true);
        }

        int mainHandHits = 1;
        if (attacker instanceof Player) {
            final Item mainHandWeapon = ((Player) attacker).getEquipment().getMainHandWeapon();
            if (mainHandWeapon != null) {
                mainHandHits = Rnd.get(1, mainHandWeapon.getItemTemplate().getWeaponStats().getHitCount());
            }
        } else {
            mainHandHits = Rnd.get(1, 3);
        }
        splitPhysicalDamage(attacker, attacked, mainHandHits, damage, mainHandStatus, attackList);
        return mainHandStatus;
    }

    /**
     * Calculate physical attack status and damage of the OFF hand
     */
    private static final void calculateOffHandResult(final Creature attacker, final Creature attacked, final AttackStatus mainHandStatus, final List<AttackResult> attackList) {
        final AttackStatus offHandStatus = AttackStatus.getOffHandStats(mainHandStatus);
        final Item offHandWeapon = ((Player) attacker).getEquipment().getOffHandWeapon();
        final int offHandDamage = StatFunctions.calculateAttackDamage(attacker, attacked, false, SkillElement.NONE);
        final int offHandHits = Rnd.get(1, offHandWeapon.getItemTemplate().getWeaponStats().getHitCount());
        splitPhysicalDamage(attacker, attacked, offHandHits, offHandDamage, offHandStatus, attackList);
    }

    /**
     * Generate attack results based on weapon hit count
     */
    private static final List<AttackResult> splitPhysicalDamage(final Creature attacker, final Creature attacked, final int hitCount, int damage, final AttackStatus status, final List<AttackResult> attackList) {
        WeaponType weaponType;

        switch (AttackStatus.getBaseStatus(status)) {
            case BLOCK:
                int reduce = damage - attacked.getGameStats().getPositiveReverseStat(StatEnum.DAMAGE_REDUCE, damage);
                if (attacked instanceof Player) {
                    final Item shield = ((Player) attacked).getEquipment().getEquippedShield();
                    if (shield != null) {
                        final int reduceMax = shield.getItemTemplate().getWeaponStats().getReduceMax();
                        if (reduceMax > 0 && reduceMax < reduce) {
                            reduce = reduceMax;
                        }
                    }
                }
                damage -= reduce;
                break;
            case DODGE:
                damage = 0;
                break;
            case PARRY:
                damage *= 0.6;
                break;
            default:
                break;
        }

        if (status.isCritical()) {
            if (attacker instanceof Player) {
                weaponType = ((Player) attacker).getEquipment().getMainHandWeaponType();
                damage = (int) calculateWeaponCritical(attacked, damage, weaponType, StatEnum.PHYSICAL_CRITICAL_DAMAGE_REDUCE);
                // Proc Stumble/Stagger on Crit calculation
                applyEffectOnCritical((Player) attacker, attacked);
            } else {
                damage = (int) calculateWeaponCritical(attacked, damage, null, StatEnum.PHYSICAL_CRITICAL_DAMAGE_REDUCE);
            }
        }

        if (damage < 1) {
            damage = 0;
        }

        final int firstHit = (int) (damage * (1f - (0.1f * (hitCount - 1))));
        final int otherHits = Math.round(damage * 0.1f);
        for (int i = 0; i < hitCount; i++) {
            final int dmg = (i == 0 ? firstHit : otherHits);
            attackList.add(new AttackResult(dmg, status, HitType.PHHIT));
        }
        return attackList;
    }

    /**
     * [Critical]
     * Spear : x1.5
     * Sword : x2.5
     * Dagger : x2.3
     * Mace : x2.0
     * Greatsword : x1.5
     * Orb : x2.0
     * Spellbook : x2.0
     * Bow : x1.4
     * Staff : x1.5
     *
     * @param damages
     * @param weaponType
     * @return
     */
    private static float calculateWeaponCritical(final Creature attacked, final float damages, final WeaponType weaponType, final StatEnum stat) {
        return calculateWeaponCritical(attacked, damages, weaponType, 0, stat);
    }

    private static float calculateWeaponCritical(final Creature attacked, float damages, final WeaponType weaponType, final int critAddDmg, final StatEnum stat) {
        float coeficient = 1.5f;

        if (weaponType != null) {
            switch (weaponType) {
                case GUN_1H:
                case DAGGER_1H:
                    coeficient = 2.3f;
                    break;
                case SWORD_1H:
                    coeficient = 2.2f;
                    break;
                case MACE_1H:
                    coeficient = 2f;
                    break;
                case SWORD_2H:
                case CANNON_2H:
                case POLEARM_2H:
                case KEYBLADE_2H:
                case KEYHAMMER_2H:
                    coeficient = 1.8f;
                    break;
                case STAFF_2H:
                case BOW:
                    coeficient = 1.7f;
                    break;
                default:
                    coeficient = 1.5f;
                    break;
            }

            if (stat.equals(StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE)) {
                coeficient = 1.5f; //Magical skill with physical weapon TODO: confirm this
            }
        }

        if (attacked instanceof Player) { //Strike Fortitude lowers the crit multiplier
            final Player player = (Player) attacked;
            int fortitude = 0;
            switch (stat) {
                case PHYSICAL_CRITICAL_DAMAGE_REDUCE:
                case MAGICAL_CRITICAL_DAMAGE_REDUCE:
                    fortitude = player.getGameStats().getStat(stat, 0).getCurrent();
                    coeficient -= Math.round(fortitude / 1000f);
                    break;
            }
        }

        //add critical add dmg
        coeficient += (float) critAddDmg / 100f;

        damages = Math.round(damages * coeficient);

        if (attacked instanceof Npc) {
            damages = attacked.getAi2().modifyDamage((int) damages);
        }
        return damages;
    }

    /**
     * @param effect
     * @param skillDamage
     * @param func         (add/percent)
     * @param randomDamage
     * @param accMod
     */
    public static void calculateSkillResult(final Effect effect, final int skillDamage, final ActionModifier modifier, final Func func, final int randomDamage, final int accMod, final int criticalProb, final int critAddDmg, final boolean cannotMiss, final boolean shared, final boolean ignoreShield, final boolean isMainHand) {
        final Creature effector = effect.getEffector();
        final Creature effected = effect.getEffected();

        int damage = 0;
        int baseAttack = 0;
        if (effector.getAttackType() == ItemAttackType.PHYSICAL) {
            baseAttack = effector.getGameStats().getMainHandPAttack().getBase();
            damage = StatFunctions.calculatePhysicalAttackDamage(effector, effected, true);
        } else {
            if (isMainHand) {
                baseAttack = effector.getGameStats().getMainHandMAttack().getBase();
            } else {
                baseAttack = effector.getGameStats().getOffHandMAttack().getBase();
            }
            damage = StatFunctions.calculateMagicalAttackDamage(effector, effected, effector.getAttackType().getMagicalElement(), isMainHand);
        }

        //add skill damage
        if (func != null) {
            switch (func) {
                case ADD:
                    damage += skillDamage;
                    break;
                case PERCENT:
                    damage += baseAttack * skillDamage / 100f;
                    break;
            }
        }

        //add bonus damage
        if (modifier != null) {
            final int bonus = modifier.analyze(effect);
            switch (modifier.getFunc()) {
                case ADD:
                    damage += bonus;
                    break;
                case PERCENT:
                    damage += baseAttack * bonus / 100f;
                    break;
            }
        }

        // adjusting baseDamages according to attacker and target level
        damage = (int) StatFunctions.adjustDamages(effect.getEffector(), effect.getEffected(), damage, effect.getPvpDamage(), true);
        final float damageMultiplier = effector.getObserveController().getBasePhysicalDamageMultiplier(true);
        damage = Math.round(damage * damageMultiplier);

        // implementation of random damage for skills like Stunning Shot, etc
        log.warn("randomDamage : " + randomDamage);
        if (randomDamage > 0) {
            final int randomChance = Rnd.get(100);
            // TODO Hard fix
            if (effect.getSkillId() == 20033) {
                damage *= 10;
            }

            switch (randomDamage) {
                case 1:
                    if (randomChance <= 40) {
                        damage /= 2;
                    } else if (randomChance <= 70) {
                        damage *= 1.1;
                    }
                    break;
                case 2:
                    if (randomChance <= 25) {
                        damage *= 2;
                    }
                    break;
                case 6:
                    if (randomChance <= 30) {
                        damage *= 1.5;
                    }
                    break;
                // TODO rest of the cases
                default:
                    /*
                     * chance to do from 50% to 200% damage This must NOT be calculated after critical status check, or it will be
                     * over powered and not retail
                     */
                    damage *= (Rnd.get(1, 50) * 0.02f);
                    //damage *= (Rnd.get(1, 100) * 0.02f);
                    break;
            }
        }

        AttackStatus status = AttackStatus.NORMALHIT;
        if (effector.getAttackType() == ItemAttackType.PHYSICAL) {
            status = calculatePhysicalStatus(effector, effected, true, accMod, criticalProb, true, cannotMiss);
        } else {
            status = calculateMagicalStatus(effector, effected, criticalProb, true);
        }

        switch (AttackStatus.getBaseStatus(status)) {
            case BLOCK:
                int reduce = damage - effected.getGameStats().getPositiveReverseStat(StatEnum.DAMAGE_REDUCE, damage);
                if (effected instanceof Player) {
                    final Item shield = ((Player) effected).getEquipment().getEquippedShield();
                    if (shield != null) {
                        final int reduceMax = shield.getItemTemplate().getWeaponStats().getReduceMax();
                        if (reduceMax > 0 && reduceMax < reduce) {
                            reduce = reduceMax;
                        }
                    }
                }
                damage -= reduce;
                break;
            case PARRY:
                damage *= 0.6;
                break;
            default:
                break;
        }

        if (status.isCritical()) {
            if (effector instanceof Player) {
                final WeaponType weaponType = ((Player) effector).getEquipment().getMainHandWeaponType();
                damage = (int) calculateWeaponCritical(effected, damage, weaponType, critAddDmg, StatEnum.PHYSICAL_CRITICAL_DAMAGE_REDUCE);
                log.warn("calculateSkillResult Damage " + damage);
                if ((((Player) effector).getPlayerClass()) == PlayerClass.GLADIATOR && ((Player) effector).isPvpTarget(effected)){
                    damage = (int)(damage * 0.10f);
                }
                log.warn("After Reduc Glad Damage " + damage);
                // Proc Stumble/Stagger on Crit calculation
                applyEffectOnCritical((Player) effector, effected);
            } else {
                damage = (int) calculateWeaponCritical(effected, damage, null, critAddDmg, StatEnum.PHYSICAL_CRITICAL_DAMAGE_REDUCE);
            }
        }

        if (effected instanceof Npc) {
            damage = effected.getAi2().modifyDamage(damage);
        }
        if (effector instanceof Npc) {
            damage = effector.getAi2().modifyOwnerDamage(damage);
        }

        if (shared && !effect.getSkill().getEffectedList().isEmpty()) {
            damage /= effect.getSkill().getEffectedList().size();
        }

        if (damage < 0) {
            damage = 0;
        }
        log.warn("End Damage " + damage);
        calculateEffectResult(effect, effected, damage, status, HitType.PHHIT, ignoreShield);
    }

    /**
     * @param effect
     * @param effected
     * @param damage
     * @param status
     * @param hitType
     */
    private static void calculateEffectResult(final Effect effect, final Creature effected, final int damage, final AttackStatus status, final HitType hitType, final boolean ignoreShield) {
        final AttackResult attackResult = new AttackResult(damage, status, hitType);
        if (!ignoreShield) {
            effected.getObserveController().checkShieldStatus(Collections.singletonList(attackResult), effect, effect.getEffector());
        }
        effect.setReserved1(attackResult.getDamage());
        effect.setAttackStatus(attackResult.getAttackStatus());
        effect.setLaunchSubEffect(attackResult.isLaunchSubEffect());
        effect.setReflectedDamage(attackResult.getReflectedDamage());
        effect.setReflectedSkillId(attackResult.getReflectedSkillId());
        effect.setMpShield(attackResult.getShieldMp());
        effect.setProtectedDamage(attackResult.getProtectedDamage());
        effect.setProtectedSkillId(attackResult.getProtectedSkillId());
        effect.setProtectorId(attackResult.getProtectorId());
        effect.setShieldDefense(attackResult.getShieldType());
    }

    public static List<AttackResult> calculateMagicalAttackResult(final Creature attacker, final Creature attacked, final SkillElement elem) {
        /*
         int damage = StatFunctions.calculateAttackDamage(attacker, attacked, true, elem);

         AttackStatus status = calculateMagicalStatus(attacker, attacked, 100, false);
         List<AttackResult> attackList = new ArrayList<AttackResult>();
         switch (status) {
         case RESIST:
         damage = 0;
         break;
         case CRITICAL:
         if (attacker instanceof Player)
         damage = (int) calculateWeaponCritical(attacked, damage, ((Player) attacker).getEquipment().getMainHandWeaponType(), StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
         else
         damage = (int) calculateWeaponCritical(attacked, damage, null, StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
         break;
         }
         attackList.add(new AttackResult(damage, status));
         attacked.getObserveController().checkShieldStatus(attackList, attacker);
         return attackList;
         */
        final List<AttackResult> attackList = new ArrayList<AttackResult>();

        int damage = StatFunctions.calculateAttackDamage(attacker, attacked, true, elem);

        // calculate status
        final AttackStatus status = calculateMagicalStatus(attacker, attacked, 100, false);

        if (status == AttackStatus.CRITICAL) {
            damage = (int) calculateWeaponCritical(attacked, damage, ((Player) attacker).getEquipment().getMainHandWeaponType(), StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
        }

        // adjusting baseDamages according to attacker and target level
        damage = (int) StatFunctions.adjustDamages(attacker, attacked, damage, 1, false);

        if (damage <= 0) {
            damage = 1;
        }

        switch (status) {
            case RESIST:
            case CRITICAL_RESIST:
                damage = 0;
                break;
        }

        attackList.add(new AttackResult(damage, status));

        // calculate offhand damage
        if (attacker instanceof Player
                && ((Player) attacker).getEquipment().getOffHandWeaponType() != null) {
            int offHandDamage = StatFunctions.calculateAttackDamage(attacker, attacked, false, elem);

            final AttackStatus offHandStatus = calculateMagicalStatus(attacker, attacked, 100, false);
            if (offHandStatus == AttackStatus.CRITICAL) {
                offHandDamage = (int) calculateWeaponCritical(attacked, damage, ((Player) attacker).getEquipment().getMainHandWeaponType(), StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
            }

            offHandDamage = (int) StatFunctions.adjustDamages(attacker, attacked, damage, 0, false);

            if (offHandDamage <= 0) {
                offHandDamage = 1;
            }

            switch (offHandStatus) {
                case RESIST:
                case CRITICAL_RESIST:
                    offHandDamage = 0;
                    break;
            }

            attackList.add(new AttackResult(offHandDamage, status));
        }

        // check for shield
        attacked.getObserveController().checkShieldStatus(attackList, null, attacker);

        return attackList;
    }

    public static List<AttackResult> calculateHomingAttackResult(final Creature attacker, final Creature attacked, final SkillElement elem) {
        int damage = StatFunctions.calculateAttackDamage(attacker, attacked, true, elem);

        final AttackStatus status = calculateHomingAttackStatus(attacker, attacked);
        final List<AttackResult> attackList = new ArrayList<AttackResult>();
        switch (status) {
            case RESIST:
            case DODGE:
                damage = 0;
                break;
            case PARRY:
                damage *= 0.6;
                break;
            case BLOCK:
                damage /= 2;
                break;
        }
        attackList.add(new AttackResult(damage, status));
        attacked.getObserveController().checkShieldStatus(attackList, null, attacker);
        return attackList;
    }

    /**
     * @param effect
     * @param skillDamage
     * @param element
     * @param position
     * @param useMagicBoost
     * @param criticalProb
     * @param critAddDmg
     * @return
     */
    public static int calculateMagicalOverTimeSkillResult(final Effect effect, final int skillDamage, final SkillElement element, final int position, final boolean useMagicBoost, final int criticalProb, final int critAddDmg) {
        final Creature effector = effect.getEffector();
        final Creature effected = effect.getEffected();

        //TODO is damage multiplier used on dot?
        final float damageMultiplier = effector.getObserveController().getBaseMagicalDamageMultiplier();

        int damage = Math.round(StatFunctions.calculateMagicalSkillDamage(effect.getEffector(), effect.getEffected(), skillDamage,0, element, useMagicBoost, false, false, effect.getSkillTemplate().getPvpDamage()) * damageMultiplier);

        AttackStatus status = effect.getAttackStatus();
        // calculate attack status only if it has not been forced already
        if (status == AttackStatus.NORMALHIT && position == 1) {
            status = calculateMagicalStatus(effector, effected, criticalProb, true);
        }
        switch (status) {
            case CRITICAL:
                if (effector instanceof Player) {
                    final WeaponType weaponType = ((Player) effector).getEquipment().getMainHandWeaponType();
                    damage = (int) calculateWeaponCritical(effected, damage, weaponType, critAddDmg, StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
                } else {
                    damage = (int) calculateWeaponCritical(effected, damage, null, critAddDmg, StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
                }
                break;
            default:
                break;
        }

        if (damage <= 0) {
            damage = 1;
        }

        if (effected instanceof Npc) {
            damage = effected.getAi2().modifyDamage(damage);
        }

        return damage;
    }

    /**
     * @param effect
     * @param skillDamage
     * @param element
     * @param isNoReduceSpell
     */
    public static void calculateMagicalSkillResult(final Effect effect, final int skillDamage, final ActionModifier modifier, final SkillElement element) {
        calculateMagicalSkillResult(effect, skillDamage, modifier, element, true,
                true, false, Func.ADD, 100, 0, false, false);
    }

    public static void calculateMagicalSkillResult(final Effect effect, final int skillDamage, final ActionModifier modifier, final SkillElement element, final boolean useMagicBoost, final boolean useKnowledge, final boolean noReduce, final Func func, final int criticalProb, final int critAddDmg, final boolean shared, final boolean ignoreShield) {
        final Creature effector = effect.getEffector();
        final Creature effected = effect.getEffected();

        final float damageMultiplier = effector.getObserveController().getBaseMagicalDamageMultiplier();
        final int baseAttack = effector.getGameStats().getMainHandPAttack().getBase(); //Npc spells scale with this
        int damages = 0;
        int bonus = 0;

        if (func.equals(Func.PERCENT) && effector instanceof Npc) {
            damages = Math.round(baseAttack * skillDamage / 100f);
        } else {
            damages = skillDamage;
        }

        //add bonus damage
        if (modifier != null) {
            bonus = modifier.analyze(effect);
            switch (modifier.getFunc()) {
                case ADD:
                    break;
                case PERCENT:
                    if (effector instanceof Npc) {
                        bonus = Math.round(baseAttack * bonus / 100f);
                    }
                    break;
            }
        }

        int damage = Math.round(StatFunctions.calculateMagicalSkillDamage(effect.getEffector(), effect.getEffected(), damages,
                bonus, element, useMagicBoost, useKnowledge, noReduce, effect.getSkillTemplate().getPvpDamage()) * damageMultiplier);

        final AttackStatus status = calculateMagicalStatus(effector, effected, criticalProb, true);
        switch (status) {
            case CRITICAL:
                if (effector instanceof Player) {
                    final WeaponType weaponType = ((Player) effector).getEquipment().getMainHandWeaponType();
                    damage = (int) calculateWeaponCritical(effected, damage, weaponType, critAddDmg, StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
                } else {
                    damage = (int) calculateWeaponCritical(effected, damage, null, critAddDmg, StatEnum.MAGICAL_CRITICAL_DAMAGE_REDUCE);
                }
                break;
            default:
                break;
        }
        if (shared && !effect.getSkill().getEffectedList().isEmpty()) {
            damage /= effect.getSkill().getEffectedList().size();
        }

        calculateEffectResult(effect, effected, damage, status, HitType.MAHIT, ignoreShield);
    }

    /**
     * Manage attack status rate
     *
     * @return AttackStatus
     * @source http://www.aionsource.com/forum/mechanic-analysis/42597-character-stats-xp-dp-origin-gerbator-team-july-2009
     * -a.html
     */
    public static AttackStatus calculatePhysicalStatus(final Creature attacker, final Creature attacked, final boolean isMainHand) {
        return calculatePhysicalStatus(attacker, attacked, isMainHand, 0, 100, false, false);
    }

    public static AttackStatus calculatePhysicalStatus(final Creature attacker, final Creature attacked, final boolean isMainHand, final int accMod, final int criticalProb, final boolean isSkill, final boolean cannotMiss) {
        AttackStatus status = AttackStatus.NORMALHIT;
        if (!isMainHand) {
            status = AttackStatus.OFFHAND_NORMALHIT;
        }

        if (!cannotMiss) {
            if (attacked instanceof Player && ((Player) attacked).getEquipment().isShieldEquipped() && StatFunctions.calculatePhysicalBlockRate(attacker, attacked))//TODO accMod
            {
                status = AttackStatus.BLOCK;
            } // Parry can only be done with weapon, also weapon can have humanoid mobs,
            // but for now there isnt implementation of monster category
            else if (attacked instanceof Player && ((Player) attacked).getEquipment().getMainHandWeaponType() != null
                    && StatFunctions.calculatePhysicalParryRate(attacker, attacked))//TODO accMod
            {
                status = AttackStatus.PARRY;
            } else if (!isSkill && StatFunctions.calculatePhysicalDodgeRate(attacker, attacked, accMod)) {
                status = AttackStatus.DODGE;
            }
        } else {
            /**
             * Check AlwaysDodge Check AlwaysParry Check AlwaysBlock
             */
            StatFunctions.calculatePhysicalDodgeRate(attacker, attacked, accMod);
            StatFunctions.calculatePhysicalParryRate(attacker, attacked);
            StatFunctions.calculatePhysicalBlockRate(attacker, attacked);
        }

        if (StatFunctions.calculatePhysicalCriticalRate(attacker, attacked, isMainHand, criticalProb, isSkill)) {
            switch (status) {
                case BLOCK:
                    if (isMainHand) {
                        status = AttackStatus.CRITICAL_BLOCK;
                    } else {
                        status = AttackStatus.OFFHAND_CRITICAL_BLOCK;
                    }
                    break;
                case PARRY:
                    if (isMainHand) {
                        status = AttackStatus.CRITICAL_PARRY;
                    } else {
                        status = AttackStatus.OFFHAND_CRITICAL_PARRY;
                    }
                    break;
                case DODGE:
                    if (isMainHand) {
                        status = AttackStatus.CRITICAL_DODGE;
                    } else {
                        status = AttackStatus.OFFHAND_CRITICAL_DODGE;
                    }
                    break;
                default:
                    if (isMainHand) {
                        status = AttackStatus.CRITICAL;
                    } else {
                        status = AttackStatus.OFFHAND_CRITICAL;
                    }
                    break;
            }
        }

        return status;
    }

    /**
     * Every + 100 delta of (MR - MA) = + 10% to resist<br>
     * if the difference is 1000 = 100% resist
     */
    public static AttackStatus calculateMagicalStatus(final Creature attacker, final Creature attacked, final int criticalProb, final boolean isSkill) {
        if (!isSkill) {
            if (Rnd.get(0, 1000) < StatFunctions.calculateMagicalResistRate(attacker, attacked, 0)) {
                return AttackStatus.RESIST;
            }
        }

        if (StatFunctions.calculateMagicalCriticalRate(attacker, attacked, criticalProb)) {
            return AttackStatus.CRITICAL;
        }

        return AttackStatus.NORMALHIT;
    }

    private static AttackStatus calculateHomingAttackStatus(final Creature attacker, final Creature attacked) {
        if (Rnd.get(0, 1000) < StatFunctions.calculateMagicalResistRate(attacker, attacked, 0)) {
            return AttackStatus.RESIST;
        } else {
            return AttackStatus.NORMALHIT;
        }

    }

    public static void cancelCastOn(final Creature target) {
        target.getKnownList().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(final Player observer) {
                if (observer.getTarget() == target) {
                    cancelCast(observer, target);
                }
            }
        });

        target.getKnownList().doOnAllNpcs(new Visitor<Npc>() {
            @Override
            public void visit(final Npc observer) {
                if (observer.getTarget() == target) {
                    cancelCast(observer, target);
                }
            }
        });

    }

    private static void cancelCast(final Creature creature, final Creature target) {
        if (target != null && creature.getCastingSkill() != null) {
            if (creature.getCastingSkill().getFirstTarget().equals(target)) {
                creature.getController().cancelCurrentSkill();
            }
        }
    }

    /**
     * Send a packet to everyone who is targeting creature.
     *
     * @param object
     */
    public static void removeTargetFrom(final Creature object) {
        removeTargetFrom(object, false);
    }

    public static void removeTargetFrom(final Creature object, final boolean validateSee) {
        object.getKnownList().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(final Player observer) {
                if (validateSee && observer.getTarget() == object) {
                    if (!observer.canSee(object)) {
                        observer.setTarget(null);
                        // retail packet (//fsc 0x44 dhdd 0 0 0 0) right after SM_PLAYER_STATE
                        PacketSendUtility.sendPacket(observer, new SM_TARGET_SELECTED(observer));
                    }
                } else if (observer.getTarget() == object) {
                    observer.setTarget(null);
                    // retail packet (//fsc 0x44 dhdd 0 0 0 0) right after SM_PLAYER_STATE
                    PacketSendUtility.sendPacket(observer, new SM_TARGET_SELECTED(observer));
                }
            }
        });
    }

    public static void applyEffectOnCritical(final Player attacker, final Creature attacked) {
        int skillId = 0;
        final WeaponType mainHandWeaponType = attacker.getEquipment().getMainHandWeaponType();
        if (mainHandWeaponType != null) {
            switch (mainHandWeaponType) {
                case POLEARM_2H:
                case CANNON_2H:
                case STAFF_2H:
                case SWORD_2H:
                case KEYBLADE_2H:
                case KEYHAMMER_2H:
                    skillId = 8218;
                    break;
                case BOW:
                    skillId = 8217;
                    break;
            }
        }

        if (skillId == 0) {
            return;
        }
        // On retail this effect apply on each crit with 10% of base chance
        // plus bonus effect penetration calculated above
        if (Rnd.get(100) > (6 * attacked.getCriticalEffectMulti())) {
            return;
        }

        final SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
        if (template == null) {
            return;
        }
        final Effect e = new Effect(attacker, attacked, template, template.getLvl(), 0);
        e.initialize();
        e.applyEffect();
    }
}
