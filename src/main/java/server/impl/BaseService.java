package server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import server.IService;
import util.MyMapper;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: guohezuzi
 * \* Date: 2018-07-27
 * \* Time: 下午3:34
 * \* Description:通用service的通用实现抽象类
 * \
 */
public abstract class BaseService<T> implements IService<T> {
    private final
    MyMapper<T> mapper;

    @Autowired
    public BaseService(MyMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public T queryByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }
}
