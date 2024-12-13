package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntergrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
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
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?"
            );
            ps.setString(1, obs.getName());
            ps.setInt(2, obs.getId());

            ps.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department selectById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(
                    "SELECT * FROM department WHERE Id = ? "
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            Department dep = new Department();
            if (rs.next()) {
                dep.setId(id);
                dep.setName(rs.getString("Name"));
            }
            return dep;

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(
                    "SELECT * FROM department ORDER BY Name"
            );
            rs = ps.executeQuery();
            List<Department> departs = new ArrayList<>();

            while (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt(1));
                dep.setName(rs.getString(2));
                departs.add(dep);
            }
            return departs;

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}