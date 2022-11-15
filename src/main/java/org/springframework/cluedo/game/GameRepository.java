package org.springframework.cluedo.game;

import java.util.List;

import org.springframework.cluedo.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends CrudRepository<Game,Integer>{
     List<Game> findAll() throws DataAccessException;
     //User
     //H10
     @Query("select g from Game g where g.status=org.springframework.cluedo.enumerates.Status.LOBBY AND g.isPrivate = false")
     List<Game> findAllActivePublicGames();
     //H11
     @Query(nativeQuery = true, value="SELECT g.id FROM Games g JOIN Players p ON g.id=p.game_id JOIN user_games ug ON ug.id=p.players_id WHERE ug.user_id = :id AND g.status=2")
     Iterable<Integer> findAllPastUserGames(@Param("id") Integer id);
     //Admin
     //H12
     @Query("select g from Game g where g.status NOT LIKE org.springframework.cluedo.enumerates.Status.FINISHED")
     List<Game> findAllActiveGames();
     //H13
     @Query("select g from Game g WHERE g.status=org.springframework.cluedo.enumerates.Status.FINISHED")
     List<Game> findAllPastGames();
}
