package org.springframework.cluedo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private GameRepository gameRepository;
	private UserService userService;
    @Autowired
	public GameService(GameRepository gameRepository, UserService userService) {
		this.gameRepository = gameRepository;
		this.userService=userService;
	}
    //Admin
	//H12
    @Transactional(readOnly = true)
	public List<Game> findAllActiveGames() throws DataAccessException {
		return gameRepository.findAllActiveGames();
	}
	//H13
	@Transactional(readOnly=true)
	public List<Game> findAllPastGames(){
		return gameRepository.findAllPastGames();
	}

	
	
	//User
	//H10
	@Transactional(readOnly=true)
	public List<Game> findAllActivePublicGames(){
		return gameRepository.findAllActivePublicGames();
	}
	//H11
	@Transactional(readOnly=true)
	public List<Game> findAllPastUserGames(Integer userId) {
		List<Game> res= new ArrayList<>();
		gameRepository.findAllById(gameRepository.findAllPastUserGames(userId)).forEach(x->res.add(x));
		return res;
	} 
	//H1
	@Transactional
	public void saveGame(Game game){
		gameRepository.save(game);
	}

	@Transactional(readOnly=true)
	public Optional<Game> getGameById(Integer id){
		return gameRepository.findById(id);
	}
}
