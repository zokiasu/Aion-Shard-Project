package com.aionemu.gameserver.model.templates.zone;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Points")
public class Points {

    @XmlElement(required = true)
    protected List<Point2D> point;
    @XmlAttribute(name = "top")
    protected float top;
    @XmlAttribute(name = "bottom")
    protected float bottom;

    public Points() {
    }

    public Points(float bottom, float top) {
        this.bottom = bottom;
        this.top = top;
    }

    public List<Point2D> getPoint() {
        if (point == null) {
            point = new ArrayList<Point2D>();
        }
        return this.point;
    }

    /**
     * @return the top
     */
    public float getTop() {
        return top;
    }

    /**
     * @return the bottom
     */
    public float getBottom() {
        return bottom;
    }
}
