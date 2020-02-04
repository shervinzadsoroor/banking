package com.shervinzadsoroor.repositories.interfaces;

import com.shervinzadsoroor.models.CreditCard;

public interface CreditCardRepository {
    void create();

    CreditCard retrieve(Long cardId);

    void update(Long cardId);

    void delete(Long cardId);

    void editFirstPass(Long cardId, Long newPass);

    void assignSecondPass(Long cardId, Long secondPass);

    void editSecondPass(Long cardId, Long newPass);

    void cardToCard(Long originCardNumber, Long destinationCardNumber, Long amount);
}
