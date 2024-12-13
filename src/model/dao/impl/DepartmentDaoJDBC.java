package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection con;

    public DepartmentDaoJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Department dep) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
              "INSERT INTO department " +
                  "(Name) VALUES (?) ",
                  Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, dep.getName());
            int rowsAff= ps.executeUpdate();

            if (rowsAff > 0) {

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    dep.setId(rs.getInt(1));
                }
                DB.closeStatement(ps);

            }else {
                throw new DbException("Erro ao inserir um departamento");
            }

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department obs) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department selectById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }
}
