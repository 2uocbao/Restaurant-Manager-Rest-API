package com.restaurant.manager.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.manager.model.Image;
import com.restaurant.manager.service.ImageService;
import com.restaurant.manager.utils.PhotoUtils;

@RestController
@RequestMapping("/image")
public class PhotoController {
	@Autowired
	ImageService photoService;

	@PostMapping("/upload")
	ResponseEntity<Object> uploadPhoto(@RequestParam("photo") MultipartFile multipartFile,
			@RequestParam("userId") String userId) throws IOException {
		photoService.uploadPhoto(
				Image.builder().userId(userId).image(PhotoUtils.compressImage(multipartFile.getBytes())).build());
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}

	@GetMapping("/display")
	ResponseEntity<Object> displayPhoto(@RequestParam("userId") String userId) {
		Optional<Image> photodb = photoService.displayPhoto(userId);
		byte[] photo = PhotoUtils.decompressImage(photodb.get().getImage());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(photo);
	}
}
