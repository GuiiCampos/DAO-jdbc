package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao dpDao = DaoFactory.createDepartmentDao();

        System.out.println("======Department Insert======");
        Department department = new Department(null, "Music");
        dpDao.insert(department);
        System.out.println("Insert Successfully");

    }
}