/****************************************************************************
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
 ********************************************************************************/
package eu.monnetproject.lemon.impl;

import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.impl.io.ReaderAccepter;
import eu.monnetproject.lemon.model.MorphPattern;
import eu.monnetproject.lemon.model.MorphTransform;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author John McCrae
 */
public class MorphPatternImpl extends SimpleLemonElement<MorphPattern> implements MorphPattern {

    public MorphPatternImpl(String id) {
        super(id, "MorphPattern");
    }

    public MorphPatternImpl(URI uri) {
        super(uri, "MorphPattern");
    }

    @Override
    public Collection<MorphTransform> getTransforms() {
        return getStrElems("transform");
    }

    @Override
    public boolean addTransform(MorphTransform mt) {
        return addStrElem("transform", mt);
    }

    @Override
    public boolean removeTransform(MorphTransform mt) {
        return removeStrElem("transform", mt);
    }

    @Override
    public ReaderAccepter accept(URI pred, URI value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"transform")) {
            final MorphTransformImpl morphTransformImpl = factory.getMorphTransformImpl(value);
            addTransform(morphTransformImpl);
            return morphTransformImpl;
        } else {
            return defaultAccept(pred, value,lingOnto);
        }
    }

    @Override
    public ReaderAccepter accept(URI pred, String bNode, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"transform")) {
            final MorphTransformImpl morphTransformImpl = factory.getMorphTransformImpl(bNode);
            addTransform(morphTransformImpl);
            return morphTransformImpl;
        } else {
            return defaultAccept(pred, bNode);
        }
    }

    @Override
    public void accept(URI pred, String value, String lang, LinguisticOntology lingOnto, AccepterFactory factory) {
        defaultAccept(pred, value, lang);
    }
    
    @Override
    public void merge(ReaderAccepter accepter, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(accepter instanceof MorphPatternImpl) {
            final MorphPatternImpl mpi = (MorphPatternImpl)accepter;
            if(this.language == null && mpi.language != null) {
                this.language = mpi.language;
            } else if(this.language != null && mpi.language != null && !this.language.equals(mpi.language)) {
                throw new RuntimeException("Merge Exception");
            }
        }
        defaultMerge(accepter, lingOnto, factory);
    }
    
    private String language;
    
    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public Map<URI, Collection<Object>> getElements() {
        Map<URI, Collection<Object>> rv =  super.getElements();
        final URI uri = URI.create(LemonModel.LEMON_URI+"language");
        if(!rv.containsKey(uri)) {
            rv.put(uri, new LinkedList<Object>());
        }
        rv.get(uri).add(language);
        return rv;
    }
    
    

}
