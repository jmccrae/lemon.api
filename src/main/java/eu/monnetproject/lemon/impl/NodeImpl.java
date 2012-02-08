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

import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.impl.io.ReaderAccepter;
import eu.monnetproject.lemon.impl.io.UnactualizedAccepter;
import eu.monnetproject.lemon.model.Constituent;
import eu.monnetproject.lemon.model.Edge;
import eu.monnetproject.lemon.model.Node;
import eu.monnetproject.lemon.model.PhraseTerminal;
import eu.monnetproject.lemon.model.Text;
import java.net.URI;
import java.util.*;

/**
 * Instantiated via {@link SimpleLemonFactory}
 * @author John McCrae
 */
public class NodeImpl extends SimpleLemonElement implements Node {

    private Text separator;

    NodeImpl(URI uri) {
        super(uri, "Node");
    }

    NodeImpl(String id) {
        super(id, "Node");
    }

    public Constituent getConstituent() {
        return (Constituent) getStrElem("constituent");
    }

    public void setConstituent(final Constituent constituent) {
        setStrElem("constituent", constituent);
    }

    public Map<Edge, Collection<Node>> getEdges() {
        return (Map<Edge, Collection<Node>>) getPredElems(Edge.class);
    }

    public Collection<Node> getEdge(final Edge edge) {
        return (Collection<Node>) getPredElem(edge);
    }

    public boolean addEdge(final Edge edge, final Node edgeVal) {
        return addPredElem(edge, edgeVal);
    }

    public boolean removeEdge(final Edge edge, final Node edgeVal) {
        return removePredElem(edge, edgeVal);
    }

    public PhraseTerminal getLeaf() {
        return (PhraseTerminal) getStrElem("leaf");
    }

    public void setLeaf(final PhraseTerminal product) {
        setStrElem("leaf", product);
    }

    public Text getSeparator() {
        return separator;
    }

    public void setSeparator(final Text separator) {
        this.separator = separator;
    }

    public ReaderAccepter accept(URI pred, URI value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"constituent")) {
            final ConstituentImpl constituentImpl = factory.getConstituentImpl(value);
            setConstituent(constituentImpl);
            return constituentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"leaf")) {
            return new UnactualizedAccepter() {

                @Override
                public Map<Object, ReaderAccepter> actualizedAs(ReaderAccepter actual, LinguisticOntology lingOnto, AccepterFactory factory) {
                    setLeaf((PhraseTerminal)actual);
                    return super.actualizedAs(actual, lingOnto, factory);
                }
                
            };
        } else if(lingOnto != null) {
            for(Edge edge : lingOnto.getEdge()) {
                if(edge.getURI().equals(pred)) {
                    final NodeImpl nodeImpl = factory.getNodeImpl(value);
                    addEdge(edge, nodeImpl);
                    return nodeImpl;
                }
            }
        }
        return defaultAccept(pred, value);
    }

    public ReaderAccepter accept(URI pred, String value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"constituent")) {
            final ConstituentImpl constituentImpl = factory.getConstituentImpl(value);
            setConstituent(constituentImpl);
            return constituentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"leaf")) {
            return new UnactualizedAccepter() {

                @Override
                public Map<Object, ReaderAccepter> actualizedAs(ReaderAccepter actual, LinguisticOntology lingOnto, AccepterFactory factory) {
                    setLeaf((PhraseTerminal)actual);
                    return super.actualizedAs(actual, lingOnto, factory);
                }
                
            };
        } else if(lingOnto != null) {
            for(Edge edge : lingOnto.getEdge()) {
                if(edge.getURI().equals(pred)) {
                    final NodeImpl nodeImpl = factory.getNodeImpl(value);
                    addEdge(edge, nodeImpl);
                    return nodeImpl;
                }
            }
        }
        return defaultAccept(pred, value);
    }

    public void accept(URI pred, String value, String lang, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"separator")) {
            setSeparator(new Text(value,lang));
        } else {
            defaultAccept(pred, value, lang);
        }
    }
}
