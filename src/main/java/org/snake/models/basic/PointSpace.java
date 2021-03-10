package org.snake.models.basic;

public class PointSpace {
    private final Range xRange;
    private final Range yRange;

    public PointSpace(Range xRange, Range yRange) {
        this.xRange = xRange;
        this.yRange = yRange;
    }

    public Point around(Point point) {
        if(xRange.contained(point.x) && yRange.contained(point.y)) {
            return point;
        } else {
            return new Point(xRange.around(point.x), yRange.around(point.y));
        }
    }
}
