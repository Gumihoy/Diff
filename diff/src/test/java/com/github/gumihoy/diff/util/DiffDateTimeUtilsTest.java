package com.github.gumihoy.diff.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.gumihoy.diff.adt.IDiffObject;
import com.github.gumihoy.diff.object.Spu;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
@Slf4j
public final class DiffDateTimeUtilsTest {


    @Test
    public void testDiff1() {
        Spu s = Spu.builder()
                .id(1L)
                .utime(1634014268100L)
                .build();

        Spu t = Spu.builder()
                .id(1L)
                .utime(1634014268100L + 1000L)
                .build();

        IDiffObject object = DiffUtils.diff(s, t);
        String identDiff = DiffFormatUtils.toIdent(object);

        System.out.println(identDiff);

        Assert.assertEquals("商品\n" +
                "\t更新时间: 2021-10-12 12:51:08 -> 2021-10-12 12:51:09", identDiff);

        System.out.println("------------");
    }

}
