package com.example.art.fuzzySearch;

import com.example.art.info.ScanItem;

import java.util.List;

public interface FilterListener {
    void getFilterData(List<ScanItem> list);// 获取过滤后的数据
}
