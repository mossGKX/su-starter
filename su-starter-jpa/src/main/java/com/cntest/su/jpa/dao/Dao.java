package com.cntest.su.jpa.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.query.Query;

import com.cntest.su.jpa.query.Criteria;
import com.cntest.su.model.Page;
import com.cntest.su.model.PageQuery;
import com.cntest.su.utils.BeanUtils;
import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

/**
 * 泛型Dao。
 * 
 * @param <E> 业务实体类型
 */
public class Dao<E> {
  protected Class<E> entityClass;
  @PersistenceContext
  protected EntityManager entityManager;

  /**
   * 构造方法。
   * 
   * @param entityClass 业务实体类
   */
  public Dao(Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * 获取EntityManagerFactory。
   * 
   * @return 返回EntityManagerFactory。
   */
  public EntityManagerFactory getEntityManagerFactory() {
    return entityManager.getEntityManagerFactory();
  }

  /**
   * 获取CriteriaBuilder。
   * 
   * @return 返回CriteriaBuilder。
   */
  public CriteriaBuilder getCriteriaBuilder() {
    return entityManager.getCriteriaBuilder();
  }

  /**
   * 创建TypedQuery。
   * 
   * @param jpql JPQL语句
   * @param values 参数值
   * @return 返回TypedQuery。
   */
  public TypedQuery<E> createQuery(String jpql, Object... values) {
    TypedQuery<E> query = entityManager.createQuery(jpql, entityClass);
    for (int i = 0; i < values.length; i++) {
      query.setParameter(i, values[i]);
    }
    return query;
  }

  /**
   * 创建Criteria。
   * 
   * @return 返回Criteria。
   */
  public Criteria<E> createCriteria() {
    return new Criteria<>(entityClass, entityManager);
  }

  /**
   * 加载指定ID的业务实体。
   * 
   * @param id 实体ID
   * @return 返回指定ID的业务实体，如果不存在抛出异常。
   */
  public E load(Serializable id) {
    return entityManager.getReference(entityClass, id);
  }

  /**
   * 获取指定ID的业务实体。
   * 
   * @param id 实体ID
   * @return 返回指定ID的业务实体，如果没有找到则返回null。
   */
  public E get(Serializable id) {
    return get(id, LockModeType.NONE);
  }

  /**
   * 获取指定ID的业务实体。
   * 
   * @param id 实体ID
   * @param lockMode 锁定模式
   * @return 返回指定ID的业务实体，如果没有找到则返回null。
   */
  @SuppressWarnings("unchecked")
  public E get(Serializable id, LockModeType lockMode) {
    E entity = entityManager.find(entityClass, id, lockMode);
    // 如果获取的是一个代理对象，则从代理对象中获取实际的实体对象返回。
    if (entity instanceof HibernateProxy) {
      entity = (E) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
    }
    return entity;
  }

  /**
   * 保存业务实体。
   * 
   * @param entity 业务实体
   */
  public void save(E entity) {
    entityManager.persist(entity);
  }

  /**
   * 批量保存业务实体。
   * 
   * @param entities 业务实体列表
   */
  public void batchSave(List<E> entities) {
    entities.forEach(this::save);
  }

  /**
   * 更新业务实体。
   * 
   * @param entity 业务实体
   * @return 返回更新后的业务实体（持久状态的）。
   */
  public E merge(E entity) {
    return entityManager.merge(entity);
  }

  /**
   * 强制更新业务实体。
   * 
   * @param entity 业务实体
   */
  public void forceMerge(E entity) {
    detach(entity);
    merge(entity);
  }

  /**
   * 清理业务实体。
   * 
   * @param entity 业务实体
   */
  public void detach(E entity) {
    entityManager.detach(entity);
  }

  /**
   * 删除业务实体。
   * 
   * @param entity 业务实体
   */
  public void remove(E entity) {
    entityManager.remove(entity);
  }

  /**
   * 删除多个业务实体。
   * 
   * @param entitys 业务实体列表
   */
  public void remove(List<E> entitys) {
    for (E entity : entitys) {
      remove(entity);
    }
  }

  /**
   * 删除指定ID的业务实体。
   * 
   * @param id 业务实体ID
   */
  public void remove(Serializable id) {
    remove(get(id));
  }

  /**
   * 删除多个指定ID的业务实体。
   * 
   * @param ids 业务实体ID数组
   */
  public void remove(Serializable[] ids) {
    for (Serializable id : ids) {
      remove(id);
    }
  }

  /**
   * 根据属性批量删除业务实体。
   * 
   * @param name 属性名
   * @param value 属性值
   * @return 返回删除记录数。
   */
  public Integer removeBy(String name, Object value) {
    String sql = String.format("delete from %s where %s = :value", entityClass.getName(), name);
    javax.persistence.Query query = entityManager.createQuery(sql);
    query.setParameter("value", value);
    return query.executeUpdate();
  }

  /**
   * 获取所有业务实体。
   * 
   * @return 返回指定类型的所有业务实体。
   */
  public List<E> getAll() {
    Criteria<E> criteria = createCriteria();
    return criteria.toTypedQuery().getResultList();
  }

  /**
   * 获取所有业务实体并进行排序。
   * 
   * @param orderBy 排序的属性名
   * @param isAsc 是否升序
   * @return 返回排序后的指定类型的所有业务实体。
   */
  public List<E> getAll(String orderBy, Boolean isAsc) {
    Criteria<E> criteria = createCriteria();
    if (isAsc) {
      criteria.asc(orderBy);
    } else {
      criteria.desc(orderBy);
    }
    return criteria.toTypedQuery().getResultList();
  }

  /**
   * 根据属性的值查找业务实体。
   * 
   * @param name 属性名
   * @param value 属性值
   * @return 返回属性值相符的业务实体集合，如果没有找到返回一个空的集合。
   */
  public List<E> findBy(String name, Object value) {
    Criteria<E> criteria = createCriteria();
    if (value == null) {
      criteria.isNull(name);
    } else {
      criteria.eq(name, value);
    }
    return findBy(criteria);
  }

  /**
   * 根据属性的值查找业务实体并进行排序。
   * 
   * @param name 属性名
   * @param value 属性值
   * @param orderBy 排序属性
   * @param isAsc 是否升序
   * @return 返回排序后的属性值相符的业务实体集合，如果没有找到返回一个空的集合。
   */
  public List<E> findBy(String name, Object value, String orderBy, boolean isAsc) {
    Criteria<E> criteria = createCriteria();
    if (isAsc) {
      criteria.asc(orderBy);
    } else {
      criteria.desc(orderBy);
    }
    if (value == null) {
      criteria.isNull(name);
    } else {
      criteria.eq(name, value);
    }
    return findBy(criteria);
  }

  /**
   * 根据条件查找业务实体。
   * 
   * @param criteria 查询条件
   * @return 返回条件相符的业务实体集合，如果没有找到返回一个空的集合。
   */
  public List<E> findBy(Criteria<E> criteria) {
    return criteria.toTypedQuery().getResultList();
  }

  /**
   * 判断是否存在属性重复的业务实体。
   * 
   * @param entity 待判断的业务实体
   * @param names 属性名
   * @return 如果存在重复的业务实体返回false，否则返回true。
   */
  public Boolean isUnique(E entity, String... names) {
    Criteria<E> criteria = createCriteria();
    for (String name : names) {
      criteria.eq(name, BeanUtils.getField(entity, name));
    }
    Object id = getId(entity);
    if (id != null) {
      criteria.notEq(getIdName(), id);
    }
    return criteria.count() == 0;
  }

  /**
   * 根据属性的值查找唯一的业务实体。
   * 
   * @param name 属性名
   * @param value 属性值
   * @return 返回指定唯一的业务实体，如果没有找到则返回null。
   */
  public E findUnique(String name, Object value) {
    Criteria<E> criteria = createCriteria();
    criteria.eq(name, value);
    return findUnique(criteria);
  }

  /**
   * 根据条件查找唯一的业务实体。
   * 
   * @param criteria 查询条件
   * @return 返回指定唯一的业务实体，如果没有找到则返回null。
   */
  public E findUnique(Criteria<E> criteria) {
    try {
      TypedQuery<E> query = criteria.toTypedQuery();
      // 启用查询缓存
      query.setHint("org.hibernate.cacheable", true);
      // 用于解决spring-data-jpa的审计功能bug
      // 可参考：http://forum.spring.io/forum/spring-projects/data/106312-spring-data-jpa-infinite-loop-when-updating-but-not-saving-an-auditable-object
      query.setFlushMode(FlushModeType.COMMIT);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  /**
   * 执行count查询获得记录总数。
   * 
   * @return 返回记录总数。
   */
  public Integer count() {
    Criteria<E> criteria = createCriteria();
    return criteria.count();
  }

  /**
   * 根据JPQL查询语句进行分页查询。
   * 
   * @param jpql JPQL查询语句
   * @param pageNum 待获取的页数
   * @param pageSize 每页的记录数
   * @param values 参数值
   * @return 返回查询得到的分页对象。
   */
  public Page<E> findPage(String jpql, Integer pageNum, Integer pageSize, Object... values) {
    Integer count = count(jpql, values);
    if (count < 1) {
      return new Page<>(pageSize);
    }

    Page<E> page = new Page<>(count, pageNum, pageSize);
    TypedQuery<E> query = createQuery(jpql, values);

    List<E> list = query.setFirstResult((page.getNumber() - 1) * pageSize).setMaxResults(pageSize)
        .getResultList();
    page.setContents(list);
    return page;
  }

  /**
   * 根据查询条件进行分页查询。
   * 
   * @param query TypedQuery
   * @param pageNum 待获取的页数
   * @param pageSize 每页的记录数
   * @return 返回查询得到的分页对象。
   */
  public Page<E> findPage(TypedQuery<E> query, Integer pageNum, Integer pageSize) {
    String jpql = query.unwrap(Query.class).getQueryString();
    return findPage(jpql, pageNum, pageSize);
  }

  /**
   * 根据查询条件进行分页查询。
   * 
   * @param criteriaQuery CriteriaQuery
   * @param pageNum 待获取的页数
   * @param pageSize 每页的记录数
   * @return 返回查询得到的分页对象。
   */
  public Page<E> findPage(CriteriaQuery<E> criteriaQuery, Integer pageNum, Integer pageSize) {
    TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
    return findPage(query, pageNum, pageSize);
  }

  /**
   * 根据查询条件进行分页查询。
   * 
   * @param criteria 查询条件
   * @param pageNum 待获取的页数
   * @param pageSize 每页的记录数
   * @return 返回查询得到的分页对象。
   */
  public Page<E> findPage(Criteria<E> criteria, Integer pageNum, Integer pageSize) {
    Integer count = criteria.count();
    if (count < 1) {
      return new Page<>(pageSize);
    }
    Page<E> page = new Page<>(count, pageNum, pageSize);
    List<E> list = criteria.toTypedQuery().setFirstResult((page.getNumber() - 1) * pageSize)
        .setMaxResults(pageSize).getResultList();
    page.setContents(list);
    return page;
  }

  /**
   * 根据查询条件进行分页查询。
   * 
   * @param criteria 查询条件
   * @param pageQuery 搜索模型
   * @param likeFields 模糊查询字段
   * @return 返回搜索得到的分页对象。
   */
  public Page<E> findPage(Criteria<E> criteria, PageQuery pageQuery, String... likeFields) {
    String keyword = pageQuery.getKeyword();
    if (StringUtils.isNotBlank(keyword) && CollectionUtils.isNotEmpty(likeFields)) {
      criteria.like(CollectionUtils.toList(likeFields), keyword);
    }
    return findPage(criteria, pageQuery.getPageNum(), pageQuery.getPageSize());
  }

  /**
   * 获取查询所能获得的对象总数。<br>
   * 本函数只能自动处理简单的JPQL语句,复杂的JPQL查询请另行编写count语句查询。
   * 
   * @param jpql 查询语句
   * @param values 查询参数
   * @return 返回查询结果总数。
   */
  public Integer count(String jpql, Object... values) {
    String fromJpql = "from " + StringUtils.substringBetween(jpql, "from", "order by");
    String countJpql = "select count(*) " + fromJpql;

    TypedQuery<Integer> query = entityManager.createQuery(countJpql, Integer.class);
    for (int i = 0; i < values.length; i++) {
      query.setParameter(i, values[i]);
    }
    return query.getSingleResult();
  }

  /**
   * 获取业务实体的缓存对象。
   * 
   * @return 返回业务实体的缓存对象。
   */
  public Cache getCache() {
    return getEntityManagerFactory().getCache();
  }

  /**
   * 清理业务实体缓存。
   */
  public void evictCache() {
    getCache().evict(entityClass);
  }

  /**
   * 清理指定ID的业务实体缓存。
   * 
   * @param id 业务实体ID
   */
  public void evictCache(Serializable id) {
    getCache().evict(entityClass, id);
  }

  /**
   * 获取实体类的主键值。
   * 
   * @param entity 业务实体
   * @return 返回实体类的主键值。
   */
  private Object getId(E entity) {
    return getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
  }

  /**
   * 获取实体类的主键名。
   * 
   * @return 返回实体类的主键名。
   */
  private String getIdName() {
    List<Field> fields = BeanUtils.findField(entityClass, Id.class);
    if (CollectionUtils.isNotEmpty(fields)) {
      return fields.get(0).getName();
    } else {
      return null;
    }
  }
}
