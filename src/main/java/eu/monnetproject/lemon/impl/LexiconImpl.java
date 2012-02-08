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

import eu.monnetproject.lemon.impl.io.ReaderAccepter;
import eu.monnetproject.lemon.model.MorphPattern;
import java.net.URI;
import java.util.*;
import eu.monnetproject.lemon.*;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.LexicalTopic;
import eu.monnetproject.lemon.model.Lexicon;

/**
 * Instantiated via {@link SimpleLemonModel}
 * @author John McCrae
 */
public class LexiconImpl extends SimpleLemonElement implements Lexicon {

    private String language;
    private LemonModel model;

    public LexiconImpl(URI uri, LemonModel model) {
        super(uri, "Lexicon");
        this.model = model;
    }

    public LexiconImpl(String id, LemonModel model) {
        super(id, "Lexicon");
        this.model = model;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public boolean hasEntry(final LexicalEntry entry) {
        return getStrElems("entry").contains(entry);
    }

    @Override
    public int countEntrys() {
        return getStrElems("entry").size();
    }

    @Override
    public Collection<LexicalEntry> getEntrys() {
        return (Collection<LexicalEntry>) getStrElems("entry");
    }

    @Override
    public boolean addEntry(final LexicalEntry entry) {
        return addStrElem("entry", entry);
    }

    @Override
    public boolean removeEntry(final LexicalEntry entry) {
        return removeStrElem("entry", entry);
    }

    @Override
    public Collection<LexicalTopic> getTopics() {
        return (Collection<LexicalTopic>) getStrElems("topic");
    }

    @Override
    public boolean addTopic(final LexicalTopic topic) {
        return addStrElem("topic", topic);
    }

    @Override
    public boolean removeTopic(final LexicalTopic topic) {
        return removeStrElem("topic", topic);
    }

    @Override
    public LemonModel getModel() {
        return model;
    }

    @Override
    public ReaderAccepter accept(URI pred, URI value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"entry")) {
            final LexicalEntryImpl lexicalEntryImpl = factory.getLexicalEntryImpl(value);
            addEntry(lexicalEntryImpl);
            return lexicalEntryImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"topic")) {
            final TopicImpl topicImpl = factory.getTopicImpl(value);
            addTopic(topicImpl);
            return topicImpl;
        } 
        return defaultAccept(pred, value,lingOnto);
    }

    @Override
    public ReaderAccepter accept(URI pred, String value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"entry")) {
            final LexicalEntryImpl lexicalEntryImpl = factory.getLexicalEntryImpl(value);
            addEntry(lexicalEntryImpl);
            return lexicalEntryImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"topic")) {
            final TopicImpl topicImpl = factory.getTopicImpl(value);
            addTopic(topicImpl);
            return topicImpl;
        } 
        return defaultAccept(pred, value);
    }

    @Override
    public void accept(URI pred, String value, String lang, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"language")) {
            setLanguage(value);
        } else {
            defaultAccept(pred, value, lang);
        }
    }

    @Override
    public Collection<MorphPattern> getPatterns() {
        return getStrElems("pattern");
    }

    @Override
    public boolean addPattern(MorphPattern mp) {
        return addStrElem("pattern", mp);
    }

    @Override
    public boolean removePattern(MorphPattern mp) {
        return removeStrElem("pattern", mp);
    }

    @Override
    public Map<URI,Collection<Object>> getElements() {
        final Map<URI,Collection<Object>> elements = super.getElements();
        elements.put(URI.create(LemonModel.LEMON_URI+"language"), Collections.singletonList((Object)language));
        return elements;
    }
    
    
}
