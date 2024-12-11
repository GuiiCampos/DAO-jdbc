package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insert (Department obs);
    void update (Department obs);
    void deleteById (Integer id);
    Department selectById (Integer id);
    List<Department> findAll ();
}
