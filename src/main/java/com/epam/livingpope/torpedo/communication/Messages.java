package com.epam.livingpope.torpedo.communication;

public interface Messages {
    void sendMessage(String message);

    void greeting(int size);
    
    void fire(int x, int y);
    
    void hit();
    
    void miss();
    
    void sunk();
    
    void win();
}
