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
import eu.monnetproject.lemon.LemonSerializer;
import eu.monnetproject.lemon.model.Argument;
import eu.monnetproject.lemon.model.Frame;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.Lexicon;
import eu.monnetproject.lemon.model.SynArg;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John McCrae
 */
public class SynArgReadTest {

    private final String input = "@prefix MusicBrainzLexicon: <http://monnetproject.deri.ie/lemonsource/user/httpswwwgooglecomaccountso8ididAItOawnRWNkyXGW_lk5kD1JgLCzU9MCwC_R8TY/MusicBrainzLexicon#>.\n"
            + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n"
            + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n"
            + "@prefix lemon: <http://www.monnet-project.eu/lemon#>.\n"
            + "@prefix lexinfo: <http://www.lexinfo.net/ontology/2.0/lexinfo#>."
            + "@prefix : <http://monnetproject.deri.ie/lemonsource/user/httpswwwgooglecomaccountso8ididAItOawnRWNkyXGW_lk5kD1JgLCzU9MCwC_R8TY/MusicBrainzLexicon#>.\n\n"
            + "MusicBrainzLexicon:lexicon a lemon:Lexicon ; lemon:entry MusicBrainzLexicon:collaborationOf.\n"
            + "MusicBrainzLexicon:collaborationOf lemon:sense [ lemon:reference <http://purl.org/vocab/relationship/collaboratesWith> ;\n"
            + "                                    lemon:subjOfProp :arg1collaboration ;\n"
            + "                                   lemon:objOfProp  :arg2collaboration ] ;\n"
            + "lemon:synBehavior [ rdf:type lexinfo:NounPPFrame ;\n"
            + " 	              lemon:synArg :arg2collaboration ;\n"
            + "                    lemon:synArg :arg1collaboration ] ;\n"
            + "lexinfo:partOfSpeech lexinfo:noun ;\n"
            + "lemon:canonicalForm [ lemon:writtenRep \"collaboration\"@en ;\n"
            + "                      lexinfo:number lexinfo:singular ] ;\n"
            + "lemon:otherForm [ lemon:writtenRep \"collaborations\"@en ;\n"
            + "                  lexinfo:number lexinfo:plural ] .\n";

    @Test
    public void testSynArgRead() {
        final LemonSerializer lemonSerializer = LemonSerializer.newInstance();
        final LemonModel model = lemonSerializer.read(new StringReader(input));
        final Collection<Lexicon> lexica = model.getLexica();
        assertFalse(lexica.isEmpty());
        final Lexicon lexicon = lexica.iterator().next();
        final Collection<LexicalEntry> entrys = lexicon.getEntrys();
        assertFalse(entrys.isEmpty());
        final LexicalEntry entry = entrys.iterator().next();
        final Collection<Frame> synBehaviors = entry.getSynBehaviors();
        assertFalse(synBehaviors.isEmpty());
        final Frame frame = synBehaviors.iterator().next();
        final Map<SynArg, Collection<Argument>> synArgs = frame.getSynArgs();
        assertFalse(synArgs.isEmpty());
    }
}
