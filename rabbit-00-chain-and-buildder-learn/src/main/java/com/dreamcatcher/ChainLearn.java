package com.dreamcatcher;

public class ChainLearn {
    public static void main(String[] args) {
        Student student = new Student();
        //写法一
        student.setId(10);
        student.setName("张三");
        student.setAge(20);
        student.setAddress("北京市海淀区");
        System.out.println(student);
        //写法二 链式编程
        Student student1 = new Student().setId(10).setName("张三").setAge(20).setAddress("北京市海淀区");
        System.out.println(student1);


        Teacher teacher = new Teacher();
        //写法一
        teacher.setId(20);
        teacher.setName("lisi");
        teacher.setAge(20);
        teacher.setAddress("北京市海淀区");
        System.out.println(teacher);
        //写法二 链式编程
        Teacher teacher1 = new Teacher().setId(10).setName("张三").setAge(20).setAddress("北京市海淀区");
        System.out.println(teacher1);

        //写法三 建造者模式
        Teacher teacher2 = Teacher.builder().id(10).name("张三").age(20).address("北京市海淀区").build();
        System.out.println(teacher2);
    }
}
