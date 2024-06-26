package com.coffee.GUI.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the Searchable interface that searches a List of String objects.
 * <p>
 * This implementation searches only the beginning of the words, and is not be optimized
 * <p>
 * for very large Lists.
 *
 * @author G. Cope
 */

public class StringSearchable implements Searchable<String, String> {


    private List<String> terms = new ArrayList<String>();


    /**
     * Constructs a new object based upon the parameter terms.
     *
     * @param terms The inventory of terms to search.
     */

    public StringSearchable(List<String> terms) {

        this.terms.addAll(terms);

    }


    @Override

    public Collection<String> search(String value) {

        List<String> founds = new ArrayList<String>();


        for (String s : terms) {

            if (s.toLowerCase().contains(value.toLowerCase())) {

                founds.add(s);

            }

        }

        return founds;

    }


}