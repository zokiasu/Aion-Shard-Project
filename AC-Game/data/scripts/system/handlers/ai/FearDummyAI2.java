package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.geoEngine.collision.CollisionIntention;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PositionUtil;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 *
 * @author Steve
 * @AE implement Wasacacax
 */
@AIName("fear_dummy")
public class FearDummyAI2 extends GeneralNpcAI2 {

	@Override
	public int modifyDamage(int damage) {
		return 1;
	}

	@Override
	protected void handleAttack(Creature creature) {

		float x = getOwner().getX();
		float y = getOwner().getY();
		if (!MathUtil.isNearCoordinates(getOwner(), creature, 40)) {
			return;
		}
		byte moveAwayHeading = PositionUtil.getMoveAwayHeading(creature, getOwner());
		double radian = Math.toRadians(MathUtil.convertHeadingToDegree(moveAwayHeading));
		float maxDistance = getOwner().getGameStats().getMovementSpeedFloat();
		float x1 = (float) (Math.cos(radian) * maxDistance);
		float y1 = (float) (Math.sin(radian) * maxDistance);
		byte intentions = (byte) (CollisionIntention.PHYSICAL.getId() | CollisionIntention.DOOR.getId());
		Vector3f closestCollision = GeoService.getInstance().getClosestCollision(getOwner(), x + x1, y + y1, getOwner().getZ(), true, intentions);

		closestCollision.setZ(getOwner().getZ());
		getOwner().getMoveController().resetMove();
		getOwner().getMoveController().moveToPoint(closestCollision.getX(), closestCollision.getY(), closestCollision.getZ());
	}

}
