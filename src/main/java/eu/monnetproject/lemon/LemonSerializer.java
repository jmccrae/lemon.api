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
package eu.monnetproject.lemon;

import eu.monnetproject.lemon.impl.SimpleLemonSerializer;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.Lexicon;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

/**
 * Interface for I/O on lemon models
 * @author John McCrae
 */
public abstract class LemonSerializer {

    /**
     * Read a lemon model from a given data source
     */
    public abstract LemonModel read(Reader source);

    /**
     * Read a lemon model putting the data in a given model
     */
    public abstract void read(LemonModel model, Reader source);

    /**
     * Create a blank model. Equivalent to {@code create(null)}
     */
    public abstract LemonModel create();
    
    /**
     * Create a blank model
     * @param graph The context of the model or null for no context
     */
    public abstract LemonModel create(URI graph);

    /**
     * Write a lemon model to a given data source
     * @param model The model to write
     * @param target The target to write to
     */
    public abstract void write(LemonModel model, Writer target);

    /**
     * Write a single entry to a file
     * @param model The model to write from
     * @param entry The entry to write
     * @param lingOnto The linguistic ontology (necessary to avoid following to other entries)
     * @param target The target to write to
     */
    public abstract void writeEntry(LemonModel model, LexicalEntry entry, LinguisticOntology lingOnto,
            Writer target);

    /**
     * Write a single lexicon to a file
     * @param model The model to write from
     * @param lexicon The lexicon to write
     * @param lingOnto The linguistic ontology (necessary to avoid following to other entries)
     * @param target The target to write to
     */
    public abstract void writeLexicon(LemonModel model, Lexicon lexicon, LinguisticOntology lingOnto,
            Writer target);

    /**
     * Write a lemon model to a given data source
     * @param model The model to write
     * @param target The target to write to
     * @param xml Write as XML?
     */
    public abstract void write(LemonModel model, Writer target, boolean xml);

    /**
     * Write a single entry to a file
     * @param model The model to write from
     * @param entry The entry to write
     * @param lingOnto The linguistic ontology (necessary to avoid following to other entries)
     * @param target The target to write to
     * @param xml Write as XML?
     */
    public abstract void writeEntry(LemonModel model, LexicalEntry entry, LinguisticOntology lingOnto,
            Writer target, boolean xml);

    /**
     * Write a single lexicon to a file
     * @param model The model to write from
     * @param lexicon The lexicon to write
     * @param lingOnto The linguistic ontology (necessary to avoid following to other entries)
     * @param target The target to write to
     * @param xml Write as XML?
     */
    public abstract void writeLexicon(LemonModel model, Lexicon lexicon, LinguisticOntology lingOnto,
            Writer target, boolean xml);

    /**
     * Move a lexicon from one model to another. Note this may not work if the models were created 
     * by different serializers
     * @param lexicon The lexicon to move
     * @param from The source model containing the lexicon
     * @param to The target model
     */
    public abstract void moveLexicon(Lexicon lexicon, LemonModel from, LemonModel to);

    /**
     * Get a new instance of a lemon serializer. This will use LexInfo as the linguistic ontology
     * @return A lemon serializer
     */
    public final static LemonSerializer newInstance() {
        return new SimpleLemonSerializer(null);
    }

    /**
     * Get a new instance of a lemon serializer
     * @param lingOnto The linguistic ontology used in all models
     * @return A lemon serializer
     */
    public final static LemonSerializer newInstance(LinguisticOntology lingOnto) {
        return new SimpleLemonSerializer(lingOnto);
    }
}
