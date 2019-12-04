package com.perfree.mapper;

import com.perfree.module.Table;
import com.perfree.module.TableField;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TableMapper {
    List<Table> queryAllTable();
    Table queryTableByName(String tableName);
    List<TableField> queryFieldForTable(String table_name);
}
