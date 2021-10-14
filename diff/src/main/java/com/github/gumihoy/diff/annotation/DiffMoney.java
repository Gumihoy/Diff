package com.github.gumihoy.diff.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-02
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DiffMoney {
    /**
     * Field Name Desc.
     */
    String name();

    /**
     * 是否比较
     * true: 比较，如果修改前、修改后相等 不创建 DiffNode
     * false: 不比较，如果修改前、修改后不为null 就创建DiffNode
     */
    boolean isCompare() default true;

    CurrencyCode code() default CurrencyCode.CNY;

    CurrencyUnit unit() default CurrencyUnit.BASIC;


    /**
     * Currency Code
     * <p>
     * https://zh.wikipedia.org/wiki/ISO_4217
     */
    enum CurrencyCode {
        CNY("¥"),
        USD("$"),
        ;
        private final String symbol;

        CurrencyCode(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    /**
     * Currency Unit.
     * https://en.wikipedia.org/wiki/Metric_prefix.
     */
    enum CurrencyUnit {
        /**
         * 基本单位
         * 10^0
         */
        BASIC(CurrencyUnit.BASIC_SCALE),

        /**
         * 10^-1
         */
        DECI(CurrencyUnit.DECI_SCALE) {

        },

        /**
         * 10^-2
         */
        CENTI(CurrencyUnit.CENTI_SCALE),

        /**
         * 10^-3
         */
        MILLI(CurrencyUnit.MILLI_SCALE),
        ;

        private static final int BASIC_SCALE = 1;
        private static final int DECI_SCALE = 10 * BASIC_SCALE;
        private static final int CENTI_SCALE = 10 * DECI_SCALE;
        private static final int MILLI_SCALE = 10 * CENTI_SCALE;


        private final int scale;

        CurrencyUnit(int scale) {
            this.scale = scale;
        }

        public BigDecimal toBasic(Object value) {
            return cvt(value, scale, BASIC_SCALE);
        }

        public BigDecimal toDeci(Object value) {
            return cvt(value, scale, DECI_SCALE);
        }

        public BigDecimal toCenti(Object value) {
            return cvt(value, scale, CENTI_SCALE);
        }

        public BigDecimal toMilli(Object value) {
            return cvt(value, scale, MILLI_SCALE);
        }

        /**
         * General conversion utility.
         *
         * @param value value
         * @param src   source unit scale
         * @param dst   result unit scale
         */
        private static BigDecimal cvt(Object value, int src, int dst) {
            BigDecimal decimal = new BigDecimal(value.toString());
            if (src == dst) {
                return decimal;
            } else if (src < dst) {
                return decimal.multiply(new BigDecimal(dst / src));
            } else {
                return decimal.divide(new BigDecimal(src / dst));
            }

        }
    }

}
