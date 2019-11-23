package com.perfree.mapper;

import com.perfree.module.Table;
import com.perfree.module.TableField;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableMapper {
    List<Table> queryAllTable();

    List<TableField> queryFieldForTable(String table_name);
}
