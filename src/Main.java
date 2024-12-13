import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("======Test seller findById========");
        Seller sell = sellerDao.findById(3);
        System.out.println(sell);

        System.out.println("\n======Test seller findByDepartmentId========");
        Department dp = new Department(2, null);
        List<Seller> sellers = sellerDao.findByDepartment(dp);
        sellers.forEach(System.out::println);

        System.out.println("\n======Test seller findAll========");
        sellers = sellerDao.findAll();
        sellers.forEach(System.out::println);

        System.out.println("\n======Test seller Insert========");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dp);
        sellerDao.insert(newSeller);
        System.out.println("New ID: " + newSeller.getId());
    }
}