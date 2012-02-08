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

import eu.monnetproject.lemon.ElementVisitor;
import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.URIElement;
import eu.monnetproject.lemon.impl.io.ReaderAccepter;
import eu.monnetproject.lemon.model.LemonElement;
import eu.monnetproject.lemon.model.LemonPredicate;
import eu.monnetproject.lemon.model.Property;
import eu.monnetproject.lemon.model.PropertyValue;
import eu.monnetproject.lemon.model.Text;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base class for element implementations
 * @author John McCrae
 */
public abstract class SimpleLemonElement<Elem extends LemonElement> extends URIElement implements LemonElement, ReaderAccepter, IntrospectableElement {

    private final HashMap<LemonPredicate, Collection<LemonElement>> predElems =
            new HashMap<LemonPredicate, Collection<LemonElement>>();
    private final HashMap<String, Collection<LemonElement>> strElems =
            new HashMap<String, Collection<LemonElement>>();
    private final HashMap<String, LemonElement> strElem =
            new HashMap<String, LemonElement>();
    private final HashMap<String, Text> strText =
            new HashMap<String, Text>();
    private HashSet<URI> types = new HashSet<URI>();
    protected List<SimpleLemonElement> referencers = new LinkedList<SimpleLemonElement>();
    private final HashMap<URI, Collection<Object>> annotations = new HashMap<URI, Collection<Object>>();
    private final String modelName;

    protected SimpleLemonElement(URI uri, String type) {
        super(uri);
        types.add(URI.create(LemonModel.LEMON_URI + type));
        modelName = type;
    }

    protected SimpleLemonElement(String id, String type) {
        super(id);
        types.add(URI.create(LemonModel.LEMON_URI + type));
        modelName = type;
    }

    @Override
    public Map<Property, Collection<PropertyValue>> getPropertys() {
        return (Map<Property, Collection<PropertyValue>>) getPredElems(Property.class);
    }

    @Override
    public Collection<PropertyValue> getProperty(final Property property) {
        return (Collection<PropertyValue>) getPredElem(property);
    }

    @Override
    public boolean addProperty(final Property property, final PropertyValue propertyVal) {
        return addPredElem(property, propertyVal);
    }

    @Override
    public boolean removeProperty(final Property property, final PropertyValue propertyVal) {
        return removePredElem(property, propertyVal);
    }

    @Override
    public Collection<URI> getTypes() {
        return Collections.unmodifiableSet(types);
    }

    @Override
    public void addType(URI uri) {
        types.add(uri);
    }

    @Override
    public void removeType(URI uri) {
        types.add(uri);
    }

    @Override
    public Map<URI, Collection<Object>> getAnnotations() {
        return annotations;
    }

    @Override
    public Collection<Object> getAnnotations(URI uri) {
        return annotations.get(uri);
    }

    @Override
    public boolean addAnnotation(URI uri, Object o) {
        if (o instanceof URI || o instanceof String || o instanceof Text) {
            if (!annotations.containsKey(uri)) {
                annotations.put(uri, new LinkedList<Object>());
            }
            return annotations.get(uri).add(o);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean removeAnnotation(URI uri, Object o) {
        if (o instanceof URI || o instanceof String || o instanceof Text) {
            if (annotations.containsKey(uri)) {
                annotations.put(uri, new LinkedList<Object>());
            }
            final boolean rval = annotations.get(uri).remove(o);
            if (annotations.get(uri).isEmpty()) {
                annotations.remove(uri);
            }
            return rval;
        } else {
            throw new IllegalArgumentException();
        }
    }

    ////////////////////
    // General functions
    protected LemonElement getStrElem(String name) {
        return strElem.get(name);
    }

    protected void setStrElem(String name, LemonElement elem) {
        if (strElem.containsKey(name)) {
            if (strElem.get(name) instanceof SimpleLemonElement) {
                ((SimpleLemonElement) strElem.get(name)).removeReference(this);
            }
        }
        if (elem != null && elem instanceof SimpleLemonElement) {
            ((SimpleLemonElement) elem).addReference(this);
        }
        if (elem != null) {
            strElem.put(name, elem);
        } else {
            strElem.remove(name);
        }
    }

    protected Text getStrText(String name) {
        return strText.get(name);
    }

    protected void setStrText(String name, Text txt) {
        strText.put(name, txt);
    }

    protected <Pred extends LemonPredicate> Map getPredElems(Class<Pred> clazz) {
        Map<Pred, Collection<LemonElement>> rval = new HashMap<Pred, Collection<LemonElement>>();
        for (LemonPredicate p : predElems.keySet()) {
            if (clazz.isInstance(p)) {
                rval.put((Pred) p, predElems.get(p));
            }
        }
        return Collections.unmodifiableMap(rval);
    }

    protected Collection getPredElem(LemonPredicate p) {
        if (predElems.containsKey(p)) {
            return Collections.unmodifiableCollection(new LinkedList(predElems.get(p)));
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    protected boolean addPredElem(LemonPredicate p, LemonElement e) {
        if (e instanceof SimpleLemonElement) {
            ((SimpleLemonElement) e).addReference(this);
        }
        if (!predElems.containsKey(p)) {
            predElems.put(p, new HashSet<LemonElement>());
        }
        return predElems.get(p).add(e);
    }

    protected boolean removePredElem(LemonPredicate p, LemonElement e) {
        if (e instanceof SimpleLemonElement) {
            ((SimpleLemonElement) e).removeReference(this);
        }
        if (predElems.containsKey(p)) {
            return predElems.get(p).remove(e);
        } else {
            return false;
        }
    }

    protected Collection getStrElems(String name) {
        if (strElems.containsKey(name)) {
            return Collections.unmodifiableCollection(new LinkedList(strElems.get(name)));
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    protected boolean addStrElem(String p, LemonElement e) {
        if (e instanceof SimpleLemonElement) {
            ((SimpleLemonElement) e).addReference(this);
        }
        if (!strElems.containsKey(p)) {
            strElems.put(p, new HashSet<LemonElement>());
        }
        return strElems.get(p).add(e);
    }

    protected boolean removeStrElem(String p, LemonElement e) {
        if (e instanceof SimpleLemonElement) {
            ((SimpleLemonElement) e).removeReference(this);
        }
        if (strElems.containsKey(p)) {
            return strElems.get(p).remove(e);
        } else {
            return false;
        }
    }

    ////////////////
    // Merge support
    protected void addReference(SimpleLemonElement element) {
        referencers.add(element);
    }

    protected void removeReference(SimpleLemonElement element) {
        referencers.remove(element);
    }

    protected void updateReference(LemonElement from, LemonElement to) {
        for (LemonPredicate pred : predElems.keySet()) {
            if (predElems.get(pred).contains(from)) {
                predElems.get(pred).remove(from);
                predElems.get(pred).add(to);
            }
        }
        for (String pred : strElems.keySet()) {
            if (strElems.get(pred).contains(from)) {
                strElems.get(pred).remove(from);
                strElems.get(pred).add(to);
            }
        }
        List<String> preds = new LinkedList<String>();
        for (String pred : strElem.keySet()) {
            if (strElem.get(pred).equals(from)) {
                // Avoid concurrent modification
                preds.add(pred);
            }
        }
        for (String pred : preds) {
            strElem.put(pred, to);
        }
    }

    protected void mergeIn(Elem elem) {
        if (!(elem instanceof SimpleLemonElement)) {
            throw new IllegalArgumentException("Cannot merge non-simple lemon element into simple store");
        }
        SimpleLemonElement lesp = (SimpleLemonElement) elem;
        predElems.putAll(lesp.predElems);
        strElems.putAll(lesp.strElems);
        strElem.putAll(lesp.strElem);
        strText.putAll(lesp.strText);
        types.addAll(lesp.types);
        for (SimpleLemonElement rlesp : referencers) {
            rlesp.updateReference(elem, this);
        }
    }

    ///////////////////////////
    // Serialization related
    protected boolean refers() {
        return !(predElems.isEmpty() && strElems.isEmpty() && strElem.isEmpty()
                && strText.isEmpty() && types.isEmpty());
    }

    public void visit(java.io.PrintWriter stream, SerializationState state) {
        boolean refers = refers();
        // If the object is referenced at mutliple points we only put its ID
        if (referencers.size() > 1) {
            if (this.getURI() != null) {
                printURI(this.getURI(), stream);
            } else {
                stream.print("_:" + this.getID());
            }
        } else {
            if (this.getURI() != null) {
                printURI(this.getURI(), stream);
            } else if (!refers || state.serialized.contains(this)) {
                stream.print("_:" + this.getID());
            } else {
                stream.print("[ ");
                printAsBlankNode(stream, state);
                stream.print(" ]");
            }
        }
    }

    protected void printAsBlankNode(java.io.PrintWriter stream, SerializationState state) {
        state.serialized.add(this);
        boolean first = true;

        for (LemonPredicate pred : predElems.keySet()) {
            if (!first) {
                stream.println(" ;");
            }
            boolean first2 = true;
            printURI(pred.getURI(), stream);
            for (LemonElement elem : predElems.get(pred)) {
                if (!first2) {
                    stream.println(" ,");
                }
                ((SimpleLemonElement) elem).visit(stream, state);
                if (((SimpleLemonElement) elem).isMultiReferenced() && elem.getURI() != null) {
                    state.postponed.add(elem);
                }
                first2 = false;
            }
            first = false;
        }

        for (String pred : strElems.keySet()) {
            if (!first) {
                stream.println(" ;");
            }
            boolean first2 = true;
            stream.print(" lemon:" + pred + " ");
            for (LemonElement elem : strElems.get(pred)) {
                if (!first2) {
                    stream.println(" ,");
                }
                ((SimpleLemonElement) elem).visit(stream, state);
                if (((SimpleLemonElement) elem).isMultiReferenced() && elem.getURI() != null) {
                    state.postponed.add(elem);
                }
                first2 = false;
            }
            first = false;
        }

        for (String pred : strElem.keySet()) {
            if (!first) {
                stream.println(" ;");
            }
            stream.print(" lemon:" + pred + " ");
            LemonElement elem = strElem.get(pred);
            ((SimpleLemonElement) elem).visit(stream, state);
            if (((SimpleLemonElement) elem).isMultiReferenced() && elem.getURI() != null) {
                state.postponed.add(elem);
            }
            first = false;
        }

        for (String pred : strText.keySet()) {
            if (!first) {
                stream.println(" ;");
            }
            stream.print(" lemon:" + pred + " ");
            Text txt = strText.get(pred);
            if (txt.language != null) {
                stream.print("\"" + txt.value + "\"@" + txt.language);
            } else {
                stream.print("\"" + txt.value + "\"");
            }
            first = false;
        }

        boolean first2 = true;
        for (URI typeURI : types) {
            if (first) {
                stream.print(" a ");
            } else if (first2) {
                stream.print(" ;\n a ");
            } else {
                stream.println(" ,");
            }
            printURI(typeURI, stream);
            first2 = false;
            first = false;
        }

        printAsBlankNode(stream, state, first);
    }

    protected void printURI(URI uri, java.io.PrintWriter stream) {
        if (uri.toString().startsWith(LemonModel.LEMON_URI)) {
            stream.print("lemon:" + uri.toString().substring(LemonModel.LEMON_URI.length()));
        } else {
            stream.print("<" + uri + ">");
        }
    }

    protected void printAsBlankNode(java.io.PrintWriter stream, SerializationState state, boolean first) {
    }

    public void write(java.io.PrintWriter stream, SerializationState state) {
        if (!refers() || (referencers.size() == 1 && getURI() == null)) {
            return;
        }
        if (this.getURI() != null) {
            printURI(this.getURI(), stream);
        } else {
            stream.print("_:" + getID() + " ");
        }
        printAsBlankNode(stream, state);
        stream.println(" .\n");
    }

    protected boolean follow(LemonPredicate predicate) {
        return true;
    }

    protected boolean follow(String predName) {
        return true;
    }

    protected void doAccept(ElementVisitor visitor) {
        
    }
    
    public final void accept(ElementVisitor visitor) {
        if (visitor.hasVisited(this)) {
            return;
        }
        if (visitor.visitFirst()) {
            visitor.visit(this);
        }
        for (Map.Entry<String, Collection<LemonElement>> elemsES : strElems.entrySet()) {
            final String pred = elemsES.getKey();
            final Collection<LemonElement> elems = elemsES.getValue();
            for (LemonElement elem : elems) {
                if (elem instanceof SimpleLemonElement) {
                    if (!visitor.follow(URI.create(LemonModel.LEMON_URI + pred)) || follow(pred)) {
                        ((SimpleLemonElement) elem).accept(visitor);
                    }
                } else {
                    //log.info("Could not visit " + elem);
                }
            }
        }
        for (Map.Entry<LemonPredicate, Collection<LemonElement>> elemsES : predElems.entrySet()) {
            final LemonPredicate pred = elemsES.getKey();
            final Collection<LemonElement> elems = elemsES.getValue();
            for (LemonElement elem : elems) {
                if (elem instanceof SimpleLemonElement) {
                    if (!visitor.follow(pred.getURI()) || follow(pred)) {
                        ((SimpleLemonElement) elem).accept(visitor);
                    }
                } else {
                    //log.info("Could not visit " + elem);
                }
            }
        }
        for (Map.Entry<String, LemonElement> es : strElem.entrySet()) {
            final String pred = es.getKey();
            final LemonElement elem = es.getValue();
            if (elem instanceof SimpleLemonElement) {
                if (visitor.follow(URI.create(LemonModel.LEMON_URI + pred)) || follow(pred)) {
                    ((SimpleLemonElement) elem).accept(visitor);
                }
            } else {
                //log.info("Could not visit " + elem);
            }
        }
        doAccept(visitor);
        if (!visitor.visitFirst()) {
            visitor.visit(this);
        }
    }

    public void clearAll() {
        for (Collection<LemonElement> elems : predElems.values()) {
            for (LemonElement lemonElement : elems) {
                if (lemonElement instanceof SimpleLemonElement) {
                    ((SimpleLemonElement) lemonElement).referencers.remove(this);
                }
            }
        }
        for (Collection<LemonElement> elems : strElems.values()) {
            for (LemonElement lemonElement : elems) {
                if (lemonElement instanceof SimpleLemonElement) {
                    ((SimpleLemonElement) lemonElement).referencers.remove(this);
                }
            }
        }
        for (LemonElement lemonElement : strElem.values()) {
            if (lemonElement instanceof SimpleLemonElement) {
                ((SimpleLemonElement) lemonElement).referencers.remove(this);
            }
        }
        predElems.clear();
        strElem.clear();
        strElems.clear();
        strText.clear();
    }

    protected ReaderAccepter defaultAccept(URI pred, URI value, LinguisticOntology lingOnto) {
        if (pred.toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
            if(!value.toString().startsWith(LemonModel.LEMON_URI)) {
                addType(value);
                return null;
            } else {
                return null;
            }
        }
        if(!acceptProperties(pred, value, lingOnto)) {
            System.err.println("Skipped triple: " + pred + " " + value);
        }
        return null;
    }

    protected ReaderAccepter defaultAccept(URI pred, String value) {
        System.err.println("Skipped triple: " + pred + " " + value);
        return null;
    }

    protected void defaultAccept(URI pred, String val, String lang) {
        System.err.println("Skipped triple: " + pred + " " + val + "@" + lang);
    }

    protected boolean acceptProperties(URI pred, URI value, LinguisticOntology lingOnto) {
        try {
            if(pred.getFragment() != null && lingOnto.getProperty(pred.getFragment()) != null &&
                    value.getFragment() != null && lingOnto.getPropertyValue(value.getFragment()) != null) {
                addProperty(lingOnto.getProperty(pred.getFragment()), lingOnto.getPropertyValue(value.getFragment()));
                return true;
            }  
        } catch(Exception x) {
            
        }
        return false;
    }
    
    @Override
    public Map<URI, Collection<Object>> getElements() {
        Map<URI, Collection<Object>> rval = new HashMap<URI, Collection<Object>>();
        for (Map.Entry<LemonPredicate, Collection<LemonElement>> entry : predElems.entrySet()) {
            rval.put(entry.getKey().getURI(), (Collection) entry.getValue());
        }

        for (Map.Entry<String, LemonElement> entry : strElem.entrySet()) {
            URI uri = URI.create(LemonModel.LEMON_URI + entry.getKey());
            if (!rval.containsKey(uri)) {
                rval.put(uri, new LinkedList());
            }
            rval.get(uri).add(entry.getValue());
        }

        for (Map.Entry<String, Collection<LemonElement>> entry : strElems.entrySet()) {
            URI uri = URI.create(LemonModel.LEMON_URI + entry.getKey());
            if (!rval.containsKey(uri)) {
                rval.put(uri, new LinkedList());
            }
            rval.get(uri).addAll(entry.getValue());
        }

        for (Map.Entry<String, Text> entry : strText.entrySet()) {
            URI uri = URI.create(LemonModel.LEMON_URI + entry.getKey());
            if (!rval.containsKey(uri)) {
                rval.put(uri, new LinkedList());
            }
            rval.get(uri).add(entry.getValue());
        }


        final URI RDF_TYPE = URI.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        if (!rval.containsKey(RDF_TYPE) && !types.isEmpty()) {
            rval.put(RDF_TYPE, new LinkedList<Object>());
        }
        for (URI type : types) {
            rval.get(RDF_TYPE).add(type);
        }
        return rval;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public boolean isMultiReferenced() {
        return referencers.size() > 1;
    }
}
