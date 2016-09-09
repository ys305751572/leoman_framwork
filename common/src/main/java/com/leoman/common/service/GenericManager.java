package com.leoman.common.service;

import org.hibernate.criterion.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface GenericManager<T> {

    EntityManager getEntityManager();

    /****
     * 查询分页实现
     *
     * @param entity 实体类
     * @return 实体Page
     */
    Page<T> query(T entity, int start, int length);

    /**
     * 按照指定字段查询指定条数的记录
     * @param sort 排序字段
     * @param isAsc 是否升序
     * @param count 查询数量
     * @return list
     */
    List<T> queryTop(String sort, boolean isAsc, int count);

    /****
     * 分页规则构建
     * if(StringUtils.isNotBlank(czexpert.getName())){
     * filters.put("name",new SearchFilter("name", Operator.LIKE,czexpert.getName()) );
     * }
     * if(StringUtils.isNotBlank(czexpert.getTelephone())){
     * filters.put("telephone",new SearchFilter("telephone",Operator.LIKE, czexpert.getTelephone()) );
     * }
     *
     * @return Specification
     */
    Specification<T> buildSpecification(T t);

    /**
     * 查询所有的对象
     * @return list
     */
    List<T> queryAll();

    /**
     * 查询所有对象，并进行排序
     *
     * @param orderBy 排序属性
     * @param isAsc   是否asc
     * @return list
     */
    List<T> queryAll(String orderBy, boolean isAsc);

    /**
     * 根据查询语句进行查询
     *
     * @param hql    查询语句
     * @param values 语句对应的值
     * @return list
     */
    List<T> query(String hql, Object... values);

    /**
     * 根据类属性进行查询
     *
     * @param propertyName 属性名
     * @param value        属性对应的值
     * @return list
     */
    List<T> queryByProperty(String propertyName, Object value);

    /**
     * 根据类属性进行查询（支持排序）
     *
     * @param propertyName 属性名
     * @param value        属性对应的值
     * @param orderBy      排序字段
     * @param isAsc        是否增序
     * @return list
     */
    List<T> queryByProperty(String propertyName, Object value, String orderBy,
                            boolean isAsc);

    /**
     * 根据类属性进行查询，返回唯一对象
     *
     * @param propertyName 属性名
     * @param value        属性对应的值
     * @return entity
     */
    T queryUniqueBy(String propertyName, Object value);

    /**
     * 根据条件查询
     *
     * @param criterions
     * @return list
     */
    List<T> query(Criterion... criterions);

    /**
     * 根据条件查询
     * @param predicate
     * @return list
     */
    List<T> query(Predicate... predicate);

    /**
     *
     * @param entity
     * @param uniquePropertyName
     * @return list
     */
    boolean isUnique(Object entity, String... uniquePropertyName);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return entity
     */
    T queryByPK(Serializable id);

    /**
     * 对象是否存在
     *
     * @param id 主键
     * @return boolean
     */
    boolean exists(Serializable id);

    /**
     * 新增对象保存
     *
     * @param object entity
     * @return entity
     */
    T insert(T object);

    /**
     * 保存
     *
     * @param object entity
     * @return entity
     */
    T save(T object);

    /**
     * 批量保存
     *
     * @param list list entity
     * @return null
     */
    T saveList(List<T> list);


    /**
     * 批量更新
     * @param list list entity
     */
    void updateList(List<T> list);

    /**
     * 更新对象保存
     *
     * @param object entity
     * @return entity
     */
    T update(T object);

    /**
     * 根据主键删除对象
     * @param id 主键
     */
    void deleteByPK(Serializable id);

    /**
     * 根据主键删除对象
     *
     * @param ids 主键集合
     */
    void deleteByPKs(Serializable[] ids);

    /**
     * 删除对象
     *
     * @param object
     */
    void delete(T object);

    void deletes(Collection<T> objects);

    String getIdName();

    Class getIdClass();

    Class getEntityClass();

    /**
     * 根据条件查询前N条记录
     *
     * @param sort
     * @param propertyName
     * @param value
     * @param isAsc
     * @param count
     * @return
     */
    List<T> queryTop(String sort, final String propertyName, final Object value, boolean isAsc, int count);

    /**
     * 根据条件查询某个实体的列表
     *
     * @param firstindex 开始行
     * @param maxresult  结束行
     * @param sql        sql语句
     * @return
     * @author zb
     */
    Page<T> scroll(final int firstindex, final int maxresult, final String sql);

    /**
     * 根据条件查询数量
     *
     * @param query
     * @return
     */
    Long getCount(Query query);

    Integer getCountSql(String sql);

    /**
     * 根据条件查询数据集合（带分页）
     *
     * @param query
     * @return
     */
    Page<T> queryPage(Query query);

    List<T> queryAll(Query query);

    /**
     * 根据jpql语句查询
     * @param jpql
     * @return
     */
    List<T> queryByJpql(String jpql,Class<T> clazz);

    /**
     * 根据jpql语句查询
     * @param jpql
     * @return
     */
    Integer updateByJpql(String jpql);

    /**
     * 根据原生态sql查询
     * @param sql
     * @return
     */
    List<T> queryBySql(String sql,Class<T> clazz);

    /**
     * 根据原生态sql更新
     * @param sql
     * @return
     */
    Integer updateBySql(String sql);

    Page queryPageByJpql(String jpql, int pageNo, int rowsPerPage);
}
