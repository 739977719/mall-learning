package com.dz.ftsp.codelab.service;

import com.dz.ftsp.codelab.dao.cl.TClCategoryMapper;
import com.dz.ftsp.codelab.model.cl.TClCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TClCategoryService extends ClBaseService {

   @Autowired
   private TClCategoryMapper tClCategoryMapper;

    public List<TClCategory> getAll() {
        return tClCategoryMapper.getAll();
    }
}