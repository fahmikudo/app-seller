package id.web.fahmikudo.test.appseller.service;

import id.web.fahmikudo.test.appseller.Exception.CustomError;
import id.web.fahmikudo.test.appseller.model.Barang;
import id.web.fahmikudo.test.appseller.model.Seller;
import id.web.fahmikudo.test.appseller.repository.BarangRepo;
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
public class BarangServiceImpl implements BarangService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarangServiceImpl.class);

    @Autowired
    private BarangRepo barangRepo;

    @Autowired
    private SellerService sellerService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Barang save(Barang barang) {
        LOGGER.debug("Save {}", barang);
        Optional<Barang> b = barangRepo.findById(barang.getId());
        Optional<Seller> s = sellerService.findById(barang.getSellers().getId());

        boolean valid = false;
        if (b.isPresent() && s.isPresent()){
            valid = true;
        }
        if (valid){
            barang.setSellers(s.get());
            throw new CustomError(String.format("There already exists a barang with id = ", barang.getId()));
        }

        return barangRepo.save(barang);

    }

    @Override
    public Optional<Barang> findById(String id) {
        LOGGER.debug("Search barang by id: " + id);
        return barangRepo.findById(id);
    }

    @Override
    public List<Barang> findAll() {
        LOGGER.debug("Retrieve the list of all barangs!");
        return barangRepo.findAll();
    }

    @Override
    public Barang update(Barang barang) {
        LOGGER.debug("barang with id: " + barang.getId() + " updated!");
        if (!entityManager.contains(barang)){
            barang = entityManager.merge(barang);
        }
        return barang;
    }

    @Override
    public void delete(String id) {
        LOGGER.debug("barang with id: " + id + "deleted");
        Barang b = barangRepo.findById(id).get();
        barangRepo.delete(b);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("The list all barang deleted!");
        barangRepo.deleteAll();
    }
}
