package com.blibli.distro_pos.DAO.ledger.Interface;

import com.blibli.distro_pos.Model.ledger.Ledger;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface LedgerInterface {
    List<Ledger> getIndex();

    List<Ledger> getFilter(String date_from, String date_to, int offset);

    double getTotalIn(String date_from, String date_to);

    double getTotalOut(String date_from, String date_to);

    List<Ledger> paginate(int page);

    int count(String date_from, String date_to);
}
