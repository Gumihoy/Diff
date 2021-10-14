package com.github.gumihoy.diff.object;

import java.util.List;

import com.github.gumihoy.diff.annotation.Diff;
import com.github.gumihoy.diff.annotation.DiffTimeStamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-07-07
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Diff(name = "商品")
public class Spu {
    @Diff(name = "主键")
    private long id;

    @Diff(name = "标题")
    private String title;

    @Diff(name = "描述")
    private String description;

    @Diff(name = "规格")
    private List<Sku> skus;

    @Diff(name = "包装")
    private String packagings;

    @Diff(name = "售后服务")
    private String afterService;

    @Diff(name = "评价")
    private String comment;

    @Diff(name = "商品分类")
    private String categoryId;

    @Diff(name = "品牌")
    private long brandId;

    @DiffTimeStamp(name = "创建时间")
    private long ctime;

    @DiffTimeStamp(name = "更新时间")
    private long utime;

}
