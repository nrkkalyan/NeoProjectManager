/*
 * Copyright (c) 2010. Alessandro Mecca <alessandro.mecca@gmail.com>
 * All rights reserved.
 *
 * See LICENSE file for details.
 */

package com.neoprojectmanager.controller;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by IntelliJ IDEA.
 * User: xan
 * Date: Apr 30, 2010
 * Time: 9:51:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class WicketBoot extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return Hello.class;
    }
}
