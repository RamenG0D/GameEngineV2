/*
 * Copyright (c) 2014. Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */

package com.Math3D.geom;

import java.io.Serializable;


/**
 * Rect is a utility class based by the Pygame Rect module.
 * <p>
 * Rect objects are immutable. While the width and height parameters of a Rect
 * may be given negative parameters, most operations are undefined in such
 * cases. The <code>isValid()</code> test will return true iff both dimensions
 * are non-negative, which means that all Rect operations are valid.
 * <p>
 * Both the horizontal and vertical ranges of a rectangle are computed as
 * half-open sets. That is, the left and top edges are on the rectangle
 * interior, whereas the right and bottom edges are on the rectangle exterior.
 * 
 * @author Philip DeCamp
 */
@Deprecated public class LongRect implements Cloneable, Serializable {

    private static final long serialVersionUID = 4315162825058796608L;


    /**
     * Create a new Rect by specifying left and top edges and width and height
     * dimensions.
     * 
     * @param minX   The left edge of the rectangle.
     * @param minY   The top edge of the rectangle.
     * @param spanX  The width of the rectangle.
     * @param spanY  The height of the rectangle.
     * @return a new Rect object with the specified edges and dimensions.
     */
    public static LongRect fromBounds(long minX, long minY, long spanX, long spanY) {
        return new LongRect(minX, minY, minX + spanX, minY + spanY);
    }

    /**
     * Create a new Rect by specifying location of all four edges.
     * 
     * @param minX   The left edge of the rectangle.
     * @param minY   The top edge of the rectangle.
     * @param maxX   The right edge of the rectangle.
     * @param maxY   The bottom edge of the rectangle.
     * @return a new Rect object with the specified edges.
     */
    public static LongRect fromEdges(long minX, long minY, long maxX, long maxY) {
        return new LongRect(minX, minY, maxX, maxY);
    }

    /**
     * Creates a new Rect by specifying center and size.
     * 
     * @param centerX
     * @param centerY
     * @param spanX
     * @param spanY
     * @return a new Rect
     */
    public static LongRect fromCenter(long centerX, long centerY, long spanX, long spanY) {
        centerX -= spanX / 2;
        centerY -= spanY / 2;

        return new LongRect(centerX, centerY, centerX + spanX, centerY + spanY);
    }

    
    private final long mMinX;
    private final long mMinY;
    private final long mMaxX;
    private final long mMaxY;

    
    private LongRect(long minX, long minY, long maxX, long maxY) {
        if(minX <= maxX) {
            mMinX = minX;
            mMaxX = maxX;
        }else{
            mMinX = maxX;
            mMaxX = minX;
        }
        
        if(minY <= maxY) {
            mMinY = minY;
            mMaxY = maxY;
        }else{
            mMinY = maxY;
            mMaxY = minY;
        }
    }

    
    /****** POSITION ******/

    /**
     * @return left edge of this rectangle.
     */
    public long minX() {
        return mMinX;
    }

    /**
     * @return the top edge of this rectangle.
     */
    public long minY() {
        return mMinY;
    }

    /**
     * @return the right edge of this rectangle.
     */
    public long maxX() {
        return mMaxX;
    }

    /**
     * @return the bottom edge of this rectangle.
     */
    public long maxY() {
        return mMaxY;
    }

    /**
     * @return the center polong between the left and right edges.
     */
    public long centerX() {
        return (mMinX + mMaxX) / 2;
    }

    /**
     * @return the center polong between the bottom and top edges.
     */
    public long centerY() {
        return (mMinY + mMaxY) / 2;
    }

    
    /****** SIZE ******/

    /**
     * @return the width of this rectangle. May be negative.
     */
    public long spanX() {
        return mMaxX - mMinX;
    }

    /**
     * @return the height of this rectangle. May be negative.
     */
    public long spanY() {
        return mMaxY - mMinY;
    }

    /**
     * @return the absolute area of this rectangle.
     */
    public long area() {
        return Math.abs((mMaxX - mMinX) * (mMaxY - mMinY));
    }

    
    /****** TRANSFORMATIONS ******/

    /**
     * Creates a new Rect that has the size of <code>this</code> Rect, but is
     * moved completely inside the argument Rect. If the rectangle is too large
     * to fit inside, it is centered inside the argument Rect, but its size is
     * not changed.
     * 
     * @param rect
     *            - Rect in which to fit <code>this</code> rectangle.
     * @return a new Rect.
     */
    public LongRect clamp(LongRect rect) {
        long left, right, top, bottom;

        if (mMinX < rect.mMinX) {
            left = rect.mMinX;
            right = left + mMaxX - mMinX;

            if (right > rect.mMaxX) {
                left = (rect.mMinX + rect.mMaxX + mMinX - mMaxX) / 2;
                right = left + mMaxX - mMinX;
            }

        } else if (mMaxX > rect.mMaxX) {
            right = rect.mMaxX;
            left = right + mMinX - mMaxX;

            if (left < rect.mMinX) {
                left = (rect.mMinX + rect.mMaxX + mMinX - mMaxX) / 2;
                right = left + mMaxX - mMinX;
            }

        } else {
            left = mMinX;
            right = mMaxX;
        }

        if (mMinY < rect.mMinY) {
            top = rect.mMinY;
            bottom = top + mMaxY - mMinY;

            if (bottom > rect.mMaxY) {
                top = (rect.mMinY + rect.mMaxY + mMinY - mMaxY) / 2;
                bottom = top + mMaxY - mMinY;
            }

        } else if (mMaxY > rect.mMaxY) {
            bottom = rect.mMaxY;
            top = bottom + mMinY - mMaxY;

            if (top < rect.mMinY) {
                top = (rect.mMinY + rect.mMaxY + mMinY - mMaxY) / 2;
                bottom = top + mMaxY - mMinY;
            }

        } else {
            top = mMinY;
            bottom = mMaxY;
        }

        return new LongRect(left, top, right, bottom);
    }

    /**
     * @return the intersection between <code>this</code> Rect and the
     *          Parameter Rect. If the two Rects do not overlap, a Rect with 0
     *          size is returned.
     */
    public LongRect clip(LongRect rect) {
        return new LongRect(Math.max(mMinX, rect.mMinX), Math.max(mMinY,
                rect.mMinY), Math.min(mMaxX, rect.mMaxX), Math.min(mMaxY,
                rect.mMaxY));
    }

    /**
     * Centers this Rect inside another Rect and scales the size until the two
     * rectangles share borders, but does not affect the aspect ratio.
     * 
     * @param rect  Rectangle into which to fit this Rect.
     * @return new Rect object that fits inside bounds.
     */
    public LongRect fit(LongRect rect) {
        if(spanX() * rect.spanY() > rect.spanX() * spanY()) {
            long height = (long)((double)(spanY() * rect.spanX()) / spanX() + 0.5);
            long margin = (rect.spanY() - height) / 2;

            return new LongRect(rect.minX(), rect.minY() + margin, rect.maxX(), rect.minY() + margin + height);
            
        }else{
            long width = (long)((double)(spanX() * rect.spanY()) / spanY() + 0.5);
            long margin = (rect.spanX() - width) / 2;
            
            return new LongRect(rect.minX() + margin, rect.minY(), rect.minX() + margin + width, rect.maxY());
        }
    }

    /**
     * Scales the size of the Rect without changing the center point.
     * 
     * @param scaleX  Amount to scale width.
     * @param scaleY  Amount to scale height.
     * @return new Rect with scaled width and height.
     */
    public LongRect inflate(double scaleX, double scaleY) {
        return LongRect.fromCenter( centerX(), 
                                    centerY(),
                                    (long)(spanX() * scaleX + 0.5),
                                    (long)(spanY() * scaleY + 0.5));
    }

    /**
     * @return the pointUnion between <code>this</code> Rect and the parameter Rect.
     *          The pointUnion may contain area not covered by either input Rect.
     */
    public LongRect union(LongRect r) {
        return new LongRect(Math.min(mMinX, r.mMinX), Math.min(mMinY, r.mMinY),
                Math.max(mMaxX, r.mMaxX), Math.max(mMaxY, r.mMaxY));
    }

    /**
     * This will flip the width or height of a rectangle if it has a negative
     * size. The rectangle will remain in the same place, with only the sides
     * swapped.
     * 
     * @return a new Rect.
     */
    public LongRect normalize() {
        if (mMinX < mMaxX) {
            if (mMinY < mMaxY) {
                return this;
            } else {
                return new LongRect(mMinX, mMaxY, mMaxX, mMinY);
            }
        } else {
            if (mMinY < mMaxY) {
                return new LongRect(mMaxX, mMinY, mMinX, mMaxY);
            } else {
                return new LongRect(mMaxX, mMaxY, mMinX, mMinY);
            }
        }
    }

    /**
     * Multiplies location and size.
     * 
     * @param multX
     *            - Amount to multiply the width and left edge.
     * @param multY
     *            - Amount to multiply the height and top edge.
     * @return new Rect object.
     */
    public LongRect scale(long multX, long multY) {
        return new LongRect(mMinX * multX, mMinY * multY, mMaxX * multX, mMaxY
                * multY);
    }

    /**
     * Moves the Rect.
     * 
     * @param dx
     *            - Amount to move the rectangle horizantally.
     * @param dy
     *            - Amount to move the rectangle vertically.
     * @return a new rectangle.
     */
    public LongRect translate(long dx, long dy) {
        return new LongRect(mMinX + dx, mMinY + dy, mMaxX + dx, mMaxY + dy);
    }

    /**
     * Centers the rect on the given point.
     * 
     * @return new Rect centered on the given point.
     */
    public LongRect setCenter(long x, long y) {
        x -= (mMaxX - mMinX) / 2;
        y -= (mMaxY - mMinY) / 2;

        return new LongRect(x, y, x + mMaxX - mMinX, y + mMaxY - mMinY);
    }

    
    /****** TESTS ******/

    /**
     * @return true iff both dimensions are non-negative.
     */
    public boolean isValid() {
        return mMaxX >= mMinX && mMaxY >= mMinY;
    }

    /**
     * Tests if rectangle intersects with a given point.
     * 
     * @param x  The x coordinate of a point.
     * @param y  The y coordinate of a point.
     * @return true iff the polong lies within the rectangle.
     */
    public boolean intersects(long x, long y) {
        return x >= mMinX && x < mMaxX && y >= mMinY && y < mMaxY;
    }

    /**
     * Tests if this rectangle has any intersection with other rectangle.
     * 
     * @param rect
     *            - A rect to check for overlap.
     * @return true if rectangles have any intersection.
     */
    public boolean intersects(LongRect rect) {
        return !(mMinX < rect.mMinX && mMaxX < rect.mMinX
                || mMinX >= rect.mMaxX && mMaxX >= rect.mMaxX
                || mMinY < rect.mMinY && mMaxY < rect.mMinY || mMinY >= rect.mMaxY
                && mMaxY >= rect.mMaxY);
    }

    
    public boolean equals(Object o) {
        if(!(o instanceof LongRect))
            return false;

        LongRect r = (LongRect) o;
        return mMinX == r.mMinX && mMinY == r.mMinY && mMaxX == r.mMaxX && mMaxY == r.mMaxY;
    }
    
    
    @Override
    public int hashCode() {
        return (int)(mMinX ^ mMaxX ^ mMinY ^ mMaxY);
    }

    
    /****** CONVERSIONS ******/

    /**
     * @return equivalent Rect object.
     */
    public Rect toRect() {
        return Rect.fromEdges(mMinX, mMinY, mMaxX, mMaxY);
    }

    /**
     * @param scaleX  Amount to scale horizontal dimensions.
     * @param scaleY  Amount to scale vertical dimensions.
     * @return equivalent Rect object after getScale.
     */
    public Rect scaleToFRect(double scaleX, double scaleY) {
        return Rect.fromEdges(mMinX * scaleX, mMinY * scaleY, mMaxX * scaleX, mMaxY * scaleY);
    }

    
    public String toString() {
        StringBuilder b = new StringBuilder("LongRect [");
        b.append(mMinX);
        b.append(", ");
        b.append(mMinY);
        b.append(", ");
        b.append((mMaxX - mMinX));
        b.append(", ");
        b.append((mMaxY - mMinY));
        b.append("]");
        return b.toString();
    }

}
