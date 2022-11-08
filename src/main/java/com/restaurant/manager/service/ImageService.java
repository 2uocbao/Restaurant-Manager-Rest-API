package com.restaurant.manager.service;

import java.util.Optional;

import com.restaurant.manager.model.Image;

public interface ImageService {
	public boolean uploadPhoto(Image photo);

	public Optional<Image> displayPhoto(String userId);

	public boolean updateImage(Image image);

	public boolean deleteImage(String userId);
}
