<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : lmf2lemon.xsl
    Created on : 09 August 2012, 16:40
    Author     : John McCrae
    Description: LMF to Lemon Transforms
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:lemon="http://www.monnet-project.eu/lemon#"
    xmlns:lexinfo="http://www.lexinfo.net/ontology/2.0/lexinfo#"
    xmlns:dcterms="http://purl.org/dc/terms/"
    xmlns:lmf="http://www.tagmatica.fr/lmf/DTD_LMF_REV_16.dtd#"
    xmlns:dc="http://purl.org/dc/terms/"
    version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <rdf:RDF>
            <xsl:apply-templates select="LexicalResource"/>
        </rdf:RDF>
    </xsl:template>

    <xsl:template match="LexicalResource">
        <xsl:apply-templates select="GlobalInformation"/>
        <xsl:apply-templates select="Lexicon"/>
        <xsl:apply-templates select="SenseAxis"/>
        <xsl:apply-templates select="TransferAxis"/>
        <xsl:apply-templates select="ContextAxis"/>
    </xsl:template>

    <xsl:template match="GlobalInformation">
        <rdf:Description rdf:about="#GlobalInformation">
            <xsl:apply-templates select="feat"/>
        </rdf:Description>
    </xsl:template>

    <xsl:template match="Lexicon">
        <lemon:Lexicon>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="feat/@att='language'">
                <lemon:language>
                    <xsl:value-of select="feat[@att='language']/@val"/>
                </lemon:language>
            </xsl:if>
            <xsl:apply-templates select="feat"/>
            <lemon:entry>
                <xsl:apply-templates select="LexicalEntry"/>
            </lemon:entry>
        </lemon:Lexicon>
        <xsl:apply-templates select="SubcategorizationFrame"/>
        <xsl:apply-templates select="MorphologicalPattern"/>
        <xsl:apply-templates select="ConstraintSet"/>
        <xsl:apply-templates select="MWEPattern"/>
        <xsl:apply-templates select="SemanticPredicate"/>
        <xsl:apply-templates select="SynSemCorrespondence"/>
        <xsl:apply-templates select="Synset"/>
        <xsl:apply-templates select="ConstraintSet"/>
    </xsl:template>

    <xsl:template match="LexicalEntry">
        <lemon:LexicalEntry>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/>
            <xsl:apply-templates select="Lemma"/>
            <xsl:apply-templates select="WordForm[position() > 1]"/>
            <xsl:apply-templates select="Stem"/>
            <xsl:apply-templates select="SyntacticBehaviour"/>
            <xsl:for-each select="Sense">
                <lemon:sense>
                    <xsl:apply-templates select="."/>
                </lemon:sense>
            </xsl:for-each>
            <xsl:apply-templates select="ListOfComponents"/>
            <xsl:apply-templates select="RelatedForm"/>
            <xsl:apply-templates select="TransformCategory"/>
            <xsl:if test="@morphologicalPatterns">
                <lemon:pattern>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@morphologicalPatterns)"/>
                    </xsl:attribute> 
                </lemon:pattern>
            </xsl:if>
            <xsl:if test="@mwePattern">
                <lemon:phraseRoot>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@mwePattern)"/>
                    </xsl:attribute>
                </lemon:phraseRoot>
            </xsl:if>
        </lemon:LexicalEntry>
    </xsl:template>

    <xsl:template match="Sense">
        <lemon:LexicalSense>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/>
            <xsl:apply-templates select="SenseRelation"/>
            <xsl:apply-templates select="Context"/>
            <xsl:apply-templates select="Equivalent"/>
            <xsl:apply-templates select="SubjectField"/>
            <xsl:apply-templates select="PredicativeRepresentation"/>
            <xsl:apply-templates select="SenseExample"/>
            <xsl:apply-templates select="Definition"/>
            <xsl:apply-templates select="MonolingualExternalRef"/>
            <xsl:for-each select="Sense">
                <lemon:subsense>
                    <xsl:apply-templates select="."/>
                </lemon:subsense>
            </xsl:for-each>
            <xsl:if test="@synset">
                <lemon:reference>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@synset)"/>
                    </xsl:attribute>
                </lemon:reference>
            </xsl:if>
        </lemon:LexicalSense>
    </xsl:template>

    <xsl:template match="Definition">
        <lemon:definition>
            <lemon:SenseDefinition>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="Statement"/>
                <xsl:apply-templates select="TextRepresentation"/>
            </lemon:SenseDefinition>
        </lemon:definition>
    </xsl:template>

    <xsl:template match="Statement">
        <!-- why is a statement different from a text representation?? --> 
        <lmf:Statement>
            <rdf:Description>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="TextRepresentation"/>
            </rdf:Description>
        </lmf:Statement>
    </xsl:template>

    <xsl:template match="TextRepresentation">
        <xsl:apply-templates select="feat"/>
    </xsl:template>

    <xsl:template match="Lemma">
        <lemon:canonicalForm>
            <lemon:Form>
                <xsl:apply-templates select="feat"/>
                <xsl:if test="../WordForm">
                    <!-- Some LMF document store the lemma representation on the first word form! -->
                    <xsl:apply-templates select="../WordForm[1]/feat"/>
                </xsl:if>
                <xsl:apply-templates select="FormRepresentation"/>
            </lemon:Form>
        </lemon:canonicalForm>
    </xsl:template>

    <xsl:template match="WordForm">
        <lemon:otherForm>
            <lemon:Form>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="FormRepresentation"/>
            </lemon:Form>
        </lemon:otherForm>
    </xsl:template>

    <xsl:template match="Stem">
        <lemon:abstractForm>
            <lemon:Form>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="FormRepresentation"/>
                <xsl:apply-templates select="GrammaticalFeatures"/>
            </lemon:Form>
        </lemon:abstractForm>
    </xsl:template>

    <xsl:template match="FormRepresentation">
        <xsl:apply-templates select="feat"/>
    </xsl:template>

    <xsl:template match="RelatedForm">
        <xsl:comment>Related form is not documented, this representation may be incorrect</xsl:comment>
        <lemon:abstractForm>
            <lemon:Form>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="FormRepresentation"/>
            </lemon:Form>
        </lemon:abstractForm>
     </xsl:template>

    <xsl:template match="ListOfComponents">
        <lemon:decomposition rdf:parseType="Collection">
            <xsl:for-each select="Component">
                <lemon:Component>
                    <xsl:apply-templates select="feat"/>
                    <lemon:element>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('#',@entry)"/>
                        </xsl:attribute>
                    </lemon:element>
                </lemon:Component>
            </xsl:for-each>
        </lemon:decomposition>
    </xsl:template>

    <xsl:template match="Equivalent">
        <lemon:equivalent>
            <lemon:LexicalSense>
                <lemon:isSenseOf>
                    <lemon:LexicalEntry>
                        <lemon:canonicalForm>
                            <lemon:Form>
                                <xsl:apply-templates select="feat[@att='writtenForm']"/>
                                <xsl:apply-templates select="TextRepresentation"/>
                            </lemon:Form>
                        </lemon:canonicalForm>
                    </lemon:LexicalEntry>
                </lemon:isSenseOf>
            </lemon:LexicalSense>
        </lemon:equivalent>
    </xsl:template>

    <xsl:template match="Context">
        <xsl:choose>
            <xsl:when test="feat[@att='sentence']/@val">
                <lexinfo:sentenceContext>
                    <xsl:value-of select="feat[@att='sentence']/@val"/>
                </lexinfo:sentenceContext>
            </xsl:when>
            <xsl:otherwise>
                <lmf:Context>
                    <rdf:Description>
                        <xsl:apply-templates select="feat"/>
                        <xsl:apply-templates select="TextRepresentation"/>
                    </rdf:Description>
                </lmf:Context>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="SubjectField">
        <xsl:choose>
            <xsl:when test="feat[@att='text']">
                <dc:subject><xsl:value-of select="feat[@att='text']/@val"/>
                    <xsl:if test="feat[@att='language']">
                        <xsl:attribute name="xml:lang">
                            <xsl:value-of select="feat[@att='language']/@val"/>
                        </xsl:attribute>
                    </xsl:if>
                </dc:subject>
            </xsl:when>
            <xsl:otherwise>
                <lmf:SubjectField>
                    <rdf:Description>
                        <xsl:apply-templates select="feat"/>
                        <xsl:apply-templates select="SubjectField"/>
                    </rdf:Description>
                </lmf:SubjectField>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="SyntacticBehaviour">
        <xsl:choose>
            <xsl:when test="@subcategorizationFrames">
                <xsl:variable name="sfs">
                    <xsl:value-of select="@subcategorizationFrameSets"/>
                </xsl:variable>
                <lemon:synBehavior>
                    <xsl:apply-templates select="//SubcategorizationFrame[@id=$sfs]"/>
                </lemon:synBehavior>
            </xsl:when>
            <xsl:when test="@subcategorizationFrameSets">
                <xsl:variable name="sfs">
                    <xsl:value-of select="@subcategorizationFrameSets"/>
                </xsl:variable>
                <xsl:apply-templates select="//SubcategorizationFrameSet[@id=$sfs]"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:comment>Syntactic Behaviour without link to subcategorization</xsl:comment>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="SubcategorizationFrameSet">
        <xsl:choose>
            <xsl:when test="@subcategorizationFrames">
                <xsl:call-template name="subcat-frames">
                    <xsl:with-param name="list" select="@subcategorizationFrames"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:comment>Subcategorization Frame set without subcategorizations</xsl:comment>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:apply-templates select="SynArgMap"/>
        <xsl:if test="@inherit">
            <xsl:comment>Inherit on subcategorization frame set</xsl:comment>
        </xsl:if>
        <xsl:if test="feat">
            <xsl:comment>Features on subcategorization frame set</xsl:comment>
        </xsl:if>
    </xsl:template>

    <xsl:template name="subcat-frames">
        <xsl:param name="list"/> 
        <xsl:variable name="newlist" select="concat(normalize-space($list), ' ')" /> 
        <xsl:variable name="first" select="substring-before($newlist, ' ')" /> 
        <xsl:variable name="remaining" select="substring-after($newlist, ' ')" /> 
        <lemon:synBehavior>
            <xsl:attribute name="rdf:resource" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                <xsl:value-of select="concat('#',$first)"/>
            </xsl:attribute>
        </lemon:synBehavior>
        <xsl:if test="$remaining">
            <xsl:call-template name="subcat-frames">
                <xsl:with-param name="list" select="$remaining" /> 
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template match="SubcategorizationFrame">
        <lemon:Frame>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/>
            <xsl:apply-templates select="SyntacticArgument"/>
            <xsl:apply-templates select="LexemeProperty"/>
            <xsl:if test="@inherit">
                <lmf:inherit><xsl:value-of select="@inherit"/></lmf:inherit>
            </xsl:if>
        </lemon:Frame>
    </xsl:template>

    <xsl:template match="LexemeProperty">
        <lmf:LexemeProperty>
            <rdf:Description>
                <xsl:apply-templates select="feat"/>
            </rdf:Description>
        </lmf:LexemeProperty>
    </xsl:template>
    
    <xsl:template match="SyntacticArgument">
        <xsl:choose>
            <xsl:when test="feat[@att='syntacticFunction']/@val">
                <xsl:variable name="synFunc">
                    <xsl:value-of select="feat[@att='syntacticFunction']/@val"/>
                </xsl:variable>
                <xsl:element name="{$synFunc}" namespace="http://lexinfo.net/ontology/2.0/lexinfo#">
                    <lemon:Argument>
                        <xsl:if test="@id">
                            <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:apply-templates select="feat"/>
                        <xsl:if test="@target">
                            <xsl:comment>Target ignored</xsl:comment>
                        </xsl:if>
                    </lemon:Argument>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <lemon:synArg>
                    <lemon:Argument>
                        <xsl:if test="@id">
                            <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:apply-templates select="feat"/>
                        <xsl:if test="@target">
                            <xsl:comment>Target ignored</xsl:comment>
                        </xsl:if>
                    </lemon:Argument>
                </lemon:synArg>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="SynArgMap">
        <lmf:SynArgMap>
            <rdf:Description>
                <lmf:arg1>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@arg1)"/>
                    </xsl:attribute>
                </lmf:arg1>
                <lmf:arg2>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@arg2)"/>
                    </xsl:attribute>
                </lmf:arg2>
                <xsl:apply-templates select="feat"/>
            </rdf:Description>
        </lmf:SynArgMap>
    </xsl:template>

    <xsl:template match="PredicativeRepresentation">
        <lmf:PredicativeRepresentation>
            <rdf:Description>
                <xsl:apply-templates select="feat"/>
                <lmf:predicate>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@predicate)"/>
                    </xsl:attribute>
                </lmf:predicate>
                <lmf:correspondences>
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat('#',@correspondences)"/>
                    </xsl:attribute>
                </lmf:correspondences>
            </rdf:Description>
        </lmf:PredicativeRepresentation>
    </xsl:template>

    <xsl:template name="semanticTypes">
        <xsl:param name="list"/> 
        <xsl:variable name="newlist" select="concat(normalize-space($list), ' ')" /> 
        <xsl:variable name="first" select="substring-before($newlist, ' ')" /> 
        <xsl:variable name="remaining" select="substring-after($newlist, ' ')" /> 
        <lmf:semanticTypes>
            <xsl:attribute name="rdf:resource">
                <xsl:value-of select="concat('#',$first)"/>
            </xsl:attribute>
        </lmf:semanticTypes>
        <xsl:if test="$remaining">
            <xsl:call-template name="semanticTypes">
                <xsl:with-param name="list" select="$remaining" /> 
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template match="SemanticPredicate">
        <lmf:SemanticPredicate>
            <rdf:Description>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="Definition"/>
                <xsl:apply-templates select="SemanticArgument"/>
                <xsl:apply-templates select="PredicateRelation"/>
                <xsl:if test="@semanticTypes">
                    <xsl:call-template name="semanticTypes">
                        <xsl:with-param name="list" select="@semanticTypes"/>
                    </xsl:call-template>
                </xsl:if>
            </rdf:Description>
        </lmf:SemanticPredicate>
    </xsl:template>

    <xsl:template match="SemanticArgument">
        <lemon:semArg>
            <lemon:Argument>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:apply-templates select="feat"/>
                <xsl:apply-templates select="ArgumentRelation"/>
                <xsl:if test="@semanticTypes">
                    <xsl:call-template name="semanticTypes">
                        <xsl:with-param name="list" select="@semanticTypes"/>
                    </xsl:call-template>
                </xsl:if>
            </lemon:Argument>
        </lemon:semArg>
    </xsl:template>

    <xsl:template name="targets">
        <xsl:param name="list"/> 
        <xsl:variable name="newlist" select="concat(normalize-space($list), ' ')" /> 
        <xsl:variable name="first" select="substring-before($newlist, ' ')" /> 
        <xsl:variable name="remaining" select="substring-after($newlist, ' ')" /> 
        <lmf:targets>
            <xsl:attribute name="rdf:resource">
                <xsl:value-of select="concat('#',$first)"/>
            </xsl:attribute>
        </lmf:targets>
        <xsl:if test="$remaining">
            <xsl:call-template name="targets">
                <xsl:with-param name="list" select="$remaining" /> 
            </xsl:call-template>
        </xsl:if>
    </xsl:template>


    <xsl:template match="ArgumentRelation">
        <lmf:ArgumentRelation>
            <rdf:Description>
                <xsl:apply-templates select="feat"/>
                <xsl:if test="@targets">
                    <xsl:call-template name="targets">
                        <xsl:with-param name="list" select="@targets"/>
                    </xsl:call-template>
                </xsl:if>
            </rdf:Description>
        </lmf:ArgumentRelation>
    </xsl:template>

    <xsl:template match="SenseRelation">
        <xsl:if test="@targets and feat[@att='type']/@val">
            <xsl:call-template name="senserel-targs">
                <xsl:with-param name="list" select="@targets"/>
                <xsl:with-param name="type" select="feat[@att='type']/@val"/>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="@targets and feat[@att='label']/@val">
            <xsl:call-template name="senserel-targs">
                <xsl:with-param name="list" select="@targets"/>
                <xsl:with-param name="type" select="feat[@att='label']/@val"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>


    <xsl:template name="senserel-targs">
        <xsl:param name="list"/> 
        <xsl:param name="type"/>
        <xsl:variable name="newlist" select="concat(normalize-space($list), ' ')" /> 
        <xsl:variable name="first" select="substring-before($newlist, ' ')" /> 
        <xsl:variable name="remaining" select="substring-after($newlist, ' ')" /> 
        <xsl:element name="{$type}" namespace="http://lexinfo.net/ontology/2.0/lexinfo#">
            <xsl:attribute name="rdf:resource" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                <xsl:value-of select="concat('#',$first)"/>
            </xsl:attribute>
        </xsl:element>
        <xsl:if test="$remaining">
            <xsl:call-template name="senserel-targs">
                <xsl:with-param name="list" select="$remaining" /> 
                <xsl:with-param name="type" select="$type" /> 
            </xsl:call-template>
        </xsl:if>
    </xsl:template>


    <xsl:template match="MorphologicalPattern">
        <lemon:MorphPattern>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/>
            <xsl:apply-templates select="TransformSet"/>
        </lemon:MorphPattern>
    </xsl:template>

    <xsl:template match="TransformSet">
        <lemon:transform>
            <lemon:MorphTransform>
                <xsl:if test="@id">
                    <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                        <xsl:value-of select="@id"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:apply-templates select="feat"/>
                <!-- rules shoule be validated -->
                <lemon:rule>
                    <xsl:for-each select="Process">
                        <xsl:choose>
                            <xsl:when test="feat[@att='operator']/@val='addLemma'">~</xsl:when>
                            <xsl:when test="feat[@att='operator']/@val='addAfter'">
                                <xsl:value-of select="feat[@att='stringValue']/@val"/>
                            </xsl:when>
                            <xsl:when test="feat[@att='operator']/@val='removeAfter'">./~</xsl:when>
                        </xsl:choose>
                    </xsl:for-each>
                </lemon:rule>
                <xsl:apply-templates select="GrammaticalFeatures"/>
            </lemon:MorphTransform>
        </lemon:transform>
    </xsl:template>

    <xsl:template match="GrammaticalFeatures">
        <lemon:generates>
            <lemon:Prototype>
                <xsl:if test="@id">
                    <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                        <xsl:value-of select="@id"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:apply-templates select="feat"/>                        
            </lemon:Prototype>
        </lemon:generates>
    </xsl:template>

    <xsl:template match="ConstraintSet">
        <xsl:comment>Constraint sets are not mapped. These should be modelled by the category selection.</xsl:comment>
    </xsl:template>

    <xsl:template match="SynSemCorrespondence">
        <xsl:comment>SynSem Correspondences use odd values in examples. No generic mapping please do it manually.</xsl:comment>
    </xsl:template>

    <xsl:template match="MWEPattern">
        <lemon:Node>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/> 
            <xsl:apply-templates select="MWENode[1]/feat"/> 
            <xsl:for-each select="MWENode[1]/MWEEdge">
                <lemon:edge>
                    <xsl:apply-templates select="MWENode"/>
                </lemon:edge>
            </xsl:for-each>
            <xsl:for-each select="MWENode[1]/MWELex">
                <lemon:edge>
                    <lemon:Node>
                        <xsl:if test="@id">
                            <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:apply-templates select="feat"/> 
                        <xsl:if test="@entry">
                            <lemon:leaf>
                                <xsl:attribute name="rdf:resource">
                                    <xsl:value-of select="concat('#',@entry)"/>
                                </xsl:attribute>
                            </lemon:leaf>
                        </xsl:if>
                    </lemon:Node>
                </lemon:edge>
            </xsl:for-each>
        </lemon:Node>
    </xsl:template>

    <xsl:template match="MWENode">
        <lemon:Node>
            <xsl:if test="@id">
                <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="feat"/> 
            <xsl:for-each select="MWEEdge">
                <lemon:edge>
                    <xsl:apply-templates select="MWENode"/>
                </lemon:edge>
            </xsl:for-each>
            <xsl:for-each select="MWELex">
                <lemon:edge>
                    <lemon:Node>
                        <xsl:if test="@id">
                            <xsl:attribute name="rdf:ID" namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                        </xsl:if>
                        <xsl:apply-templates select="feat"/> 
                        <xsl:if test="@entry">
                            <lemon:leaf>
                                <xsl:attribute name="rdf:resource">
                                    <xsl:value-of select="concat('#',@entry)"/>
                                </xsl:attribute>
                            </lemon:leaf>
                        </xsl:if>
                    </lemon:Node>
                </lemon:edge>
            </xsl:for-each>
        </lemon:Node>
    </xsl:template>

    <xsl:template match="SenseAxis">
        <rdf:Description>
            <!-- TODO Verify if sense axis is always of arity 2 -->
            <xsl:attribute name="rdf:about">
                <xsl:if test="@senses">
                    <xsl:value-of select="concat('#',substring-before(@senses,' '))"/>
                </xsl:if>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="SenseAxisRelation">
                    <!-- TODO -->
                </xsl:when>
                <xsl:otherwise>
                    <lemon:senseRelation>
                        <xsl:attribute name="rdf:resource">
                            <xsl:value-of select="concat('#',substring-after(@senses,' '))"/>
                        </xsl:attribute>
                    </lemon:senseRelation>
                </xsl:otherwise>
            </xsl:choose>
        </rdf:Description>
    </xsl:template>        

    <xsl:template match="feat">
        <xsl:if test="@att='partOfSpeech'">
            <lexinfo:partOfSpeech>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.lexinfo.net/ontology/2.0/lexinfo#',@val)"/>
                </xsl:attribute>
            </lexinfo:partOfSpeech>
        </xsl:if>
        <xsl:if test="@att='grammaticalNumber'">
            <lexinfo:number>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.lexinfo.net/ontology/2.0/lexinfo#',@val)"/>
                </xsl:attribute>
            </lexinfo:number>
        </xsl:if>
        <xsl:if test="@att='grammaticalGender'">
            <lexinfo:gender>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.lexinfo.net/ontology/2.0/lexinfo#',@val)"/>
                </xsl:attribute>
            </lexinfo:gender>
        </xsl:if>
        <xsl:if test="@att='grammaticalTense'">
            <lexinfo:tense>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.lexinfo.net/ontology/2.0/lexinfo#',@val)"/>
                </xsl:attribute>
            </lexinfo:tense>
        </xsl:if>
        <xsl:if test="@att='verbFormMood'">
            <lexinfo:verbFormMood>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat('http://www.lexinfo.net/ontology/2.0/lexinfo#',@val)"/>
                </xsl:attribute>
            </lexinfo:verbFormMood>
        </xsl:if>
        <xsl:if test="@att='image'">
            <lexinfo:image>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="@val"/>
                </xsl:attribute>
            </lexinfo:image>
        </xsl:if>
        <xsl:if test="@att='sound'">
            <lexinfo:sound>
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="@val"/>
                </xsl:attribute>
            </lexinfo:sound>
        </xsl:if>
        <xsl:if test="@att='gloss'">
            <lexinfo:gloss>
                <lemon:Defintion>
                    <lemon:value>
                        <xsl:value-of select="@val"/>
                    </lemon:value>
                </lemon:Defintion>
            </lexinfo:gloss>
        </xsl:if>
        <xsl:if test="@att='definition'">
            <lemon:definition>
                <lemon:Defintion>
                    <lemon:value>
                        <xsl:value-of select="@val"/>
                    </lemon:value>
                </lemon:Defintion>
            </lemon:definition>
        </xsl:if>
        <xsl:if test="@att='syntacticConstituent'">
            <lemon:constituent>
                <xsl:value-of select="@val"/>
            </lemon:constituent>
        </xsl:if>
        <xsl:if test="@att='pronounciation'">
            <lexinfo:pronounciation>
                <xsl:value-of select="@val"/>
            </lexinfo:pronounciation>
        </xsl:if>
        <xsl:if test="@att='phoneticForm'">
            <lexinfo:pronounciation>
                <xsl:value-of select="@val"/>
            </lexinfo:pronounciation>
        </xsl:if>
        <xsl:if test="@att='creationDate'">
            <dcterms:created>
                <xsl:value-of select="@val"/>
            </dcterms:created>
        </xsl:if>
        <xsl:if test="@att='domain'">
            <dcterms:subject>
                <xsl:value-of select="@val"/>
            </dcterms:subject>
        </xsl:if>
        <xsl:if test="@att='author'">
            <dcterms:creator>
                <xsl:value-of select="@val"/>
            </dcterms:creator>
        </xsl:if>
        <xsl:if test="@att='label'">
            <rdfs:label>
                <xsl:value-of select="@val"/>
            </rdfs:label>
        </xsl:if>
        <xsl:if test="@att='comment'">
            <rdfs:comment>
                <xsl:value-of select="@val"/>
            </rdfs:comment>
        </xsl:if>
        <xsl:if test="@att='past'">
            <lexinfo:pastTenseForm>
                <lemon:Form>
                    <lemon:writtenRep>
                        <xsl:value-of select="@val"/>
                    </lemon:writtenRep>
                </lemon:Form>
            </lexinfo:pastTenseForm>
        </xsl:if>
        <xsl:if test="@att='rank'">
            <lexinfo:rank><xsl:value-of select="@val"/></lexinfo:rank>
        </xsl:if>
        <xsl:if test="@att='graphicalSeparator'">
            <lemon:separator><xsl:value-of select="@val"/></lemon:separator>
        </xsl:if>
        <xsl:if test="@att='structureHead' and @val='yes'">
            <rdf:type rdf:resource="http://lexinfo.net/ontology/2.0/lexinfo#PhraseHead"/>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
