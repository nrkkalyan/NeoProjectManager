/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.controller;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;


/**
 * Created by IntelliJ IDEA.
 * User: xan
 * Date: Apr 30, 2010
 * Time: 9:38:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hello extends WebPage {
    public Hello() {
        add(new Label("title", "Hello Manager"));

    }
}
