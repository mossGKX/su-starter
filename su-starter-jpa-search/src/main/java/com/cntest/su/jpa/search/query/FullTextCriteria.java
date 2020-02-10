package com.cntest.su.jpa.search.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.HibernateException;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.springframework.util.Assert;

import com.cntest.su.utils.BeanUtils;
import com.cntest.su.utils.CollectionUtils;
import com.cntest.su.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Hibernate Search全文搜索查询条件构造器。
 */
@Slf4j
@Getter
@Setter
public class FullTextCriteria {
  private FullTextEntityManager entityManager;
  private Class<?> entityClass;
  /** 搜索关键字 */
  private String keyword;
  /** 待搜索的字段 */
  private Map<String, Analyze> searchFields = new LinkedHashMap<>();
  /** 排序字段 */
  private List<SortField> sortFields = new ArrayList<>();
  /** 附加的Lucene查询条件列表 */
  private List<AttachLuceneQuery> luceneQueries = new ArrayList<>();
  /** 是否优先从二级缓存中获取数据 */
  private Boolean lookupCache = true;

  /**
   * 构造方法。
   * 
   * @param entityManager Hibernate全文搜索EntityManager
   * @param entityClass 实体class
   * @param searchFields 待搜索的字段
   */
  public FullTextCriteria(FullTextEntityManager entityManager, Class<?> entityClass,
      Map<String, Analyze> searchFields) {
    this.entityManager = entityManager;
    this.entityClass = entityClass;
    if (CollectionUtils.isNotEmpty(searchFields)) {
      this.searchFields.putAll(searchFields);
    }
  }

  /**
   * 清空全文搜索字段。
   */
  public void clearSearchFields() {
    searchFields.clear();
  }

  /**
   * 添加全文搜索字段。
   * 
   * @param fieldNames 字段名称
   */
  public void addSearchField(String... fieldNames) {
    for (String fieldName : fieldNames) {
      Field field = BeanUtils.findField(entityClass, fieldName);
      Analyze analyze = field.getAnnotation(org.hibernate.search.annotations.Field.class).analyze();
      searchFields.put(fieldName, analyze);
    }
  }

  /**
   * 添加全文搜索字段。
   * 
   * @param fieldName 字段名称
   * @param analyze 是否分词
   */
  public void addSearchField(String fieldName, Analyze analyze) {
    searchFields.put(fieldName, analyze);
  }

  /**
   * 移除全文搜索字段。
   * 
   * @param fieldNames 字段名称
   */
  public void removeSearchField(String... fieldNames) {
    for (String fieldName : fieldNames) {
      searchFields.remove(fieldName);
    }
  }

  /**
   * 增加等于过滤条件。
   * 
   * @param fieldName 字段名
   * @param fieldValue 字段值
   */
  public void eq(String fieldName, Object fieldValue) {
    TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
    addLuceneQuery(query, Occur.MUST);
  }

  /**
   * 增加等于过滤条件（多个字段值）。
   * 
   * @param fieldName 字段名
   * @param fieldValues 字段值
   */
  public void eq(String fieldName, List<?> fieldValues) {
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    for (Object fieldValue : fieldValues) {
      TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
      builder.add(query, Occur.SHOULD);
    }
    addLuceneQuery(builder.build(), Occur.MUST);
  }

  /**
   * 增加等于过滤条件（多个字段值）。
   * 
   * @param fieldName 字段名
   * @param fieldValues 字段值
   */
  public void eq(String fieldName, Object[] fieldValues) {
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    for (Object fieldValue : fieldValues) {
      TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
      builder.add(query, Occur.SHOULD);
    }
    addLuceneQuery(builder.build(), Occur.MUST);
  }

  /**
   * 增加不等于过滤条件。
   * 
   * @param fieldName 字段名
   * @param fieldValue 字段值
   */
  public void notEq(String fieldName, Object fieldValue) {
    TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
    addLuceneQuery(query, Occur.MUST_NOT);
  }

  /**
   * 增加不等于过滤条件（多个字段值）。
   * 
   * @param fieldName 字段名
   * @param fieldValues 字段值
   */
  public void notEq(String fieldName, List<?> fieldValues) {
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    for (Object fieldValue : fieldValues) {
      TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
      builder.add(query, Occur.SHOULD);
    }
    addLuceneQuery(builder.build(), Occur.MUST_NOT);
  }

  /**
   * 增加不等于过滤条件（多个字段值）。
   * 
   * @param fieldName 字段名
   * @param fieldValues 字段值
   */
  public void notEq(String fieldName, Object[] fieldValues) {
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    for (Object fieldValue : fieldValues) {
      TermQuery query = new TermQuery(new Term(fieldName, fieldValue.toString()));
      builder.add(query, Occur.SHOULD);
    }
    addLuceneQuery(builder.build(), Occur.MUST_NOT);
  }

  /**
   * 增加区间字段查询条件。
   * 
   * @param fieldName 字段名
   * @param lowerTerm 最小值
   * @param upperTerm 最大值
   */
  public void between(String fieldName, String lowerTerm, String upperTerm) {
    TermRangeQuery query =
        TermRangeQuery.newStringRange(fieldName, lowerTerm, upperTerm, true, false);
    addLuceneQuery(query, Occur.MUST);
  }

  /**
   * 增加区间字段查询条件。
   * 
   * @param fieldName 字段名
   * @param lowerTerm 最小值
   * @param upperTerm 最大值
   */
  public void between(String fieldName, Integer lowerTerm, Integer upperTerm) {
    NumericRangeQuery<Integer> query =
        NumericRangeQuery.newIntRange(fieldName, lowerTerm, upperTerm, true, false);
    addLuceneQuery(query, Occur.MUST);
  }

  /**
   * 增加Lucene查询条件。
   * 
   * @param query Lucene查询条件
   * @param occur Lucene查询条件关系
   */
  public void addLuceneQuery(Query query, Occur occur) {
    if (query != null && occur != null) {
      luceneQueries.add(new AttachLuceneQuery(query, occur));
    }
  }

  /**
   * 增加顺序排列的排序字段。<br>
   * 类型请参考SortField的公共常量。
   * 
   * @param fieldName 字段名
   * @param type 类型
   */
  public void asc(String fieldName, SortField.Type type) {
    asc(fieldName, type, null);
  }

  /**
   * 增加顺序排列的排序字段。<br>
   * 类型请参考SortField的公共常量。
   * 
   * @param fieldName 字段名
   * @param type 类型
   * @param missingValue 为空设定值
   */
  public void asc(String fieldName, SortField.Type type, Object missingValue) {
    SortField sortField = new SortField(fieldName, type);
    if (missingValue != null) {
      sortField.setMissingValue(missingValue);
    }
    sortFields.add(sortField);
  }

  /**
   * 增加倒序排列的排序字段。<br>
   * 类型请参考SortField的公共常量。
   * 
   * @param fieldName 字段名
   * @param type 类型
   */
  public void desc(String fieldName, SortField.Type type) {
    desc(fieldName, type, null);
  }

  /**
   * 增加倒序排列的排序字段。<br>
   * 类型请参考SortField的公共常量。
   * 
   * @param fieldName 字段名
   * @param type 类型
   * @param missingValue 为空设定值
   */
  public void desc(String fieldName, SortField.Type type, Object missingValue) {
    SortField sortField = new SortField(fieldName, type, true);
    if (missingValue != null) {
      sortField.setMissingValue(missingValue);
    }
    sortFields.add(sortField);
  }

  /**
   * 生成Hibernate的FullTextQuery全文搜索对象。
   * 
   * @return 返回Hibernate的FullTextQuery全文搜索对象。
   */
  public FullTextQuery toFullTextQuery() {
    FullTextQuery fullTextQuery =
        entityManager.createFullTextQuery(generateLuceneQuery(), entityClass);
    if (!sortFields.isEmpty()) {
      fullTextQuery.setSort(new Sort(sortFields.toArray(new SortField[] {})));
    }
    if (lookupCache) {
      fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE,
          DatabaseRetrievalMethod.QUERY);
    }
    return fullTextQuery;
  }

  /**
   * 生成多字段查询对象。
   * 
   * @param query 查询条件
   * @param fields 查询字段
   * @return 返回多字段查询对象。
   */
  public Query toLuceneQuery(String query, Map<String, Analyze> fields) {
    Assert.notEmpty(fields, "必须指定查询的字段。");
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    for (Entry<String, Analyze> field : fields.entrySet()) {
      if (field.getValue() == Analyze.NO) {
        Term term = new Term(field.getKey(), "*" + query + "*");
        WildcardQuery fuzzyQuery = new WildcardQuery(term);
        builder.add(fuzzyQuery, Occur.SHOULD);
      } else {
        QueryParser parser = new QueryParser(field.getKey(),
            entityManager.getSearchFactory().getAnalyzer(entityClass));
        parser.setPhraseSlop(0);
        parser.setAutoGeneratePhraseQueries(true);
        try {
          builder.add(parser.parse(query), Occur.SHOULD);
        } catch (ParseException e) {
          throw new HibernateException("生成多字段查询时发生异常", e);
        }
      }
    }
    return builder.build();
  }

  /**
   * 生成多字段查询对象。
   * 
   * @param query 查询条件
   * @param fieldNames 查询字段
   * @return 返回多字段查询对象。
   */
  public Query generateMultiFieldQuery(String query, String[] fieldNames) {
    Assert.notEmpty(fieldNames, "必须指定查询的字段。");
    Map<String, Analyze> fields = new LinkedHashMap<>();
    for (String fieldName : fieldNames) {
      fields.put(fieldName, searchFields.get(fieldName));
    }
    return toLuceneQuery(query, fields);
  }

  /**
   * 根据当前设置生成Lucene查询对象。
   * 
   * @return 返回Lucene查询对象。
   */
  private Query generateLuceneQuery() {
    BooleanQuery.Builder builder = new BooleanQuery.Builder();
    // 如果关键字为空，则匹配任意记录
    if (StringUtils.isEmpty(keyword)) {
      builder.add(new WildcardQuery(new Term("id", "*")), Occur.MUST);
    } else {
      // 支持空格分隔多个关键词，之间是“与”的关系
      for (String key : keyword.trim().split(" ")) {
        builder.add(toLuceneQuery(QueryParser.escape(key), searchFields), Occur.MUST);
      }
    }
    log.debug("全文搜索包含字段：{}", searchFields.keySet());
    for (AttachLuceneQuery attachLuceneQuery : luceneQueries) {
      builder.add(attachLuceneQuery.getQuery(), attachLuceneQuery.getOccur());
    }
    BooleanQuery query = builder.build();
    log.debug("全文搜索[{}]查询语句：{}", entityClass.getSimpleName(), query);
    return query;
  }

  /**
   * 附加Lucene查询条件。
   */
  private class AttachLuceneQuery {
    /** Lucene查询条件 */
    private Query query;
    /** Lucene查询条件关系 */
    private Occur occur;

    /**
     * 构造方法。
     * 
     * @param query Lucene查询条件
     * @param occur Lucene查询条件关系
     */
    public AttachLuceneQuery(Query query, Occur occur) {
      this.query = query;
      this.occur = occur;
    }

    public Query getQuery() {
      return query;
    }

    public Occur getOccur() {
      return occur;
    }
  }
}
