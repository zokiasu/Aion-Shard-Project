package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("armsfusion")
public class ArmsfusionAI2 extends NpcAI2 {

    @Override
    public void think() {
        ThinkEventHandler.onThink(this);
    }

    @Override
    protected void handleDied() {
        DiedEventHandler.onDie(this);
    }

    @Override
    protected void handleAttack(Creature creature) {
        AttackEventHandler.onAttack(this, creature);
    }

    @Override
    protected boolean handleCreatureNeedsSupport(Creature creature) {
        return AggroEventHandler.onCreatureNeedsSupport(this, creature);
    }

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendBrightYellowMessageOnCenter(player, "The price of the fusion has been set at 0. Do not rely on the price displayed.");
        TalkEventHandler.onTalk(this, player);
    }

    @Override
    protected void handleDialogFinish(Player creature) {
        TalkEventHandler.onFinishTalk(this, creature);
    }

    @Override
    protected void handleFinishAttack() {
        AttackEventHandler.onFinishAttack(this);
    }

    @Override
    protected void handleAttackComplete() {
        AttackEventHandler.onAttackComplete(this);
    }

    @Override
    protected void handleTargetReached() {
        TargetEventHandler.onTargetReached(this);
    }

    @Override
    protected void handleNotAtHome() {
        ReturningEventHandler.onNotAtHome(this);
    }

    @Override
    protected void handleBackHome() {
        ReturningEventHandler.onBackHome(this);
    }

    @Override
    protected void handleTargetTooFar() {
        TargetEventHandler.onTargetTooFar(this);
    }

    @Override
    protected void handleTargetGiveup() {
        TargetEventHandler.onTargetGiveup(this);
    }

    @Override
    protected void handleTargetChanged(Creature creature) {
        super.handleTargetChanged(creature);
        TargetEventHandler.onTargetChange(this, creature);
    }

    @Override
    protected void handleMoveValidate() {
        MoveEventHandler.onMoveValidate(this);
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        MoveEventHandler.onMoveArrived(this);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        CreatureEventHandler.onCreatureMoved(this, creature);
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
    }

    @Override
    protected boolean canHandleEvent(AIEventType eventType) {
        boolean canHandle = super.canHandleEvent(eventType);

        switch (eventType) {
            case CREATURE_MOVED:
                return canHandle
                        || DataManager.NPC_SHOUT_DATA.hasAnyShout(getOwner().getWorldId(), getOwner().getNpcId(), ShoutEventType.SEE);
            case CREATURE_NEEDS_SUPPORT:
                return canHandle
                        && isNonFightingState()
                        && DataManager.TRIBE_RELATIONS_DATA.hasSupportRelations(getOwner().getTribe());
        }
        return canHandle;
    }

    @Override
    public AttackIntention chooseAttackIntention() {
        VisibleObject currentTarget = getTarget();
        Creature mostHated = getAggroList().getMostHated();

        if (mostHated == null || mostHated.getLifeStats().isAlreadyDead()) {
            return AttackIntention.FINISH_ATTACK;
        }

        if (currentTarget == null || !currentTarget.getObjectId().equals(mostHated.getObjectId())) {
            onCreatureEvent(AIEventType.TARGET_CHANGED, mostHated);
            return AttackIntention.SWITCH_TARGET;
        }

        NpcSkillEntry skill = SkillAttackManager.chooseNextSkill(this);
        if (skill != null) {
            skillId = skill.getSkillId();
            skillLevel = skill.getSkillLevel();
            return AttackIntention.SKILL_ATTACK;
        }
        return AttackIntention.SIMPLE_ATTACK;
    }
}
