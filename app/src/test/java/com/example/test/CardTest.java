package com.example.test;

import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;


public class CardTest {

    //WRITTEN BY ALEX
    @Test
    public void equals(){
        Card card1 = new Card(CARDTYPE.DEFUSE);
        Card card2 = new Card(card1);
        Assert.assertEquals(true,card1.equals(card2));

    }
}
