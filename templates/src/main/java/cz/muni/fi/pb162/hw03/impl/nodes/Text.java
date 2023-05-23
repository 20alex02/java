package cz.muni.fi.pb162.hw03.impl.nodes;

import cz.muni.fi.pb162.hw03.template.model.TemplateModel;

public class Text extends Node {
    private final String text;

    /**
     * Creates new text node
     * @param text text
     */
    public Text(String text) {
        this.text = text;
    }
    @Override
    public void evaluate(TemplateModel model, StringBuilder builder) {
        builder.append(text);
    }
}
