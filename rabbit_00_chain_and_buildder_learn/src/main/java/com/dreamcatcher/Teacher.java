package com.dreamcatcher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true) //开启链式编程
@Builder //开启建造者模式
public class Teacher implements Serializable {
    //一般情况下实体类不需要序列化，但是如果实体类中有引用类型的属性，那么这个实体类就需要序列化
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer age;
    private String address;

}
