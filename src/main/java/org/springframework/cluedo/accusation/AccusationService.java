package org.springframework.cluedo.accusation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.card.Card;
import org.springframework.cluedo.user.UserGame;
import org.springframework.stereotype.Service;

@Service
public class AccusationService {
    private AccusationRepository accusationRepository;
    private CrimeSceneRepository crimeSceneRepository;

    @Autowired
    public AccusationService(AccusationRepository accusationRepository, CrimeSceneRepository crimeSceneRepository) {
        this.accusationRepository = accusationRepository;
        this.crimeSceneRepository = crimeSceneRepository;
    }
    
    public Accusation saveAccusation(Accusation accusation) {
        return accusationRepository.save(accusation);
    }

    public CrimeScene saveCrimeScene(CrimeScene crimeScene) {
        return crimeSceneRepository.save(crimeScene);
    }

    public List<Card> getMatchingCardsFromUser(Accusation accusation, UserGame user) {
        List<Card> matchingCards = new ArrayList<Card>();
        Card roomCard = accusation.getRoomCard();
        Card suspectCard = accusation.getSuspectCard();
        Card weaponCard = accusation.getWeaponCard();
        
        for(Card card:user.getCards()) {
            if(card.equals(weaponCard) || card.equals(roomCard) || card.equals(suspectCard)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

}
