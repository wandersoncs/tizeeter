package br.ufc.qx.tizeeter.controller;

import javax.servlet.http.HttpServlet;

public abstract class TizeeterGenericBaseHttpServlet<T> extends HttpServlet {
	
	private T dao;

	public T getDAO() {
		return dao;
	}

	public void setDAO(T dao) {
		this.dao = dao;
	}

}
