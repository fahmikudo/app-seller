package id.web.fahmikudo.test.appseller.service;

import id.web.fahmikudo.test.appseller.model.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

    //boolean isExist(Seller seller);

    Seller save(Seller seller);

    Optional<Seller> findById(String id);

    List<Seller> findAll();

    Seller update(Seller seller);

    void delete(String id);

    void deleteAll();


}
