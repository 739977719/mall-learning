package com.dz.ftsp.codelab.service;

import com.dz.ftsp.codelab.dao.cl.TCIUserMapper;
import com.dz.ftsp.codelab.model.cl.TCIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TCIUserService extends ClBaseService {

    @Autowired
    private TCIUserMapper tciUserMapper;

    public boolean login(String username, String password) {
        TCIUser user = tciUserMapper.findByUsername(username);
        if (user != null && user.getUserPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
