package com.perfree.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;
import java.util.*;

/**
 * 动态切换数据源,存储了当前sessionID和数据源
 * @author YinPengFei
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    // 数据源集合
    public static List<Map<String,Object>> datasource = new ArrayList<>();

    /**
     * 设置数据源
     * @param dataSource 数据源
     */
    public static void setDataSource(DataSource dataSource) {
        String sessionId =((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getId();
        Map<String,Object> param = new HashMap<>();
        param.put("dataSource", dataSource);
        param.put("sessionId", sessionId);
        DynamicDataSource.datasource.add(param);
    }

    /**
     * 获取数据源
     * @return DataSource
     */
    public static DataSource getDataSource() {
        String sessionId =((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getId();
        for (Map<String,Object> param: DynamicDataSource.datasource) {
            if(param.get("sessionId").equals(sessionId)){
                return (DataSource) param.get("dataSource");
            }
        }
        return null;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
    @Override
    protected DataSource determineTargetDataSource() {
        return getDataSource();
    }

    /**
     * 清空数据源
     */
    public static void clear() {
        if(DynamicDataSource.datasource != null && DynamicDataSource.datasource.size() > 0){
            String sessionId =((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getId();
            DynamicDataSource.datasource.removeIf(stringObjectMap -> stringObjectMap.get("sessionId").equals(sessionId));
        }
    }
}
