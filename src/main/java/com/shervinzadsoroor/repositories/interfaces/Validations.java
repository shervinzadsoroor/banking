package com.shervinzadsoroor.repositories.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Validations {
    boolean isCardToCardInfoValid(String info) throws JsonProcessingException;

    boolean isPasswordValid(int password, String info) throws JsonProcessingException;

    boolean isAccountActive(String info) throws JsonProcessingException;
}
