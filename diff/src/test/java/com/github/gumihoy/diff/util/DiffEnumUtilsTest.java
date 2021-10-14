package com.github.gumihoy.diff.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.enums.Color;
import com.github.gumihoy.diff.object.Sku;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
@Slf4j
public final class DiffEnumUtilsTest {


    @Test
    public void testDiff1() {
        Sku s = Sku.builder()
                .color(Color.RED.value())
                .build();

        Sku t = Sku.builder()
                .color(Color.GREEN.value())
                .build();

        IDiffObject object = DiffUtils.diff(s, t);
        String identDiff = DiffFormatUtils.toIdent(object);

        System.out.println(identDiff);

        Assert.assertEquals("规格\n" +
                "\t颜色: 红色 -> 青色", identDiff);

        System.out.println("------------");
    }

}
