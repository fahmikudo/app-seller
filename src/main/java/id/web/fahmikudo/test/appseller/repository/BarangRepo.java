package id.web.fahmikudo.test.appseller.repository;

import id.web.fahmikudo.test.appseller.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangRepo extends JpaRepository<Barang, String> {
}
