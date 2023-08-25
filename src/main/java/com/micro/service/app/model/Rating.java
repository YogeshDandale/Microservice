package com.micro.service.app.model;




import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

	private String ratingId;
	private String userId;
	private String rating;
	private String feedback;
	@Transient
	private Hotel hotel;
	
}
