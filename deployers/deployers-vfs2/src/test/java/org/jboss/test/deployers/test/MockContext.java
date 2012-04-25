/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.test.deployers.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.Reference;

import org.jboss.util.naming.NonSerializableFactory;

/**
 * @author Marius Bogoevici
 */
class MockContext implements Context {

    private static Map<String, Object> registered = new HashMap<String, Object>();

    private static Hashtable<Object, Object> environment = new Hashtable<Object, Object>();

    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        environment.put(propName, propVal);
        return null;
    }

    public Object lookup(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object lookup(String name) throws NamingException {
        Object entry = registered.get(name);
        if (entry instanceof Reference) {
            return NonSerializableFactory.lookup(name);
        }
        if (entry == null) {
            throw new NameNotFoundException();
        }
        return entry;
    }

    public void bind(Name name, Object obj) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void bind(String name, Object obj) throws NamingException {
        Object entry = registered.get(name);
        if (entry == null) {
            throw new NameAlreadyBoundException();
        }
        if (obj instanceof Reference) {
            ((Reference) obj).get("nns");
        }
        registered.put(name, obj);
    }

    public void rebind(Name name, Object obj) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void rebind(String name, Object obj) throws NamingException {
        registered.put(name, obj);
    }

    public void unbind(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void unbind(String name) throws NamingException {
        registered.remove(name);
    }

    public void rename(Name oldName, Name newName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void rename(String oldName, String newName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void destroySubcontext(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void destroySubcontext(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Context createSubcontext(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Context createSubcontext(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object lookupLink(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object lookupLink(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NameParser getNameParser(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NameParser getNameParser(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Name composeName(Name name, Name prefix) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public String composeName(String name, String prefix) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object removeFromEnvironment(String propName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return environment;
    }

    public void close() throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNameInNamespace() throws NamingException {
        throw new UnsupportedOperationException();
    }

    public static Map<String, Object> getRegistered() {
        return registered;
    }

    public static void reset() {
        environment = new Hashtable<Object, Object>();
        registered = new HashMap<String, Object>();
    }
}
