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

import eu.monnetproject.lemon.impl.AccepterFactory;
import eu.monnetproject.lemon.ElementVisitor;
import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.impl.io.ListAccepter;
import eu.monnetproject.lemon.impl.io.ReaderAccepter;
import eu.monnetproject.lemon.model.Component;
import eu.monnetproject.lemon.model.Frame;
import eu.monnetproject.lemon.model.LemonElement;
import eu.monnetproject.lemon.model.LemonPredicate;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.LexicalForm;
import eu.monnetproject.lemon.model.LexicalSense;
import eu.monnetproject.lemon.model.LexicalTopic;
import eu.monnetproject.lemon.model.LexicalVariant;
import eu.monnetproject.lemon.model.MorphPattern;
import eu.monnetproject.lemon.model.Node;
import java.net.URI;
import java.util.*;

/**
 * Instantiated via {@link SimpleLemonFactory}
 * @author John McCrae
 */
public class LexicalEntryImpl extends SimpleLemonElement<LexicalEntryImpl> implements LexicalEntry {

    private final HashSet<List<Component>> components = new HashSet<List<Component>>();

    LexicalEntryImpl(URI uri) {
        super(uri, "LexicalEntry");
    }

    LexicalEntryImpl(String id) {
        super(id, "LexicalEntry");
    }

    LexicalEntryImpl(URI uri, String type) {
        super(uri, type);
    }

    LexicalEntryImpl(String id, String type) {
        super(id, type);
    }

    public LexicalForm getCanonicalForm() {
        return (LexicalForm) getStrElem("canonicalForm");
    }

    public void setCanonicalForm(final LexicalForm canonicalForm) {
        setStrElem("canonicalForm", canonicalForm);
    }

    public Collection<LexicalForm> getOtherForms() {
        return (Collection<LexicalForm>) getStrElems("otherForm");
    }

    public boolean addOtherForm(final LexicalForm otherForm) {
        return addStrElem("otherForm", otherForm);
    }

    public boolean removeOtherForm(final LexicalForm otherForm) {
        return removeStrElem("otherForm", otherForm);
    }

    public Collection<LexicalForm> getAbstractForms() {
        return (Collection<LexicalForm>) getStrElems("abstractForm");
    }

    public boolean addAbstractForm(final LexicalForm abstractForm) {
        return addStrElem("abstractForm", abstractForm);
    }

    public boolean removeAbstractForm(final LexicalForm abstractForm) {
        return removeStrElem("abstractForm", abstractForm);
    }

    public Collection<LexicalTopic> getTopics() {
        return (Collection<LexicalTopic>) getStrElems("topic");
    }

    public boolean addTopic(final LexicalTopic topic) {
        return addStrElem("topic", topic);
    }

    public boolean removeTopic(final LexicalTopic topic) {
        return removeStrElem("topic", topic);
    }

    public Map<LexicalVariant, Collection<LexicalEntry>> getLexicalVariants() {
        return (Map<LexicalVariant, Collection<LexicalEntry>>) getPredElems(LexicalVariant.class);
    }

    public Collection<LexicalEntry> getLexicalVariant(final LexicalVariant lexicalVariant) {
        return (Collection<LexicalEntry>) getPredElem(lexicalVariant);
    }

    public boolean addLexicalVariant(final LexicalVariant lexicalVariant, final LexicalEntry lexicalVariantVal) {
        return addPredElem(lexicalVariant, lexicalVariantVal);
    }

    public boolean removeLexicalVariant(final LexicalVariant lexicalVariant, final LexicalEntry lexicalVariantVal) {
        return removePredElem(lexicalVariant, lexicalVariantVal);
    }

    public Collection<Frame> getSynBehaviors() {
        return (Collection<Frame>) getStrElems("synBehavior");
    }

    public boolean addSynBehavior(final Frame synBehavior) {
        return addStrElem("synBehavior", synBehavior);
    }

    public boolean removeSynBehavior(final Frame synBehavior) {
        return removeStrElem("synBehavior", synBehavior);
    }

    public Collection<List<Component>> getDecompositions() {
        return Collections.unmodifiableSet(components);
    }

    public void addDecomposition(List<Component> comps) {
        for (Component comp : comps) {
            if (comp instanceof SimpleLemonElement) {
                ((SimpleLemonElement) comp).addReference(this);
            }
        }
        components.add(comps);
    }

    public boolean removeDecomposition(List<Component> comps) {
        for (Component comp : comps) {
            if (comp instanceof SimpleLemonElement) {
                ((SimpleLemonElement) comp).removeReference(this);
            }
        }
        return components.remove(comps);
    }

    public Collection<LexicalSense> getSenses() {
        return (Collection<LexicalSense>) getStrElems("sense");
    }

    public boolean addSense(final LexicalSense sense) {
        return addStrElem("sense", sense);
    }

    public boolean removeSense(final LexicalSense sense) {
        return removeStrElem("sense", sense);
    }

    public Collection<Node> getPhraseRoots() {
        return (Collection<Node>) getStrElems("phraseRoot");
    }

    public boolean addPhraseRoot(final Node phraseRoot) {
        return addStrElem("phraseRoot", phraseRoot);
    }

    public boolean removePhraseRoot(final Node phraseRoot) {
        return addStrElem("phraseRoot", phraseRoot);
    }

    public Collection<LexicalForm> getForms() {
        LinkedList<LexicalForm> forms = new LinkedList<LexicalForm>(getStrElems("form"));
        if(getCanonicalForm() != null) {
            forms.add(getCanonicalForm());
        }
        forms.addAll(getOtherForms());
        forms.addAll(getAbstractForms());
        return Collections.unmodifiableCollection(forms);
    }

    public boolean addForm(final LexicalForm form) {
        return addStrElem("form", form);
    }

    public boolean removeForm(final LexicalForm form) {
        return removeStrElem("form", form);
    }

    @Override
    protected void updateReference(LemonElement from, LemonElement to) {
        super.updateReference(from, to);
        for (List<Component> comps : components) {
            for (int i = 0; i < comps.size(); i++) {
                if (comps.get(i).equals(from)) {
                    comps.set(i, (Component) to);
                }
            }
        }
    }

    @Override
    protected void mergeIn(LexicalEntryImpl elem) {
        super.mergeIn(elem);
        components.addAll(elem.components);
    }

    @Override
    protected boolean refers() {
        return super.refers() || !components.isEmpty();
    }

    @Override
    protected void printAsBlankNode(java.io.PrintWriter stream, SerializationState state, boolean first) {
        boolean first2 = true;
        for (List<Component> componentList : components) {
            if (!first) {
                stream.println(" ;");
                stream.print(" lemon:decomposition (");
            } else if (first2) {
                stream.println(" lemon:decomposition (");
            } else {
                stream.println(", (");
            }
            for (Component c : componentList) {
                ((ComponentImpl) c).visit(stream, state);
                state.postponed.add(c);
            }
            stream.print(") ");
        }
    }

    @Override
    protected boolean follow(LemonPredicate predicate) {
        return !(predicate instanceof LexicalVariant);
    }

    @Override
    public void doAccept(ElementVisitor visitor) {
        for (List<Component> compList : components) {
            for (Component comp : compList) {
                if (comp instanceof SimpleLemonElement) {
                    ((SimpleLemonElement) comp).accept(visitor);
                }
            }
        }
    }

    @Override
    public Map<URI, Collection<Object>> getElements() {
        final Map<URI, Collection<Object>> elements = super.getElements();
        final URI decomposition = URI.create(LemonModel.LEMON_URI+"decomposition");
        if(!components.isEmpty()) {
            elements.put(decomposition,new LinkedList());
        }
        for(List<Component> compList : components) {
            elements.get(decomposition).add(compList);
        }
        return elements;
    }

    
    
    @Override
    public void clearAll() {
        for (List<Component> compList : components) {
            for (Component comp : compList) {
                if (comp instanceof SimpleLemonElement) {
                    ((SimpleLemonElement) comp).referencers.remove(this);
                }
            }
        }
        components.clear();
        super.clearAll();
    }

    public ReaderAccepter accept(URI pred, URI value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if (pred.toString().equals(LemonModel.LEMON_URI + "abstractForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addAbstractForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "canonicalForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            setCanonicalForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "otherForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addOtherForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "form")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "decomposition")) {
            final ListAccepter listAccepter = new ListAccepter();
            addDecomposition(listAccepter);
            return listAccepter;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "phraseRoot")) {
            final NodeImpl nodeImpl = factory.getNodeImpl(value);
            addPhraseRoot(nodeImpl);
            return nodeImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "sense")) {
            final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(value);
            addSense(lexicalSenseImpl);
            return lexicalSenseImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "synBehavior")) {
            final FrameImpl frameImpl = factory.getFrameImpl(value);
            addSynBehavior(frameImpl);
            return frameImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "topic")) {
            final TopicImpl topicImpl = factory.getTopicImpl(value);
            addTopic(topicImpl);
            return topicImpl;
        }
        if(lingOnto != null) {
            for(LexicalVariant lexicalVariant : lingOnto.getLexicalVariant()) {
                if(lexicalVariant.getURI().equals(pred)) {
                    final LexicalEntryImpl lexicalEntryImpl = factory.getLexicalEntryImpl(value);
                    addLexicalVariant(lexicalVariant, lexicalEntryImpl);
                    return lexicalEntryImpl;
                }
            }
        }
        return defaultAccept(pred, value);
    }

    public ReaderAccepter accept(URI pred, String value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if (pred.toString().equals(LemonModel.LEMON_URI + "abstractForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addAbstractForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "canonicalForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            setCanonicalForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "otherForm")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addOtherForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "form")) {
            final FormImpl formImpl = factory.getFormImpl(value);
            addForm(formImpl);
            return formImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "decomposition")) {
            final ListAccepter listAccepter = new ListAccepter();
            addDecomposition(listAccepter);
            return listAccepter;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "phraseRoot")) {
            final NodeImpl nodeImpl = factory.getNodeImpl(value);
            addPhraseRoot(nodeImpl);
            return nodeImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "sense")) {
            final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(value);
            addSense(lexicalSenseImpl);
            return lexicalSenseImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "synBehavior")) {
            final FrameImpl frameImpl = factory.getFrameImpl(value);
            addSynBehavior(frameImpl);
            return frameImpl;
        } else if (pred.toString().equals(LemonModel.LEMON_URI + "topic")) {
            final TopicImpl topicImpl = factory.getTopicImpl(value);
            addTopic(topicImpl);
            return topicImpl;
        }
        if(lingOnto != null) {
            for(LexicalVariant lexicalVariant : lingOnto.getLexicalVariant()) {
                if(lexicalVariant.getURI().equals(pred)) {
                    final LexicalEntryImpl lexicalEntryImpl = factory.getLexicalEntryImpl(value);
                    addLexicalVariant(lexicalVariant, lexicalEntryImpl);
                    return lexicalEntryImpl;
                }
            }
        }
        return defaultAccept(pred, value);
    }

    public void accept(URI pred, String value, String lang, LinguisticOntology lingOnto, AccepterFactory factory) {
        defaultAccept(pred, value, lang);
    }

    public Collection<MorphPattern> getPatterns() {
        return (Collection<MorphPattern>)getStrElems("pattern");
    }

    public boolean addPattern(MorphPattern mp) {
        return addStrElem("pattern", mp);
    }

    public boolean removePattern(MorphPattern mp) {
        return removeStrElem("pattern", mp);
    }

    public Component getHead() {
        return (Component)getStrElem("head");
    }

    public void setHead(Component cmpnt) {
        setStrElem("head", cmpnt);
    }
}
