package instance.pvparenas;

import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.instance.playerreward.HarmonyGroupReward;

/**
 * @author xTz
 */
@InstanceID(300450000)
public class ArenaOfHarmonyInstance extends HarmonyTrainingCenterInstance {

    @Override
    protected void reward() {
        float totalScoreAP = (9.599f * 3) * 100;
        float totalScoreCourage = (0.1f * 3) * 100;
        int totalPoints = instanceReward.getTotalPoints();
        for (HarmonyGroupReward group : instanceReward.getGroups()) {
            int score = group.getPoints();
            int rank = instanceReward.getRank(score);
            float percent = group.getParticipation();
            float scoreRate = ((float) score / (float) totalPoints);
            int basicAP = 200;
            int rankingAP = 0;
            basicAP *= percent;
            int basicCoI = 0;
            int rankingCoI = 0;
            basicCoI *= percent;
            int scoreAP = (int) (totalScoreAP * scoreRate);
            switch (rank) {
                case 0:
                    rankingAP = 4681;
                    rankingCoI = 49;
                    group.setGloryTicket(1);
                    group.setMithrilMedal(2);
                    break;
                case 1:
                    rankingAP = 1887;
                    rankingCoI = 20;
                    group.setplatinumMedal(4);
                    break;
                case 2:
                    rankingAP = 151;
                    rankingCoI = 1;
                    group.setplatinumMedal(3);
                    break;
            }
            rankingAP *= percent;
            rankingCoI *= percent;
            int scoreCoI = (int) (totalScoreCourage * scoreRate);
            group.setBasicAP(basicAP);
            group.setRankingAP(rankingAP);
            group.setScoreAP(scoreAP);
            group.setBasicCourage(basicCoI);
            group.setRankingCourage(rankingCoI);
            group.setScoreCourage(scoreCoI);
        }
        super.reward();
    }
}
