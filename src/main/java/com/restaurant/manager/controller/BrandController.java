//package com.restaurant.manager.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.restaurant.manager.model.Brand;
//import com.restaurant.manager.request.BrandRequest;
//import com.restaurant.manager.service.BrandService;
//import com.restaurant.manager.service.CheckService;
//import com.restaurant.manager.service.RestaurantService;
//
//@RestController
//@RequestMapping("/brand")
//public class BrandController {
//
//	@Autowired
//	BrandService brandService;
//
//	@Autowired
//	RestaurantService restaurantService;
//
//	@Autowired
//	CheckService checkService;
//
//	@PostMapping("/create")
//	ResponseEntity<String> createBrand(@Valid @RequestParam(name = "restaurantId") String restaurantId,
//			@Valid @RequestBody BrandRequest brandRequest) {
//		String message;
//		Brand brand = brandService.detailBrand(restaurantId);
//		if (!checkService.checkName(brandRequest.getName())) {
//			return ResponseEntity.badRequest().body("Tên không hợp lệ");
//		} else if (!checkService.checkName(brandRequest.getBanner())) {
//			return ResponseEntity.badRequest().body("Banner không hợp lệ");
//		} else if (!checkService.checkName(brandRequest.getDescription())) {
//			return ResponseEntity.badRequest().body("Mô tả không được chứa kí tự đặc biệt");
//		} else if (brand != null) {
//			return ResponseEntity.badRequest().body("Nhà hàng này đã có nhãn hiệu");
//		} else {
//			Brand brandz = new Brand();
//			brandz.setRestaurantId(restaurantId);
//			brandz.setName(brandRequest.getName().replaceAll("//s+", " ").trim());
//			brandz.setBanner(brandRequest.getBanner().replaceAll("//s+", " ").trim());
//			brandz.setDescription(brandRequest.getDescription().replaceAll("//s+", " ").trim());
//			message = brandService.createBrand(brandz) ? "Tạo nhãn hiệu mới thành công" : "Không thành công";
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(message);
//	}
//
//	@GetMapping("/detail")
//	ResponseEntity<?> detailBrand(@Valid @RequestParam(name = "restaurantId") String restaurantId) {
//		Brand brand = brandService.detailBrand(restaurantId);
//		BrandRequest brandRequest = new BrandRequest();
//		if (brand == null) {
//			return ResponseEntity.badRequest().body("Nhãn hiệu không tồn tại");
//		} else {
//			brandRequest.setName(brand.getName());
//			brandRequest.setBanner(brand.getBanner());
//			brandRequest.setDescription(brand.getDescription());
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(brandRequest);
//	}
//
//	@PutMapping("/update")
//	ResponseEntity<String> updateBrand(@Valid @RequestParam(name = "restaurantId") String restaurantId,
//			@Valid @RequestBody BrandRequest brandRequest) {
//		String message;
//		Brand brand = brandService.detailBrand(restaurantId);
//		if (!checkService.checkName(brandRequest.getName())) {
//			return ResponseEntity.badRequest().body("Tên không hợp lệ");
//		} else if (!checkService.checkName(brandRequest.getBanner())) {
//			return ResponseEntity.badRequest().body("Banner không hợp lệ");
//		} else if (!checkService.checkName(brandRequest.getDescription())) {
//			return ResponseEntity.badRequest().body("Mô tả không được chứa kí tự đặc biệt");
//		} else {
//			brand.setRestaurantId(restaurantId);
//			brand.setName((brandRequest.getName().isEmpty() ? brand.getName() : brandRequest.getName())
//					.replaceAll("//s+", " ").trim());
//			brand.setBanner((brandRequest.getBanner().isEmpty() ? brand.getBanner() : brandRequest.getBanner())
//					.replaceAll("//s+", " ").trim());
//			brand.setDescription(
//					(brandRequest.getDescription().isEmpty() ? brand.getDescription() : brandRequest.getDescription())
//							.replaceAll("//s+", " ").trim());
//			message = brandService.updateBrand(brand) ? "Cập nhật thông tin thành công" : "Không thành công";
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(message);
//	}
//
//	@PutMapping("/change-status")
//	ResponseEntity<String> changeStatusBrand(@Valid @RequestParam(name = "restaurantId") String restaurantId) {
//		String message;
//		int statusRestaurant = restaurantService.getStatusById(restaurantId);
//		if (statusRestaurant == 0) {
//			return ResponseEntity.badRequest()
//					.body("Không thể trở lại hoạt động, vì nhà hàng chính đang tạm ngưng hoạt động");
//		} else {
//			boolean statusBrandNow = brandService.getStatusbyRestaurantId(restaurantId) ? false : true;
//			message = brandService.changeStatusBrand(restaurantId, statusBrandNow) ? "Nhãn hiệu đang hoạt động"
//					: "Nhãn hiệu tạm ngưng hoạt động";
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(message);
//	}
//}
