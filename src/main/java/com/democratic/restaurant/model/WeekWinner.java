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
@Table(name="week_winners")
public class WeekWinner {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="restaurant")
	Restaurant restaurant;
	
	public int getId() {
		return id;
	}
	
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
	
}
