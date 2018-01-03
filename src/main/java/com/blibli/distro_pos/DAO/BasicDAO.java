package com.blibli.distro_pos.DAO;

import java.util.List;

public interface BasicDAO<T, S> {
    public List<T> getAll();
    public T getOne(S id);
    public void save(T t);
    public void update(T t);
    public void delete(S id);
    public void softDelete(S id);
    public int count();
    public List<T> paginate(int page);
}
