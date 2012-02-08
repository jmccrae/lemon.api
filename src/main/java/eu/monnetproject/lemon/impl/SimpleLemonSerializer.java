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
import eu.monnetproject.lemon.impl.io.turtle.TurtleParser;
import eu.monnetproject.lemon.impl.io.turtle.TurtleWriter;
import eu.monnetproject.lemon.impl.io.xml.RDFXMLReader;
import eu.monnetproject.lemon.impl.io.xml.RDFXMLWriter;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.Lexicon;
import java.io.*;
import java.net.URI;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import net.lexinfo.LexInfo;

/**
 * A serializer for in-memory lemon models
 * @author John McCrae
 */
public class SimpleLemonSerializer extends LemonSerializer {
 
    private final LinguisticOntology lingOnto;

    /** Create a serializer */
    public SimpleLemonSerializer() {
        lingOnto = new LexInfo();
    }

    public SimpleLemonSerializer(LinguisticOntology lingOnto) {
        this.lingOnto = lingOnto;
    }

    @Override
    public LemonModel read(Reader source) {
        final SimpleLemonModel model = new SimpleLemonModel();
        read(model, source);
        return model;
    }

    @Override
    public void write(LemonModel model, Writer target) {
        try {
            final RDFXMLWriter xmlWriter = new RDFXMLWriter(lingOnto);
            for (Lexicon lexicon : model.getLexica()) {
                ((LexiconImpl) lexicon).accept(xmlWriter);
            }
            target.write(xmlWriter.getDocument());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public LemonModel create(URI context) {
        return new SimpleLemonModel(context != null ? context.toString() : "unknown:lexicon");
    }

    @Override
    public void writeEntry(LemonModel model, LexicalEntry entry, LinguisticOntology lingOnto,
            Writer target) {
        try {
            final RDFXMLWriter visitor = new RDFXMLWriter(lingOnto);
            if (entry instanceof LexicalEntryImpl) {
                ((LexicalEntryImpl) entry).accept(visitor);
            } else {
                throw new IllegalArgumentException("Cannot write model I didn't create");
            }
            target.append(visitor.getDocument());
        } catch (IOException x) {
            throw new RuntimeException(x);
        } catch (ParserConfigurationException x) {
            throw new RuntimeException(x);
        } catch (TransformerException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public void writeLexicon(LemonModel model, Lexicon lexicon, LinguisticOntology lingOnto,
            Writer target) {

        try {
            final RDFXMLWriter visitor = new RDFXMLWriter(lingOnto);
            if (lexicon instanceof LexiconImpl) {
                ((LexiconImpl) lexicon).accept(visitor);
            } else {
                throw new IllegalArgumentException("Cannot write model I didn't create");
            }
            target.append(visitor.getDocument());
        } catch (IOException x) {
            throw new RuntimeException(x);
        } catch (ParserConfigurationException x) {
            throw new RuntimeException(x);
        } catch (TransformerException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public void moveLexicon(Lexicon lexicon, LemonModel from, LemonModel to) {
        if (lexicon instanceof LexiconImpl) {
            final CopyVisitor copyVisitor = new CopyVisitor(lingOnto, to);
            ((LexiconImpl) lexicon).accept(copyVisitor);
        } else {
            throw new IllegalArgumentException("moveLexicon has to be called by the serializer that created the from lexicon");
        }
    }

    @Override
    public void read(LemonModel lm, Reader source) {
        if(!(source instanceof BufferedReader )) {
            source = new BufferedReader(source);
        }
        if (!(lm instanceof SimpleLemonModel)) {
            throw new IllegalArgumentException("Lemon Model not created by this serializer");
        }
        final SimpleLemonModel model = (SimpleLemonModel) lm;
        final RDFXMLReader rdfXMLReader = new RDFXMLReader(model);
        try {
            source.mark(1000000);
            rdfXMLReader.parse(source);
        } catch (Exception ex) {
            try {
                source.reset();
                final TurtleParser parser = new TurtleParser(source, model);
                parser.parse();
            } catch (Exception ex2) {
                ex.printStackTrace();
                throw new RuntimeException(ex2);
            }
        }
    }

    @Override
    public void write(LemonModel lm, Writer dt, boolean xml) {
        if (xml) {
            write(lm, dt);
        } else {

            try {
                final TurtleWriter turtleWriter = new TurtleWriter(lingOnto);
                for (Lexicon lexicon : lm.getLexica()) {
                    ((LexiconImpl) lexicon).accept(turtleWriter);
                }
                dt.write(turtleWriter.getDocument());
            } catch (Exception ex) {
                if(ex instanceof RuntimeException) 
                    throw (RuntimeException)ex;
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void writeEntry(LemonModel lm, LexicalEntry le, LinguisticOntology lo, Writer dt, boolean xml) {
        if (xml) {
            write(lm, dt);
        } else {
            try {
                final TurtleWriter turtleWriter = new TurtleWriter(lingOnto);
                ((LexicalEntryImpl) le).accept(turtleWriter);
                dt.write(turtleWriter.getDocument());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void writeLexicon(LemonModel lm, Lexicon lxcn, LinguisticOntology lo, Writer dt, boolean xml) {
        if (xml) {
            write(lm, dt);
        } else {

            try {
                final TurtleWriter turtleWriter = new TurtleWriter(lingOnto);
                ((LexiconImpl) lxcn).accept(turtleWriter);
                dt.write(turtleWriter.getDocument());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
