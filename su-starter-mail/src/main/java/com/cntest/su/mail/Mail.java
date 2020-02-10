package com.cntest.su.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 邮件。
 */
@Data
public class Mail {
  /** 标题 */
  protected String subject;
  /** 正文 */
  protected String text;
  /** 发送方邮件地址 */
  protected String from;
  /** 回复邮件地址 */
  protected String replyTo;
  /** 收件方邮件地址列表 */
  protected List<String> to = new ArrayList<>();
  /** 抄送方邮件地址列表 */
  protected List<String> cc = new ArrayList<>();
  /** 秘密抄送方邮件地址列表 */
  protected List<String> bcc = new ArrayList<>();
  /** 附件 */
  protected List<File> attachements = new ArrayList<>();

  /**
   * 新增发送方邮件地址。
   * 
   * @param mails 邮件地址
   */
  public void addTo(String... mails) {
    for (String mail : mails) {
      to.add(mail);
    }
  }

  /**
   * 新增抄送方邮件地址。
   * 
   * @param mails 邮件地址
   */
  public void addCc(String... mails) {
    for (String mail : mails) {
      cc.add(mail);
    }
  }

  /**
   * 新增秘密抄送方邮件地址。
   * 
   * @param mails 邮件地址
   */
  public void addBcc(String... mails) {
    for (String mail : mails) {
      bcc.add(mail);
    }
  }

  /**
   * 新增附件。
   * 
   * @param attachement 附件
   */
  public void addAttachement(File attachement) {
    attachements.add(attachement);
  }
}
