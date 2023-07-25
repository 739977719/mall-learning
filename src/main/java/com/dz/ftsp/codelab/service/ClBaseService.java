package com.dz.ftsp.codelab.service;

import com.dz.ftsp.codelab.manager.ClCommoditiesManager;
import com.dz.ftsp.codelab.manager.ClLogManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * service基类
 */
public abstract class ClBaseService {

    @Autowired
    ClLogManager logManager;

    public ClLogManager getClLogManager() {
        return logManager;
    }

    @Autowired
    ClCommoditiesManager commoditiesManager;

    public ClCommoditiesManager getClCommoditiesManager(){return commoditiesManager;}
    }

