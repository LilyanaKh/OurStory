package com.example.ourstoryapp.web;

import java.net.URI;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ourstoryapp.da.PictureRepository;
import com.example.ourstoryapp.domain.Picture;

@RestController
@RequestMapping("/pictures")
public class PictureController {

	@Autowired
	PictureRepository repository;
    Logger logger = LogManager.getLogger(PictureController.class);

	@GetMapping("/findAll")
	public Iterable<Picture> findAll() {
		logger.info("Find All Pictures");
		return repository.findAll();
	}

	@PostMapping("/create")
	public Picture create(@Valid @RequestBody Picture picture) {
		logger.info("Cretae Picture");
		return repository.save(picture);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Picture> findById(@PathVariable(value = "id") URI pictureId) {
		if(repository.findById(pictureId).map(record -> ResponseEntity.ok().body(record)).isPresent()) {
			logger.info("Find Pictrure By ID");
			return repository.findById(pictureId).map(record -> ResponseEntity.ok().body(record))
					.orElse(ResponseEntity.notFound().build());
		}
		else {
			logger.info("Picture By ID Is Not Found");
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") URI pictureId) {
		if(repository.findById(pictureId).map(record -> ResponseEntity.ok().body(record)).isPresent()) {
			logger.info("Successfully Deleted Picture");
			return repository.findById(pictureId).map(record -> {
				repository.deleteById(pictureId);
				return ResponseEntity.ok().build();
			}).orElse(ResponseEntity.notFound().build());
		}
		else {
			logger.info("Picture Is Not Existing");
			return ResponseEntity.notFound().build();
		}
	}

}
