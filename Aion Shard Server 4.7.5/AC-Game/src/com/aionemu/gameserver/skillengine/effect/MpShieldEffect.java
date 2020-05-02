package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.observer.AttackCalcObserver;
import com.aionemu.gameserver.controllers.observer.AttackShieldObserver;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpShieldEffect")
public class MpShieldEffect extends EffectTemplate {

    @XmlAttribute
    protected int hitdelta;
    @XmlAttribute
    protected int hitvalue;
    @XmlAttribute
    protected boolean percent;
    @XmlAttribute
    protected int radius = 0;
    @XmlAttribute
    protected int minradius = 0;
    @XmlAttribute
    protected Race condrace = null;

    public void applyEffect(Effect effect)
    {
      if ((this.condrace != null) && (effect.getEffected().getRace() != this.condrace)) {
        return;
      }
      effect.addToEffectedController();
    }
    
    public void calculate(Effect effect)
    {
      effect.addSucessEffect(this);
    }
    
    public void startEffect(Effect effect)
    {
      int skillLvl = effect.getSkillLevel();
      int valueWithDelta = this.value + this.delta * skillLvl;
      int hitValueWithDelta = this.hitvalue + this.hitdelta * skillLvl;
      
      AttackShieldObserver asObserver = new AttackShieldObserver(hitValueWithDelta, valueWithDelta, this.percent, effect, this.hitType, getType(), this.hitTypeProb);
      

      effect.getEffected().getObserveController().addAttackCalcObserver(asObserver);
      effect.setAttackShieldObserver(asObserver, this.position);
      effect.getEffected().getEffectController().setUnderShield(true);
    }
    
    public void endEffect(Effect effect)
    {
      AttackCalcObserver acObserver = effect.getAttackShieldObserver(this.position);
      if (acObserver != null) {
        effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
      }
      effect.getEffected().getEffectController().setUnderShield(false);
    }
    
    public int getType()
    {
      return 16;
    }
}