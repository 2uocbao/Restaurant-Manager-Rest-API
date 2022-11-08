package com.restaurant.manager.repository;

import com.restaurant.manager.model.Image;

public interface ImageRepository {
	public boolean uploadImage(Image image);

	public Image displayImagebyUserId(String userId);

	public boolean updateImage(Image image);

	public boolean deleteImage(String userId);
}
