package com.blibli.distro_pos.DAO.cashier.Interface;

import com.blibli.distro_pos.Model.cashier.Transaction;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface TransactionInterface {
    List<Transaction> getAll();

    Transaction getOne(String id);

    void save(Transaction transaction);

    void update(Transaction transaction);

    List<Transaction> getTransactionList(ResultSet rs);

    String getListString();

    String getTransID();

    List<Transaction> paginate(int page);

    int count();

    Map<String, Object> search(String key, int page);
}
