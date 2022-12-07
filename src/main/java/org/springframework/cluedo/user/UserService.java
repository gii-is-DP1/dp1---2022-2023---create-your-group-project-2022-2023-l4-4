
package org.springframework.cluedo.user;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.enumerates.SuspectType;
import org.springframework.cluedo.game.Game;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserGameService userGameService;

	@Autowired
	public UserService(UserRepository userRepository,UserGameService userGameService) {
		this.userRepository = userRepository;
		this.userGameService = userGameService;
	}
	@Transactional
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}


	@Transactional
	public void saveUser(User user) throws DataAccessException {
		userRepository.save(user);
	}
	
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}

	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> getLoggedUser(){
		UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}

	public UserDetails getUserDetails(){
		return(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
    public void initializePlayers(List<User> lobby, Game copy) { 
		List<SuspectType> suspects= new ArrayList<>(Arrays.asList(SuspectType.values()));
		List<Integer> orderList= IntStream.rangeClosed(1, lobby.size())
		.boxed().collect(Collectors.toList()); 
		for (User user : lobby) {
			Integer available = suspects.size();
			UserGame userGame = new UserGame();
			userGame.setAccusationsNumber(0);
			userGame.setGame(copy);
			userGame.setUser(user);
			userGame.setIsAfk(false);
			Integer suspect = ThreadLocalRandom.current().nextInt(available);
			userGame.setSuspect(suspects.get(suspect));
			suspects.remove(suspects.get(suspect));
			userGame.setCards(new HashSet<Card>());
			Integer order = ThreadLocalRandom.current().nextInt(orderList.size());
			userGame.setOrderUser(orderList.get(order));
			orderList.remove(orderList.get(order));
			userGameService.saveUserGame(userGame);
			copy.addPlayers(userGame);
		}
		
    }
}
