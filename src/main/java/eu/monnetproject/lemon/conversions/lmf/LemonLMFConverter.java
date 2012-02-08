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
package eu.monnetproject.lemon.conversions.lmf;

/**
 *
 * @author John McCrae
 */
import eu.monnetproject.lemon.LemonModel;
import eu.monnetproject.lemon.model.LemonElement;
import eu.monnetproject.lemon.model.LemonElementOrPredicate;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.Lexicon;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LemonLMFConverter {
}/*
    private final String ISOCAT = "http://www.isocat.org/datcat/null#";
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

        public Node lemon2lmf(LemonModel model) {

//     header(model) ++ 
//        (model.lexica map(lexicon => lexicon2lmf(lexicon,model) )) ++
//          senseRelations2lmf(model) 
            return root;
        }

        public Node header(LemonModel model) {
            final Element globalInfo = document.createElement("GlobalInformation");
            append(globalInfo,getFeat("label", "LMF Lexicon derived from lemon model " + model.getLexica().iterator().next().getURI().toString()));
            return globalInfo;
        }

        private void append(Node head, Node to) {
            head.appendChild(to);
        }
        
        private void append(Node head, List<Node> tos) {
            for(Node to : tos) {
                head.appendChild(tos);
            }
        }
        
        public Node lexicon2lmf(Lexicon lexicon, LemonModel model) {
            final Element lexiconTag = document.createElement("Lexicon");
            append(lexiconTag,getFeat("language", lexicon.getLanguage()));
            for (LexicalEntry entry : lexicon.getEntrys()) {
                append(lexiconTag, lexEntry2lmf(entry,model));
                append(lexiconTag, senses2spc(lexicon,model));
                append(lexiconTag, frames2lmf(lexicon,model));
            }
        }

        public List<Node> lexEntry2lmf(LexicalEntry lexEntry, LemonModel model) {
            final Element lexEntryTag = document.createElement("LexicalEntry");
            append(lexEntryTag,getFeats(lexEntry.getPropertys(), model));
            append(lexEntryTag,getFeat("lexEntryType", lexEntry.getTypes()));
            append(lexEntryTag,canForm2lmf(lexEntry.getCanonicalForm(),model));
            append(lexEntryTag,forms2lmf(lexEntry,model));
            append(lexEntryTag,abstractForms2lmf(lexEntry, model));
            append(lexEntryTag,compList2lmf(lexEntry, model));
            append(lexEntryTag,senses2lmf(lexEntry, model));
        synBeh2lmf(lexEntry, model)
            )
              
      Seq(Elem(null, "LexicalEntry", new UnprefixedAttribute("id", frag(lexEntry, model), Null), TopScope,
                    elems.toSeq
            :_ *
            )) ++
        mweLink2lmf(lexEntry, model)++
        mwe2lmf(lexEntry, model) // TODO: topics
                    // TODO: lexical variants
        }

        def canForm2lmf(form  
            : Form, model : LemonModel) = <Lemma>{getFeat("writtenForm", form.writtenRep.value)
        }
        </Lemma

        >
    
    def forms2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) = {
      ((lexEntry.canonicalForm 
            :: lexEntry.otherForm
            ) map(form2lmf(_, model))
            ).toSeq
        }

        def form2lmf(form  
            : Form, model : LemonModel) = <WordForm>{
      getFeat("writtenForm", form.writtenRep.value)++ 
      getFeats(form.property, model)++
      reps2formRep(form.representation, model)
        }
        </WordForm

        >
    
    def abstractForms2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) : Seq[XMLNode] = {
      for (form< -lexEntry.abstractForm) {
                yield(<Stem>
            }
            {
                getFeat("writtenForm", form.writtenRep.value)++
        getFeats(form.property, model)++
      reps2formRep(form.representation, model)
            }
            < / Stem >
        

        )
      
      // TODO : form variants
    }
    
    def reps2formRep(repMap  
            : Map[Representation,List[Text]], model : LemonModel) = {
      for (rep< -repMap.keys;
                    value < -repMap(rep)) {
                yield(<FormRepresentation>
            }
            {
                getFeat("writtenForm", value.value)++
        decodeLangTag(value.lang)
            }
            < / FormRepresentation >
        

        )
      
    }
    
    def decodeLangTag(langTag  
            : String) = {
      val ieft = ""
            "([A-Za-z]{2,3})(-[A-Za-z]{4})?(-[A-Za-z]{2}|-[0-9]{3})?(-[A-Za-z]{5,8}|-[0-9]\w{3})?(-\w-\w{2,8})?"""r val ieft(lang, script, region, variant, extension) = langTag
      val langTags = new ListBuffer[XMLNode]
            ()
      
      if (script != null) {
                langTags.append(getFeat("script", script.substring(1)))
            }

            if (region != null) {
                langTags.append(getFeat("geographicalVariant", region.substring(1)))
            }
            if (variant != null) {
                langTags.append(getFeat("variant", variant.substring(1)))
            }
            if (extension != null) {
                langTags.append(getFeat("orthographyName", extension.substring(3)))
            }
            langTags.toList
        }

        def compList2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) : Seq[XMLNode] =
      if(lexEntry.decomposition.isEmpty) {
        Seq()
        }
        

        
            else {
        Seq(<ListOfComponents>
            {
                Seq(getFeat("size", lexEntry.decomposition(0).size.toString))++
          (comp2lmf(lexEntry.decomposition(0), model, 1)
            
            )
        }
        < / ListOfComponents >
        

        )
      }
    
    def comp2lmf(compList  
        

        : List[Component], model : LemonModel, order : Int) : List[XMLNode] = compList match {
      case comp :: rest => (<Component>{
        getFeat("order",order.toString) ++
        getFeats(comp.property,model)
      }</Component> % Map("entry" -> frag(comp.element,model))) :: comp2lmf(rest,model,order+1)
      case Nil => Nil
    }
    
    def senses2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) : Seq[XMLNode] = {
      lexEntry.sense flatMap {
                sense =  > sense2lmf(sense, model) }
        }

        def sense2lmf(sense  
            : LexicalSense, model : LemonModel) : XMLNode = {
      <Sense>
       < MonolingualExternalRef >
            {
                getFeat("externalSystem", "application/rdf")++
        getFeat("externalReference", sense.reference.toString)
            }
            < / MonolingualExternalRef >
      
            {
                Seq(getFeat("refPref", sense.refPref.toString))++ // TODO: context
                        // TODO: condition
        (sense.subsense map {
                    subsense = 
                            > sense2lmf(subsense, model) }
                
                ) ++
        examples2lmf(sense.example, model)++
        defs2lmf(sense.definition, model)++
        semArgs2pr(sense, model)++        
        getFeats(sense.property, model)
            }
            < / Sense >
    
        }

        def senseRelations2lmf(model  
            : LemonModel) = {
      // TODO: sense relations
      Nil
        }

        def examples2lmf(examples  
            : List[Example], model : LemonModel) = {
      examples map {
                example =  > 
                {
                    (<SenseExample> 
                    {
                        texts2textRep(example.value)++
          getFeats(example.property, model)
                    }
                    < / SenseExample >
                
            
        

        )
        }
      }
    }
    
    def defs2lmf(defs  
            : List[Definition], model : LemonModel) = {
      defs map {
                defn =  > 
                {
                    (<Definition> 
                    {
                        texts2textRep(defn.value)++
          getFeats(defn.property, model)
                    }
                    < / Definition >
                
            
        

        )
        }
      }
    } 
    
    def texts2textRep(reps  
            : List[Text]) = {
      for (rep< -reps) {
                yield(<TextRepresentation>
            }
            {
                getFeat("writtenForm", rep.value)++
        decodeLangTag(rep.lang)
            }
            < / TextRepresentation >
        

        )
      
    }
    
    def semArgs2pr(sense  
            : LexicalSense, model : LemonModel) = {
      if (sense.subjOfProp != None
                    || sense.objOfProp != None
                    || sense.isA != None) {
                Seq((<PredicativeRepresentation / >) % Map("predicate" - > ("__predicate_" + frag(sense, model)),
                        "correspondences" - > ("__correspondence_" + frag(sense, model))))
            } else {
                Seq()
            }
        }

        def senses2spc(lexicon  
            : Lexicon, model : LemonModel) : Seq[XMLNode] = {
      lexicon.entry flatMap {
                entry = 
                        > entry.sense flatMap {
                    sense =  >
          
                    if (sense.subjOfProp != None
                            || sense.objOfProp != None
                            || sense.isA != None) {
                        (<SemanticPredicate>
                        {
                            (
                            if (sense.subjOfProp != None) {
                                Seq((<SemanticArgument>
                   < feat 
                                att = "label" val = "subjOfProp" /
                                        > < / SemanticArgument >
                  
                                ) % Map("id" - > ("__subjOfProp_" + frag(sense, model)))
                              
                                )
               } else { Seq() }
                            
                            ) ++
               (if (sense.objOfProp != None) {
                                Seq((<SemanticArgument>
                  < feat 
                                att = "label" val = "objOfProp" /
                                        > < / SemanticArgument >
                 
                                ) % Map("id" - > ("__objOfProp_" + frag(sense, model)))
                              
                                )
               } else { Seq() }
                            
                            ) ++
               (if (sense.isA != None) {
                                Seq((<SemanticArgument>
                  < feat 
                                att = "label" val = "isA" /
                                        > < / SemanticArgument >
                 
                                ) % Map("id" - > ("__isA_" + frag(sense, model)))
                              
                                )
               } else { Seq() }
                            
                        
                        )
              }< / SemanticPredicate >  % Map("id" - > ("__predicate_" + frag(sense, model)))
                        ) ++
              (<SynSemCorrespondence >
                        {
                            (
                            if (sense.subjOfProp != None) {
                                Seq((<SynSemArgMap / >) % Map("semArg" - > ("__subjOfProp_" + frag(sense, model)),
                                        "synArg" - > frag(sense.subjOfProp.get, model)))
                            } else {
                                Seq() }
                            
                            ) ++
               (if (sense.objOfProp != None) {
                                Seq((<SynSemArgMap / >) % Map("semArg" - > ("__objOfProp_" + frag(sense, model)),
                                        "synArg" - > frag(sense.objOfProp.get, model)))
                            } else {
                                Seq() }
                            
                            ) ++
               (if (sense.isA != None) {
                                Seq((<SynSemArgMap / >) % Map("semArg" - > ("__isA_" + frag(sense, model)),
                                        "synArg" - > frag(sense.isA.get, model)))
                            } else {
                                Seq() }
                            
                        
                        )
              }< / SynSemCorrespondence >  % Map("id" - > ("__correspondence_" + frag(sense, model)))
                      
                        )
           } else { Seq() }
                }
            }

        }
        def getFeats[
        T<
        : LemonPredicate , U <: LemonElement
        ](propVals 
        : Map 
        [ T,List[U
        ]], model 
        : LemonModel
        ) : Seq 

        
            [XMLNode] = {
      (
            for (prop< -propVals.keys;
                    value < -propVals(prop)) {
                yield(getFeat(toLMFValue(prop, model), toLMFValue(value, model)))
            }
            ).toSeq
        }

        def getFeat(att 
        : String, vl : String) = <feat/> % Map("att" -> att, "val" -> vl)
    
    private val rand = new java.util.Random
    
    

        private def frag(elem  
        

        : LemonElement, model : LemonModel) = model.uri(elem) match {
      case nn : NamedNode => nn.uri.getFragment
      case bn : BlankNode => bn.id
      case _ => "id"+Math.abs(rand.nextLong)
    }
    
    def synBeh2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) : Seq[XMLNode]= {
      if (lexEntry.synBehavior.isEmpty) {
                Seq()
            } else {
                Seq((<SyntacticBehaviour>
         <  / SyntacticBehaviour >)
                        % Map("subcategorizationFrames" - > ((lexEntry.synBehavior map(sb =  > frag(sb, model))).mkString(" ")))
            
        

        )
      }
      // TODO: subcat frame sets
    }
    
    
    
    def frames2lmf(lexicon  
            : Lexicon, model : LemonModel) = {
      lexicon.entry flatMap {
                lexEntry = 
                        > lexEntry.synBehavior map {
                    frame = 
                            > (<SubcategorizationFrame>
                    {
                        args2lmf(frame, model)
                    }
                    < / SubcategorizationFrame >
                    ) % Map("id" - > frag(frame, model))
                }
            }
        }

        def args2lmf(frame  
            : Frame, model : LemonModel) = {
      frame.synArg.keys map {
                synArg = 
                        > frame.synArg(synArg) map {
                    arg = 
                            > (<SyntacticArgument>
                    {
                        Seq(getFeat("syntacticFunction", toLMFValue(synArg, model)))++
          getFeats(frame.property, model)++
          getArgProps(arg, model)
                    }
                    < / SyntacticArgument >
                    ) % Map("id" - > frag(arg, model))
                }
            }
        }

        def getArgProps(arg  
            : Argument, model : LemonModel) = {
      (arg.marker match {
            
            case Some(marker) => Seq(getFeat("marker",frag(marker,model)))
        case None => Seq()
      }
        

        )
    }
    
    def mwe2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) = {
      lexEntry.phraseRoot map {
                root = 
                        > (<MWEPattern>
        
                {
                    node2lmf(root, model) }
                < / MWEPattern >
                ) % Map("id" - > frag(root, model))
            }
        }

        def mweLink2lmf(lexEntry  
            : LexicalEntry, model : LemonModel) = {
      if (lexEntry.phraseRoot == Nil) {
                Map[String
                ,String
              
                ]()
      } else {
        Map("mwePattern" - > (lexEntry.phraseRoot map 
                {
                    node = 
                            > frag(node, model) }
                
                ).mkString(" ")
            
        

        )
      }
    }
        
    
    def node2lmf(node  
            : Node, model : LemonModel) : XMLNode = {
      (<MWENode>
            {
                (
                if (node.constituent != None) {
                    getFeat("syntacticConstituent", toLMFValue(node.constituent.get, model))
                } else {
                    Seq() }
                
                ) ++
        (node.edge.keys map {
                    edge2 = 
                            > node.edge(edge2) map {
                        node2 = 
                                > (<MWEEdge>
                        {
                            (
                            if (edge2 != edge) {
                                getFeat("function", toLMFValue(edge2, model)) 
                            } else {
                                Seq() }
                            
                            ) ++
          node2lmf(node2, model) }
                        < / MWEEdge >
                    
                
                ) 
          }
        }
                ) ++
        (node.leaf match {
                
                case Some(leaf) => Seq(<MWELex/> % Map("target" -> frag(leaf,model)))
          case None => Seq()
        }
                ) ++
        (if (node.separator != None) {
                    getFeat("separator", node.separator.get)
                } else {
                    Seq() }
                
            
            )
      }< / MWENode >
     
        
    
    )
    }
    
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////
  // LMF 2 lemon
  
  private object ToLemon

    {
    
    
    private [
    ToLemon

    ] class LMFAugments 

        (val nodeMap : HashMap[(String,String),XMLNode],
    val axisMap : HashMap[String,List[XMLNode]]) {
      val idMap = new HashMap[String, LemonElement
        ]()
      var language = "und"
      val argMap = new HashMap[String, Argument
        ]()
      
      def add[
        T<
        : LemonElement
        ](node 
        : XMLNode , elem : T
        ) : T  = {
            (node 
        \ "@id") match 

        {
        
    

case Seq(x,_*) => idMap.put(x.text,elem); elem
          case Seq() => elem
        }
      }
    }
    
    def lmf2lemon(lmfDoc : XMLNode) : List[Lexicon] = {
      var augment = new LMFAugments(buildIDMap(lmfDoc),
      buildAxisMap(lmfDoc))
      
      if(lmfDoc.label != "LexicalResource") {
        throw new IllegalArgumentException("Not a LMF file: " + lmfDoc)
      }
      
      val lexicons = new ListBuffer[Lexicon]
      for(lexicon <- lmfDoc \ "Lexicon") {
        lexicons += readLexicon(lexicon,augment)
      }
      
      // IGNORE: Global Information
      
      lexicons.toList
    }
    
    def buildIDMap(head : XMLNode) = {
      val map = new HashMap[(String,String),XMLNode]()
      
      def _buildIDMap(node : XMLNode) {
        for(elem <- node.child) {
          if((elem\"@id").size >= 1) {
            map.put((elem.label,(elem\"@id")(0).text),elem)
          }
          _buildIDMap(elem)
        }
      }
      
      _buildIDMap(head)
      
      map
    }
    
    def buildAxisMap(head : XMLNode) = {
      val map = new HashMap[String,List[XMLNode]]()
      
      for(senseAxis <- head \\ "SenseAxis") {
        for(senseAxisRelation <- senseAxis \ "SenseAxisRelation") {
          for(targ <- (senseAxisRelation \ "@senses")(0).text.split(" ")) {
            if(map.contains(targ)) {
              map.put(targ, senseAxis :: map(targ))
            } else {
              map.put(targ, List(senseAxis))
            }
          }
        }
      }
      
      // TODO: Target axes
      
      map
    }
    
    def readLexicon(lexiconNode : XMLNode, augment : LMFAugments) : Lexicon = {
      val language = getFeat(lexiconNode, "language").getOrElse("und") // "und" is code for undetermined
      augment.language = language
      val entries = (lexiconNode \ "LexicalEntry") map {
      lexEntryNode => readLexicalEntry(lexEntryNode,augment) }
      
      // IGNORE: MorphologicalPattern
      for(node <- lexiconNode \ "MorphologicalPattern") {
        System.err.println("Morphological patterns not supported by lemon. Ignoring " + 
        (node \ "@id")(0).text)
      }
      
      // TODO: ConstraintSet
      
      Lexicon(language,entries.toList)
    }
    
    def getByID(node : XMLNode, augment : LMFAugments) : Option[LemonElement] = {
      (node \ "@id") match {
        case Seq(x, _*) => { 
          augment.idMap.get(x text)
        }
        case Seq() => None
      }
    }
    
    def readLexicalEntry(lexEntryNode : XMLNode, augment : LMFAugments) : LexicalEntry = {
      getByID(lexEntryNode,augment) match {
        case Some(elem) => elem.asInstanceOf[LexicalEntry]
        case None => {
          val lemmaNode = (lexEntryNode \ "Lemma")(0)
          val canRep = getWrittenRep(lemmaNode,augment)
          
          val (canForm,otherForms) = filterCanonical(readForm(lexEntryNode \ "WordForm",augment),canRep.value)
          
          
          val abstractForms = readForm(lexEntryNode \ "Stem",augment) 
          
          val decomposition = ((lexEntryNode \ "ListOfComponents") map { loc => readCompList(loc,augment) }).toList
          
          val lexEntryType = getFeat(lexEntryNode,"lexEntryType") match {
            case Some("Word") => Word
            case Some("Phrase") => Phrase
            case Some("Part") => Part
            case None => if(decomposition.isEmpty) { Word } else { Phrase }
            case _ => throw new LMFFormatException("Unrecognised lexEntryType value")
          }
          
          // TODO: Form relations
          
          val frames = ((lexEntryNode \ "SyntacticBehaviour") flatMap { synBehNode =>
            readSynBehavior(synBehNode,augment)
          }).toList
          
          val senses = ((lexEntryNode \ "Sense") map { senseNode =>
            readSense(senseNode, augment)
          }).toList
          
          val phraseRoot = if((lexEntryNode \ "@mwePattern").size >= 1) { 
            readMWEPattern(augment.nodeMap("MWEPattern",(lexEntryNode \ "@mwePattern")(0).text),augment,decomposition)
          } else {
            Nil
          }
          
          augment.add(lexEntryNode,
          LexicalEntry(canForm, 
          entryType = lexEntryType,
          decomposition = decomposition,
          otherForm = otherForms,
          abstractForm = abstractForms,
          sense = senses,
          property = getFeats(lexEntryNode),
          synBehavior = frames,
          phraseRoot = phraseRoot))
        }
      }
    }
    
    def filterCanonical(forms : List[Form], canFormRep : String) : (Form, List[Form]) = {
      forms match {
        case head :: rest => if(head.writtenRep.value == canFormRep) {
          (head, rest)
        } else {
          val (canForm,otherForms) = filterCanonical(rest,canFormRep)
          (canForm, head :: otherForms)
        }
        case Nil => throw new LMFFormatException("Lemma \""+canFormRep+"\" does not have a WordForm")
      }
    }
    
    def readForm(formNodes : NodeSeq, augment : LMFAugments) : List[Form] = {
      (formNodes map { formNode => {
        if((formNode \ "FormRepresentation").size > 1) {
          Form(getWrittenRep(formNode,augment),property = getFeats(formNode),
          representation = Map(representation -> ((formNode \ "FormRepresentation") map { repNode =>
          getTextFromRep(repNode, augment) }).toList))
        } else {
          Form(getWrittenRep(formNode,augment), property = getFeats(formNode)) 
        }
        }
      }).toList
    }
    
    def readCompList(compListNode : XMLNode, augment : LMFAugments) = {
      ((compListNode \ "Component") map { compNode => 
        val element = augment.nodeMap(("LexicalEntry",(compNode \ "@entry")(0).text))
        
        Component(readLexicalEntry(element,augment),getFeats(compNode))
      }).toList
    }
    
    def readSynBehavior(synBehNode : XMLNode, augment : LMFAugments) : List[Frame] = {
      for(frameNode <- synBehNode \ "@subcategorizationFrameSets") {
        for(entryID <- frameNode.text.split(" ")) {
          readSubcatFrameSet(augment.nodeMap(("SubcategorizationFrameSet",entryID)), augment)
        }
      }
      
      ((synBehNode \ "@subcategorizationFrames") flatMap { frameNode =>
        frameNode.text.split(" ") map { entryID =>
      readSubcatFrame(augment.nodeMap(("SubcategorizationFrame",entryID)), augment) } } ).toList
    }
    
    
    def readSubcatFrameSet(frameSetNode : XMLNode, augment : LMFAugments) : Unit = {
      for(argMap <- frameSetNode \ "SynArgMap") {
        val arg1ID = (argMap \ "@arg1")(0).text
        val arg2ID = (argMap \ "@arg2")(0).text
        
        if(augment.argMap.contains(arg1ID)) {
          if(!augment.argMap.contains(arg2ID)) {
            augment.argMap.put(arg2ID,augment.argMap(arg1ID))
          } else {
            // Already processed
          }
        } else {
          if(!augment.argMap.contains(arg1ID)) {
            augment.argMap.put(arg1ID,augment.argMap(arg2ID))
          } else {
            val arg = readSyntacticArgument(augment.nodeMap(("Argument",arg1ID)), augment)
            augment.argMap.put(arg1ID,arg)
            augment.argMap.put(arg2ID,arg)
          }
        }
      }
    }
    
    def readSubcatFrame(frameNode : XMLNode, augment : LMFAugments) : Frame = {
      getByID(frameNode,augment) match {
        case Some(elem) => elem.asInstanceOf[Frame]
        case None => {
          val arguments = toFeatMap(((frameNode \ "SyntacticArgument") map { argNode => 
          (readSynArgLink(argNode,augment), readSyntacticArgument(argNode,augment)) }).toList)
          
          for(lexemeProp <- frameNode \ "LexemeProperty") {
            System.err.println("LexemeProperty ignored")
          }
          
          
          augment.add(frameNode,
          Frame(synArg = arguments, property = getFeats(frameNode)))
        }
      }
    }
    
    def readSynArgLink(argNode : XMLNode, augment : LMFAugments) : SynArg = {
      getFeat(argNode,"syntacticFunction") match {
        case Some(label) => ISOcatSynArg(label)
        case None => synArg
      }
    }
    
    def readSyntacticArgument(argNode : XMLNode, augment : LMFAugments) : Argument = {
      val id = (argNode \ "@id") match {
        case Seq(x,_*) => Some(x.text)
        case Seq() => None
      }
      
      if(id != None && augment.argMap.contains(id.get)) {
        augment.argMap(id.get)
      } else {
        val marker = getFeat(argNode,"marker") match {
          case Some(markerID) => {
            augment.nodeMap.get(("LexicalEntry",markerID)) match {
              case Some(node) => Some(readLexicalEntry(node,augment))
              case None => Some(ISOcatPropertyValue(markerID))
            }
          }
          case None => None
        }
        
        val arg = Argument(marker = marker, property = getFeats(argNode))
        
        if(id != None) {
          augment.argMap.put(id.get,arg)
        }
        
        arg
      }
    }
    
    
    def readSense(senseNode : XMLNode, augment : LMFAugments) : LexicalSense = {
      getByID(senseNode,augment) match {
        case Some(sememe) => sememe.asInstanceOf[LexicalSense]
        case None => {
          for(senseNode2 <- senseNode \ "Sense") {
            System.err.println("Recursive sense ignored!")
          }
          
          for(equivNode <- senseNode \ "Equivalent") {
            System.err.println("Equivalent ignored! Equivalents in LMF are ambiguous, please use SenseRelation instead");
          }
          
          val examples = ((senseNode \ "Context") map { contextNode =>
            readExample(contextNode,augment)
          }).toList :::
          ((senseNode \ "SenseExample") map { exampleNode =>
            readExample(exampleNode,augment)
          }).toList
          
          // TODO: Subject Field
          
          val semArgs = readPredicativeRepresentation((senseNode \ "PredicativeRepresentation")(0),augment)
          
          val definitions = ((senseNode \ "Definition") map { defNode =>
          readDefinition(defNode,augment) }).toList
          
          val relations = toMutFeatMap(
          ((senseNode \ "SenseRelation") map { relNode =>
          readSenseRelation(relNode,augment) }).toList ::: 
          ((senseNode \ "Synset") flatMap { synsetNode =>
            (synsetNode \ "SynsetRelation") map { relNode =>
            readSenseRelation(relNode,augment) }
          }
          ).toList ::: 
          readAxes(senseNode,augment))
          
          val reference = (senseNode \ "MonolingualExternalRef") match {
            case Seq(ref, _*) => Some(URI.create(getFeat(ref,"externalReference").getOrElse("error:undefined_reference")))
            case Seq() => None
          }
          
          val refPref = getFeat(senseNode,"refPref") match {
            case Some("altSem") => altSem
            case Some("hiddenSem") => hiddenSem
            case _ => prefSem
          }
          
          val subsense = ((senseNode \ "Sense") map {
            subsenseNode => readSense(subsenseNode, augment)
          }).toList
          
          augment.add(senseNode,
          LexicalSense(reference,
            refPref = refPref,
            example = examples,
            definition = definitions,
            subjOfProp = semArgs.get("subjOfProp"),
            objOfProp = semArgs.get("objOfProp"),
            isA = semArgs.get("isA"),
            senseRelation = relations,
            subsense = subsense,
            property = getFeats(senseNode)
          ))
        }
      }
    }
    
    def readExample(node : XMLNode, augment : LMFAugments) : Example = {
      val values = ((node \ "TextRepresentation") map { textNode =>
        getTextFromRep(textNode,augment) 
      }).toList
      
      Example(values, getFeats(node))
    }
    
    def readPredicativeRepresentation(prNode : XMLNode, augment : LMFAugments) : Map[String,Argument] = {
      // IGNORE: Predicates
      if((prNode \ "@correspondences").isEmpty) {
        Map()
      } else {
      
        val sscNodes = ((prNode \ "@correspondences")(0).text).split(" ") map ( corrID => 
          augment.nodeMap(("SynSemCorrespondence",corrID) ))
        val semPredNode = augment.nodeMap(("SemanticPredicate",(prNode \ "@predicate")(0).text))
        
        (sscNodes flatMap { corrNode => {
            val buffer = new ListBuffer[Tuple2[String,Argument]]()
            
            for(mapNode <- corrNode \"SynSemArgMap") {
              if((mapNode \ "@synArg").size > 0) {
                // Thank god! Probably reversing a lemon lexicon
                buffer += Tuple2(readSemArg(semPredNode,mapNode,augment),augment.argMap((mapNode \ "@synArg")(0).text))
              } else {
                System.err.println("Decoding LMF syn-sem arg map. This may take a while!")
                
                val synFeat = ISOcatPropertyValue(getFeat(mapNode, "syntacticFeature").getOrElse("#ERROR#"))
                
                var first = true
                
                (augment.argMap.values find { arg =>
                arg.property(ISOcatProperty("syntacticFunction")) == synFeat }) match {
                  case Some(arg) => { buffer += Tuple2(if(first) { "subjOfProp" } else { "objOfProp"},arg); 
                                      first = false }
                  case None =>
                }
              }
            }
            
            buffer.toList
          }
        }).toMap
      }
    }
    
    def readSemArg(semPredNode : XMLNode, mapNode : XMLNode, augment : LMFAugments) : String = {
      val targID = (mapNode \ "@semArg")(0).text
      
      ((semPredNode \ "SemanticArgument") find { argNode => 
      (argNode \ "@id")(0).text ==  targID }) match {
        case Some(node) => getFeat(node,"label").getOrElse("subjOfProp")
        case None => "subjOfProp"
      }
    }
    
    def readDefinition(defNode : XMLNode, augment : LMFAugments) : Definition = {
      val values = ((defNode \ "TextRepresentation") map { textNode =>
        getTextFromRep(textNode,augment) 
      }).toList
      
      Definition(values, getFeats(defNode))
    }
    
    def readSenseRelation(relNode : XMLNode, augment : LMFAugments) : (SenseRelation,LexicalSense) = {
      // Assuming sense relations are binary
      val targs = (relNode \ "@targets")(0).text.split(" ")
      if(targs.length != 2) {
        throw new LMFFormatException("Non-binary sense relation")
      }
      // UNSAFE: other target is second
      val otherSem = readSense(augment.nodeMap(("Sense",targs(1))),augment)
      getFeat(relNode,"label") match {
        case Some("equivalent") => (equivalent,otherSem)
        case Some("narrower") => (narrower,otherSem)
        case Some("broader") => (broader,otherSem)
        case Some("incompatible") => (incompatible,otherSem)
        case Some(x) => (ISOcatSenseRelation(x),otherSem)
        case None => (senseRelation,otherSem)
      }
    }
    
    def readAxes(senseNode : XMLNode, augment : LMFAugments) : List[(SenseRelation,LexicalSense)] = {
      if(senseNode \ "@id" isEmpty) {
        Nil
      } else {
        val id = (senseNode \ "@id")(0).text
        if(!augment.axisMap.contains(id)) {
          Nil
        } else {
          augment.axisMap(id) flatMap { senseAxis =>
            (senseAxis \ "SenseAxisRelation") map { relNode => {
                val targ = (relNode \ "@targets")(0).text.split(" ") find ( _ != id )
                // UNSAFE: target may be synset
                val otherSem = readSense(augment.nodeMap(("Sense",targ.get)),augment)
                getFeat(relNode,"label") match {
                  case Some("equivalent") => (equivalent,otherSem)
                  case Some("narrower") => (narrower,otherSem)
                  case Some("broader") => (broader,otherSem)
                  case Some("incompatible") => (incompatible,otherSem)
                  case Some(x) => (ISOcatSenseRelation(x),otherSem)
                  case None => (senseRelation,otherSem)
                }
              }
            }
          }
        }
      }
    } 
    
    def readMWEPattern(patternNode : XMLNode, augment : LMFAugments, decomposition : List[List[Component]]) : List[Node] = {
      ((patternNode \ "MWENode") map { nodeNode =>
      readMWENode(nodeNode,augment,decomposition) } ).toList
    }
    
    def readMWENode(nodeNode : XMLNode, augment : LMFAugments, decomposition : List[List[Component]]) : Node = {
      val edges = readMWEEdge(nodeNode, augment,decomposition)
      
      val constituent = getFeat(nodeNode,"syntacticConstituent") match {
        case Some(cons) => Some(ISOcatConstituent(cons))
        case None => None
      }
      
      val separator = getFeat(nodeNode,"separator") 
      
      val lex = (nodeNode \ "MWELex") match {
        case Seq(lexNode,_*) => readMWELex(lexNode,augment,decomposition)
        case Seq() => None
      }
      
      Node(constituent = constituent, 
        separator = separator, 
        edge = edges, 
        leaf = lex, 
        property = getFeats(nodeNode))
    }
    
    def readMWEEdge(nodeNode : XMLNode, augment : LMFAugments, decomposition : List[List[Component]]) : Map[Edge,List[Node]] = {
      var map = HashMap[Edge,List[Node]]()
      
      for(edgeNode <- (nodeNode \ "MWEEdge")) {
        val _edge = if(getFeat(edgeNode, "function") != None) {
          ISOcatEdge(getFeat(edgeNode,"function").get)
        } else {
          eu.monnetproject.lemon.scala.edge
        }
        
        val nodeNode2 = (edgeNode \ "MWENode")(0)
        if(map.contains(_edge)) {
          map.put(_edge, readMWENode(nodeNode2, augment,decomposition) :: map.get(_edge).get)
        } else {
          map.put(_edge, List(readMWENode(nodeNode2,augment,decomposition)))
        }
      }
      
      Map[Edge,List[Node]]() ++ map
    }
    
    def readMWELex(lexNode : XMLNode, augment : LMFAugments, decomposition : List[List[Component]]) : Option[PhraseTerminal] = {
      getFeat(lexNode, "rank") match {
        case Some(rank) => Some(decomposition(0)(Integer.parseInt(rank)))
        case None =>
        getFeat(lexNode, "componentRank") match {
          case Some(rank) => Some(decomposition(0)(Integer.parseInt(rank)))
          case None => None
        }
      }
    }
    
    private def getWrittenRep(form : XMLNode, augment : LMFAugments) : Text = {
      getFeat(form,"writtenForm") match {
        case Some(writtenRep) => Text(writtenRep,augment.language)
        case None => getTextFromRep((form \ "FormRepresentation")(0),augment)
      }
    }
    
    private def getTextFromRep(repNode : XMLNode, augment : LMFAugments) : Text = {
      val langTag = new StringBuilder
      langTag.append(augment.language)
      
      getFeat(repNode(0),"script") match {
        case Some(script) => langTag.append("-" + script)
        case None =>
      }
      getFeat(repNode(0),"geographicalVariant") match {
        case Some(geo) => langTag.append("-" + geo)
        case None =>
      }
      getFeat(repNode(0),"orthographyName") match {
        case Some(ortho) => langTag.append("-x-"+ortho)
        case None =>
      }
      
      Text(getFeat(repNode,"writtenForm").getOrElse(""), langTag.toString)
    }
    
    private def getFeat(node : XMLNode, att : String) : Option[String] = {
      ((node \ "feat") find { feat => featAtt(feat) == att }) match {
        case Some(feat) => Some(featVal(feat))
        case None => None
      }
    }
    
    private def getFeats(node : XMLNode) : Map[Property,List[PropertyValue]] = {
      val feats = new HashMap[Property,List[PropertyValue]]()
      
      for(feat <- node \ "feat" ; 

if(!ignoredFeats.contains(featAtt(feat)))) {
        val att = ISOcatProperty(featAtt(feat))
        val value = ISOcatPropertyValue(featVal(feat))
        if(feats.containsKey(att)) {
          if(!feats(att).contains(value)) {
            feats.put(att, value :: feats(att))
          } else { }
        } else {
          feats.put(att, List(value))
        }
      }
      
      Map[Property,List[PropertyValue]]() ++ feats
    }
    
    private def featAtt(feat:XMLNode) : String = (feat \ "@att")(0) text
    private def featVal(feat:XMLNode) : String = (feat \ "@val")(0) text
    
    private def toFeatMap[T,U](list : List[Tuple2[T,U]]) : Map[T,List[U]] = {
      Map[T,List[U]]() ++ toMutFeatMap(list)
    }
    
    private def toMutFeatMap[T,U](list : List[Tuple2[T,U]]) : MutableMap[T,List[U]] = {
      val rval = new HashMap[T,List[U]]()
      for((prop,value) <- list) {
        if(rval.contains(prop)) {
          rval.put(prop,value :: rval(prop))
        } else {
          rval.put(prop,List(value))
        }
      }
      rval
    }
  }
}

case class ISOcatProperty 

    (val value : String) extends Property

case class ISOcatPropertyValue 

        (val value : String) extends PropertyValue {
  override val property  = Map[Property, List[
        PropertyValue
    
    ]]()
}



    case class ISOcatEdge 

        (val value : String) extends Edge

case class ISOcatSenseRelation 

            (val value : String) extends SenseRelation

case class ISOcatConstituent 

                (val value : String) extends Constituent {  
  override val property  = Map[Property, List[
                PropertyValue
            
            ]]()
}



            case class ISOcatSynArg 

                (val value : String) extends SynArg

class LMFFormatException 
                
            
        
    

(message:String) extends java.lang.RuntimeException(message)
*/