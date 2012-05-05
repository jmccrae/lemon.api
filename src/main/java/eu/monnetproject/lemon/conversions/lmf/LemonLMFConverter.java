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
package eu.monnetproject.lemon.conversions.lmf;

/**
 *
 * @author John McCrae
 */
import eu.monnetproject.lemon.LemonFactory;
import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.URIElement;
import eu.monnetproject.lemon.URIValue;
import static eu.monnetproject.lemon.conversions.lmf.CollectionFunctions.*;
import eu.monnetproject.lemon.impl.LexiconImpl;
import eu.monnetproject.lemon.impl.LemonModelImpl;
import eu.monnetproject.lemon.model.Argument;
import eu.monnetproject.lemon.model.Component;
import eu.monnetproject.lemon.model.Edge;
import eu.monnetproject.lemon.model.Example;
import eu.monnetproject.lemon.model.Frame;
import eu.monnetproject.lemon.model.LemonElement;
import eu.monnetproject.lemon.model.LemonElementOrPredicate;
import eu.monnetproject.lemon.model.LemonPredicate;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.LexicalForm;
import eu.monnetproject.lemon.model.LexicalSense;
import eu.monnetproject.lemon.model.Lexicon;
import eu.monnetproject.lemon.model.Property;
import eu.monnetproject.lemon.model.PropertyValue;
import eu.monnetproject.lemon.model.Representation;
import eu.monnetproject.lemon.model.SenseDefinition;
import eu.monnetproject.lemon.model.SynArg;
import eu.monnetproject.lemon.model.Text;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LemonLMFConverter {

    private static final String ISOCAT = "http://www.isocat.org/datcat/null#";
    private List<String> ignoredFeats = Arrays.asList(new String[]{"writtenForm", "lexEntryType", "syntacticFunction", "syntacticConstituent", "marker", "separator"});

    //def lemon2lmf(model:LemonModel) : XMLNode = ToLMF.lemon2lmf(model)
    //def lmf2lemon(lmfDoc:XMLNode) : List[Lexicon] = ToLemon.lmf2lemon(lmfDoc)
    ////////////////////////////////////////////////////////////////////////////////////
    // Lemon to LMF
    private static class toLMF {

        private final Document document;
        private final Element root;

        public toLMF() throws ParserConfigurationException {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            root = document.createElement("LexicalResource");
            root.setAttribute("dtdVersion", "16");
            document.appendChild(root);
        }

        private String toLMFValue(LemonElementOrPredicate elem, LemonModel model) {
            if (elem.getURI() != null) {
                if (elem.getURI().getFragment() != null) {
                    return elem.getURI().getFragment();
                } else {
                    return elem.getURI().toString().substring(elem.getURI().toString().lastIndexOf("/"));
                }
            } else {
                return ((LemonElement) elem).getID();
            }
        }

        public Document lemon2lmf(LemonModel model) {
            append(root, header(model));
            for (Lexicon lexicon : model.getLexica()) {
                append(root, lexicon2lmf(lexicon, model));
            }
            return document;
        }

        private Node header(LemonModel model) {
            final Element globalInfo = document.createElement("GlobalInformation");
            append(globalInfo, getFeat("label", "LMF Lexicon derived from lemon model " + model.getLexica().iterator().next().getURI().toString()));
            return globalInfo;
        }

        private void append(Node head, Node to) {
            head.appendChild(to);
        }

        private void append(Node head, List<Node> tos) {
            for (Node to : tos) {
                head.appendChild(to);
            }
        }

        private Node lexicon2lmf(Lexicon lexicon, LemonModel model) {
            final Element lexiconTag = document.createElement("Lexicon");
            append(lexiconTag, getFeat("language", lexicon.getLanguage()));
            for (LexicalEntry entry : lexicon.getEntrys()) {
                append(lexiconTag, lexEntry2lmf(entry, model));
                append(lexiconTag, senses2spc(lexicon, model));
                append(lexiconTag, frames2lmf(lexicon, model));
            }
            return lexiconTag;
        }

        private List<Node> lexEntry2lmf(LexicalEntry lexEntry, LemonModel model) {
            final Element lexEntryTag = document.createElement("LexicalEntry");
            append(lexEntryTag, getFeats(lexEntry.getPropertys(), model));
            append(lexEntryTag, getFeat("lexEntryType", lexEntry.getTypes().iterator().next().toString()));
            append(lexEntryTag, canForm2lmf(lexEntry.getCanonicalForm(), model));
            append(lexEntryTag, forms2lmf(lexEntry, model));
            append(lexEntryTag, abstractForms2lmf(lexEntry, model));
            append(lexEntryTag, compList2lmf(lexEntry, model));
            append(lexEntryTag, senses2lmf(lexEntry, model));
            append(lexEntryTag, synBeh2lmf(lexEntry, model));

            LinkedList<Node> tags = new LinkedList<Node>();
            tags.add(lexEntryTag);
            tags.addAll(mweLink2lmf(lexEntry, model));
            tags.addAll(mwe2lmf(lexEntry, model));
            // TODO: topics
            // TODO: lexical variants
            return tags;
        }

        private Element canForm2lmf(LexicalForm form, LemonModel model) {
            final Element lemmaTag = document.createElement("Lemma");
            append(lemmaTag, getFeat("writtenForm", form.getWrittenRep().value));
            return lemmaTag;
        }

        private List<Node> forms2lmf(LexicalEntry lexEntry, LemonModel model) {
            List<Node> rval = new LinkedList<Node>();
            rval.add(form2lmf(lexEntry.getCanonicalForm(), model));
            for (LexicalForm form : lexEntry.getOtherForms()) {
                rval.add(form2lmf(form, model));
            }
            return rval;
        }

        private Node form2lmf(LexicalForm form, LemonModel model) {
            final Element formTag = document.createElement("WordForm");
            append(formTag, getFeat("writtenForm", form.getWrittenRep().value));
            append(formTag, getFeats(form.getPropertys(), model));
            append(formTag, reps2formRep(form.getRepresentations(), model));
            return formTag;
        }

        private List<Node> abstractForms2lmf(LexicalEntry lexEntry, LemonModel model) {
            List<Node> nodes = new LinkedList<Node>();
            for (LexicalForm form : lexEntry.getAbstractForms()) {
                final Element stemTag = document.createElement("Stem");
                append(stemTag, getFeat("writtenForm", form.getWrittenRep().value));
                append(stemTag, getFeats(form.getPropertys(), model));
                append(stemTag, reps2formRep(form.getRepresentations(), model));
                nodes.add(stemTag);
            }

            // TODO : form variants
            return nodes;
        }

        private List<Node> reps2formRep(Map<Representation, Collection<Text>> repMap, LemonModel model) {
            List<Node> nodes = new LinkedList<Node>();
            for (Representation rep : repMap.keySet()) {
                for (Text value : repMap.get(rep)) {
                    final Element formRepTag = document.createElement("FormRepresentation");
                    append(formRepTag, getFeat("writtenForm", value.value));
                    append(formRepTag, decodeLangTag(value.language));
                    nodes.add(formRepTag);
                }
            }
            return nodes;
        }

        private List<Node> decodeLangTag(String langTag) {
            final String ieft = "([A-Za-z]{2,3})(-[A-Za-z]{4})?(-[A-Za-z]{2}|-[0-9]{3})?(-[A-Za-z]{5,8}|-[0-9]\\w{3})?(-\\w-\\w{2,8})?";
            final Matcher matcher = Pattern.compile(ieft).matcher(langTag);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Bad lang tag " + langTag);
            }
            final String lang = matcher.group(1);
            final String script = matcher.group(2);
            final String region = matcher.group(3);
            final String variant = matcher.group(4);
            final String extension = matcher.group(5);
            final List<Node> langTags = new LinkedList<Node>();

            if (script != null) {
                langTags.add(getFeat("script", script.substring(1)));
            }

            if (region != null) {
                langTags.add(getFeat("geographicalVariant", region.substring(1)));
            }
            if (variant != null) {
                langTags.add(getFeat("variant", variant.substring(1)));
            }
            if (extension != null) {
                langTags.add(getFeat("orthographyName", extension.substring(3)));
            }
            return langTags;
        }

        private List<Node> compList2lmf(LexicalEntry lexEntry, LemonModel model) {
            if (lexEntry.getDecompositions().isEmpty()) {
                return Collections.EMPTY_LIST;
            } else {
                final Node loc = document.createElement("ListOfComponents");
                append(loc, getFeat("size", "" + lexEntry.getDecompositions().iterator().next().size()));
                append(loc, comp2lmf(lexEntry.getDecompositions().iterator().next(), model, 1));
                return Collections.singletonList(loc);
            }
        }

        private List<Node> comp2lmf(List<Component> compList, LemonModel model, int order) {
            if (compList.isEmpty()) {
                return Collections.EMPTY_LIST;
            } else {
                final Element compTag = document.createElement("Component");
                append(compTag, getFeat("order", "" + order));
                append(compTag, getFeats(compList.get(0).getPropertys(), model));
                compTag.setAttribute("entry", frag(compList.get(0).getElement(), model));
                List<Node> nodes = new LinkedList<Node>();
                nodes.add(compTag);
                nodes.addAll(comp2lmf(compList.subList(1, compList.size()), model, order + 1));
                return nodes;
            }
        }

        private List<Node> senses2lmf(LexicalEntry lexEntry, LemonModel model) {
            List<Node> nodes = new LinkedList<Node>();
            for (LexicalSense sense : lexEntry.getSenses()) {
                nodes.add(sense2lmf(sense, model));
            }
            return nodes;
        }

        private Node sense2lmf(LexicalSense sense, LemonModel model) {
            final Element senseTag = document.createElement("Sense");
            final Element refTag = document.createElement("MonolingualExternalRef");

            append(refTag, getFeat("externalSystem", "application/rdf"));
            append(refTag, getFeat("externalReference", sense.getReference().toString()));
            append(senseTag, refTag);

            append(senseTag, getFeat("refPref", sense.getRefPref().toString()));
            // TODO: context
            // TODO: condition
            for (LexicalSense subsense : sense.getSubsenses()) {
                append(senseTag, sense2lmf(sense, model));
            }
            append(senseTag, examples2lmf(sense.getExamples(), model));
            append(senseTag, defs2lmf(sense.getDefinitions().values(), model));
            append(senseTag, semArgs2pr(sense, model));
            append(senseTag, getFeats(sense.getPropertys(), model));
            return senseTag;
        }

        private List<Node> senseRelations2lmf(LemonModel model) {
            // TODO: sense relations
            return Collections.EMPTY_LIST;
        }

        private List<Node> examples2lmf(Collection<Example> examples, LemonModel model) {
            final LinkedList<Node> nodes = new LinkedList<Node>();

            for (Example example : examples) {
                final Element exampleTag = document.createElement("SenseExample");
                append(exampleTag, texts2textRep(example.getValue()));
                append(exampleTag, getFeats(example.getPropertys(), model));
                nodes.add(exampleTag);
            }
            return nodes;
        }

        private List<Node> defs2lmf(Collection<Collection<SenseDefinition>> defss, LemonModel model) {
            final LinkedList<Node> nodes = new LinkedList<Node>();
            for (Collection<SenseDefinition> defs : defss) {
                for (SenseDefinition defn : defs) {
                    final Element defTag = document.createElement("Definition");
                    append(defTag, texts2textRep(defn.getValue()));
                    append(defTag, getFeats(defn.getPropertys(), model));
                    nodes.add(defTag);
                }
            }
            return nodes;
        }

        private List<Node> texts2textRep(Collection<Text> reps) {
            final LinkedList<Node> nodes = new LinkedList<Node>();
            for (Text rep : reps) {
                Element repTag = texts2textRep(rep);
                nodes.add(repTag);
            }
            return nodes;
        }

        private Element texts2textRep(Text rep) throws DOMException {
            final Element repTag = document.createElement("TextRepresentation");
            append(repTag, getFeat("writtenForm", rep.value));
            append(repTag, decodeLangTag(rep.language));
            return repTag;
        }

        private List<Node> semArgs2pr(LexicalSense sense, LemonModel model) {
            if (!sense.getSubjOfProps().isEmpty()
                    || !sense.getObjOfProps().isEmpty()
                    || !sense.getIsAs().isEmpty()) {
                final Element tag = document.createElement("PredicativeRepresentation");
                tag.setAttribute("predicate", ("__predicate_" + frag(sense, model)));
                tag.setAttribute("correspondences", ("__correspondence_" + frag(sense, model)));
                return Collections.singletonList((Node) tag);
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        private List<Node> senses2spc(Lexicon lexicon, LemonModel model) {
            List<Node> nodes = new LinkedList<Node>();
            for (LexicalEntry entry : lexicon.getEntrys()) {
                for (LexicalSense sense : entry.getSenses()) {
                    if (!sense.getSubjOfProps().isEmpty() || !sense.getObjOfProps().isEmpty() || !sense.getIsAs().isEmpty()) {
                        final Element semPredTag = document.createElement("SemanticPredicate");
                        final Element sscTag = document.createElement("SynSemCorrespondence");
                        for (Argument arg : sense.getSubjOfProps()) {
                            final Element semArgTag = document.createElement("SemanticArgument");
                            append(semArgTag, getFeat("label", "subjOfProp"));
                            append(semArgTag, getFeat("id", "__subjOfProp_" + frag(sense, model)));
                            append(semPredTag, semArgTag);
                            final Element ssamTag = document.createElement("SynSemArgMap");
                            ssamTag.setAttribute("semArg", "__subjOfProp_" + frag(sense, model));
                            ssamTag.setAttribute("synArg", frag(arg, model));
                            append(sscTag, ssamTag);
                        }

                        for (Argument arg : sense.getObjOfProps()) {
                            final Element semArgTag = document.createElement("SemanticArgument");
                            append(semArgTag, getFeat("label", "objOfProp"));
                            append(semArgTag, getFeat("id", "__objOfProp_" + frag(sense, model)));
                            append(semPredTag, semArgTag);
                            final Element ssamTag = document.createElement("SynSemArgMap");
                            ssamTag.setAttribute("semArg", "__subjOfProp_" + frag(sense, model));
                            ssamTag.setAttribute("synArg", frag(arg, model));
                            append(sscTag, ssamTag);
                        }

                        for (Argument arg : sense.getIsAs()) {
                            final Element semArgTag = document.createElement("SemanticArgument");
                            append(semArgTag, getFeat("label", "isA"));
                            append(semArgTag, getFeat("id", "__isA_" + frag(sense, model)));
                            append(semPredTag, semArgTag);
                            final Element ssamTag = document.createElement("SynSemArgMap");
                            ssamTag.setAttribute("semArg", "__subjOfProp_" + frag(sense, model));
                            ssamTag.setAttribute("synArg", frag(arg, model));
                            append(sscTag, ssamTag);
                        }
                        semPredTag.setAttribute("id", ("__predicate_" + frag(sense, model)));
                        nodes.add(semPredTag);
                        sscTag.setAttribute("id", "__correspondence_" + frag(sense, model));
                        nodes.add(sscTag);
                    }
                }
            }
            return nodes;
        }

        private List<Node> synBeh2lmf(LexicalEntry lexEntry, LemonModel model) {
            List<Node> nodes = new LinkedList<Node>();
            if (lexEntry.getSynBehaviors().isEmpty()) {
                return Collections.EMPTY_LIST;
            } else {
                final Element sbTag = document.createElement("SyntacticBehavior");
                final StringBuilder builder = new StringBuilder();
                for (Frame frame : lexEntry.getSynBehaviors()) {
                    builder.append(frag(frame, model)).append(" ");
                }
                sbTag.setAttribute("subcategorizationFrames", builder.toString().trim());
                return Collections.singletonList((Node) sbTag);
            }
            // TODO: subcat frame sets
        }

        private List<Node> frames2lmf(Lexicon lexicon, LemonModel model) {
            final LinkedList<Node> nodes = new LinkedList<Node>();
            for (LexicalEntry entry : lexicon.getEntrys()) {
                for (Frame frame : entry.getSynBehaviors()) {
                    final Element sfTag = document.createElement("SubcategorizationFrame");
                    append(sfTag, args2lmf(frame, model));
                    sfTag.setAttribute("id", frag(frame, model));
                    nodes.add(sfTag);
                }
            }
            return nodes;
        }

        private List<Node> args2lmf(Frame frame, LemonModel model) {
            final LinkedList<Node> nodes = new LinkedList<Node>();
            for (SynArg synArg : frame.getSynArgs().keySet()) {
                for (Argument arg : frame.getSynArgs().get(synArg)) {
                    final Element synArgTag = document.createElement("SyntacticArgument");
                    append(synArgTag, getFeat("syntacticFunction", toLMFValue(synArg, model)));
                    append(synArgTag, getFeats(frame.getPropertys(), model));
                    append(synArgTag, getArgProps(arg, model));
                    synArgTag.setAttribute("id", frag(arg, model));
                    nodes.add(synArgTag);
                }
            }
            return nodes;
        }

        private List<Node> getArgProps(Argument arg, LemonModel model) {
            if (arg.getMarker() != null) {
                return Collections.singletonList(getFeat("marker", frag(arg.getMarker(), model)));
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        private List<Node> mwe2lmf(LexicalEntry entry, LemonModel model) {
            final LinkedList<Node> nodes = new LinkedList<Node>();
            for (eu.monnetproject.lemon.model.Node root : entry.getPhraseRoots()) {
                final Element patTag = document.createElement("MWEPattern");
                append(patTag, node2lmf(root, model));
                patTag.setAttribute("id", frag(root, model));
                nodes.add(patTag);
            }
            return nodes;
        }

        private List<Node> mweLink2lmf(LexicalEntry lexEntry, LemonModel model) {
            if (lexEntry.getPhraseRoots().isEmpty()) {
                return Collections.EMPTY_LIST;
            } else {
                final StringBuilder sb = new StringBuilder();
                for (eu.monnetproject.lemon.model.Node node : lexEntry.getPhraseRoots()) {
                    sb.append(frag(node, model)).append(" ");
                }
                return Collections.singletonList(getFeat("mwePattern", sb.toString().trim()));
            }
        }

        private Node node2lmf(eu.monnetproject.lemon.model.Node node, LemonModel model) {
            final Element nodeTag = document.createElement("MWENode");
            if (node.getConstituent() != null) {
                append(nodeTag, getFeat("syntacticConstituent", toLMFValue(node.getConstituent(), model)));
            }
            for (Edge edge2 : node.getEdges().keySet()) {
                for (eu.monnetproject.lemon.model.Node node2 : node.getEdge(edge2)) {
                    final Element edgeTag = document.createElement("MWEEdge");
                    append(edgeTag, getFeat("function", toLMFValue(edge2, model)));
                    append(edgeTag, node2lmf(node, model));
                    append(nodeTag, edgeTag);
                }
            }
            if (node.getLeaf() != null) {
                final Element lexTag = document.createElement("MWELex");
                lexTag.setAttribute("target", frag(node.getLeaf(), model));
                append(nodeTag, lexTag);
            }
            if (node.getSeparator() != null) {
                append(nodeTag, getFeat("separator", node.getSeparator().value));
            }
            return nodeTag;
        }

        private <T extends LemonPredicate, U extends LemonElement> List<Node> getFeats(Map<T, Collection<U>> props, LemonModel model) {
            List<Node> rval = new LinkedList<Node>();
            for (T prop : props.keySet()) {
                for (U value : props.get(prop)) {
                    rval.add(getFeat(toLMFValue(prop, model), toLMFValue(value, model)));
                }
            }
            return rval;
        }

        private Node getFeat(String att, String vl) {
            final Element featTag = document.createElement("feat");
            featTag.setAttribute("att", att);
            featTag.setAttribute("val", vl);
            return featTag;
        }
        private final Random rand = new java.util.Random();

        private String frag(LemonElement elem, LemonModel model) {
            if (elem.getURI() != null) {
                return elem.getURI().getFragment();
            } else /*
             * if (elem.getID() != null)
             */ {
                return elem.getID();
            }
        }
    }

    private static class ToLemon {

        private List<eu.monnetproject.lemon.model.Node> readMWEPattern(Element e, LMFAugments augment, List<List<Component>> decomposition) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private void readSubcatFrameSet(Frame frame, Element get, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private void readDefinition(LexicalSense sense, Element defNode, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private void readSenseRelation(LexicalSense sense, Element relNode, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private void readAxes(LexicalSense sense, Element senseNode, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private String readSemArg(Element semPredNode, Element mapNode, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private static class StringPair {

            private final String s1, s2;

            public StringPair(String s1, String s2) {
                this.s1 = s1;
                this.s2 = s2;
            }

            public String s1() {
                return s1;
            }

            public String s2() {
                return s2;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final StringPair other = (StringPair) obj;
                if ((this.s1 == null) ? (other.s1 != null) : !this.s1.equals(other.s1)) {
                    return false;
                }
                if ((this.s2 == null) ? (other.s2 != null) : !this.s2.equals(other.s2)) {
                    return false;
                }
                return true;
            }

            @Override
            public int hashCode() {
                int hash = 3;
                hash = 73 * hash + (this.s1 != null ? this.s1.hashCode() : 0);
                hash = 73 * hash + (this.s2 != null ? this.s2.hashCode() : 0);
                return hash;
            }
        }

        private static class LMFAugments {

            public final Map<StringPair, Element> nodeMap;
            public final Map<String, List<Node>> axisMap;
            private final LemonModelImpl model;
            public final LinguisticOntology lingOnto;
            public final LemonFactory factory;

            public LMFAugments(Map<StringPair, Element> nodeMap, Map<String, List<Node>> axisMap, LemonModelImpl model, LinguisticOntology lingOnto) {
                this.nodeMap = nodeMap;
                this.axisMap = axisMap;
                this.model = model;
                this.factory = model.getFactory();
                this.lingOnto = lingOnto;
            }
            private final Map<String, LemonElement> idMap = new HashMap<String, LemonElement>();
            private String language = "und";
            private final Map<String, Argument> argMap = new HashMap<String, Argument>();

            public <T extends LemonElement> T add(Node node, T elem) {
                final Node x = node.getAttributes().getNamedItem("id");
                if (x != null) {
                    idMap.put(x.getTextContent(), elem);
                    return elem;
                } else {
                    return elem;
                }
            }
        }

        private List<Element> e(NodeList nl) {
            final LinkedList<Element> es = new LinkedList<Element>();
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i) instanceof Element) {
                    es.add((Element) nl.item(i));
                }
            }
            return es;
        }

        private String att(Element n, String p) {
            return n.getAttributes().getNamedItem(p).getTextContent();
        }

        private List<Element> c(Node n, String tag) {
            final NodeList childNodes = n.getChildNodes();
            final LinkedList<Element> elems = new LinkedList<Element>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i) instanceof Element && ((Element) childNodes.item(i)).getTagName().equals(tag)) {
                    elems.add((Element) childNodes.item(i));
                }
            }
            return elems;
        }

        private Element c1(Node n, String tag) {
            final NodeList childNodes = n.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i) instanceof Element && ((Element) childNodes.item(i)).getTagName().equals(tag)) {
                    return (Element) childNodes.item(i);
                }
            }
            return null;
        }

        private URI uri(String s1, String s2) {
            try {
                return URI.create(s1 + URLEncoder.encode(s2, "UTF-8"));
            } catch (Exception x) {
                throw new RuntimeException(x);
            }
        }

        public List<Lexicon> lmf2lemon(Document lmfDoc, LemonModelImpl model, LinguisticOntology lingOnto) {
            final LMFAugments augment = new LMFAugments(buildIDMap(lmfDoc), buildAxisMap(lmfDoc.getDocumentElement()), model, lingOnto);

            if (!lmfDoc.getDocumentElement().getTagName().equals("LexicalResource")) {
                throw new IllegalArgumentException("Not an LMF file");
            }
            final LinkedList<Lexicon> lexica = new LinkedList<Lexicon>();

            for (Element lexicon : e(lmfDoc.getDocumentElement().getElementsByTagName("Lexicon"))) {
                lexica.add(readLexicon(lexicon, augment));
            }
            return lexica;
        }

        private Map<StringPair, Element> buildIDMap(Node head) {
            final HashMap<StringPair, Element> map = new HashMap<StringPair, Element>();
            _buildIDMap(head, map);
            return map;
        }

        private void _buildIDMap(Node head, HashMap<StringPair, Element> map) {
            for (Element elem : e(head.getChildNodes())) {
                if (att(elem, "id") != null) {
                    map.put(new StringPair(elem.getTagName(), att(elem, "id")), elem);
                }
                _buildIDMap(elem, map);
            }
        }

        private Map<String, List<Node>> buildAxisMap(Element head) {
            final HashMap<String, List<Node>> map = new HashMap<String, List<Node>>();

            for (Element senseAxis : e(head.getElementsByTagName("SenseAxis"))) {
                for (Element senseAxisRelation : c(head, "SenseAxisRelation")) {
                    for (String targ : att(senseAxisRelation, "id").split(" ")) {
                        if (!map.containsKey(targ)) {
                            map.put(targ, new LinkedList<Node>());
                        }
                        map.get(targ).add(senseAxis);
                    }
                }
            }

            // TODO: Target axes 

            return map;
        }

        private Lexicon readLexicon(Element lexiconNode, LMFAugments augment) {
            String language = getFeatOrElse(lexiconNode, "language", "und");
            augment.language = language;
            final LinkedList<LexicalEntry> entries = new LinkedList<LexicalEntry>();
            for (Element lexEntryNode : c(lexiconNode, "LexicalEntry")) {
                entries.add(readLexicalEntry(lexEntryNode, augment));
            }

            // IGNORE: MorphologicalPattern
            for (Element node : c(lexiconNode, "MorphologicalPattern")) {
                System.err.println("Morphological patterns not supported by lemon. Ignoring " + att(node, "id"));
            }
            final LexiconImpl lexicon = new LexiconImpl(URI.create(getFeatOrElse(lexiconNode, "uri", "unknown:lexicon#lexicon")), augment.model);
            for (LexicalEntry entry : entries) {
                lexicon.addEntry(entry);
            }
            return lexicon;
        }

        private LemonElement getByID(Element node, LMFAugments augment) {
            final String att = att(node, "id");
            if (att != null) {
                return augment.idMap.get(att);
            } else {
                return null;
            }
        }

        private LexicalEntry readLexicalEntry(Element lexEntryNode, final LMFAugments augment) {
            if (getByID(lexEntryNode, augment) != null) {
                return (LexicalEntry) getByID(lexEntryNode, augment);
            }
            final Element lemmaNode = c1(lexEntryNode, "Lemma");
            if (lemmaNode == null) {
                throw new RuntimeException();
            }
            String canRep = getWrittenRep(lemmaNode, augment);
            LexicalEntry entry = getFeat(lexEntryNode, "lexEntryType") == null
                    ? augment.factory.makeLexicalEntry(URI.create(getFeatOrElse(lexEntryNode, "uri", uri("unknown:lexicon#", canRep).toString())))
                    : (getFeat(lexEntryNode, "lexEntryType").equals("Word")
                    ? augment.factory.makeWord(URI.create(getFeatOrElse(lexEntryNode, "uri", uri("unknown:lexicon#", canRep).toString())))
                    : (getFeat(lexEntryNode, "lexEntryType").equals("Phrase")
                    ? augment.factory.makePhrase(URI.create(getFeatOrElse(lexEntryNode, "uri", uri("unknown:lexicon#", canRep).toString())))
                    : augment.factory.makePart(URI.create(getFeatOrElse(lexEntryNode, "uri", uri("unknown:lexicon#", canRep).toString())))));


            final List<LexicalForm> canOtherForm = filterCanonical(readForm(c(lexEntryNode, "WordForm"), augment), canRep);
            final LexicalForm canForm = canOtherForm.get(0);
            final List<LexicalForm> otherForms = canOtherForm.subList(1, canOtherForm.size());

            final List<LexicalForm> abstractForms = readForm(c(lexEntryNode, "Stem"), augment);

            final List<List<Component>> decomposition = map(c(lexEntryNode, "ListOfComponents"), new Converter<Element, List<Component>>() {

                @Override
                public List<Component> f(Element loc) {
                    return readCompList(loc, augment);
                }
            });
            // TODO: Form relations


            final List<Frame> frames = map(c(lexEntryNode, "SyntacticBehaviour"), new Converter<Element, Frame>() {

                @Override
                public Frame f(Element e) {
                    return readSynBehavior(e, augment);
                }
            });

            final List<LexicalSense> sense = map(c(lexEntryNode, "Sense"), new Converter<Element, LexicalSense>() {

                @Override
                public LexicalSense f(Element e) {
                    return readSense(e, augment);
                }
            });


            final List<eu.monnetproject.lemon.model.Node> phraseRoots = getFeat(lexEntryNode, "mwePattern") != null
                    ? readMWEPattern(augment.nodeMap.get(new StringPair("MWEPattern", getFeat(lexEntryNode, "mwePattern"))), augment, decomposition) : null;

            entry.setCanonicalForm(canForm);
            for (List<Component> c : decomposition) {
                entry.addDecomposition(c);
            }
            for (LexicalForm othForm : otherForms) {
                entry.addOtherForm(othForm);
            }

            for (LexicalForm absForm : abstractForms) {
                entry.addAbstractForm(absForm);
            }

            for (LexicalSense s : sense) {
                entry.addSense(s);
            }

            addProperties(entry, lexEntryNode);

            for (Frame frame : frames) {
                entry.addSynBehavior(frame);
            }

            for (eu.monnetproject.lemon.model.Node node : phraseRoots) {
                entry.addPhraseRoot(node);
            }

            return entry;
        }

        private List<LexicalForm> filterCanonical(List<LexicalForm> readForm, String canRep) {
            if (readForm.isEmpty()) {
                throw new LMFFormatException("Lemma \"" + canRep + "\" does not have a WordForm");
            } else {
                if (readForm.get(0).getWrittenRep().value.equals(canRep)) {
                    return readForm;
                } else {
                    final List<LexicalForm> c2 = filterCanonical(readForm.subList(1, readForm.size()), canRep);
                    final ArrayList<LexicalForm> rv = new ArrayList<LexicalForm>();
                    rv.add(c2.get(0));
                    rv.add(readForm.get(0));
                    rv.addAll(c2.subList(1, c2.size()));
                    return rv;
                }
            }
        }

        private List<LexicalForm> readForm(List<Element> c, final LMFAugments augment) {
            return map(c, new Converter<Element, LexicalForm>() {

                @Override
                public LexicalForm f(Element e) {
                    final LexicalForm f = augment.factory.makeForm();
                    f.setWrittenRep(new Text(getWrittenRep(e, augment), augment.language));
                    addProperties(f, e);
                    for (Element repNode : c(e, "FormRepresentation")) {
                        f.addRepresentation(Representation.representation, getTextFromRep(repNode, augment));
                    }
                    return f;
                }
            });
        }

        private List<Component> readCompList(Element element, final LMFAugments augment) {
            return map(c(element, "Component"), new Converter<Element, Component>() {

                @Override
                public Component f(Element e) {
                    final Component component = augment.factory.makeComponent();
                    if (getFeat(e, "entry") != null) {
                        final LemonElement entry = getByID(augment.nodeMap.get(new StringPair("LexicalEntry", getFeat(e, "entry"))), augment);
                        if (entry != null && entry instanceof LexicalEntry) {
                            component.setElement((LexicalEntry) entry);
                        }
                    }
                    return component;
                }
            });
        }

        private Frame readSynBehavior(Element synBehNode, LMFAugments augment) {
            final Frame frame = augment.factory.makeFrame();
            final String frameNode = getFeat(synBehNode, "subcategorizationFrameSets");
            if (frameNode != null) {
                for (String entryID : frameNode.split("\\s+")) {
                    readSubcatFrameSet(frame, augment.nodeMap.get(new StringPair("SubcategorizationFrameSet", entryID)), augment);
                }
            }

            final String frameNode2 = getFeat(synBehNode, "subcategorizationFrames");
            if (frameNode2 != null) {
                for (String entryID : frameNode2.split("\\s+")) {
                    readSubcatFrameSet(frame, augment.nodeMap.get(new StringPair("SubcategorizationFrame", entryID)), augment);
                }
            }
            return frame;
        }

        private LexicalSense readSense(Element senseNode, LMFAugments augment) {
            if (getByID(senseNode, augment) != null) {
                return (LexicalSense) getByID(senseNode, augment);
            } else {
                final LexicalSense sense = augment.factory.makeSense();

                for (Element senseNode2 : c(senseNode, "Sense")) {
                    System.err.println("Recursive sense ignored!");
                }

                for (Element equivNode : c(senseNode, "Equivalent")) {
//            System.err.println("Equivalent ignored! Equivalents in LMF are ambiguous, please use SenseRelation instead");
                }

                for (Element contextNode : c(senseNode, "Context")) {
                    readExample(sense, contextNode, augment);
                }

                for (Element exampleNode : c(senseNode, "SenseExample")) {
                    readExample(sense, exampleNode, augment);
                }

                // TODO: Subject Field

                readPredicativeRepresentation(sense, c1(senseNode, "PredicativeRepresentation"), augment);

                for (Element defNode : c(senseNode, "Definition")) {
                    readDefinition(sense, defNode, augment);
                }


                for (Element relNode : c(senseNode, "SenseRelation")) {
                    readSenseRelation(sense, relNode, augment);
                }

                for (Element synsetNode : c(senseNode, "Synset")) {
                    for (Element relNode : c(synsetNode, "SynsetRelation")) {
                        readSenseRelation(sense, relNode, augment);
                    }
                }

                readAxes(sense, senseNode, augment);

                if (!c(senseNode, "MonolingualExternalRef").isEmpty()) {
                    sense.setReference(URI.create(getFeat(c1(senseNode, "MonolingualExternalRef"), "externalReference")));
                }

                if (getFeat(senseNode, "refPref") != null) {
                    if (getFeat(senseNode, "refPref").equals("altSem")) {
                        sense.setRefPref(LexicalSense.ReferencePreference.altRef);
                    } else if (getFeat(senseNode, "refPref").equals("hiddenSem")) {
                        sense.setRefPref(LexicalSense.ReferencePreference.hiddenRef);
                    } else {
                        sense.setRefPref(LexicalSense.ReferencePreference.prefRef);
                    }
                }
                return sense;
            }
        }

        private void readExample(LexicalSense sense, Element contextNode, final LMFAugments augment) {
            for (Element textNode : c(contextNode, "TextRepresentation")) {
                final Example example = augment.factory.makeExample();
                final Text text = getTextFromRep(textNode, augment);
                example.setValue(text);
                sense.addExample(example);
            }
        }

        private void readPredicativeRepresentation(LexicalSense sense, Element prNode, LMFAugments augment) {
            if (getFeat(prNode, "correspondences") != null) {
                final Element semPredNode = augment.nodeMap.get(new StringPair("SemanticPredicate", getFeat(prNode, "predicate")));
                for (String corrID : getFeat(prNode, "correspondences").split("\\s+")) {
                    final Element corrNode = augment.nodeMap.get(new StringPair("SynSemCorrespondence", corrID));
                    final LinkedList<StringArgument> buffer = new LinkedList<StringArgument>();
                    for (Element mapNode : c(corrNode, "SynSemArgMap")) {
                        if (getFeat(mapNode, "synArg") != null) {
                            // Thank god! Probably reversing a lemon lexicon
                            buffer.add(new StringArgument(readSemArg(semPredNode, mapNode, augment), augment.argMap.get(getFeat(mapNode, "synArg"))));
                        } else {
                            System.err.println("Decoding LMF syn-sem arg map. This may take a while!");
                            final PropertyValue synFeat = toPropertyValue(getFeat(mapNode, "syntacticFeature"), augment.lingOnto);

                            boolean first = true;

                            final PropertyImpl synFuncProp = new PropertyImpl("syntacticFunction");

                            for (Argument arg : augment.argMap.values()) {
                                if (arg.getProperty(synFuncProp).equals(synFeat)) {
                                    buffer.add(new StringArgument(first ? "subjOfProp" : "objOfProp", arg));
                                    first = false;
                                }
                            }
                        }
                    }
                    for (StringArgument sa : buffer) {
                        if(sa.s.equals("subjOfProp")) {
                            sense.addObjOfProp(sa.arg);
                        } else if(sa.s.equals("objOfProp")) {
                            sense.addSubjOfProp(sa.arg);
                        } else if(sa.s.equals("isA")) {
                            sense.addIsA(sa.arg);
                        } else {
                            System.err.println("Bad syn arg type: " + sa.s);
                        }
                    }
                }
            }
        }

        //        def readSynArgLink(argNode : XMLNode, augment : LMFAugments) : SynArg = {
//      getFeat(argNode,"syntacticFunction") match {
//        case Some(label) => ISOcatSynArg(label)
//        case None => synArg
//      }
//    }
//    
//    def readSyntacticArgument(argNode : XMLNode, augment : LMFAugments) : Argument = {
//      val id = (argNode \ "@id") match {
//        case Seq(x,_*) => Some(x.text)
//        case Seq() => None
//      }
//      
//      if(id != None && augment.argMap.contains(id.get)) {
//        augment.argMap(id.get)
//      } else {
//        val marker = getFeat(argNode,"marker") match {
//          case Some(markerID) => {
//            augment.nodeMap.get(("LexicalEntry",markerID)) match {
//              case Some(node) => Some(readLexicalEntry(node,augment))
//              case None => Some(ISOcatPropertyValue(markerID))
//            }
//          }
//          case None => None
//        }
//        
//        val arg = Argument(marker = marker, property = getFeats(argNode))
//        
//        if(id != None) {
//          augment.argMap.put(id.get,arg)
//        }
//        
//        arg
//      }
//    }
//    
        public Text getTextFromRep(Element repNode, final LMFAugments augment) {
            throw new UnsupportedOperationException();
        }

        private String getWrittenRep(Element lemmaNode, LMFAugments augment) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        private String getFeatOrElse(Element lexiconNode, String att, String def) {
            final String val = getFeat(lexiconNode, att);
            if (val == null) {
                return def;
            } else {
                return val;
            }
        }

        private String getFeat(Element lexiconNode, String att) {
            return lexiconNode.getAttribute(att);
        }

        private List<StringPair> getFeats(Element node) {
            final ArrayList<StringPair> feats = new ArrayList<StringPair>(node.getAttributes().getLength());
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                System.err.println("TEST this: " + node.getAttributes().item(i).getNodeName() + " " + node.getAttributes().item(i).getNodeValue());
                feats.add(new StringPair(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue()));
            }
            return feats;
        }

        private Property toProperty(String name, LinguisticOntology lingOnto) {
            final Property property = lingOnto.getProperty(name);
            if (property != null) {
                return property;
            } else {
                return new PropertyImpl(name);
            }
        }

        private PropertyValue toPropertyValue(String name, LinguisticOntology lingOnto) {
            final PropertyValue propertyVal = lingOnto.getPropertyValue(name);
            if (propertyVal != null) {
                return propertyVal;
            } else {
                return new PropertyValueImpl(name);
            }
        }

        private void addProperties(LemonElement element, Element xml) {
            throw new UnsupportedOperationException();
        }

        private static class PropertyImpl extends URIValue implements Property {

            public PropertyImpl(String n) {
                super(URI.create(ISOCAT + n));
            }
        }

        private static class PropertyValueImpl extends URIElement implements PropertyValue {

            public PropertyValueImpl(String n) {
                super(URI.create(ISOCAT + n));
            }
        }

        private static class StringArgument {

            public final String s;
            public final Argument arg;

            public StringArgument(String s, Argument arg) {
                this.s = s;
                this.arg = arg;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final StringArgument other = (StringArgument) obj;
                if ((this.s == null) ? (other.s != null) : !this.s.equals(other.s)) {
                    return false;
                }
                if (this.arg != other.arg && (this.arg == null || !this.arg.equals(other.arg))) {
                    return false;
                }
                return true;
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 29 * hash + (this.s != null ? this.s.hashCode() : 0);
                hash = 29 * hash + (this.arg != null ? this.arg.hashCode() : 0);
                return hash;
            }
        }
    }
}
