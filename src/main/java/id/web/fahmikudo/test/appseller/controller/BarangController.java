package id.web.fahmikudo.test.appseller.controller;


import id.web.fahmikudo.test.appseller.Exception.CustomError;
import id.web.fahmikudo.test.appseller.model.Barang;
import id.web.fahmikudo.test.appseller.service.BarangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    //update barang
    @RequestMapping(
            value = "barangs/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Barang> updateBarang(@PathVariable String id, @RequestBody Barang barang){
        LOGGER.debug(">>> Updating barang with id: " + id);
        Barang currentBarang = barangService.findById(id).get();
        if (currentBarang == null){
            LOGGER.debug("<<< Barangs with id: " + id + ", not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentBarang.setBarangName(barang.getBarangName());
        currentBarang.setBarangCategory(barang.getBarangCategory());
        currentBarang.setBarangStock(barang.getBarangStock());
        currentBarang.setBarangPrice(barang.getBarangPrice());
        currentBarang.setSellers(barang.getSellers());

        barangService.update(currentBarang);
        return new ResponseEntity<>(currentBarang, HttpStatus.OK);

    }

    @RequestMapping(
            value = "barangs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Barang> deleteBarang(@PathVariable String id){
        LOGGER.debug("Fetching & Deleting barang with id: " + id + " is successfully removed from database!");
        Barang barang = barangService.findById(id).get();
        if (barang == null){
            LOGGER.debug("Unable to delete. Barang with id: " + id + ", not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        barangService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(
            value = "barangs",
            method = RequestMethod.DELETE)
    public ResponseEntity<Barang> deleteAllBarangs(){
        barangService.deleteAll();
        LOGGER.debug("Removed all barangs from database!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExistsException(CustomError exception) {
        return exception.getMessage();
    }




}
