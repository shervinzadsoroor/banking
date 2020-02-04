package com.shervinzadsoroor.repositories.impls;

import com.shervinzadsoroor.models.CreditCard;
import com.shervinzadsoroor.repositories.interfaces.CreditCardRepository;

public class CreditCardRepositoryImpl implements CreditCardRepository {
    @Override
    public void create() {

    }

    @Override
    public CreditCard retrieve(Long cardId) {
        return null;
    }

    @Override
    public void update(Long cardId) {

    }

    @Override
    public void delete(Long cardId) {

    }

    @Override
    public void editFirstPass(Long cardId, Long newPass) {

    }

    @Override
    public void assignSecondPass(Long cardId, Long secondPass) {

    }

    @Override
    public void editSecondPass(Long cardId, Long newPass) {

    }

    @Override
    public void cardToCard(Long originCardNumber, Long destinationCardNumber, Long amount) {

    }
}
