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
 * Rect objects are immutable.  While the width and height parameters
 * of a Rect may be given negative parameters, most operations are 
 * undefined in such cases.  The <code>isValid()</code> test will
 * return true iff both dimensions are non-negative, which means that
 * all Rect operations are valid.
 * <p>  
 * Both the horizontal and vertical ranges of a rectangle are computed as 
 * half-open sets.  That is, the left and top edges are on the rectangle 
 * interior, whereas the right and bottom edges are on the rectangle exterior.
 * </p>
 * @author Philip DeCamp  
 */
@Deprecated
public class Rect implements Serializable {

    private static final long serialVersionUID = 2023954814050670713L;
    
    
    public static final Rect UNIT = Rect.fromBounds(0, 0, 1, 1);
    
    
    
    /**
     * Create a new Rect by specifying min edges and dimensions.
     * 
     * @param minX    The min X edge of the rectangle.
     * @param minY    The min Y edge of the rectangle.
     * @param width   The width of the rectangle.
     * @param height  The height of the rectangle.
     * @return a newFRect object with the specified edges and dimensions.
     */
    public static Rect fromBounds(double minX, double minY, double width, double height) {
        return new Rect(minX, minY, minX + width, minY + height);
    }
        
    /**
     * Create a new Rect by specifying location of all four edges.
     * 
     * @param minX  The x0 edge of the rectangle.
     * @param minY  The y0 edge of the rectangle.
     * @param maxX  The x1 edge of the rectangle.
     * @param maxY  The y1 edge of the rectangle.
     * @return a new Rect object with the specified edges.
     */
    public static Rect fromEdges(double minX, double minY, double maxX, double maxY) {
        return new Rect(minX, minY, maxX, maxY);
    }
    
    /**
     * Creates a new Rect by specifying center and size.
     * @return new Rect
     */ 
    public static Rect fromCenter(double centerX, double centerY, double width, double height) {
        return new Rect(   centerX - width / 2f, 
                            centerY - height / 2f, 
                            centerX + width / 2f, 
                            centerY + height / 2f);
    }
    
    
    private final double mMinX, mMinY, mMaxX, mMaxY;
    
    
    protected Rect(double minX, double minY, double maxX, double maxY) {
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
     * @return the min x value at the contained edge of this rectangle.
     */
    public double minX() {
        return mMinX;
    }
    
    /**
     * @return the min y value at the contained edge of this rectangle.
     */
    public double minY() {
        return mMinY;
    }

    /**
     * @return the max x value at the uncontained edge of this Rect.
     */
    public double maxX() {
        return mMaxX;
    }
        
    /**
     * @return the max y value at the uncontained edge of this Rect.
     */
    public double maxY() {
        return mMaxY;
    }
    
    /**
     * @return the center point between the left and right edges.
     */
    public double centerX() {
        return (mMinX + mMaxX) * 0.5f;
    }
    
    /**
     * @return the center point between the bottom and top edges.
     */
    public double centerY() {
        return (mMinY + mMaxY) * 0.5f;
    }
    
    
    /****** SIZE ******/
    
    /**
     * @return the width of this rectangle.
     */
    public double spanX() {
        return mMaxX - mMinX;
    }
    
    /**
     * @return the height of this rectangle.
     */
    public double spanY() {
        return mMaxY - mMinY;
    }
                
    /**
     * @return the absolute area of this rectangle.
     */
    public double area() {
        return Math.abs((mMaxX - mMinX) * (mMaxY - mMinY));
    }
    
    
    /****** TRANSFORMATIONS ******/
    
    /**
     * Creates a new Rect that has the size of <code>this</code> Rect, but is 
     * moved completely inside the argument Rect.  If the rectangle is too
     * large to fit inside, it is centered inside the argument Rect, but its
     * size is not changed.
     * 
     * @param bounds - Rect in which to fit <code>this</code> rectangle.
     * @return a new Rect.
     */
    public Rect clamp(Rect bounds) {
        double left, right, top, bottom;
                
        if(mMinX < bounds.mMinX) {
            left = bounds.mMinX;
            right = left + mMaxX - mMinX;
            
            if(right > bounds.mMaxX) {
                left = (bounds.mMinX + bounds.mMaxX + mMinX - mMaxX) * 0.5;
                right = left + mMaxX - mMinX;
            }
            
        }else if(mMaxX > bounds.mMaxX) {
            right = bounds.mMaxX;
            left = right + mMinX - mMaxX;
                
            if(left < bounds.mMinX) {
                left  = (bounds.mMinX + bounds.mMaxX + mMinX - mMaxX) * 0.5;
                right = left + mMaxX - mMinX;
            }
            
        }else{
            left = mMinX;
            right = mMaxX;
        }
         
        if(mMinY < bounds.mMinY) {
            top = bounds.mMinY;
            bottom = top + mMaxY - mMinY;
            
            if(bottom > bounds.mMaxY) {
                top = (bounds.mMinY + bounds.mMaxY + mMinY - mMaxY) * 0.5;
                bottom = top + mMaxY - mMinY;
            }
            
        }else if(mMaxY > bounds.mMaxY) {
            bottom = bounds.mMaxY;
            top = bottom + mMinY - mMaxY;
            
            if(top < bounds.mMinY) {
                top = (bounds.mMinY + bounds.mMaxY + mMinY - mMaxY) * 0.5;
                bottom = top + mMaxY - mMinY;
            }
            
        }else{
            top = mMinY;
            bottom = mMaxY;
        }
        
        return new Rect(left, top, right, bottom);
    }
    
    /**
     * @return the intersection between <code>this</code> Rect and the Parameter Rect.
     * If the two FRects do not overlap, a Rect with 0 size is returned.
     */
    public Rect clip(Rect bounds) {
        return new Rect(Math.max(mMinX, bounds.mMinX),
                        Math.max(mMinY, bounds.mMinY),
                        Math.min(mMaxX, bounds.mMaxX),
                        Math.min(mMaxY, bounds.mMaxY));
    }

    /**
     * Centers this Rect inside another Rect and scales the size until the 
     * two rectangles share borders, but does not affect the aspect ratio.
     * 
     * @param bounds - Rectangle into which to fit this Rect.
     * @return new Rect object that fits inside bounds.
     */
    public Rect fit(Rect bounds) {
        if(spanX() * bounds.spanY() > bounds.spanX() * spanY()) {
            double height = spanY() * bounds.spanX() / spanX();
            double margin = (bounds.spanY() - height) * 0.5;
            
            return new Rect(bounds.minX(),
                            bounds.minY() + margin,
                            bounds.maxX(),
                            bounds.minY() + margin + height);
            
        }else{
            double width = spanX() * bounds.spanY() / spanY();
            double margin = (bounds.spanX() - width) * 0.5;
            
            return new Rect(bounds.minX() + margin,
                            bounds.minY(),
                            bounds.minX() + margin + width,
                            bounds.maxY());
        }
    }
    
    /**
     * Scales the size of the Rect without changing the center point.
     * 
     * @param scale - Amount to scale width and height.
     * @return new Rect with scaled width and height.
     */
    public Rect inflate(double scale) {
        double width  = (mMaxX - mMinX) * scale;
        double height = (mMaxY - mMinY) * scale;
        
        return new Rect( (mMinX + mMaxX - width)  * 0.5,
                         (mMinY + mMaxY - height) * 0.5,
                         (mMinX + mMaxX + width)  * 0.5,
                         (mMinY + mMaxY + height) * 0.5);
    }
    
    /**
     * Scales the size of the Rect without changing the center point.
     * 
     * @param scaleWidth Amount to scale width.
     * @param scaleHeight Amount to scale height.
     * @return new Rect with scaled width and height.
     */
    public Rect inflate(double scaleWidth, double scaleHeight) {
        double width  = (mMaxX - mMinX) * scaleWidth;
        double height = (mMaxY - mMinY) * scaleHeight;
        
        return new Rect( (mMinX + mMaxX - width)  * 0.5,
                         (mMinY + mMaxY - height) * 0.5,
                         (mMinX + mMaxX + width)  * 0.5,
                         (mMinY + mMaxY + height) * 0.5);
    }
    
    /**
     * @return the pointUnion between <code>this</code> Rect and the parameter Rect.  The
     * pointUnion may contain area not covered by either input Rect.
     */
    public Rect union(Rect r) {
        return new Rect(Math.min(mMinX, r.mMinX),
                        Math.min(mMinY, r.mMinY),
                        Math.max(mMaxX, r.mMaxX),
                        Math.max(mMaxY, r.mMaxY));
    }
    
    /**
     * This will flip the width or height of a rectangle if it has a negative 
     * size. The rectangle will remain in the same place, with only the sides swapped.
     * 
     * @return a new Rect.
     */
    public Rect normalize() {
        if(mMinX < mMaxX){
            if(mMinY < mMaxY){
                return this;
            }else{
                return new Rect(mMinX, mMaxY, mMaxX, mMinY);
            }
        }else{
            if(mMinY < mMaxY){
                return new Rect(mMaxX, mMinY, mMinX, mMaxY);
            }else{
                return new Rect(mMaxX, mMaxY, mMinX, mMinY);
            }
        }
    }
            
    /**
     * Multiplies location and size.
     * @param multX - Amount to multiply the width and left edge.
     * @param multY - Amount to multiply the height and top edge.
     * @return new Rect object.
     */
    public Rect scale(double multX, double multY) {
        return new Rect(mMinX * multX, mMinY * multY, mMaxX * multX, mMaxY * multY);
    }
    
    /**
     * Moves the Rect.
     * @param dx - Amount to move the rectangle horizantally.
     * @param dy - Amount to move the rectangle vertically.
     * @return a new rectangle.
     */
    public Rect translate(double dx, double dy) {
        return new Rect(mMinX + dx, mMinY + dy, mMaxX + dx, mMaxY + dy);
    }
    
    /**
     * Rounds each edge to the nearest int.
     * @return Rect with near integer values.
     */
    public Rect round() {
        return new Rect(Math.round(mMinX),
                        Math.round(mMinY), 
                        Math.round(mMaxX), 
                        Math.round(mMaxY));
    }
    
    /**
     * Centers the rect on the given point.
     * 
     * @param x - The new horizontal center.
     * @param y - The new vertical center.
     * @return a new Rect centered at (x, y)
     */
    public Rect setCenter(double x, double y) {
        x -= (mMaxX - mMinX) * 0.5;
        y -= (mMaxY - mMinY) * 0.5;
        
        return new Rect(x, y, x + mMaxX - mMinX, y + mMaxY - mMinY);
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
     * @param x - The x coordinate of a point.
     * @param y - The y coordinate of a point.
     * @return true if the point lies within the rectangle, otherwise false.
     */
    public boolean intersects(double x, double y) {
        return x >= mMinX && x < mMaxX && y >= mMinY && y < mMaxY;
    }
    
    /**
     * Tests if this rectangle has any intersection with other rectangle.  
     * 
     * @param rect - A rect to check for overlap.
     * @return true if rectangles have any intersection.
     */
    public boolean intersects(Rect rect) {
        return !(mMinX <  rect.mMinX   && mMaxX  <  rect.mMinX   ||
                 mMinX >= rect.mMaxX  && mMaxX  >= rect.mMaxX  ||
                 mMinY  <  rect.mMinY    && mMaxY <  rect.mMinY    ||
                 mMinY  >= rect.mMaxY && mMaxY >= rect.mMaxY);
    }

    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Rect))
            return false;
        
        Rect r = (Rect)o;
        return mMinX == r.mMinX &&
               mMinY == r.mMinY &&
               mMaxX == r.mMaxX &&
               mMaxY == r.mMaxY;
    }

    
    @Override
    public int hashCode() {
        long v1 = Double.doubleToLongBits(mMaxY);
        long v2 = Double.doubleToLongBits(mMinY);
        long v3 = Double.doubleToLongBits(mMinX);
        long v4 = Double.doubleToLongBits(mMaxX);
        
        return (int)(v1 ^ v2 ^ v3 ^ v4);
    }
    
    

    /****** CONVERSIONS ******/
    
    /**
     * Rounds Rect values to nearest integers.
     * @return new LongRect object.
     */
    public LongRect roundToLong() {
        return LongRect.fromEdges( Math.round(mMinX),
                                   Math.round(mMinY),
                                   Math.round(mMaxX),
                                   Math.round(mMaxY));
    }
    
    /**
     * Truncates Rect values to nearest longs.
     * @return new LongRect object.
     */
    public LongRect floorToLong() {
        return LongRect.fromEdges( (long)mMinX,
                                   (long)mMinY,
                                   (long)mMaxX,
                                   (long)mMaxY);
    }

    /**
     * Rounds scaled Rect values to nearest longs.
     * 
     * @param scaleX  Amount to scale horizontal dimensions.
     * @param scaleY  Amount to scale vertical dimensions.
     * @return equivalent LongRect object.
     */
    public LongRect scaleAndRoundToLong(double scaleX, double scaleY) {
        return LongRect.fromEdges(  Math.round(mMinX * scaleX),
                                    Math.round(mMinY * scaleY),
                                    Math.round(mMaxX * scaleX),
                                    Math.round(mMaxY * scaleY));
    }
    
    /**
     * Truncates scaled Rect values to nearest longs.
     * 
     * @param scaleX  Amount to scale horizontal dimensions. 
     * @param scaleY  Amount to scale vertical dimensions.
     * @return equivalent Rect object.
     */
    public LongRect scaleAndFloorToLong(double scaleX, double scaleY) {
        return LongRect.fromEdges( (long)(mMinX * scaleX),
                                   (long)(mMinY * scaleY),
                                   (long)(mMaxX * scaleX),
                                   (long)(mMaxY * scaleY));
    }

    
    public String toString() {
        StringBuilder b = new StringBuilder("Rect [");
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
