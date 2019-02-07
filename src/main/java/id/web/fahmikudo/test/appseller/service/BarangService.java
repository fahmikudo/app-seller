package id.web.fahmikudo.test.appseller.service;

import id.web.fahmikudo.test.appseller.model.Barang;

import java.util.List;
import java.util.Optional;

public interface BarangService {

    Barang save(Barang barang);

    Optional<Barang> findById(String id);

    List<Barang> findAll();

    Barang update(Barang barang);

    void delete(String id);

    void deleteAll();


}
