package br.com.crm.controller.exceptionhandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Fabrica do Manipulador de Exceptions do JSF. (vai configurado em faces-config.xml)
 * @author Solkam
 * @since 26 abr 2016
 */
public class MyExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	public MyExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new MyExceptionHandler(parent.getExceptionHandler());
	}

}
