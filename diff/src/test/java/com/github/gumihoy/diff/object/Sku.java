package com.github.gumihoy.diff.object;

import java.math.BigDecimal;
import java.util.Objects;

import com.github.gumihoy.diff.annotation.Diff;
import com.github.gumihoy.diff.annotation.DiffEnum;
import com.github.gumihoy.diff.annotation.DiffImage;
import com.github.gumihoy.diff.annotation.DiffMoney;
import com.github.gumihoy.diff.enums.Color;

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
@Diff(name = "规格")
public class Sku {

    @Diff(name = "主键")
    protected long id;

    @Diff(name = "商品Id")
    protected long spuId;

    @DiffMoney(name = "价格")
    protected BigDecimal price;

    @DiffImage(name = "图片")
    protected String image;

    @DiffMoney(name = "库存")
    protected int stock;

    @DiffEnum(name = "颜色", enumClass = Color.class)
    protected int color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sku sku = (Sku) o;
        return id == sku.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
