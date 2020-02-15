package com.fayou.dao;

import com.fayou.domain.Account;

import java.util.List;

public interface IAccountDao {
    List<Account> findAll();
}
