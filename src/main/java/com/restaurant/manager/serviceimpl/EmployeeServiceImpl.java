package com.restaurant.manager.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.manager.model.Branch;
import com.restaurant.manager.model.Employee;
import com.restaurant.manager.model.Restaurant;
import com.restaurant.manager.repository.BranchRepository;
import com.restaurant.manager.repository.EmployeeRepository;
import com.restaurant.manager.repository.RestaurantRepository;
import com.restaurant.manager.request.EmployeeRequest;
import com.restaurant.manager.sercurity.CheckService;
import com.restaurant.manager.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	BranchRepository branchRepository;

	private CheckService checkService = new CheckService();
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private String success = "success";
	private String nosuccess = "no success";

	@Override
	public String createEmployee(EmployeeRequest employeeRequest) {
		String message = checkInfor(employeeRequest);
		if (!message.equals(success)) {
			return message;
		} else if (employeeRepository.getEmployeeByEmail(employeeRequest.getEmail()) != null) {
			return "Email already in use";
		} else if (employeeRepository.detailEmployee(employeeRequest.getPhone()) != null) {
			return "Phone number already in use";
		} else {
			Restaurant restaurant = restaurantRepository.detailRestaurant(employeeRequest.getRestaurantId());
			Branch branch = employeeRequest.getBranchId() == null ? null
					: branchRepository.detailBranch(employeeRequest.getBranchId());
			Employee employee = new Employee();
			employee.setId(employeeRequest.getPhone().trim());
			employee.setRestaurant(restaurant);
			employee.setBranch(branch);
			employee.setFirstName(employeeRequest.getFirstName().replaceAll("\\s+", " ").trim());
			employee.setLastName(employeeRequest.getLastName().replaceAll("\\s+", " ").trim());
			employee.setFullName(employeeRequest.getFullName().replaceAll("\\s+", " ").trim());
			employee.setGender(employeeRequest.getGender().trim());
			employee.setDateOfBirth(employeeRequest.getDateOfbirth());
			employee.setImage(employeeRequest.getImage());
			employee.setEmail(employeeRequest.getEmail().trim());
			employee.setPhone(employeeRequest.getPhone().trim());
			employee.setRole(employeeRequest.getRole());
			employee.setCity(employeeRequest.getCity().replaceAll("\\s+", " ").trim());
			employee.setDistrict(employeeRequest.getDistrict().replaceAll("\\s+", " ").trim());
			employee.setAddress(employeeRequest.getAddress().replaceAll("\\s+", " ").trim());
			employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
			employee.setStatus(1);
			boolean successful = employeeRepository.createEmployee(employee);
			return successful ? success : nosuccess;
		}
	}

	@Override
	public EmployeeRequest detailEmployee(String employeeId) {
		EmployeeRequest employeeRequest = new EmployeeRequest();
		Employee employee = employeeRepository.detailEmployee(employeeId);
		if (employee != null) {
			employeeRequest.setEmployeeId(employee.getId());
			employeeRequest.setRestaurantId(employee.getRestaurant().getId());
			employeeRequest.setBranchId(employee.getBranch() != null ? employee.getBranch().getId() : null);
			employeeRequest.setFirstName(employee.getFirstName());
			employeeRequest.setLastName(employee.getLastName());
			employeeRequest.setFullName(employee.getFullName());
			employeeRequest.setGender(employee.getGender());
			employeeRequest.setDateOfbirth(employee.getDateOfBirth());
			employeeRequest.setImage(employee.getImage());
			employeeRequest.setEmail(employee.getEmail());
			employeeRequest.setPhone(employee.getPhone());
			employeeRequest.setRole(employee.getRole());
			employeeRequest.setCity(employee.getCity());
			employeeRequest.setDistrict(employee.getDistrict());
			employeeRequest.setAddress(employee.getAddress());
			employeeRequest.setStatus(employee.getStatus());
		} else {
			return null;
		}
		return employeeRequest;
	}

	@Override
	public String updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
		String message = checkInfor(employeeRequest);
		Employee employee = employeeRepository.detailEmployee(employeeId);
		if (!message.equals(success)) {
			return message;
		} else if (!employee.getEmail().equals(employeeRequest.getEmail())
				&& employeeRepository.getEmployeeByEmail(employeeRequest.getEmail()) != null) {
			return "Email already in use";
		} else if (employeeRepository.getEmployeeByPhone(employeeRequest.getPhone()) != null
				&& !employee.getPhone().equals(employeeRequest.getPhone())) {
			return "Phone number already in use";
		} else {
			employee.setFirstName(employeeRequest.getFirstName().replaceAll("\\s+", " ").trim());
			employee.setLastName(employeeRequest.getLastName().replaceAll("\\s+", " ").trim());
			employee.setFullName(employeeRequest.getFullName().replaceAll("\\s+", " ").trim());
			employee.setGender(employeeRequest.getGender().trim());
			employee.setDateOfBirth(employeeRequest.getDateOfbirth());
			employee.setImage(employeeRequest.getImage());
			employee.setEmail(employeeRequest.getEmail().trim());
			employee.setPhone(employeeRequest.getPhone().trim());
			employee.setRole(employeeRequest.getRole());
			employee.setCity(employeeRequest.getCity().replaceAll("\\s+", " ").trim());
			employee.setDistrict(employeeRequest.getDistrict().replaceAll("\\s+", " ").trim());
			employee.setAddress(employeeRequest.getAddress().replaceAll("\\s+", " ").trim());
			boolean successful = employeeRepository.updateEmployee(employee);
			return successful ? success : nosuccess;
		}
	}

	@Override
	public String deleteEmployee(String employeeId) {
		return employeeRepository.deleteEmployee(employeeId) ? success : "No success";
	}

	@Override
	public List<EmployeeRequest> listEmpoyeeByResIdOrBranId(String restaurantId, String branchId) {
		List<EmployeeRequest> employeeRequests = new ArrayList<>();
		List<Employee> employees = employeeRepository.listEmpoyeeByResIdOrBranId(restaurantId,
				branchId == null ? "" : branchId);
		for (Employee employee : employees) {
			EmployeeRequest employeeRequest = new EmployeeRequest();
			employeeRequest.setEmployeeId(employee.getId());
			employeeRequest.setRestaurantId(employee.getRestaurant().getId());
			employeeRequest.setBranchId(branchId);
			employeeRequest.setFirstName(employee.getFirstName());
			employeeRequest.setLastName(employee.getLastName());
			employeeRequest.setFullName(employee.getFullName());
			employeeRequest.setGender(employee.getGender());
			employeeRequest.setDateOfbirth(employee.getDateOfBirth());
			employeeRequest.setImage(employee.getImage());
			employeeRequest.setEmail(employee.getEmail());
			employeeRequest.setPhone(employee.getPhone());
			employeeRequest.setRole(employee.getRole());
			employeeRequest.setCity(employee.getCity());
			employeeRequest.setDistrict(employee.getDistrict());
			employeeRequest.setAddress(employee.getAddress());
			employeeRequest.setStatus(employee.getStatus());
			employeeRequests.add(employeeRequest);
		}
		return employeeRequests;
	}

	@Override
	public String changePasswordEmployee(String employeeId, String password) {
		return employeeRepository.changePasswordEmployee(employeeId, passwordEncoder.encode(password)) ? success
				: "No success";
	}

	@Override
	public String changeStatusEmployee(String employeeId) {
		Employee employee = employeeRepository.detailEmployee(employeeId);
		if (employee.getRestaurant().getStatus() == 0) {
			return "The restaurant is not working";
		} else if (employee.getBranch().getStatus() == 0) {
			return "The branch is not working";
		} else if (employee.getStatus() == 0) {
			employeeRepository.changeStatusEmployee(employeeId, 1);
			return "Active";
		}
		employeeRepository.changeStatusEmployee(employeeId, 0);
		return "Inactive";
	}

	// override from userdetailservice
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.getEmployeeByPhone(username);
		if (employee == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return User.withUsername(employee.getEmail()).password(employee.getPassword()).authorities(employee.getRole())
				.build();
	}

	@SuppressWarnings("unused")
	private Collection<GrantedAuthority> getAuthorities(Employee user) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return authorities;
	}

	public String checkInfor(EmployeeRequest employeeRequest) {
		if (!checkService.checkName(employeeRequest.getFirstName())
				|| !checkService.checkName(employeeRequest.getLastName())
				|| !checkService.checkName(employeeRequest.getFullName())) {
			return "Invalid name";
		} else if (!checkService.checkPhone(employeeRequest.getPhone())) {
			return "Invalid phone number";
		} else if (!checkService.isValidEmail(employeeRequest.getEmail())) {
			return "Invalid mail";
		}
		return success;
	}

	@Override
	public Employee getEmployeeByPhone(String phone) {
		return employeeRepository.getEmployeeByPhone(phone);
	}
}
