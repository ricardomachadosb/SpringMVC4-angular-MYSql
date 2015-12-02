package com.democratic.restaurant.dao.impl;

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
import com.democratic.restaurant.model.WeekWinner;

/**
 * @author Ricardo Machado
 *
 */
@Repository
@Transactional
public class RestaurantDaoImpl implements RestaurantDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Restaurant> list() {
		Criteria criteria = getSession().createCriteria(Restaurant.class);
		
		@SuppressWarnings("unchecked")
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
		Criteria criteria = getSession().createCriteria(Votes.class);
		
		@SuppressWarnings("unchecked")
		List<Votes> votes = (List<Votes>) criteria.list();
		for(Votes vt: votes){
			getSession().delete(vt);
		}
	}

	@Override
	public void clearWeekWinnersHistory() {
		Criteria criteria = getSession().createCriteria(WeekWinner.class);
		
		@SuppressWarnings("unchecked")
		List<WeekWinner> weekWinners = (List<WeekWinner>) criteria.list();
		for(WeekWinner ww: weekWinners){
			getSession().delete(ww);
		}
	}

	@Override
	public List<WeekWinner> getWeekWinners() {
		Criteria criteria = getSession().createCriteria(WeekWinner.class);
		
		@SuppressWarnings("unchecked")
		List<WeekWinner> weekWinners = (List<WeekWinner>) criteria.list();
		
		return weekWinners;
	}

	@Override
	public Map<Restaurant, Integer> getResultMap() {
		Map<Restaurant, Integer> resultMap = new LinkedHashMap<Restaurant, Integer>();
		
		Criteria criteria = getSession().createCriteria(Votes.class);
		
		@SuppressWarnings("unchecked")
		List<Votes> votes = (List<Votes>) criteria.list();
		
		for(Votes vt: votes){
			resultMap.put(vt.getRestaurant(), vt.getVoutsCount());
		}
		
		return resultMap;
		
	}

	@Override
	public void addWeekWinner(Restaurant restaurant) {
		WeekWinner ww = new WeekWinner();
		ww.setRestaurant(restaurant);
		getSession().save(ww);
	}
}
