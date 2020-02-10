package com.cntest.su.mail;

import java.io.File;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cntest.su.constant.Encoding;
import com.cntest.su.exception.SysException;
import com.cntest.su.utils.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 邮件组件。
 */
@Slf4j
public class MailSender extends JavaMailSenderImpl {
  /**
   * 发送邮件。
   * 
   * @param mail 邮件
   */
  public void send(Mail mail) {
    try {
      if (CollectionUtils.isEmpty(mail.getTo())) {
        log.warn("发送邮件时，收件人邮件地址列表为空。");
        return;
      }
      MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage(), true, Encoding.UTF8);
      helper.setSubject(mail.getSubject());
      helper.setFrom(mail.getFrom());
      helper.setTo(mail.getTo().toArray(new String[] {}));
      helper.setCc(mail.getCc().toArray(new String[] {}));
      helper.setBcc(mail.getBcc().toArray(new String[] {}));
      helper.setText(mail.getText(), true);

      for (File attachement : mail.getAttachements()) {
        helper.addAttachment(attachement.getName(), attachement);
      }
      send(helper.getMimeMessage());
    } catch (Exception e) {
      throw new SysException("发送邮件时发生异常。", e);
    }
  }
}
