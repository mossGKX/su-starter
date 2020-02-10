package com.cntest.su.jpa.search.dao;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.SortField;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.cntest.su.jpa.dao.Dao;
import com.cntest.su.jpa.search.query.FullTextCriteria;
import com.cntest.su.model.Page;
import com.cntest.su.model.PageQuery;
import com.cntest.su.utils.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FullTextDao<E> extends Dao<E> {
  private Map<String, Analyze> searchFields = new LinkedHashMap<>();

  public FullTextDao(Class<E> entityClass) {
    super(entityClass);
    initSearchFields();
  }

  public FullTextEntityManager getFullTextEntityManager() {
    return Search.getFullTextEntityManager(entityManager);
  }

  /**
   * 创建全文搜索查询条件。
   * 
   * @return 返回全文搜索查询条件。
   */
  public FullTextCriteria createFullTextCriteria() {
    return new FullTextCriteria(getFullTextEntityManager(), entityClass, searchFields);
  }

  /**
   * 根据全文搜索查询条件进行全文搜索。该方法直接返回业务实体列表，当数据量较大时该方法生成的SQL语句长度可能超过数据库的限制而导致异常。
   * 
   * @param criteria 全文搜索查询条件
   * @return 返回符合查询条件的业务实体列表。
   */
  @SuppressWarnings("unchecked")
  public List<E> searchBy(FullTextCriteria criteria) {
    return criteria.toFullTextQuery().getResultList();
  }

  /**
   * 根据全文搜索查询条件进行全文搜索。
   * 
   * @param criteria 全文搜索查询条件
   * @param maxResults 最大返回结果数
   * @return 返回符合查询条件的业务实体列表。
   */
  @SuppressWarnings("unchecked")
  public List<E> searchBy(FullTextCriteria criteria, Integer maxResults) {
    return criteria.toFullTextQuery().setMaxResults(maxResults).getResultList();
  }

  /**
   * 全文搜索指定类型的所有业务实体。
   * 
   * @return 返回指定类型的所有业务实体。
   */
  public List<E> searchAll() {
    return searchBy(createFullTextCriteria());
  }

  /**
   * 全文搜索指定类型的所有业务实体并进行排序。
   * 
   * @param orderBy 排序的属性名
   * @param isAsc 是否升序
   * @param type 类型
   * @return 返回排序后的指定类型的所有业务实体。
   */
  public List<E> searchAll(String orderBy, Boolean isAsc, SortField.Type type) {
    FullTextCriteria criteria = createFullTextCriteria();
    if (isAsc) {
      criteria.asc(orderBy, type);
    } else {
      criteria.desc(orderBy, type);
    }
    return searchBy(criteria);
  }

  /**
   * 全文搜索唯一业务实体。
   * 
   * @param criteria 全文搜索查询条件
   * @return 返回唯一业务实体，如果没有找到返回null。
   */
  @SuppressWarnings("unchecked")
  public E searchUnique(FullTextCriteria criteria) {
    return (E) criteria.toFullTextQuery().getSingleResult();
  }

  /**
   * 根据属性的值全文搜索唯一的业务实体。
   * 
   * @param name 属性名
   * @param value 属性值
   * @return 返回唯一业务实体，如果没有找到则返回null。
   */
  public E searchUnique(String name, Object value) {
    FullTextCriteria criteria = createFullTextCriteria();
    criteria.eq(name, value);
    return searchUnique(criteria);
  }

  /**
   * 根据全文搜索查询条件进行分页全文搜索。
   * 
   * @param criteria 全文搜索查询条件
   * @param pageQuery 搜索模型
   * @return 返回搜索得到的分页对象。
   */
  public Page<E> searchPage(FullTextCriteria criteria, PageQuery pageQuery) {
    criteria.setKeyword(pageQuery.getKeyword());
    return searchPage(criteria, pageQuery.getPageNum(), pageQuery.getPageSize());
  }

  /**
   * 根据全文搜索查询条件进行分页全文搜索。
   * 
   * @param criteria 全文搜索查询条件
   * @param pageNo 待获取的页数
   * @param pageSize 每页的记录数
   * @return 返回搜索得到的分页对象。
   */
  @SuppressWarnings("unchecked")
  public Page<E> searchPage(FullTextCriteria criteria, Integer pageNo, Integer pageSize) {
    FullTextQuery fullTextQuery = criteria.toFullTextQuery();
    int total = 0;
    try {
      // 当实体对应数据库中没有记录，其索引文件未生成时该方法会抛出异常
      // 这里捕捉后忽略该异常
      total = fullTextQuery.getResultSize();
    } catch (Exception e) {
      log.warn("实体[" + entityClass + "]全文索引文件尚未生成。", e);
    }
    if (total < 1) {
      return new Page<>(pageSize);
    }

    Page<E> page = new Page<>(total, pageNo, pageSize);
    fullTextQuery.setFirstResult((page.getNumber() - 1) * pageSize).setMaxResults(pageSize);
    List<E> result = fullTextQuery.getResultList();
    page.setContents(result);
    return page;
  }

  /**
   * 获取查询所能获得的对象总数。
   * 
   * @param criteria 全文搜索查询对象
   * @return 返回查询结果总数。
   */
  public Integer count(FullTextCriteria criteria) {
    FullTextQuery fullTextQuery = criteria.toFullTextQuery();
    try {
      // 当实体对应数据库中没有记录，其索引文件未生成时该方法会抛出异常
      // 这里捕捉后忽略该异常
      return fullTextQuery.getResultSize();
    } catch (Exception e) {
      log.warn("实体[" + entityClass + "]全文索引文件尚未生成。", e);
      return 0;
    }
  }

  /**
   * 获取绑定实体类以及一级关联类的全文索引名称集合。
   */
  private void initSearchFields() {
    // 获取实体本身声明的索引字段
    searchFields.putAll(getIndexedFields(entityClass));
    // 获取实体标注为关联索引对象中声明的索引字段名
    List<Field> embeddedEntityFields = BeanUtils.findField(entityClass, IndexedEmbedded.class);
    for (Field embeddedEntityField : embeddedEntityFields) {
      searchFields.putAll(getEmbeddedIndexedFields(embeddedEntityField));
    }
  }

  /**
   * 获取指定类中声明为索引字段的属性名称列表。
   * 
   * @param clazz 类
   * @return 返回指定类中声明为索引字段的属性名称列表。
   */
  private Map<String, Analyze> getIndexedFields(Class<?> clazz) {
    Map<String, Analyze> indexedFields = new LinkedHashMap<>();
    for (Field field : BeanUtils.findField(clazz, org.hibernate.search.annotations.Field.class)) {
      Class<?> fieldType = BeanUtils.getFieldType(field);
      if (fieldType == String.class) {
        String fieldName = field.getName();
        Analyze analyze =
            field.getAnnotation(org.hibernate.search.annotations.Field.class).analyze();
        indexedFields.put(fieldName, analyze);
      }
    }
    return indexedFields;
  }

  /**
   * 获取关联属性对象类中声明为索引字段的属性名称列表。
   * 
   * @param embeddedEntityField 关联属性
   * @return 返回关联属性对象类中声明为索引字段的属性名称列表。
   */
  private Map<String, Analyze> getEmbeddedIndexedFields(Field embeddedEntityField) {
    Map<String, Analyze> embeddedIndexedFields = new LinkedHashMap<>();
    String prefix = embeddedEntityField.getName() + ".";
    IndexedEmbedded indexedEmbedded = embeddedEntityField.getAnnotation(IndexedEmbedded.class);
    Class<?> embeddedEntityClass = BeanUtils.getFieldType(embeddedEntityField);
    for (String fieldName : indexedEmbedded.includePaths()) {
      Field field = BeanUtils.findField(embeddedEntityClass, fieldName);
      Class<?> fieldType = BeanUtils.getFieldType(field);
      if (fieldType == String.class && !fieldName.equals("id") && !fieldName.endsWith(".id")) {
        Analyze analyze =
            field.getAnnotation(org.hibernate.search.annotations.Field.class).analyze();
        embeddedIndexedFields.put(prefix + fieldName, analyze);
      }
    }

    return embeddedIndexedFields;
  }
}
