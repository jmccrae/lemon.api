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
import eu.monnetproject.lemon.model.Argument;
import eu.monnetproject.lemon.model.Condition;
import eu.monnetproject.lemon.model.Definition;
import eu.monnetproject.lemon.model.Example;
import eu.monnetproject.lemon.model.LemonPredicate;
import eu.monnetproject.lemon.model.LexicalSense;
import eu.monnetproject.lemon.model.LexicalSense.ReferencePreference;
import eu.monnetproject.lemon.model.SenseCondition;
import eu.monnetproject.lemon.model.SenseContext;
import eu.monnetproject.lemon.model.SenseDefinition;
import eu.monnetproject.lemon.model.SenseRelation;
import java.net.URI;
import java.util.*;

/**
 * Instantiated via {@link SimpleLemonFactory}
 * @author John McCrae
 */
public class LexicalSenseImpl extends SimpleLemonElement implements LexicalSense {

    private URI reference;
    private ReferencePreference refPref;

    LexicalSenseImpl(URI uri) {
        super(uri, "LexicalSense");
    }

    LexicalSenseImpl(String id) {
        super(id, "LexicalSense");
    }

    @Override
    public URI getReference() {
        return reference;
    }

    @Override
    public void setReference(final URI reference) {
        this.reference = reference;
    }

    @Override
    public ReferencePreference getRefPref() {
        return refPref;
    }

    @Override
    public void setRefPref(final ReferencePreference refPref) {
        this.refPref = refPref;
    }

    @Override
    public boolean addContext(final SenseContext context) {
        return addStrElem("context", context);
    }

    @Override
    public boolean removeContext(final SenseContext context) {
        return removeStrElem("context", context);
    }

    @Override
    public Collection<SenseContext> getContexts() {
        return (Collection<SenseContext>) getStrElems("context");
    }

    @Override
    public Map<Condition, Collection<SenseCondition>> getConditions() {
        return (Map<Condition, Collection<SenseCondition>>) getPredElems(Condition.class);
    }

    @Override
    public Collection<SenseCondition> getCondition(Condition predicate) {
        return (Collection<SenseCondition>) getPredElem(predicate);
    }

    @Override
    public boolean addCondition(final Condition predicate, final SenseCondition condition) {
        return addPredElem(predicate, condition);
    }

    @Override
    public boolean removeCondition(final Condition predicate, final SenseCondition condition) {
        return removePredElem(predicate, condition);
    }

    @Override
    public Collection<Example> getExamples() {
        return (Collection<Example>) getStrElems("example");
    }

    @Override
    public boolean addExample(final Example example) {
        return addStrElem("example", example);
    }

    @Override
    public boolean removeExample(final Example example) {
        return removeStrElem("example", example);
    }

    @Override
    public Map<Definition, Collection<SenseDefinition>> getDefinitions() {
        return (Map<Definition, Collection<SenseDefinition>>) getPredElems(Definition.class);
    }

    @Override
    public Collection<SenseDefinition> getDefinition(Definition predicate) {
        return (Collection<SenseDefinition>) getPredElem(predicate);
    }

    @Override
    public boolean addDefinition(final Definition predicate, final SenseDefinition definition) {
        return addPredElem(predicate, definition);
    }

    @Override
    public boolean removeDefinition(final Definition predicate, final SenseDefinition definition) {
        return addPredElem(predicate, definition);
    }

    @Override
    public Collection<Argument> getSubjOfProps() {
        return (Collection<Argument>) getStrElems("subjOfProp");
    }

    @Override
    public boolean addSubjOfProp(final Argument argument) {
        return addStrElem("subjOfProp", argument);
    }

    @Override
    public boolean removeSubjOfProp(final Argument argument) {
        return removeStrElem("subjOfProp", argument);
    }

    @Override
    public Collection<Argument> getObjOfProps() {
        return (Collection<Argument>) getStrElems("objOfProp");
    }

    @Override
    public boolean addObjOfProp(final Argument argument) {
        return addStrElem("objOfProp", argument);
    }

    @Override
    public boolean removeObjOfProp(final Argument argument) {
        return removeStrElem("objOfProp", argument);
    }

    @Override
    public Collection<Argument> getIsAs() {
        return (Collection<Argument>) getStrElems("isA");
    }

    @Override
    public boolean addIsA(final Argument argument) {
        return addStrElem("isA", argument);
    }

    @Override
    public boolean removeIsA(final Argument argument) {
        return removeStrElem("isA", argument);
    }

    @Override
    public Collection<LexicalSense> getSubsenses() {
        return (Collection<LexicalSense>) getStrElems("subsense");
    }

    @Override
    public boolean addSubsense(final LexicalSense sense) {
        return addStrElem("subsense", sense);
    }

    @Override
    public boolean removeSubsense(final LexicalSense sense) {
        return addStrElem("subsense", sense);
    }

    @Override
    public Map<SenseRelation, Collection<LexicalSense>> getSenseRelations() {
        return (Map<SenseRelation, Collection<LexicalSense>>) getPredElems(SenseRelation.class);
    }

    @Override
    public Collection<LexicalSense> getSenseRelation(final SenseRelation senseRelation) {
        return (Collection<LexicalSense>) getPredElem(senseRelation);
    }

    @Override
    public boolean addSenseRelation(final SenseRelation senseRelation, final LexicalSense senseRelationVal) {
        return addPredElem(senseRelation, senseRelationVal);
    }

    @Override
    public boolean removeSenseRelation(final SenseRelation senseRelation, final LexicalSense senseRelationVal) {
        return addPredElem(senseRelation, senseRelationVal);
    }

    @Override
    protected boolean refers() {
        return super.refers() || reference != null || refPref != null;
    }

    @Override
    protected void printAsBlankNode(java.io.PrintWriter stream, SerializationState state, boolean first) {
        if (reference != null) {
            if (!first) {
                stream.println(" ;");
            }
            stream.print(" lemon:reference ");
            printURI(reference, stream);
        }
    }

    @Override
    public void write(java.io.PrintWriter stream, SerializationState state) {
        super.write(stream, state);
        if (refPref != null && reference != null) {
            printURI(reference, stream);
            if (refPref == LexicalSense.ReferencePreference.prefRef) {
                stream.print(" lemon:prefRef ");
            } else if (refPref == LexicalSense.ReferencePreference.altRef) {
                stream.print(" lemon:altRef ");
            } else if (refPref == LexicalSense.ReferencePreference.hiddenRef) {
                stream.print(" lemon:hiddenRef ");
            }
            if (getURI() != null) {
                printURI(getURI(), stream);
            } else {
                stream.print("_:" + getID());
            }
            stream.println(" .\n");
        }
    }

    @Override
    protected boolean follow(LemonPredicate predicate) {
        return !(predicate instanceof SenseRelation);
    }

    @Override
    public ReaderAccepter accept(URI pred, URI value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"context")) {
            final ContextImpl contextImpl = factory.getContextImpl(value);
            addContext(contextImpl);
            return contextImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"example")) {
            final ExampleImpl exampleImpl = factory.getExampleImpl(value);
            addExample(exampleImpl);
            return exampleImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"isA")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addIsA(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"subjOfProp")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addSubjOfProp(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"objOfProp")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addObjOfProp(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"reference")) {
            setReference(value);
            return null;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"prefRef")) {
            setReference(value);
            setRefPref(ReferencePreference.prefRef);
            return null;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"altRef")) {
            setReference(value);
            setRefPref(ReferencePreference.altRef);
            return null;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"hiddenRef")) {
            setReference(value);
            setRefPref(ReferencePreference.hiddenRef);
            return null;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"subsense")) {
            final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(value);
            addSubsense(lexicalSenseImpl);
            return lexicalSenseImpl;
        } else if(lingOnto != null) {
            for(Condition condition : lingOnto.getConditions()) {
                if(condition.getURI().equals(pred)) {
                    final ConditionImpl conditionImpl = factory.getConditionImpl(value);
                    addCondition(condition, conditionImpl);
                    return conditionImpl;
                }
            }
            for(Definition definitionPredicate : lingOnto.getDefinitions()) {
                if(definitionPredicate.getURI().equals(pred)) {
                    final DefinitionImpl definitionImpl = factory.getDefinitionImpl(value);
                    addDefinition(definitionPredicate, definitionImpl);
                    return definitionImpl;
                }
            }
            for(SenseRelation senseRelation : lingOnto.getSenseRelation()) {
                if(senseRelation.getURI().equals(pred)) {
                    final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(pred);
                    addSenseRelation(senseRelation, lexicalSenseImpl);
                    return lexicalSenseImpl;
                }
            }
        }
        return defaultAccept(pred, value, lingOnto);
    }

    @Override
    public ReaderAccepter accept(URI pred, String value, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(pred.toString().equals(LemonModel.LEMON_URI+"context")) {
            final ContextImpl contextImpl = factory.getContextImpl(value);
            addContext(contextImpl);
            return contextImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"example")) {
            final ExampleImpl exampleImpl = factory.getExampleImpl(value);
            addExample(exampleImpl);
            return exampleImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"isA")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addIsA(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"subjOfProp")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addSubjOfProp(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"objOfProp")) {
            final ArgumentImpl argumentImpl = factory.getArgumentImpl(value);
            addObjOfProp(argumentImpl);
            return argumentImpl;
        } else if(pred.toString().equals(LemonModel.LEMON_URI+"subsense")) {
            final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(value);
            addSubsense(lexicalSenseImpl);
            return lexicalSenseImpl;
        } else if(lingOnto != null) {
            for(Condition condition : lingOnto.getConditions()) {
                if(condition.getURI().equals(pred)) {
                    final ConditionImpl conditionImpl = factory.getConditionImpl(value);
                    addCondition(condition, conditionImpl);
                    return conditionImpl;
                }
            }
            for(Definition definitionPredicate : lingOnto.getDefinitions()) {
                if(definitionPredicate.getURI().equals(pred)) {
                    final DefinitionImpl definitionImpl = factory.getDefinitionImpl(value);
                    addDefinition(definitionPredicate, definitionImpl);
                    return definitionImpl;
                }
            }
            for(SenseRelation senseRelation : lingOnto.getSenseRelation()) {
                if(senseRelation.getURI().equals(pred)) {
                    final LexicalSenseImpl lexicalSenseImpl = factory.getLexicalSenseImpl(pred);
                    addSenseRelation(senseRelation, lexicalSenseImpl);
                    return lexicalSenseImpl;
                }
            }
        }
        return defaultAccept(pred, value);
    }

    @Override
    public void accept(URI pred, String value, String lang, LinguisticOntology lingOnto, AccepterFactory factory) {
        defaultAccept(pred, value, lang);
    }
    
    @Override
    public void merge(ReaderAccepter accepter, LinguisticOntology lingOnto, AccepterFactory factory) {
        if(accepter instanceof LexicalSenseImpl) {
            final LexicalSenseImpl lsi = (LexicalSenseImpl)accepter;
            if(this.refPref == null && lsi.refPref != null) {
                this.refPref = lsi.refPref;
            } else if(this.refPref != null && lsi.refPref != null && !this.refPref.equals(lsi.refPref)) {
                throw new RuntimeException("Merge failure");
            }
            if(this.reference == null && lsi.reference != null) {
                this.reference = lsi.reference;
            } else if(this.reference != null && lsi.reference != null && !this.reference.equals(lsi.reference)) {
                throw new RuntimeException("Merge failure");
            }
        }
        defaultMerge(accepter, lingOnto, factory);
    }

    @Override
    public Map<URI,Collection<Object>> getElements() {
        Map<URI,Collection<Object>> rval = super.getElements();
        rval.put(URI.create(LemonModel.LEMON_URI+"reference"), Collections.singletonList((Object)reference));
        return rval;
    }
    
    
}
