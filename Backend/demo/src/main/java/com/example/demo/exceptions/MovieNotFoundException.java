package com.example.demo.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String s){
        super(s);
    }
}
