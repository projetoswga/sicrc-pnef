package br.com.arquitetura.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface UniversalManager extends Serializable {

	@SuppressWarnings("rawtypes")
	Object get(Class clazz, Serializable id);

	@SuppressWarnings("rawtypes")
	List getAll(Class clazz);

	@SuppressWarnings("rawtypes")
	Long getCount(Class clazz, Map filters) throws Exception;

	Long getCount(Object o);

	@SuppressWarnings("rawtypes")
	public List listBy(Object o, boolean like) throws Exception;

	Object save(Object o) throws Exception;

	@SuppressWarnings("rawtypes")
	List listBy(Object o) throws Exception;

	@SuppressWarnings("rawtypes")
	void remove(Class clazz, Serializable id) throws Exception;

	@SuppressWarnings("rawtypes")
	List paginate(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, Class clazz) throws Exception;

	@SuppressWarnings("rawtypes")
	Long getCount(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, Class clazz) throws Exception;

	@SuppressWarnings("rawtypes")
	List getAll(Class clazz, String campoOrdenacao);
}