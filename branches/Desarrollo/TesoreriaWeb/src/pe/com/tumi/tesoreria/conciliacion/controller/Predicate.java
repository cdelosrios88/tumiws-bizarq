package pe.com.tumi.tesoreria.conciliacion.controller;

import java.util.Collection;

public interface Predicate<T> {
	boolean apply(T type);
}

