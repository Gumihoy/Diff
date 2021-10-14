package com.github.gumihoy.diff.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.object.Sku;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
@Slf4j
public final class DiffMoneyUtilsTest {


    @Test
    public void testDiff1() {
        Sku s = Sku.builder()
                .id(1L)
                .price(new BigDecimal("1.00"))
                .build();

        Sku t = Sku.builder()
                .id(1L)
                .price(new BigDecimal("2.00"))
                .build();

        IDiffObject object = DiffUtils.diff(s, t);
        String identDiff = DiffFormatUtils.toIdent(object);

        System.out.println(identDiff);

        Assert.assertEquals("规格\n" +
                "\t价格: ¥1.00 -> ¥2.00", identDiff);

        System.out.println("------------");
    }

}
