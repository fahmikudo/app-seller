package id.web.fahmikudo.test.appseller.service;

import id.web.fahmikudo.test.appseller.Exception.CustomError;
import id.web.fahmikudo.test.appseller.model.Seller;
import id.web.fahmikudo.test.appseller.repository.SellerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional
public class SellerServiceImpl implements SellerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellerRepo sellerRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Seller save(Seller seller) {
        LOGGER.debug("Save {}", seller);
        Optional<Seller> s = sellerRepo.findById(seller.getId());
        if (s.isPresent()){
            throw new CustomError(String.format("There already exists a seller with id = ", seller.getId()));
        }
        return sellerRepo.save(seller);
    }

    @Override
    public Optional<Seller> findById(String id) {
        LOGGER.debug("Search seller by id: " + id);
        return sellerRepo.findById(id);
    }

    @Override
    public List<Seller> findAll() {
        LOGGER.debug("Retrieve the list of all sellers!");
        return sellerRepo.findAll();
    }

    @Override
    public Seller update(Seller seller) {
        LOGGER.debug("seller with id: " + seller.getId() + " updated!");
        if (!entityManager.contains(seller)){
            seller = entityManager.merge(seller);
        }
        return seller;
    }

    @Override
    public void delete(String id) {
        LOGGER.debug("seller with id: " + id + " deleted!");
        Seller s = sellerRepo.findById(id).get();
        sellerRepo.delete(s);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("The list all seller deleted!");
        sellerRepo.deleteAll();
    }

}
