import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Department obs = new Department(1, "teste");
        Seller seller = new Seller(21, "Bob", "Bob@gmail.com", new Date(), 3000.0, obs);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
    }
}