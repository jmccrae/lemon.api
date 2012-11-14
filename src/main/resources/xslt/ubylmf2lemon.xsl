<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : lmf2lemon.xsl
    Created on : 09 August 2012, 16:40
    Author     : John McCrae
    Description: Uby-LMF to Lemon Transforms
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:lemon="http://www.monnet-project.eu/lemon#"
                xmlns:uby="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#"
                xmlns:dcr="http://www.isocat.org/ns/dcr.rdf#"
                xmlns:owl="http://www.w3.org/2002/07/owl#"
                version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <rdf:RDF>
            <xsl:apply-templates select="LexicalResource"/>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#partOfSpeech">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-396"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#grammaticalNumber">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-251"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#grammaticalGender">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3217"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#case">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-2720"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#person">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3385"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#tense">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3519"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#verbFormMood">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1427"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#degree">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-2779"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#grammaticalFunction">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1296"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#syntacticCategory">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1506"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#determiner">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3159"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#verbForm">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1427"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#complementizer">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3124"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#coreType">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4461"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#exampleType">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#syntacticProperty">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#property"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#phoneticForm">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#representation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1837"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#sound">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#representation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-2250"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#hyphenation">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#representation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-264"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#etymology">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-221"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#compound">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3127"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#compoundRoot">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-357"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#derivative">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4611"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#derivationBase">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4612"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#derivationBaseNoun">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4615"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#derivationBaseVerb">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4614"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#derivationBaseAdj">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4616"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#loanWord">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-511"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#supports">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#lexicalVariant"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3003"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#taxonomic">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4039"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#partWhole">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-397"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#assocation">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-438"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#complementary">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-83"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#translation">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-4020"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#label">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-1857"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#labelOmegaWiki">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#predicative">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
                <dcr:datcat rdf:resource="http://www.isocat.org/datcat/DC-3415"/>
            </rdf:Property>
            
            <rdf:Property rdf:about="http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#predicativeOmegaWiki">
                <rdfs:subPropertyOf rdf:resource="http://www.monnet-project.eu/lemon#senseRelation"/>
            </rdf:Property>
        </rdf:RDF>
    </xsl:template>
    
    <xsl:template match="LexicalResource">
        <xsl:apply-templates select="GlobalInformation"/>
        <xsl:apply-templates select="Lexicon"/>
        <xsl:apply-templates select="SenseAxis"/>
    </xsl:template>
    
    <xsl:template match="GlobalInformation">
        <rdf:Description rdf:about="#GlobalInformation">
            <xsl:if test="@label">
                <rdfs:label>
                    <xsl:if test="FormRepresentation/@xml:lang">
                        <xsl:attribute name="xml:lang">
                            <xsl:value-of select="FormRepresentation/@xml:lang"/>
                        </xsl:attribute>
                    </xsl:if>    
                    <xsl:value-of select="@label"/>
                </rdfs:label>
            </xsl:if>
        </rdf:Description>
    </xsl:template>
    
    <xsl:template match="Lexicon">
        <lemon:Lexicon>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="@languageIdentifier">
                <lemon:language>
                    <xsl:value-of select="@languageIdentifier"/>
                </lemon:language>
            </xsl:if>
            <xsl:if test="@name">
                <rdfs:label>
                    <xsl:value-of select="@name"/>
                </rdfs:label>
            </xsl:if>
            <xsl:apply-templates select="LexicalEntry"/>
        </lemon:Lexicon>
        <xsl:apply-templates select="SubcategorizationFrame"/>
        <!--<xsl:apply-templates select="Synset"/>-->
        <xsl:apply-templates select="SynSemCorrespondence"/>
        <xsl:apply-templates select="ConstraintSet"/>
    </xsl:template>
    
    <xsl:template match="LexicalEntry">
        <lemon:entry>
            <lemon:LexicalEntry>
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <uby:partOfSpeech>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@partOfSpeech)"/>
                    </xsl:attribute>
                </uby:partOfSpeech>
                <xsl:if test="@separableParticle">
                    <uby:separableParticle>
                        <xsl:value-of select="@separableParticle"/>
                    </uby:separableParticle>
                </xsl:if>
                <xsl:apply-templates select="Lemma"/>
                <xsl:apply-templates select="WordForm[position() > 1]"/>
                <xsl:apply-templates select="RelatedForm"/>
                <xsl:apply-templates select="Sense"/>
                <xsl:apply-templates select="SyntacticBehaviour"/>
                <xsl:apply-templates select="ListOfComponents"/>
                <xsl:apply-templates select="Frequency"/>
            </lemon:LexicalEntry>
        </lemon:entry>
    </xsl:template>
    
    <xsl:template match="Sense">
        <lemon:sense>
            <lemon:LexicalSense>
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:if test="@index">
                    <uby:index>
                        <xsl:value-of select="@index"/>
                    </uby:index>
                </xsl:if>
                <xsl:if test="@synset">
                    <xsl:variable name="ssid" select="@synset"/>
                    <xsl:apply-templates select="//Synset[@id=$ssid]"/>
                    <!--<lemon:equivalent>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('#',@synset)"/>
                        </xsl:attribute>
                    </lemon:equivalent>-->
                </xsl:if>
                <xsl:if test="@incorporatedSemArg">
                    <!-- No I have no clue what this means -->
                </xsl:if>
                <xsl:if test="@transparentMeaning">
                    <uby:transparentMeaning>
                        <xsl:value-of select="@transparentMeaning"/>
                    </uby:transparentMeaning>
                </xsl:if>
                <xsl:for-each select="Sense">
                    <lemon:narrower>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('#',@id)"/>
                        </xsl:attribute>
                    </lemon:narrower>
                </xsl:for-each>
                <xsl:apply-templates select="Context"/>
                <xsl:apply-templates select="PredicativeRepresentation"/>
                <xsl:apply-templates select="SenseExample"/>
                <xsl:apply-templates select="Definition"/>
                <xsl:apply-templates select="SenseRelation"/>
                <xsl:apply-templates select="MonolingualExternalRef"/>
                <xsl:apply-templates select="Frequency"/>
                <xsl:apply-templates select="SemanticLabel"/>
            </lemon:LexicalSense>
        </lemon:sense>
        <xsl:apply-templates select="Sense"/>
    </xsl:template>
    
    <xsl:template match="Definition">
        <xsl:if test="Statement">
            <xsl:for-each select="Statment">
                <lemon:definition>
                    <lemon:SenseDefinition>
                        <xsl:if test="../@definitionType">
                            <uby:definitionType>
                                <xsl:value-of select="../@definitionType"/>
                            </uby:definitionType>
                        </xsl:if>         
                        <xsl:if test="@statementType">
                            <uby:statementType>
                                <xsl:value-of select="@statementType"/>
                            </uby:statementType>
                        </xsl:if>
                        <xsl:apply-templates select="../TextRepresentation"/>
                        <xsl:apply-templates select="TextRepresentation"/>
                    </lemon:SenseDefinition>
                </lemon:definition>
            </xsl:for-each>
        </xsl:if>
        <xsl:if test="not(Statement)">
            <lemon:definition>
                <lemon:SenseDefinition>
                    <xsl:if test="@definitionType">
                        <uby:definitionType>
                            <xsl:value-of select="@definitionType"/>
                        </uby:definitionType>
                    </xsl:if>
                    <xsl:apply-templates select="TextRepresentation"/>
                </lemon:SenseDefinition>
            </lemon:definition>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="TextRepresentation">
        <lemon:value>
            <xsl:if test="@languageIdentifier">
                <xsl:attribute name="xml:lang">
                    <xsl:if test="@languageIdentifier">
                        <xsl:value-of select="@languageIdentifier"/>
                    </xsl:if>
                    <xsl:if test="@orthographyName">-
                        <xsl:value-of select="@orthographyName"/>
                    </xsl:if>
                    <xsl:if test="@geographicalVariant">-
                        <xsl:value-of select="@geographicalVariant"/>
                    </xsl:if>
                </xsl:attribute>
                <xsl:value-of select="@writtenText"/>
            </xsl:if>
            <xsl:if test="@xml:lang">
                <xsl:attribute name="xml:lang">
                    <xsl:value-of select="@xml:lang"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="@writtenText"/>
        </lemon:value>
    </xsl:template>
    
    
    <xsl:template match="Lemma">
        <lemon:canonicalForm>
            <lemon:Form>
                <xsl:apply-templates select="FormRepresentation"/>
            </lemon:Form>
            <xsl:apply-templates select="../WordForm[1]"/>
        </lemon:canonicalForm>
    </xsl:template>
    
    <xsl:template select="WordForm">
        <xsl:if test="@grammaticalNumber">
            <uby:grammaticalNumber>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@grammaticalNumber)"/>
                </xsl:attribute>
            </uby:grammaticalNumber>
        </xsl:if>
        <xsl:if test="@grammaticalGender">
            <uby:grammaticalGender>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@grammaticalGender)"/>
                </xsl:attribute>
            </uby:grammaticalGender>
        </xsl:if>
        <xsl:if test="@case">
            <uby:case>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@case)"/>
                </xsl:attribute>
            </uby:case>
        </xsl:if>
        <xsl:if test="@person">
            <uby:person>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@person)"/>
                </xsl:attribute>
            </uby:person>
        </xsl:if>
        <xsl:if test="@tense">
            <uby:tense>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@tense)"/>
                </xsl:attribute>
            </uby:tense>
        </xsl:if>
        <xsl:if test="@verbFormMood">
            <uby:verbFormMood>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@verbFormMood)"/>
                </xsl:attribute>
            </uby:verbFormMood>
        </xsl:if>
        <xsl:if test="@degree">
            <uby:degree>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@degree)"/>
                </xsl:attribute>
            </uby:degree>
        </xsl:if>
        <xsl:apply-templates match="FormRepresentation"/>
        <xsl:apply-templates match="Frequency"/>
    </xsl:template>
    
    
    <xsl:template match="FormRepresentation">
        <lemon:writtenRep>
            <xsl:if test="@languageIdentifier">
                <xsl:attribute name="xml:lang">
                    <xsl:if test="@languageIdentifier">
                        <xsl:value-of select="@languageIdentifier"/>
                    </xsl:if>
                    <xsl:if test="@orthographyName">-
                        <xsl:value-of select="@orthographyName"/>
                    </xsl:if>
                    <xsl:if test="@geographicalVariant">-
                        <xsl:value-of select="@geographicalVariant"/>
                    </xsl:if>
                </xsl:attribute>
                <xsl:value-of select="@writtenText"/>
            </xsl:if>
            <xsl:if test="@xml:lang">
                <xsl:attribute name="xml:lang">
                    <xsl:value-of select="@xml:lang"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="@writtenForm"/>
        </lemon:writtenRep>
        <xsl:if test="@phoneticForm">
            <uby:phoneticForm>
                <xsl:value-of select="@phoneticForm"/>
            </uby:phoneticForm>
        </xsl:if>
        <xsl:if test="@sound">
            <uby:sound>
                <xsl:value-of select="@sound"/>
            </uby:sound>
        </xsl:if>
        <xsl:if test="@hyphenation">
            <uby:hyphenation>
                <xsl:value-of select="@hyphenation"/>
            </uby:hyphenation>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="RelatedForm">
        <xsl:if test="@relType">
            <xsl:element name="{concat('uby:',@relType)}">
                <xsl:if test="@targetLexicalEntry">
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@targetLexicalEntry)"/>
                    </xsl:attribute>
                </xsl:if>
            </xsl:element>
            <xsl:if test="@targetSense">
                <xsl:comment>Linked to sense 
                    <xsl:value-of select="@targetSense"/>
                </xsl:comment>
            </xsl:if>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="ListOfComponents">
        <lemon:decomposition rdf:parseType="Collection">
            <xsl:for-each select="Component">
                <lemon:Component>
                    <lemon:element>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('#',@targetLexicalEntry)"/>
                        </xsl:attribute>
                    </lemon:element>
                    <xsl:if test="@isHead">
                        <uby:isHead>
                            <xsl:value-of select="@isHead"/>
                        </uby:isHead>
                    </xsl:if>
                    <xsl:if test="@position">
                        <uby:position>
                            <xsl:value-of select="@position"/>
                        </uby:position>
                    </xsl:if>
                    <xsl:if test="@isBreakBefore">
                        <uby:isBreakBefore>
                            <xsl:value-of select="@isBreakBefore"/>
                        </uby:isBreakBefore>
                    </xsl:if>
                </lemon:Component>
            </xsl:for-each>
        </lemon:decomposition>
    </xsl:template>
    
    <xsl:template match="Context">
        <lemon:context>
            <lemon:LexicalContext>
                <xsl:if test="@source">
                    <uby:source>
                        <xsl:value-of select="@source"/>
                    </uby:source>
                </xsl:if>
                <xsl:if test="@contextType">
                    <uby:contextType>
                        <xsl:value-of select="@contextType"/>
                    </uby:contextType>
                </xsl:if>
                <xsl:apply-templates select="TextRepresentation"/>
                <xsl:apply-templates select="MonolingualExternalRef"/>
            </lemon:LexicalContext>
        </lemon:context>
    </xsl:template>
    
    <xsl:template match="SyntacticBehaviour">
        <xsl:choose>
            <xsl:when test="@subcategorizationFrame">
                <lemon:synBehavior>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@subcategorizationFrame)"/>
                    </xsl:attribute>
                </lemon:synBehavior>
            </xsl:when>
            <xsl:when test="@subcategorizationFrameSet">
                <xsl:variable name="sfs" select="@subcategorizationFrameSet"/>
                <xsl:apply-templates select="//SubcategorizationFrameSet[@id=$sfs]"/>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="@sense">
            <xsl:comment>Different senses do not have different subcategorizations in lemon.</xsl:comment>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="SubcategorizationFrame">
        <lemon:Frame>
            <xsl:attribute name="rdf:ID">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:if test="@parentSubcatFrame">
                <uby:parentSubcatFrame>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@parentSubcatFrame)"/>
                    </xsl:attribute>
                </uby:parentSubcatFrame>
            </xsl:if>
            <xsl:if test="@subcatLabel">
                <rdfs:label>
                    <xsl:value-of select="@subcatLabel"/>
                </rdfs:label>
            </xsl:if>
            <xsl:apply-templates select="LexemeProperty"/>
            <xsl:apply-templates select="SyntacticArgument"/>
            <xsl:apply-templates select="Frequency"/>
        </lemon:Frame>
    </xsl:template>
    
    <xsl:template match="LexemeProperty">
        <xsl:if test="@auxiliary">
            <uby:auxiliary>
                <xsl:value-of select="@parentSubcatFrame"/>
            </uby:auxiliary>
        </xsl:if>
        <xsl:if test="@syntacticProperty">
            <uby:syntacticProperty>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@syntacticProperty)"/>
                </xsl:attribute>
            </uby:syntacticProperty>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="SyntacticArgument">
        <lemon:synArg>
            <lemon:Argument>
                <xsl:attribute name="rdf:ID">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:if test="@optional">
                    <lemon:optional>
                        <xsl:value-of select="@optional"/>
                    </lemon:optional>
                </xsl:if>
                <xsl:if test="@grammaticalFunction">
                    <uby:grammaticalFunction>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@grammaticalFunction)"/>
                        </xsl:attribute>
                    </uby:grammaticalFunction>
                </xsl:if>
                <xsl:if test="@syntacticCategory">
                    <uby:syntacticCategory>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@syntacticCategory)"/>
                        </xsl:attribute>
                    </uby:syntacticCategory>
                </xsl:if>
                <xsl:if test="@case">
                    <uby:case>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@case)"/>
                        </xsl:attribute>
                    </uby:case>
                </xsl:if>
                <xsl:if test="@determiner">
                    <uby:determiner>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@determiner)"/>
                        </xsl:attribute>
                    </uby:determiner>
                </xsl:if>
                <xsl:if test="@number">
                    <uby:number>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@number)"/>
                        </xsl:attribute>
                    </uby:number>
                </xsl:if>
                <xsl:if test="@verbForm">
                    <uby:verbForm>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@verbForm)"/>
                        </xsl:attribute>
                    </uby:verbForm>
                </xsl:if>
                <xsl:if test="@tense">
                    <uby:tense>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@tense)"/>
                        </xsl:attribute>
                    </uby:tense>
                </xsl:if>
                <xsl:if test="@complementizer">
                    <uby:complementizer>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@complementizer)"/>
                        </xsl:attribute>
                    </uby:complementizer>
                </xsl:if>
                <xsl:if test="@preposition">
                    <uby:preposition>
                        <xsl:value-of select="@preposition"/>
                    </uby:preposition>
                </xsl:if>
                <xsl:if test="@prepositionType">
                    <uby:prepositionType>
                        <xsl:value-of select="@prepositionType"/>
                    </uby:prepositionType>
                </xsl:if>
                <xsl:if test="@lexeme">
                    <uby:lexeme>
                        <xsl:value-of select="@lexeme"/>
                    </uby:lexeme>
                </xsl:if>
            </lemon:Argument>
        </lemon:synArg>
    </xsl:template>
    
    <xsl:template match="SubcategorizationFrameSet">
        <xsl:for-each select="SubcatFrameSetElement">
            <lemon:synBehavior>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('#',@element)"/>
                </xsl:attribute> 
            </lemon:synBehavior>
        </xsl:for-each>
    </xsl:template>
    
    
    <!-- synargmap -->
        
    <xsl:template match="PredicativeRepresentation">
        <xsl:variable name="pr" select="@predicate"/>
        <xsl:apply-templates select="//SemanticPredicate[@id=$pr]"/>
    </xsl:template>
            
    <xsl:template match="SemanticPredicate">
        <xsl:if test="@lexicalized">
            <uby:lexicalized>
                <xsl:value-of select="@lexicalized"/>
            </uby:lexicalized>
        </xsl:if>
        <xsl:if test="@perspectivalized">
            <uby:perspectivalized>
                <xsl:value-of select="@perspectivalized"/>
            </uby:perspectivalized>
        </xsl:if>
        <xsl:apply-templates select="Definition"/>
        <xsl:apply-templates select="SemanticArgument"/>
        <xsl:apply-templates select="PredicateRelation"/>
        <xsl:apply-templates select="Frequency"/>
        <xsl:apply-templates select="SemanticLabel"/>
    </xsl:template>
    
    <xsl:template match="SemanticArgument">
        <lemon:semArg>
            <lemon:Argument>
                <xsl:attribute name="rdf:ID">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:if test="@semanticRole">
                    <uby:semanticRole>
                        <xsl:value-of select="@semanticRole"/>
                    </uby:semanticRole>
                </xsl:if>
                <xsl:if test="@isIncorporated">
                    <uby:isIncorporated>
                        <xsl:value-of select="@semanticRole"/>
                    </uby:isIncorporated>
                </xsl:if>
                <xsl:if test="@coreType">
                    <uby:coreType>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@coreType)"/>
                        </xsl:attribute>
                    </uby:coreType>
                </xsl:if>
                <xsl:apply-templates select="ArgumentRelation"/>
                <xsl:apply-templates select="Frequency"/>
                <xsl:apply-templates select="SemanticLabel"/>
                <xsl:apply-templates select="Definition"/>
            </lemon:Argument>
        </lemon:semArg>
    </xsl:template>
    
    <xsl:template match="ArgumentRelation">
        <xsl:comment>Argument relations not supported in lemon.</xsl:comment>
    </xsl:template>
    
    <xsl:template match="SynSemCorrespondence">
        <xsl:for-each select="SynSemArgMap">
            <rdf:Description>
                <xsl:attribute name="rdf:ID">
                    <xsl:value-of select="@syntacticArgument"/>
                </xsl:attribute>
                <owl:sameAs>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@semanticArgument)"/>
                    </xsl:attribute>
                </owl:sameAs>
            </rdf:Description>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="PredicateRelation">
        <xsl:comment>Predicate Relation's are not mapped as no URI for the relation could be defined.</xsl:comment>
    </xsl:template>
    
    <xsl:template match="SenseExample">
        <lemon:example>
            <lemon:UsageExample>
                <xsl:attribute name="rdf:ID">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:if test="@exampleType">
                    <uby:exampleType>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/uby/ubyLmfDTD_1_0.dtd#',@exampleType)"/>
                        </xsl:attribute>
                    </uby:exampleType>
                </xsl:if>
                <xsl:apply-templates select="TextRepresentation"/>
            </lemon:UsageExample>
        </lemon:example>
    </xsl:template>
    
    <xsl:template match="Synset">
        <xsl:apply-templates select="Definition"/>
        <xsl:apply-templates select="SynsetRelation"/>
        <xsl:apply-templates select="MonolingualExternalRef"/>
    </xsl:template>
    
    <xsl:template match="SynSetRelation">
        <xsl:choose>
            <xsl:when test="@relType">
                <xsl:element name="{concat('uby:',@relType)}">
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@target)"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <lemon:senseRelation>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@target)"/>
                    </xsl:attribute>
                </lemon:senseRelation>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="MonolingualExternalRef">
        <uby:monolingualExternalRef>
            <rdf:Description>
                <uby:externalSystem>
                    <xsl:value-of select="@externalSystem"/>
                </uby:externalSystem>
                <uby:externalReference>
                    <xsl:value-of select="@externalReference"/>
                </uby:externalReference>
            </rdf:Description>
        </uby:monolingualExternalRef>
    </xsl:template>
    
    <xsl:template match="SenseRelation">
        <xsl:choose>
            <xsl:when test="@relType">
                <xsl:element name="{concat('uby:',@relType)}">
                    <xsl:choose>
                        <xsl:when test="@target">
                            <xsl:attribute name="rdf:resource">
                                <xsl:value-of select="concat('#',@target)"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:when test="FormRepresentation">
                            <lemon:LexicalSense>
                                <lemon:isSenseOf>
                                    <lemon:LexicalEntry>
                                        <lemon:canonicalForm>
                                            <lemon:Form>
                                                <lemon:writtenRep>
                                                    <xsl:if test="FormRepresentation/@languageIdentifier">
                                                        <xsl:attribute name="xml:lang">
                                                            <xsl:if test="FormRepresentation/@languageIdentifier">
                                                                <xsl:value-of select="FormRepresentation/@languageIdentifier"/>
                                                            </xsl:if>
                                                        </xsl:attribute>
                                                    </xsl:if>
                                                    <xsl:if test="@xml:lang">
                                                        <xsl:attribute name="xml:lang">
                                                            <xsl:value-of select="@xml:lang"/>
                                                        </xsl:attribute>
                                                    </xsl:if>
                                                    <xsl:value-of select="FormRepresentation/@writtenForm"/>
                                                </lemon:writtenRep>
                                            </lemon:Form>
                                        </lemon:canonicalForm>
                                    </lemon:LexicalEntry>
                                </lemon:isSenseOf>
                            </lemon:LexicalSense>
                        </xsl:when>
                    </xsl:choose>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <lemon:senseRelation>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@target)"/>
                    </xsl:attribute>
                </lemon:senseRelation>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="SenseAxis">
        <rdf:Description>
            <xsl:attribute name="rdf:ID">
                <xsl:choose>
                    <xsl:when test="@senseOne">
                        <xsl:value-of select="@senseOne"/>
                    </xsl:when>
                    <xsl:when test="@synsetOne">
                        <xsl:value-of select="@synsetOne"/>
                    </xsl:when>
                </xsl:choose>
                
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="@senseAxisType">
                    <xsl:element name="{concat('uby:',@senseAxisType)}">
                        <xsl:attribute name="rdf:resource">
                            <xsl:choose>
                                <xsl:when test="@senseTwo">
                                    <xsl:value-of select="concat('#',@senseTwo)"/>
                                </xsl:when>
                                <xsl:when test="@synsetTwo">
                                    <xsl:value-of select="concat('#',@synsetTwo)"/>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:attribute>
                    </xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <lemon:senseRelation>
                        <xsl:attribute name="rdf:resource">
                            <xsl:choose>
                                <xsl:when test="@senseTwo">
                                    <xsl:value-of select="concat('#',@senseTwo)"/>
                                </xsl:when>
                                <xsl:when test="@synsetTwo">
                                    <xsl:value-of select="concat('#',@synsetTwo)"/>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:attribute>
                    </lemon:senseRelation>
                </xsl:otherwise>
            </xsl:choose>
        </rdf:Description>
    </xsl:template>
    
    <xsl:template match="SenseAxisRelation">
        <xsl:comment>Sense Axis Relations are not supported by lemon.</xsl:comment>
    </xsl:template>
    
    <xsl:template match="ConstraintSet">
        <!-- Yep, nothing to do here -->
    </xsl:template>
    
    <xsl:template match="Frequency">
        <xsl:if test="@corpus">
            <uby:corpus>
                <xsl:value-of select="@corpus"/>
            </uby:corpus>
        </xsl:if>
        <xsl:if test="@frequency">
            <uby:frequency>
                <xsl:value-of select="@frequency"/>
            </uby:frequency>
        </xsl:if>
        <xsl:if test="@generator">
            <uby:generator>
                <xsl:value-of select="@generator"/>
            </uby:generator>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="SemanticLabel">
        <uby:semanticLabel>
            <uby:SemanticLabel>
                <xsl:if test="@label">
                    <uby:label>
                        <xsl:value-of select="@label"/>
                    </uby:label>
                </xsl:if>
                <xsl:if test="@type">
                    <uby:type>
                        <xsl:value-of select="@type"/>
                    </uby:type>
                </xsl:if>
                <xsl:if test="@quantification">
                    <uby:quantification>
                        <xsl:value-of select="@quantification"/>
                    </uby:quantification>
                </xsl:if>
                <xsl:apply-templates select="MonolingualExternalRef"/>
            </uby:SemanticLabel>
        </uby:semanticLabel>
    </xsl:template>
</xsl:stylesheet>
