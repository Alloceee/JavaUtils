package com.yws.email.entity;

import lombok.Data;

@Data
public class Email {
    /**
     * 发送对象
     */
    private String toEmail;

    /**
     * 邮件主题
     */
    private String object;

    /**
     * 邮件内容
     */
    private String text;
}
