package cz.muni.fi.pb162.hw03.impl.nodes;

import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

public class Name extends Node {
    private final String variableName;

    /**
     * Creates new name node
     * @param variableName variable name
     */
    public Name(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public void evaluate(TemplateModel model, StringBuilder builder) {
        builder.append(model.getAsString(variableName));
    }
}

