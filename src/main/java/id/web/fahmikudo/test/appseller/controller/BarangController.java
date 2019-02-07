package id.web.fahmikudo.test.appseller.controller;


import id.web.fahmikudo.test.appseller.model.Barang;
import id.web.fahmikudo.test.appseller.service.BarangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController("barang")
@RequestMapping("/api/v1/")
public class BarangController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarangController.class);

    @Autowired
    private BarangService barangService;

    //create barang
    @RequestMapping(
            value = "barangs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Barang> createSeller(@RequestBody Barang barang, UriComponentsBuilder ucBuilder) {
        LOGGER.debug(">>> Creating barang with id: " + barang.getId());
        if (barangService.findById(barang.getId()).isPresent()) {
            LOGGER.debug("An barang with id " + barang.getId() + "exist.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        barangService.save(barang);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("barangs/{id}").buildAndExpand(barang.getId()).toUri());
        return new ResponseEntity<>(barang, headers, HttpStatus.CREATED);
    }

    //get all barang
    @RequestMapping(
            value = "barangs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Barang>> listBarangs(){
        LOGGER.debug("Received request to list all barangs");
        List<Barang> barangs = barangService.findAll();
        if (barangs.isEmpty()){
            LOGGER.debug("barangs do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(barangs, HttpStatus.OK);

    }




}
