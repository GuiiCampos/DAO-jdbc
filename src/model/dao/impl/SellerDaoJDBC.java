package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    //Inserir
    @Override
    public void insert(Seller obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?) ",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());

            int rowsAffected= ps.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()){
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);

            }else {
                throw new DbException("Error, no rows affected");
            }

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
    }

    //Atualizar
    @Override
    public void update(Seller obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                            "UPDATE seller " +
                                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                                "WHERE Id = ?"
            );
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());
            ps.setInt(6, obj.getId());

            int rowsAffected= ps.executeUpdate();
            if (rowsAffected == 0){
                throw new DbException("Error, no rows affected");
            }

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
    }

    //Deletar
    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "DELETE FROM seller " +
                            "WHERE Id = ?"
            );
            ps.setInt(1, id);

            int rowsAffected= ps.executeUpdate();
            if (rowsAffected == 0){
                throw new DbException("Error, no rows affected");
            }

        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
    }

    //Encontrar por ID
    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller .*,department.Name AS DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ? "
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller sel = instantiateSeller(rs, dep);

                return sel;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    //Retornar Tudo
    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name "
            );
            rs = ps.executeQuery();

            List<Seller> allSellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dp = map.get(rs.getInt("DepartmentId"));
                if (dp == null) {
                    dp = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dp);
                }
                Seller sel = instantiateSeller(rs, dp);
                allSellers.add(sel);
            }
            return allSellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    //Encontrar por Departamento
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name "
            );
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dp = map.get(rs.getInt("DepartmentId"));
                if (dp == null) {
                    dp = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dp);
                }
                Seller sel = instantiateSeller(rs, dp);
                sellers.add(sel);
            }
            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBirthDate(rs.getDate("BirthDate"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setDepartment(dep);
        return sel;
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
