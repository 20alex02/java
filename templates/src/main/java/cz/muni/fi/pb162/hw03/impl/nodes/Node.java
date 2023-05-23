package cz.muni.fi.pb162.hw03.impl.nodes;

import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

/**
 * Node class
 * @author Alex Popovic
 */
public abstract class Node {

    /**
     * Evaluates node and all children with given model and appends
     * evaluated values to builder
     * @param model model
     * @param builder builder
     */
    public abstract void evaluate(TemplateModel model, StringBuilder builder);
}
