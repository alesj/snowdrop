/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.tutorial.web;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;

import org.jboss.spring.samples.horoscope.ejb.Horoscope;
import org.jboss.spring.samples.horoscope.ejb.Randomizer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:ales.justin@genera-lynx.com">Ales Justin</a>
 */
@Controller
public class AppController {

    private
    @EJB(mappedName = "horoscope/RandomizerBean/local")
    Randomizer randomizer;

    private
    @EJB(mappedName = "horoscope/HoroscopeBean/local")
    Horoscope horoscope;

    @RequestMapping("/number")
    public ModelAndView numberHandler(@RequestParam("radius") int radius)
            throws Exception {
        return new ModelAndView("main", "number", randomizer.getNumber(radius));
    }

    @RequestMapping("/word")
    public ModelAndView wordHandler(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new ModelAndView("main", "word", randomizer.getWord());
    }

    @RequestMapping("/horoscope")
    public Map horoscopeHandler(@RequestParam(value = "clear", required = false) String clear,
                                @RequestParam("month") int month) throws Exception {
        Map map = new HashMap();
        if (clear != null) {
            horoscope.clear();
        } else {
            map.put("horoscope", horoscope.getHoroscope(month));
        }
        return map;
    }

}
