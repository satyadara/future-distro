package com.blibli.distro_pos.DAO.cashier.Interface;

import com.blibli.distro_pos.Model.cashier.Transaction;

import java.sql.ResultSet;
import java.util.List;

public interface TransactionInterface {
    List<Transaction> getAll();

    Transaction getOne(String id);

    void save(Transaction transaction);

    void update(Transaction transaction);

    List<Transaction> getTransactionList(ResultSet rs);

    String getTransID();
}
