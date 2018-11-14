package com.tokengeneration.components.services;

@FunctionalInterface
public interface CustomValidator<T> {

	boolean isValid();

}
