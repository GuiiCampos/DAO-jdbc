package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao dpDao = DaoFactory.createDepartmentDao();

        System.out.println("======Department Insert======");
        Department dp1 = new Department(null, "Music");
        //dpDao.insert(dp1);
        System.out.println("Insert Successfully");


        System.out.println("\n======Department Update======");
        Department dp3 = dpDao.selectById(7);
        dp3.setName("E Sport");
        dpDao.update(dp3);
        System.out.println("Update Successfully");


        System.out.println("\n======Department FindById======");
        Department dp2 = dpDao.selectById(3);
        System.out.println(dp2);
    }
}