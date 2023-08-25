package com.micro.service.app.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.service.app.exception.UserNotFoundException;
import com.micro.service.app.model.Rating;
import com.micro.service.app.model.User;
import com.micro.service.app.repository.UserRepository;
import com.micro.service.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public User saveUser(User user, String authTokenHeader) {

		User userResp = userRepository.save(user);
		List<Rating> ratingList = user.getRating();
		// System.out.println("UserServiceImpl.saveUser() rating0"+ratingList);
		String url = "http://localhost:9094/rating/save";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authTokenHeader);

		ArrayList<Rating> ratingResplist = new ArrayList<Rating>();

		for (Rating rating : ratingList) {

			rating.setUserId(user.getUserId());
			HttpEntity<Rating> entity = new HttpEntity<>(rating, headers);
			ResponseEntity<Rating> retingResp = restTemplate.exchange(url, HttpMethod.POST, entity, Rating.class);
			ratingResplist.add(retingResp.getBody());
		}
		userResp.setRating(ratingResplist);

		return userResp;
	}

	@Override
	public List<User> getAllData(String authTokenHeader) {
		String url = "http://localhost:9091/rating/getAllData";
		List<User> response = userRepository.findAll();
		System.out.println("UserServiceImpl.getAllData()" + authTokenHeader);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authTokenHeader);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		for (User user : response) {
			System.out.println("UserServiceImpl.getAllData()" + user);
			ResponseEntity<Rating[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
					Rating[].class);
			Rating[] ratingArray = responseEntity.getBody();
			List<Rating> ratings = Arrays.asList(ratingArray);
			Map<String, List<Rating>> collect = ratings.stream().collect(Collectors.groupingBy(Rating::getUserId));

			List<Rating> associatedRatings = collect.getOrDefault(user.getUserId(), Collections.emptyList());
			user.setRating(associatedRatings);
		}
		return response;
	}

	@Override
	public User getDataById(String userId, String authTokenHeader) throws UserNotFoundException  {
	
		String url = "http://localhost:9091/rating/get/" + userId + "";
		User userResp=null;
			userResp = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
			System.out.println("UserServiceImpl.getAllData()" + authTokenHeader);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authTokenHeader);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<Rating[]> forObject = restTemplate.exchange(url, HttpMethod.GET, entity, Rating[].class);
			System.out.println("UserServiceImpl.getDataById()" + forObject);
			Rating[] ratingArray = forObject.getBody();
			List<Rating> ratings = Arrays.asList(ratingArray);
			userResp.setRating(ratings);
		
		return userResp;
	}

}
