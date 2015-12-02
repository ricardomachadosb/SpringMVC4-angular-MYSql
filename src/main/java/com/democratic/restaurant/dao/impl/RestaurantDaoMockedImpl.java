package com.democratic.restaurant.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.democratic.restaurant.dao.RestaurantDao;
import com.democratic.restaurant.model.Restaurant;
import com.democratic.restaurant.model.Votes;

/**
 * @author Ricardo Machado
 *
 */
@Repository
@Transactional
public class RestaurantDaoMockedImpl implements RestaurantDao{
	
	private static List<Restaurant> restaurantList = null;
	private static Map<Integer, Integer> votes = new HashMap<Integer, Integer>();
	private static List<Restaurant> weekWinners = new ArrayList<Restaurant>();
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Restaurant> list() {
		Criteria criteria = getSession().createCriteria(Restaurant.class);
		List<Restaurant> restaurants = (List<Restaurant>) criteria.list();
		
		return restaurants;
	}
	
	@Override
	public Restaurant get(Integer id) {
		Restaurant restaurant = (Restaurant) getSession().get(Restaurant.class, id);
		return restaurant;
	}
	
	@Override
	public void vote(Restaurant restaurant) {
		
		Criteria criteria = getSession().createCriteria( Votes.class );
		criteria.createCriteria( "restaurant", "r");
		criteria.add( Restrictions.eq( "r.id", restaurant.getId()));
		Votes votes = (Votes) criteria.uniqueResult();
		
		
		if(votes != null){
			votes.setVoutsCount(votes.getVoutsCount() + 1);
		}else {
			votes = new Votes();
			votes.setRestaurant(restaurant);
			votes.setVoutsCount(1);
		}
		
		getSession().save(votes);
	}
	
	@Override
	public void clearCurrentVoteData() {
		votes.clear();
	}

	@Override
	public void clearWeekWinnersHistory() {
		weekWinners.clear();
	}

	@Override
	public List<Restaurant> getWeekWinners() {
		return weekWinners;
	}

	@Override
	public Map<Restaurant, Integer> getResultMap() {
		Map<Restaurant, Integer> resultMap = new LinkedHashMap<Restaurant, Integer>();
		
		for(Map.Entry<Integer, Integer> entry: votes.entrySet()){
			resultMap.put(get(entry.getKey()), entry.getValue());
		}
		
		return resultMap;
	}

	@Override
	public void addWeekWinner(Restaurant restaurant) {
		weekWinners.add(restaurant);
	}
}
