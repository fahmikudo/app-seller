package id.web.fahmikudo.test.appseller.repository;

import id.web.fahmikudo.test.appseller.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller, String> {

    Seller findBySellerName(String name);

}
