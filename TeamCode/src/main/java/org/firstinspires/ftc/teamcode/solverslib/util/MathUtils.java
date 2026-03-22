package org.firstinspires.ftc.teamcode.solverslib.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {
    private MathUtils() {
        throw new AssertionError("utility class");
    }

    /**
     * Returns value clamped between low and high boundaries.
     *
     * @param value Value to clamp.
     * @param low   The lower boundary to which to clamp value.
     * @param high  The higher boundary to which to clamp value.
     */
    public static int clamp(int value, int low, int high) {
        return Math.max(low, Math.min(value, high));
    }

    /**
     * Returns value clamped between low and high boundaries.
     *
     * @param value Value to clamp.
     * @param low   The lower boundary to which to clamp value.
     * @param high  The higher boundary to which to clamp value.
     */
    public static double clamp(double value, double low, double high) {
        return Math.max(low, Math.min(value, high));
    }

    /**
     * Returns value rounded to specified places
     *
     * @param number Number to round
     * @param places The number of decimal places to round to
     */
    public static double round(double number, int places) {
        return new BigDecimal((String.valueOf(number))).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Function to normalize all angles
     *
     * @param angle the angle to be normalized, in degrees or radians
     * @param zeroToMax whether the returned value should be normalized to 0 to max or -midpoint to midpoint
     * @param angleUnit the unit the angle parameter is in
     * @return the normalized angle
     */
    public static double normalizeAngle(double angle, boolean zeroToMax, AngleUnit angleUnit) {
        double max = returnMaxForAngleUnit(angleUnit);
        double angle2 = angle % max;
        if (zeroToMax && angle2 < 0) {
            return angle2 + max;
        } else if (!zeroToMax) {
            if (angle2 > max/2) {
                return angle2 - max;
            } else if (angle2 < -max/2) {
                return angle2 + max;
            }
        }

        return angle2;
    }

    public static double normalizeRadians(double angle, boolean zeroToFull) {
        return normalizeAngle(angle, zeroToFull, AngleUnit.RADIANS);
    }

    public static double normalizeDegrees(double angle, boolean zeroToFull) {
        return normalizeAngle(angle, zeroToFull, AngleUnit.DEGREES);
    }

    public static double returnMaxForAngleUnit(AngleUnit angleUnit) {
        if (angleUnit.equals(AngleUnit.RADIANS)) {
            return Math.PI * 2;
        } else {
            return 360;
        }
    }

    public static double sqrtWithSig(double val) {
        return Math.sqrt(Math.abs(val)) * Math.signum(val);
    }

    /**
     * @author Jaran Chao
     *
     * Adding quality of life updates to MathUtils.clamp function to now be able to be called as
     * extension functions of type Int and Double respectively. Inputs are generalized to Number then
     * casted to which the type it was called.
     *
     * Example:
     * 10.0.clamp(1,11) // 1 and 11 are converted to Doubles to satisfy type widening
     *
     * 10.clamp(1.5, 11.5) // 10 is converted to Double to satisfy type widening
     *
     * 10.clamp(1, 11.5) // 1 is inputted as Number, 11.5 is inputted as Double, therefore 1 and 10 are
     * casted to Double to satisfy type widening
     */
    public static int clamp(int value, Number low, Number high) {
        return MathUtils.clamp(value, low.intValue(), high.intValue());
    }

    public static double clamp(int value, double low, Number high) {
        return MathUtils.clamp((double) value, low, high.doubleValue());
    }

    public static double clamp(int value, Number low, double high) {
        return MathUtils.clamp((double) value, low.doubleValue(), high);
    }

    public static double clamp(int value, double low, double high) {
        return MathUtils.clamp((double) value, low, high);
    }

    public static double clamp(double value, Number low, Number high) {
        return MathUtils.clamp(value, low.doubleValue(), high.doubleValue());
    }
}
