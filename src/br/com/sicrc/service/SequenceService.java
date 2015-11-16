package br.com.sicrc.service;

import java.util.List;

import javax.faces.model.SelectItem;

public interface SequenceService {

	
	List<SelectItem> organizarSequence() throws IllegalArgumentException, Exception;
}
