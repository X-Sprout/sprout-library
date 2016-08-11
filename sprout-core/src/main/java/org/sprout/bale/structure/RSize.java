/**
 * Created on 2016/8/11
 */
package org.sprout.bale.structure;

/**
 * 尺寸大小
 * <p>
 *
 * @author Wythe
 */
public final class RSize {

    // 宽度大小
    private final double wide;
    // 高度大小
    private final double high;

    public RSize(final int wide, final int high) {
        this(Integer.valueOf(wide).doubleValue(), Integer.valueOf(high).doubleValue());
    }

    public RSize(final long wide, final long high) {
        this(Long.valueOf(wide).doubleValue(), Long.valueOf(high).doubleValue());
    }

    public RSize(final float wide, final float high) {
        this(Float.valueOf(wide).doubleValue(), Float.valueOf(high).doubleValue());
    }

    public RSize(final double wide, final double high) {
        this.wide = wide;
        this.high = high;
    }

    public double getHigh() {
        return high;
    }

    public double getWide() {
        return wide;
    }

    public boolean equals(RSize size) {
        if (size == null) {
            return false;
        }
        if (this == size) {
            return true;
        }
        if (this.getClass() != size.getClass()) {
            return false;
        }
        return this.wide == size.wide && this.high == size.high;
    }

}
