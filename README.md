# Diff

## Diff使用

- 普通字段

```java
// 需要Diff的字段必须如下标记
@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @Diff(name = "名称")
    private String name;
    @Diff(name = "颜色")
    protected Color color;
}

    // 使用
    C s = C.builder().name("A").build();
    C t = C.builder().name("B").build();
    IDiffObject object = DiffUtils.diff(s, t);
    // 格式化
    String identDiff = DiffFormatUtils.toIdent(object);
// 打印
System.out.println(identDiff);
```

```console
规格
        颜色: 红色 -> 青色
```

- 时间/日期字段

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @DiffDateTime(name = "日期")
    private Date date;
}
```

- 枚举值字段

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @DiffEnum(name = "颜色", enumClass = Color.class)
    protected int color;
}
```

- 图片字段

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @DiffImage(name = "图片")
    protected String image;
}
```

- 金钱字段

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @DiffMoney(name = "价格")
    protected BigDecimal price;
}
```

- 时间戳字段

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Dff(name = "C")
public class C {
    @DiffTimeStamp(name = "时间")
    protected long time;
}
```


## Diff对象自定义格式化输出
自定义格式化输出类，且继承`AbstractDiffObjectOutputVisitor`，可以参考`DiffObjectIndentOutputVisitor`实现