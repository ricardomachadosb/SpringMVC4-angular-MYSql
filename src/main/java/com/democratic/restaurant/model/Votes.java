package com.democratic.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ricardo Machado
 *
 */
@Entity
@Table(name="votes")
public class Votes {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="restaurant")
	Restaurant restaurant;
	
	@Column(name="voutsCount")
	private int voutsCount;

	/**
	 * @return
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	/**
	 * @return
	 */
	public int getVoutsCount() {
		return voutsCount;
	}

	/**
	 * @param voutsCount
	 */
	public void setVoutsCount(int voutsCount) {
		this.voutsCount = voutsCount;
	}
	
}
