package com.perfree.service;

import com.perfree.mapper.TableMapper;
import com.perfree.module.Table;
import com.perfree.module.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    @Autowired(required = false)
    private TableMapper tableMapper;

    public List<Table> queryAllTable() {
        return tableMapper.queryAllTable();
    }

    public List<TableField> queryFieldForTable(String table_name) {
        return tableMapper.queryFieldForTable(table_name);
    }
}
