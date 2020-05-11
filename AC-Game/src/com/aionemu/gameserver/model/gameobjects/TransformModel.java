package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.model.TribeClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.TransformType;

/**
 * @author Rolandas
 */
public class TransformModel {

    private int modelId;
    private int originalModelId;
    private TransformType originalType;
    private TransformType transformType;
    private int panelId;
    private boolean isActive = false;
    private TribeClass transformTribe;
    private TribeClass overrideTribe;

    public TransformModel(Creature creature) {
        if (creature instanceof Player) {
            this.originalType = TransformType.PC;
        } else {
            this.originalType = TransformType.NONE;
        }
        this.originalModelId = creature.getObjectTemplate().getTemplateId();
        this.transformType = TransformType.NONE;
    }

    /**
     * @return the modelId
     */
    public int getModelId() {
        if (isActive && modelId > 0) {
            return modelId;
        }
        return originalModelId;
    }

    public void setModelId(int modelId) {
        if (modelId == 0 || modelId == originalModelId) {
            modelId = originalModelId;
            isActive = false;
        } else {
            this.modelId = modelId;
            isActive = true;
        }
    }

    /**
     * @return the type
     */
    public TransformType getType() {
        if (isActive) {
            return transformType;
        }
        return originalType;
    }

    public void setTransformType(TransformType transformType) {
        this.transformType = transformType;
    }

    /**
     * @return the panelId
     */
    public int getPanelId() {
        if (isActive) {
            return panelId;
        }
        return 0;
    }

    public void setPanelId(int id) {
        this.panelId = id;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the transformTribe
     */
    public TribeClass getTribe() {
        if (isActive && transformTribe != null) {
            return transformTribe;
        }
        return overrideTribe;
    }

    /**
     * @param transformTribe the transformTribe to set
     */
    public void setTribe(TribeClass transformTribe, boolean override) {
        if (override) {
            this.overrideTribe = transformTribe;
        } else {
            this.transformTribe = transformTribe;
        }
    }
}
