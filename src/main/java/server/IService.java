package server;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: guohezuzi
 * \* Date: 2018-07-27
 * \* Time: 下午2:40
 * \* Description:通用service接口
 * \
 */
@Service
public interface IService<T> {
    /**
     *根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param key 主键
     * @return 对应实体**/
    T queryByKey(Object key);

    /**保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @param entity 对应实体
     * @return 是否插入成功
     * **/
    int save(T entity);

    /**根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key 主键
     * @return 是否删除成功
     * **/
    int delete(Object key);

    /**根据主键更新实体全部字段，null值会被更新
     * @param entity 对应实体
     * @return 是否更新成功
     * **/
    int updateAll(T entity);

    /**根据主键更新不为空的数据
     * @param entity 对应实体
     * @return 是否更新成功*/
    int updateNotNull(T entity);

    /**查询所有的数据
     * @return 查询的数据
     * **/
    List<T> queryAll();

}
