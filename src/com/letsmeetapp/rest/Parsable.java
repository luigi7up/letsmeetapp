package com.letsmeetapp.rest;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 03.06.13.
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public interface Parsable<E> {
    public ArrayList<E> parse(RESTResponse resource);

}
