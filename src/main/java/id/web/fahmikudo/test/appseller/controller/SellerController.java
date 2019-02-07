package id.web.fahmikudo.test.appseller.controller;


import id.web.fahmikudo.test.appseller.Exception.CustomError;
import id.web.fahmikudo.test.appseller.model.Seller;
import id.web.fahmikudo.test.appseller.service.SellerService;
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
import java.util.Optional;

@RestController("seller")
@RequestMapping("/api/v1/")
public class SellerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService sellerService;

    //create seller
    @RequestMapping(
            value = "sellers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller, UriComponentsBuilder ucBuilder) {
        LOGGER.debug(">>> Creating seller with id: " + seller.getId());
        if (sellerService.findById(seller.getId()).isPresent()) {
            LOGGER.debug("An seller with id " + seller.getId() + "exist.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        sellerService.save(seller);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("sellers/{id}").buildAndExpand(seller.getId()).toUri());
        return new ResponseEntity<>(seller, headers, HttpStatus.CREATED);
    }

    //get by id
    @RequestMapping(
            value = "sellers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Seller> getSeller(@PathVariable String id){
        LOGGER.debug("Fetching seller with id: " + id);
        Optional<Seller> s = sellerService.findById(id);
        if (s == null) {
            LOGGER.debug("Author with id: " + id + ", not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(s.get(), HttpStatus.OK);

    }

    //get all seller
    @RequestMapping(
            value = "sellers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Seller>> listSellers(){
        LOGGER.debug("Received request to list all sellers");
        List<Seller> sellers = sellerService.findAll();
        if (sellers.isEmpty()){
            LOGGER.debug("sellers do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sellers, HttpStatus.OK);

    }

    //update seller
    @RequestMapping(
            value = "sellers/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Seller> updateSeller(@PathVariable String id, @RequestBody Seller seller){
        LOGGER.debug(">>> Updating seller with id: " + id);
        Seller currentSeller = sellerService.findById(id).get();
        if (currentSeller == null){
            LOGGER.debug("<<< Sellers with id: " + id + ", not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentSeller.setSellerName(seller.getSellerName());
        currentSeller.setSallerDateofBirth(seller.getSallerDateofBirth());
        currentSeller.setSellerKTP(seller.getSellerKTP());
        currentSeller.setSellerNoHp(seller.getSellerNoHp());

        sellerService.update(currentSeller);
        return new ResponseEntity<>(currentSeller, HttpStatus.OK);

    }

    //delete seller
    @RequestMapping(
            value = "sellers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Seller> deleteSeller(@PathVariable String id){
        LOGGER.debug("Fetching & Deleting seller with id: " + id + " is successfully removed from database!");
        Seller seller = sellerService.findById(id).get();
        if (seller == null){
            LOGGER.debug("Unable to delete. Seller with id: " + id + ", not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sellerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //delete all seller
    @RequestMapping(
            value = "authors",
            method = RequestMethod.DELETE)
    public ResponseEntity<Seller> deleteAllSellers() {
        sellerService.deleteAll();
        LOGGER.debug("Removed all sellers from database!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExistsException(CustomError exception) {
        return exception.getMessage();
    }

}
