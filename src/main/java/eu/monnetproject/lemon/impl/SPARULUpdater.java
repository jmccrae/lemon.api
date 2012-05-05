/**
 * ********************************************************************************
 * Copyright (c) 2011, Monnet Project All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. * Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. * Neither the name of the Monnet Project nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE MONNET PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *******************************************************************************
 */
package eu.monnetproject.lemon.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * @author John McCrae
 */
public class SPARULUpdater implements RemoteUpdater {

    private static final String INSERT_INTO = "INSERT INTO";
    private static final String DELETE_FROM = "DELETE FROM";
    private final String url;
    private final URI graph;
    private final String username, password;

    /**
     * Create a connection to a SPARUL endpoint with no authentication
     *
     * @param url The URL of the endpoint, must include query parameter, e.g.,
     * "http://localhost:8080/sparql-auth?query="
     */
    public SPARULUpdater(String url, URI graph) {
        this.url = url;
        this.graph = graph;
        this.username = null;
        this.password = null;
    }

    /**
     * Create a connection to a SPARUL endpoint with authentication
     *
     * @param url The URL of the endpoint, must include query parameter, e.g.,
     * "http://localhost:8080/sparql-auth?query="
     * @param username The user name (or null for no authentication)
     * @param password The pass word (or null for no authentication)
     */
    public SPARULUpdater(String url, URI graph, String username, String password) {
        this.url = url;
        this.graph = graph;
        this.username = username;
        this.password = password;
    }

    private void prepSPARULUpdate(String action, String subject, String predicate, String object) throws IOException {
        StringBuilder query = new StringBuilder(action).append("<").append(graph.toString()).append("> { ");
        query.append(subject).append(" ");
        query.append(predicate).append(" ");
        query.append(object).append(" }");
        doSPARULUpdate(query);
    }

    private void doSPARULUpdate(StringBuilder query) throws UnsupportedEncodingException, MalformedURLException, IOException {
        URL queryURL = new URL(url + URLEncoder.encode(query.toString(), "UTF-8"));
        final InputStream stream = HttpAuthenticate.connectAuthenticate(queryURL, username, password);
        // Yes, just read. We really don't care what the result is just that 
        // the response is 200 OK
        final byte[] buf = new byte[1024];
        while (stream.read(buf, 0, 1024) != 1) {
        }
    }

    @Override
    public void add(URI subject, URI predicate, URI object) {
        try {
            prepSPARULUpdate(INSERT_INTO, "<" + subject + ">", "<" + predicate + ">", "<" + object + ">");
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(String subject, URI predicate, URI object) {
        try {
            prepSPARULUpdate(INSERT_INTO, "_:" + subject, "<" + predicate + ">", "<" + object + ">");
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(URI subject, URI predicate, String bNode) {
        try {
            prepSPARULUpdate(INSERT_INTO, "<" + subject + ">", "<" + predicate + ">", "_:" + bNode);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(String subject, URI predicate, String bNode) {
        try {
            prepSPARULUpdate(INSERT_INTO, "_:" + subject, "<" + predicate + ">", "_:" + bNode);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    private static String escapeLiteral(String s) {
        return s.replaceAll("\\\"", "\\\\\"").replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
    }

    @Override
    public void add(URI subject, URI predicate, String literal, String language) {
        try {
            prepSPARULUpdate(INSERT_INTO, "<" + subject + ">", "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (language == null ? "" : "@" + language));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(String subject, URI predicate, String literal, String language) {
        try {
            prepSPARULUpdate(INSERT_INTO, "_:" + subject, "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (language == null ? "" : "@" + language));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(URI subject, URI predicate, String literal, URI datatype) {
        try {
            prepSPARULUpdate(INSERT_INTO, "<" + subject + ">", "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (datatype == null ? "" : "^^<" + datatype + ">"));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void add(String subject, URI predicate, String literal, URI datatype) {
        try {
            prepSPARULUpdate(INSERT_INTO, "_:" + subject, "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (datatype == null ? "" : "^^<" + datatype + ">"));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(URI subject, URI predicate, URI object) {
        try {
            prepSPARULUpdate(DELETE_FROM, "<" + subject + ">", "<" + predicate + ">", "<" + object + ">");
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(String subject, URI predicate, URI object) {
        try {
            prepSPARULUpdate(DELETE_FROM, "_:" + subject, "<" + predicate + ">", "<" + object + ">");
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(URI subject, URI predicate, String bNode) {
        try {
            prepSPARULUpdate(DELETE_FROM, "<" + subject + ">", "<" + predicate + ">", "_:" + bNode);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(String subject, URI predicate, String bNode) {
        try {
            prepSPARULUpdate(DELETE_FROM, "_:" + subject, "<" + predicate + ">", "_:" + bNode);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(URI subject, URI predicate, String literal, String language) {
        try {
            prepSPARULUpdate(DELETE_FROM, "<" + subject + ">", "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (language == null ? "" : "@" + language));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(String subject, URI predicate, String literal, String language) {
        try {
            prepSPARULUpdate(DELETE_FROM, "_:" + subject, "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (language == null ? "" : "@" + language));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(URI subject, URI predicate, String literal, URI datatype) {
        try {
            prepSPARULUpdate(DELETE_FROM, "<" + subject + ">", "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (datatype == null ? "" : "^^<" + datatype + ">"));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void remove(String subject, URI predicate, String literal, URI datatype) {
        try {
            prepSPARULUpdate(DELETE_FROM, "_:" + subject, "<" + predicate + ">", "\"" + escapeLiteral(literal) + "\"" + (datatype == null ? "" : "^^<" + datatype + ">"));
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void addList(String subject, URI predicate, List<Object> list) {
        StringBuilder query = new StringBuilder("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        query.append(INSERT_INTO).append(" <").append(graph).append("> { ");
        query.append("_:").append(subject).append(" ");
        query.append("<").append(predicate.toString()).append(">  ( ");
        for (Object o : list) {
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
        }
        query.append(")");
        try {
            doSPARULUpdate(query);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void addList(URI subject, URI predicate, List<Object> list) {
        StringBuilder query = new StringBuilder("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        query.append(INSERT_INTO).append(" <").append(graph).append("> { ");
        query.append("<").append(subject).append("> ");
        query.append("<").append(predicate.toString()).append(">  ( ");
        for (Object o : list) {
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
        }
        query.append(")");
        try {
            doSPARULUpdate(query);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void removeList(String subject, URI predicate, List<Object> list) {
        int i = 0;
        StringBuilder query = new StringBuilder("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        query.append(DELETE_FROM).append(" <").append(graph).append("> { ");
        query.append("_:").append(subject).append(" ");
        query.append("<").append(predicate.toString()).append(">  ?n").append(i).append(" . ");
        for (Object o : list) {
            query.append("?n").append(i).append(" rdf:first ");
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> . ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" . ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
            query.append("?n").append(i).append(" rdf:rest ").append("?n").append(++i).append(" . ");
        }
        query.append("} WHERE {");
        i = 0;
        query.append("_:").append(subject).append(" ");
        query.append("<").append(predicate.toString()).append(">  ?n").append(i).append(" . ");
        for (Object o : list) {
            query.append("?n").append(i).append(" rdf:first ");
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> . ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" . ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
            query.append("?n").append(i).append(" rdf:rest ").append("?n").append(++i).append(" . ");
        }
        query.append(" }");
        try {
            doSPARULUpdate(query);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }

    @Override
    public void removeList(URI subject, URI predicate, List<Object> list) {
        
        int i = 0;
        StringBuilder query = new StringBuilder("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        query.append(DELETE_FROM).append(" <").append(graph).append("> { ");
        query.append("<").append(subject).append("> ");
        query.append("<").append(predicate.toString()).append(">  ?n").append(i).append(" . ");
        for (Object o : list) {
            query.append("?n").append(i).append(" rdf:first ");
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> . ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" . ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
            query.append("?n").append(i).append(" rdf:rest ").append("?n").append(++i).append(" . ");
        }
        query.append("} WHERE {");
        i = 0;
        query.append("<").append(subject).append("> ");
        query.append("<").append(predicate.toString()).append(">  ?n").append(i).append(" . ");
        for (Object o : list) {
            query.append("?n").append(i).append(" rdf:first ");
            if (o instanceof URI) {
                query.append("<").append(o.toString()).append("> . ");
            } else if (o instanceof String) {
                query.append("_:").append(o.toString()).append(" . ");
            } else {
                throw new IllegalArgumentException("List contained element not a URI or String");
            }
            query.append("?n").append(i).append(" rdf:rest ").append("?n").append(++i).append(" . ");
        }
        query.append(" }");
        try {
            doSPARULUpdate(query);
        } catch (IOException x) {
            throw new RemoteUpdateException(x);
        }
    }
}