package com.restaurant.manager.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Image;
import com.restaurant.manager.repository.ImageRepository;
import com.restaurant.manager.service.ImageService;

@Service
class ImageServiceImpl implements ImageService {
	@Autowired
	ImageRepository imageRepository;

	@Override
	public boolean uploadPhoto(Image image) {
		return imageRepository.uploadImage(image);

	}

	@Override
	public Optional<Image> displayPhoto(String userId) {
		return Optional.ofNullable(imageRepository.displayImagebyUserId(userId));
	}

	@Override
	public boolean updateImage(Image image) {
		return imageRepository.updateImage(image);
	}

	@Override
	public boolean deleteImage(String userId) {
		return imageRepository.deleteImage(userId);
	}
}
