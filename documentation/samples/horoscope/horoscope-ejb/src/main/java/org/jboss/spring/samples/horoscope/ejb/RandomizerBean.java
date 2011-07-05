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
package org.jboss.spring.samples.horoscope.ejb;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.annotation.spring.Spring;
import org.jboss.spring.samples.horoscope.ejb.Randomizer;
import org.jboss.spring.samples.horoscope.spring.IntCreator;
import org.jboss.spring.samples.horoscope.spring.WordsCreator;
import org.jboss.spring.callback.SpringLifecycleInterceptor;
import org.jboss.ejb3.annotation.LocalBinding;

/**
 * @author <a href="mailto:ales.justin@genera-lynx.com">Ales Justin</a>
 */
@Stateless
@Interceptors(org.jboss.spring.callback.SpringLifecycleInterceptor.class)
public class
        RandomizerBean implements Randomizer {

    private WordsCreator wordsCreator;

    @Spring(jndiName = "horoscopeContext", bean = "stateIntCreator")
    private IntCreator intCreator;

    public WordsCreator getWordsCreator() {
        return wordsCreator;
    }

    @Spring(jndiName = "horoscopeContext", bean = "staticWordsCreator")
    public void setWordsCreator(WordsCreator wordsCreator) {
        this.wordsCreator = wordsCreator;
    }

    public int getNumber(int radius) {
        return intCreator.createInt(radius);
    }

    public String getWord() {
        return getWordsCreator().createWord();
    }

}
