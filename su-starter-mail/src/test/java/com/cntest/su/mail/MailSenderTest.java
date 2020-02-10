package com.cntest.su.mail;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cntest.su.config.BaseAutoConfiguration;
import com.cntest.su.freemarker.config.FreeMarkerAutoConfiguration;
import com.cntest.su.mail.config.MailAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {BaseAutoConfiguration.class, FreeMarkerAutoConfiguration.class,
    MailAutoConfiguration.class})
public class MailSenderTest {
  @Autowired
  private MailSender mailSender;

  @Test
  public void testSendMail() throws Exception {
    Mail mail = new Mail();
    mail.setFrom("coo_mail_test@163.com");
    mail.addTo("hesong@cntest.com");
    mail.setSubject("文本邮件" + System.currentTimeMillis());
    mail.setText("这是一封测试邮件。");
    mailSender.send(mail);
  }

  @Test
  public void testSendTemplateMail() throws Exception {
    TemplateMail templateMail = new TemplateMail();
    templateMail.setFrom("coo_mail_test@163.com");
    templateMail.addTo("hesong@cntest.com");
    templateMail.setSubject("模版邮件" + System.currentTimeMillis());
    templateMail.setTemplateName("test-mail.ftl");
    templateMail.setVar("name", "coo");

    File attachement = new File("src/test/resources/测试附件.txt");
    templateMail.addAttachement(attachement);

    mailSender.send(templateMail);
  }
}
