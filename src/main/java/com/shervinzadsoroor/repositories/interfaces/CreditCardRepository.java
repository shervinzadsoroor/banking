package com.shervinzadsoroor.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shervinzadsoroor.models.CreditCard;

public interface CreditCardRepository {
    void create();

    CreditCard retrieve(Long cardId);

    void update(Long cardId);

    void delete(Long cardId);

    void changeFirstPass(String newPass) throws JsonProcessingException;

    void assignSecondPass(String idStr) throws JsonProcessingException;

    void editSecondPass(String jsonSecondPass) throws JsonProcessingException;

    void cardToCard(String info) throws JsonProcessingException;

    String cardToCardInfo() throws JsonProcessingException;

    boolean isPasswordValid(String idAndPass) throws JsonProcessingException;
}
