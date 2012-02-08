/**********************************************************************************
 * Copyright (c) 2011, Monnet Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Monnet Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE MONNET PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *********************************************************************************/
package eu.monnetproject.lemon.impl;

import eu.monnetproject.lemon.*;
import eu.monnetproject.lemon.AbstractVisitor;
import eu.monnetproject.lemon.model.LemonElement;
import eu.monnetproject.lemon.model.LemonElementOrPredicate;
import eu.monnetproject.lemon.model.Lexicon;
import java.io.Reader;
import java.net.URI;
import java.util.*;

/**
 * Instantiated via {@link SimpleLemonSerializer}
 * @author John McCrae
 */
public class SimpleLemonModel implements LemonModel {

    private HashSet<Lexicon> lexica = new HashSet<Lexicon>();
    private HashMap<URI, LemonElementOrPredicate> elements = new HashMap<URI, LemonElementOrPredicate>();
    private String baseURI;

    public SimpleLemonModel() {
    }

    public SimpleLemonModel(String baseURI) {
        this.baseURI = baseURI;
    }

    public void activate(Map properties) {
        if (properties != null && properties.containsKey("baseURI")) {
            baseURI = properties.get("baseURI").toString();
        }
    }

    public Collection<Lexicon> getLexica() {
        return lexica;
    }

    public LemonElementOrPredicate getLemonElement(URI uri) {
        return elements.get(uri);
    }

    public URI getContext() {
        return URI.create(baseURI);
    }
    private LemonFactory factory = new SimpleLemonFactory(elements);

    public LemonFactory getFactory() {
        return factory;
    }

    public Lexicon addLexicon(URI uri, String language) {
        Lexicon lexicon = new LexiconImpl(uri, this);
        lexicon.setLanguage(language);
        lexica.add(lexicon);
        return lexicon;
    }

    public void removeLexicon(Lexicon lexicon) {
        lexica.remove(lexicon);
    }
    
    void addLexicon(Lexicon lexicon) {
        lexica.add(lexicon);
    }

    public <Elem extends LemonElementOrPredicate> Iterator<Elem> query(Class<Elem> target, String sparqlQuery) {
        throw new RuntimeException("TODO");
    }

    public <Elem extends LemonElement> void merge(Elem from, Elem to) {
        if (to instanceof SimpleLemonElement) {
            ((SimpleLemonElement) to).mergeIn(from);
        } else {
            throw new IllegalArgumentException("Cannot merge element I did not create");
        }
    }

    public void purgeLexicon(Lexicon lxcn, LinguisticOntology lo) {
        final PurgeVisitor purgeVisitor = new PurgeVisitor(lo);
        if(lxcn instanceof SimpleLemonElement) {
            ((SimpleLemonElement)lxcn).accept(purgeVisitor);
        } else {
            throw new IllegalArgumentException("Lexicon not created by me");
        }
    }

    public void importLexicon(Lexicon lxcn, LinguisticOntology lo) {
        lexica.add(lxcn);
    }

    public void importPatterns(Lexicon lxcn, Reader reader) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static class PurgeVisitor extends AbstractVisitor<SimpleLemonElement> {
        private HashSet<SimpleLemonElement> visited = new HashSet<SimpleLemonElement>();

        public PurgeVisitor(LinguisticOntology lingOnto) {
            super(lingOnto);
        }
        
        public void visit(SimpleLemonElement element) {
            element.clearAll();
            visited.add(element);
        }

        public boolean hasVisited(SimpleLemonElement element) {
            return visited.contains(element);
        }
        
    }
}
