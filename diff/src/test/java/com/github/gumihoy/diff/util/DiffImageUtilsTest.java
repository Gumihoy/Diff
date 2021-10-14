package com.github.gumihoy.diff.util;

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
public final class DiffImageUtilsTest {


    @Test
    public void testDiff1() {
        Sku s = Sku.builder()
                .image("http://www.baidu.com")
                .build();

        Sku t = Sku.builder()
                .image("http://www.baidu.com/s=xx")
                .build();

        IDiffObject object = DiffUtils.diff(s, t);
        String identDiff = DiffFormatUtils.toIdent(object);

        System.out.println(identDiff);

        Assert.assertEquals("规格\n" +
                "\t图片: http://www.baidu.com -> http://www.baidu.com/s=xx", identDiff);

        System.out.println("------------");
    }


}
