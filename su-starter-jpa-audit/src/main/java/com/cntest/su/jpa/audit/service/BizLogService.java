package com.cntest.su.jpa.audit.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.search.SortField;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cntest.su.auth.AuthUser;
import com.cntest.su.auth.AuthUserService;
import com.cntest.su.jpa.audit.entity.BizLog;
import com.cntest.su.jpa.search.dao.FullTextDao;
import com.cntest.su.jpa.search.query.FullTextCriteria;
import com.cntest.su.model.Page;
import com.cntest.su.model.PageQuery;

/**
 * 业务日志组件。
 */
public class BizLogService {
  @Autowired
  private AuthUserService<? extends AuthUser> authUserService;
  @Resource
  private FullTextDao<BizLog> bizLogDao;

  /**
   * 记录普通日志。
   * 
   * @param message 日志信息
   */
  @Transactional
  public void log(String message) {
    log(getCurrentUsername(), message);
  }

  /**
   * 记录普通日志，登录时用。
   * 
   * @param username 操作人的用户名
   * @param message 日志信息
   */
  @Transactional
  public void log(String username, String message) {
    BizLog bizLog = new BizLog();
    bizLog.setMessage(message);
    bizLog.setOperator(username);
    bizLog.setOperateTime(new Date());
    bizLogDao.save(bizLog);
  }

  /**
   * 记录高级日志。
   * 
   * @param bizLog 日志对象
   */
  @Transactional
  public void log(BizLog bizLog) {
    bizLog.setOperator(getCurrentUsername());
    bizLog.setOperateTime(new Date());
    bizLogDao.save(bizLog);
  }

  /**
   * 分页全文搜索日志记录。
   * 
   * @param query 搜索条件
   * @return 返回符合条件的日志分页对象。
   */
  @Transactional(readOnly = true)
  public Page<BizLog> searchLog(PageQuery query) {
    FullTextCriteria criteria = bizLogDao.createFullTextCriteria();
    criteria.desc("operateTime", SortField.Type.STRING);
    return bizLogDao.searchPage(criteria, query);
  }

  /**
   * 全文搜索指定业务实体ID的业务日志记录。
   * 
   * @param entityId 业务实体ID
   * @return 返回指定业务实体ID的业务日志记录列表。
   */
  @Transactional(readOnly = true)
  public List<BizLog> searchEntityLog(String entityId) {
    FullTextCriteria criteria = bizLogDao.createFullTextCriteria();
    criteria.eq("entityId", entityId);
    criteria.desc("operateTime", SortField.Type.STRING);
    return bizLogDao.searchBy(criteria);
  }

  /**
   * 获取指定ID的日志记录。
   * 
   * @param logId 日志ID
   * @return 返回指定ID的日志记录。
   */
  @Transactional(readOnly = true)
  public BizLog getLog(Long logId) {
    return bizLogDao.get(logId);
  }

  /**
   * 获取当前登录用户的用户名，如果没有当前登录用户则返回系统管理员的用户名。
   * 
   * @return 返回当前登录用户的用户名。
   */
  private String getCurrentUsername() {
    if (ThreadContext.getSecurityManager() == null
        || !SecurityUtils.getSubject().isAuthenticated()) {
      return "admin";
    } else {
      return authUserService.getLogonUser().getUsername();
    }
  }
}
