import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("======Test seller findById========");
        Seller sell = sellerDao.findById(3);
        System.out.println(sell);

        System.out.println("\n======Test seller findByDepartmentId========");
        Department dp = new Department(4, null);
        List<Seller> sellers = sellerDao.findByDepartment(dp);
        sellers.forEach(System.out::println);
    }
}