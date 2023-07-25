package com.dz.ftsp.codelab.dao.cl;

import com.dz.ftsp.codelab.model.cl.TClProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TClProductMapper {

    TClProduct getById(@Param("id") Long id);

    List<TClProduct> getAllProduct();
}