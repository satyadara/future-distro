package com.blibli.distro_pos.DAO.outcome.Interface;

import com.blibli.distro_pos.DAO.BasicDAO;
import com.blibli.distro_pos.Model.outcome.Outcome;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface OutcomeInterface extends BasicDAO<Outcome, String> {

    void setActive(String id);

    List<Outcome> getOutcomeList(ResultSet rs);

    Map<String, Object> search(String key, int page);

    String getStringList();
}
