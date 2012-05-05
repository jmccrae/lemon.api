/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.monnetproject.lemon.impl;

import eu.monnetproject.lemon.LemonFactory;
import eu.monnetproject.lemon.LemonModels;
import eu.monnetproject.lemon.LinguisticOntology;
import eu.monnetproject.lemon.model.LemonElementOrPredicate;
import eu.monnetproject.lemon.model.LexicalEntry;
import eu.monnetproject.lemon.model.Lexicon;
import eu.monnetproject.lemon.model.MorphPattern;
import java.io.Reader;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import net.lexinfo.LexInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author jmccrae
 */
public class LemonModelImplTest {
    
    public LemonModelImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLexica method, of class LemonModelImpl.
     */
    @Test
    public void testGetLexica() {
        System.out.println("getLexica");
        LemonModelImpl instance = new LemonModelImpl();
        instance.addLexicon(URI.create("file:test#lexicon"),"en");
        assertFalse(instance.getLexica().isEmpty());
    }


    /**
     * Test of getContext method, of class LemonModelImpl.
     */
    @Test
    public void testGetContext() {
        System.out.println("getContext");
        LemonModelImpl instance = new LemonModelImpl();
        URI expResult = null;
        URI result = instance.getContext();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFactory method, of class LemonModelImpl.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        LemonModelImpl instance = new LemonModelImpl();
        LemonFactory result = instance.getFactory();
    }

    /**
     * Test of addLexicon method, of class LemonModelImpl.
     */
    @Test
    public void testAddLexicon() {
        System.out.println("addLexicon");
        LemonModelImpl instance = new LemonModelImpl();
        instance.addLexicon(URI.create("file:test#lexicon"),"en");
        assertFalse(instance.getLexica().isEmpty());
    }

    /**
     * Test of removeLexicon method, of class LemonModelImpl.
     */
    @Test
    public void testRemoveLexicon() {
        System.out.println("removeLexicon");
        LemonModelImpl instance = new LemonModelImpl();
        instance.addLexicon(URI.create("file:test#lexicon"),"en");
        assertFalse(instance.getLexica().isEmpty());
        instance.removeLexicon(instance.getLexica().iterator().next());
        assertTrue(instance.getLexica().isEmpty());
    }

    /**
     * Test of addPattern method, of class LemonModelImpl.
     */
    @Test
    public void testAddPattern() {
        System.out.println("addPattern");
        LemonModelImpl instance = new LemonModelImpl();
        MorphPattern pattern = instance.getFactory().makeMorphPattern();
        instance.addPattern(pattern);
        assertFalse(instance.getPatterns().isEmpty());
    }

    /**
     * Test of getPatterns method, of class LemonModelImpl.
     */
    @Test
    public void testGetPatterns() {
        System.out.println("getPatterns");
        LemonModelImpl instance = new LemonModelImpl();
        MorphPattern pattern = instance.getFactory().makeMorphPattern();
        instance.addPattern(pattern);
        assertFalse(instance.getPatterns().isEmpty());
    }

    /**
     * Test of query method, of class LemonModelImpl.
     */
    @Test
    public void testQuery() {
        System.out.println("query");
        // will fail
    }

    /**
     * Test of merge method, of class LemonModelImpl.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        LemonModelImpl instance = new LemonModelImpl();
        final Lexicon lexicon = instance.addLexicon(URI.create("file:test#lexicon"),"en");
        final LexicalEntry from = LemonModels.addEntryToLexicon(lexicon, URI.create("file:test#lexicon/test1"), "test", null);
        from.addOtherForm(instance.getFactory().makeForm());
        final LexicalEntry to = LemonModels.addEntryToLexicon(lexicon, URI.create("file:test#lexicon/test2"), "test", null);
        instance.merge(from, to);
        assertFalse(to.getOtherForms().isEmpty());
    }

    /**
     * Test of purgeLexicon method, of class LemonModelImpl.
     */
    @Test
    public void testPurgeLexicon() {
        System.out.println("purgeLexicon");
        LinguisticOntology lo = new LexInfo();
        LemonModelImpl instance = new LemonModelImpl();
        Lexicon lexicon = instance.addLexicon(URI.create("file:test#lexicon"), "en");
        final LexicalEntry from = LemonModels.addEntryToLexicon(lexicon, URI.create("file:test#lexicon/test1"), "test", null);
        assertFalse(lexicon.getEntrys().isEmpty());
        instance.purgeLexicon(lexicon, lo);
        assertTrue(instance.getLexica().isEmpty());
        lexicon = instance.addLexicon(URI.create("file:test#lexicon"), "en");
        assertTrue(lexicon.getEntrys().isEmpty());
    }

    /**
     * Test of importLexicon method, of class LemonModelImpl.
     */
    @Test
    public void testImportLexicon() {
        System.out.println("importLexicon");
        LemonModelImpl instance2 = new LemonModelImpl();
        Lexicon lexicon = instance2.addLexicon(URI.create("file:test#lexicon"), "en");
        final LexicalEntry from = LemonModels.addEntryToLexicon(lexicon, URI.create("file:test#lexicon/test1"), "test", null);
        LinguisticOntology lo = new LexInfo();
        LemonModelImpl instance = new LemonModelImpl();
        instance.importLexicon(lexicon, lo);
        assertFalse(instance.getLexica().isEmpty());
        assertFalse(instance.getLexica().iterator().next().getEntrys().isEmpty());
    }
}
