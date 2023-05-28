package com.dreamcatcher;

import java.io.Serializable;

public class Student implements Serializable {
    //一般情况下实体类不需要序列化，但是如果实体类中有引用类型的属性，那么这个实体类就需要序列化
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer age;
    private String address;

    public Integer getId() {
        return id;
    }

    public Student setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Student setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
