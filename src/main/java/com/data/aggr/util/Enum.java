/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.aggr.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xteli
 */
public class Enum {

    public enum LifespanType {

        Second(0),
        Minute(1),
        Hour(2),
        Day(3),
        Month(4),
        Year(5);

        private int enumValue;

        LifespanType(int enumValue) {
            this.enumValue = enumValue;
        }

        public int getEnumValue() {

            return enumValue;
        }

    }

    public enum ActivityType {

        Nil(0),
        Swap(1),
        Recycle(2);

        private int enumValue;

        ActivityType(int enumValue) {
            this.enumValue = enumValue;
        }

        public int getEnumValue() {
            return enumValue;
        }

        private static final Map<Integer, ActivityType> aTypeMap = new HashMap<Integer, ActivityType>();

        static {
            for (ActivityType aType : ActivityType.values()) {
                aTypeMap.put(aType.enumValue, aType);
            }
        }

        public static ActivityType toEnum(int i) {
            ActivityType aType = aTypeMap.get(i);
            return aType;
        }

    }

}
