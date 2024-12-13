import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("======seller findById========");
        Seller sell1 = sellerDao.findById(7);
        System.out.println(sell1);


        System.out.println("\n======seller findByDepartmentId========");
        Department dp1 = new Department(2, null);
        List<Seller> sellersByDep = sellerDao.findByDepartment(dp1);
        sellersByDep.forEach(System.out::println);


        System.out.println("\n======seller findAll========");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);


        System.out.println("\n======seller Insert========");
        Department dp2 = new Department(3, null);
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dp2);
        sellerDao.insert(newSeller);
        System.out.println("New ID: " + newSeller.getId());


        System.out.println("\n======seller Update========");
        Seller updSeller = sellerDao.findById(8);
        updSeller.setName("Carlos Silva");
        updSeller.setEmail("Carlos@gmail.com");
        sellerDao.update(updSeller);


        System.out.println("\n======seller Delete========");
        try { //Caso acesse um ID não existente
            sellerDao.deleteById(11);
            System.out.println("Delete Completed");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}