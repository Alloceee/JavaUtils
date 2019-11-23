package com.utils.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @Excel(name = "ID", orderNum = "0")
    private Integer id;

    @Excel(name = "姓名", orderNum = "1")
    private String name;

    @Excel(name = "年龄", orderNum = "2")
    private Integer age;

    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true_st")
    private int sex;

    @Excel(name = "地址", orderNum = "3")
    private String address;

    @Excel(name = "邮箱", orderNum = "4")
    private String email;

    @Excel(name = "QQ", orderNum = "5")
    private String qq;

    @Excel(name = "电话", orderNum = "6")
    private String phone;

}
