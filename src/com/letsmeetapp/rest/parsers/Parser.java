package com.letsmeetapp.rest.parsers;

import com.google.gson.JsonParseException;
import com.letsmeetapp.rest.RESTResponse;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: luka
 * Date: 03.06.13.
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public interface Parser {

    public void parse(RESTResponse resource);
    public Object getParsedResult();

}
