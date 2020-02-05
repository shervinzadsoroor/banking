package com.shervinzadsoroor.repositories.interfaces;

public interface Validations {
    boolean isCardToCardInfoValid(Long[] info);

    boolean isPasswordValid(int password, Long sourceCardNumber);
}
